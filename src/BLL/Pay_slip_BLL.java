package BLL;

import java.sql.SQLException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import DAO.Book_Details_DAO;
import DAO.Book_Returned_DAO;
import DAO.Loan_slip_DAO;
import DAO.Payment_slip_DAO;
import DAO.Staff_DAO;
import model.Loan_slip;
import model.Payment_slip;

public class Pay_slip_BLL {
    private Book_Details_DAO book_details_dao;
    private Book_Returned_DAO book_return_dao;
    private Payment_slip_DAO payment_slip_dao;
    private Staff_DAO staff;
    private Loan_slip_DAO loan_slip_dao;

    public Pay_slip_BLL() {
        book_details_dao = new Book_Details_DAO();
        book_return_dao = new Book_Returned_DAO();
        payment_slip_dao = new Payment_slip_DAO();
        staff = new Staff_DAO();
        loan_slip_dao = new Loan_slip_DAO();
    }

    public List<Payment_slip> getPaymentSlip() {
        try {
            List<Payment_slip> paymentSlip = payment_slip_dao.getAllPayment_slips();
            if (paymentSlip == null || paymentSlip.isEmpty()) {
                return new ArrayList<>();
            }
            return paymentSlip;
        } catch (SQLException e) {
            return new ArrayList<>();
        }
    }

    public Payment_slip search_Pay(String ID){
        try {
            Payment_slip paymentSlip = payment_slip_dao.searchPayment_slip(ID);
            return paymentSlip == null ? null : paymentSlip;
        } catch (SQLException e){
            return null;
        }
    }

    public String add_Pay(String ID, String ID_Loan_slip, String ID_Staff, String so_luong, String payment_Date) {
        try {
            if (ID == null || ID.isEmpty()) {
                return "ID không được để trống!";
            }

            Payment_slip paymentSlip = search_Pay(ID);
            if (paymentSlip != null) {
                return "ID đã tồn tại!";
            }

            if (ID_Loan_slip == null || ID_Loan_slip.isEmpty()) {
                return "ID phiếu mượn không được để trống!";
            }

            if (ID_Staff == null || ID_Staff.isEmpty()) {
                return "ID nhân viên không được để trống!";
            }

            Boolean existingStaff = staff.searchStaffID(ID_Staff);
            if (existingStaff == null) {
                return "ID nhân viên không tồn tại!";
            }

            int So_luong = Integer.parseInt(so_luong);
            if (So_luong <= 0) {
                return "Số lượng không được âm!";
            }

            int count = loan_slip_dao.count_Details(ID_Loan_slip);
            if (So_luong > count) {
                return "Số lượng sách trả không thể nhiều hơn số lượng sách mượn!";
            }

            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
            LocalDate paymentDate = LocalDate.parse(payment_Date, formatter);
            if(paymentDate.isBefore(loan_slip_dao.getBorrowDate(ID_Loan_slip))){
                return "Ngày trả không thể trước ngày mượn!";
            }

            payment_slip_dao.addPayment_slip(ID, ID_Loan_slip, ID_Staff, So_luong, paymentDate);
            return "Thêm phiếu trả thành công!";
        } catch (SQLException e) {
            return "Lỗi thêm phiếu trả: " + e.getMessage();
        } catch (Exception e) {
            return "Lỗi không xác định: " + e.getMessage();
        }
    }
    
    public String update_Pay(String ID, String ID_Loan_slip, String ID_Staff, String payment_Date) {
        try {
            if (ID == null || ID.isEmpty()) {
                return "ID không được để trống!";
            }

            Payment_slip paymentSlip = search_Pay(ID);
            if (paymentSlip == null) {
                return "ID không tồn tại!";
            }

            if (ID_Loan_slip == null || ID_Loan_slip.isEmpty()) {
                return "ID phiếu mượn không được để trống!";
            }

            Loan_slip loanSlip = loan_slip_dao.searchLoan_slip(ID_Loan_slip);
            if (loanSlip == null) {
                return "ID phiếu mượn không tồn tại!";
            }

            if (ID_Staff == null || ID_Staff.isEmpty()) {
                return "ID nhân viên không được để trống!";
            }

            Boolean existingStaff = staff.searchStaffID(ID_Staff);
            if (existingStaff == null) {
                return "ID nhân viên không tồn tại!";
            }

            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
            LocalDate paymentDate = LocalDate.parse(payment_Date, formatter);

            payment_slip_dao.updatePayment_slip(ID, ID_Loan_slip, ID_Staff, paymentDate);
            return "Cập nhật phiếu trả thành công!";
        } catch (SQLException e) {
            return "Lỗi cập nhật phiếu trả: " + e.getMessage();
        } catch (Exception e) {
            return "Lỗi không xác định: " + e.getMessage();
        }

    }

    public String delete_Pay(String ID, String ID_Loan_slip, String So_luong) {
        try {
            if (ID == null || ID.isEmpty()) {
                return "ID không được để trống!";
            }

            Payment_slip paymentSlip = search_Pay(ID);
            if (paymentSlip == null) {
                return "ID không tồn tại!";
            }

            int so_luong = Integer.parseInt(So_luong);
            List<String> bookDetails = book_return_dao.getAllBook_Return(ID);
            for (int i=0; i<so_luong; i++){
                String [] temp = bookDetails.get(i).split(";");
                book_return_dao.deleteBook_Returned(ID, temp[0]);
                book_details_dao.updateStatus_Book(ID_Loan_slip, "Đang được mượn");
            }

            payment_slip_dao.deletePayment_slip(ID);
            return "Xóa phiếu trả thành công!";
        } catch (SQLException e) {
            return "Lỗi xóa phiếu trả: " + e.getMessage();
        } catch (Exception e) {
            return "Lỗi không xác định: " + e.getMessage();
        }
    }




}
