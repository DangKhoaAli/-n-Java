package BLL;

import java.util.List;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;

import DAO.Borrow_Details_DAO;
import DAO.Loan_slip_DAO;
import DAO.Payment_slip_DAO;
import DAO.Reader_DAO;
import DAO.Staff_DAO;
import model.Loan_slip;

public class Loan_slip_BLL {
    private Loan_slip_DAO Loan_slip_DAO;
    private Reader_DAO reader;
    private Staff_DAO staff;
    private Payment_slip_DAO payment_slip_DAO;
    private Borrow_Details_BLL borrow_Details_Bll;

    public Loan_slip_BLL(){
        Loan_slip_DAO = new Loan_slip_DAO();
        reader = new Reader_DAO();
        staff = new Staff_DAO();
        payment_slip_DAO = new Payment_slip_DAO();
        borrow_Details_Bll = new Borrow_Details_BLL();

    }

    public List<Loan_slip> getLoan_slip(){
        try{
            List<Loan_slip> loan_slip = Loan_slip_DAO.getAllLoan_slips();
            if (loan_slip == null || loan_slip.isEmpty()){
                return new ArrayList<>();
            }
            return loan_slip;
        } catch (SQLException e){
            return new ArrayList<>();
        }
    }

    public Loan_slip searchLoan_slip(String key){
        try{
            Loan_slip loan_slip = Loan_slip_DAO.searchLoan_slip(key);
            return loan_slip == null ? null : loan_slip;
        } catch (SQLException e){
            return null;
        }
    }

    public String addLoan_slip(String ID, String ID_Reader, String ID_Staff, String so_luong, LocalDate Borrow_Date, LocalDate Expected_Date){
        try {
            if (ID == null || ID.isEmpty()){
                return "ID không được để trống!";
            }

            Loan_slip loan = searchLoan_slip(ID);
            if(loan != null){
                return "ID da ton tai!";
            }

            if (ID_Reader == null || ID.isEmpty()){
                return "ID độc giả không được để trống!";
            }

            Boolean existingReader = reader.searchReaderID(ID_Reader);
            if(existingReader == null){
                return "ID doc gia chua ton tai!";
            }

            if (ID_Staff == null || ID.isEmpty()){
                return "ID thu thu không được để trống!";
            }

            Boolean existingStaff = staff.searchStaffID(ID_Staff);
            if(existingStaff == null){
                return "ID thu thu chua ton tai!";
            }

            if(so_luong == null || so_luong.isEmpty()){
                return "So luong khong duoc de trong!";
            }
            int So_luong = Integer.parseInt(so_luong);
            
            if(So_luong < 0){
                return "So luong khong duoc am!";
            }

            if(So_luong > 5){
                return "So luong khong duoc lon hon 5!";
            }

            if (Borrow_Date.isAfter(LocalDate.now())) {
                return "Ngày mượn không thể là ngày trong tương lai.";
            }

            if (Expected_Date.isBefore(Borrow_Date)) {
                return "Ngày trả không thể trước ngày mượn.";
            }

            Loan_slip_DAO.addLoan_slip(ID, ID_Reader, ID_Staff, So_luong, Borrow_Date, Expected_Date);
            return "Đã thêm thành công 1 phiếu mượn";

        } catch (SQLException e){
            e.printStackTrace();
            return "Lỗi SQL: " + e.getMessage();
        } catch (Exception e){
            return "Lỗi không xác định: " + e.getMessage();
        }
    }

    public String updateLoan_slip(String ID, String ID_Reader, String ID_Staff, LocalDate Borrow_Date, LocalDate Expected_Date){
        try {
            if (ID == null || ID.isEmpty()){
                return "ID không được để trống!";
            }

            Loan_slip loan = searchLoan_slip(ID);
            if(loan == null){
                return "ID chua ton tai!";
            }

            if (ID_Reader == null || ID.isEmpty()){
                return "ID độc giả không được để trống!";
            }

            Boolean existingReader = reader.searchReaderID(ID_Reader);
            if(existingReader == null){
                return "ID doc gia chua ton tai!";
            }

            if (ID_Staff == null || ID.isEmpty()){
                return "ID thu thu không được để trống!";
            }

            Boolean existingStaff = staff.searchStaffID(ID_Staff);
            if(existingStaff == null){
                return "ID thu thu chua ton tai!";
            }

            if (Borrow_Date.isAfter(LocalDate.now())) {
                return "Ngày mượn không thể là ngày trong tương lai.";
            }

            if (Expected_Date.isBefore(Borrow_Date)) {
                return "Ngày trả không thể trước ngày mượn.";
            }

            Loan_slip_DAO.updateLoan_slip(ID, ID_Reader, ID_Staff, Borrow_Date, Expected_Date);
            return "Đã cập nhập thành công 1 phiếu mượn";

        } catch (SQLException e){
            e.printStackTrace();
            return "Lỗi SQL: " + e.getMessage();
        } catch (Exception e){
            return "Lỗi không xác định: " + e.getMessage();
        }
    }

    public String deleteLoan_slip(String ID) {
        try {
            if (ID == null || ID.isEmpty()) {
                return "ID không được để trống!";
            }

            Loan_slip existing = Loan_slip_DAO.searchLoan_slip(ID);
            if (existing == null ) {
                return "Không tìm thấy phieu muon với ID này.";
            }

            if (payment_slip_DAO.searchPayment_slipByID_Loan_slip(ID)) {
                return "Phiếu mượn này đã có phiếu trả, không thể xóa.";
            } else {
                borrow_Details_Bll.getBorrow_Details(ID);
                for (String book : borrow_Details_Bll.getBorrow_Details(ID)) {
                    String[] parts = book.split(";");
                    String ID_Book = parts[0];
                    borrow_Details_Bll.deleteBorrow_Detail(ID_Book, ID);
                }
                Loan_slip_DAO.deleteLoan_slip(ID);
                return "Xóa phiếu mượn thành công.";
            }
    

        } catch (SQLException e) {
            e.printStackTrace();
            return "Lỗi SQL: " + e.getMessage();
        } catch (Exception e) {
            return "Lỗi không xác định: " + e.getMessage();
        }
    }

    public String update_fee(String ID, LocalDate Borrow_Date, LocalDate Expected_Date){
        try{
            Loan_slip_DAO.updateLoan_fee(ID, Borrow_Date, Expected_Date);
            return "Cập nhập phí mượn thành công!";
        } catch (SQLException ex){
            ex.printStackTrace();
            return "Lỗi SQL: " + ex.getMessage();
        } catch (Exception ex){
            return "Lỗi không xác định: " + ex.getMessage();
        }
    }

    public String update_Quan(String ID){
        try{
            Loan_slip_DAO.update_Quan(ID);
            return "Cập nhập số lượng sách thành công!";
        } catch (SQLException ex){
            ex.printStackTrace();
            return "Lỗi SQL: " + ex.getMessage();
        } catch (Exception ex){
            return "Lỗi không xác định: " + ex.getMessage();
        }
    }
    
}
