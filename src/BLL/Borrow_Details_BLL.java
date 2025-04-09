package BLL;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import DAO.Book_Details_DAO;
import DAO.Borrow_Details_DAO;
import DAO.Loan_slip_DAO;
import model.Loan_slip;

public class Borrow_Details_BLL {
    private Borrow_Details_DAO borrow_details_dao;
    private Book_Details_DAO book_details_dao;
    
    public Borrow_Details_BLL() {
        borrow_details_dao = new Borrow_Details_DAO();
        book_details_dao = new Book_Details_DAO();
    }
    
    //Lấy danh sách chi tiết phiếu của phiếu mượn
    public List<String> getBorrow_Details(String ID){
        try{
            List<String> details = borrow_details_dao.getBorrow_Details(ID);
            if (details == null || details.isEmpty()){
                return new ArrayList<>();
            }
            return details;
        } catch (SQLException e){
            return new ArrayList<>();
        }
    }
    
    // Thêm 1 chi tiết phiếu mượn
    public String addBorrow_Detail(String ID_Book, String ID_Loan_slip){
        try {
            if (ID_Book == null || ID_Book.isEmpty()){
                return "ID sách không được để trống!";
            }
            
            if (ID_Loan_slip == null || ID_Loan_slip.isEmpty()){
                return "ID phiếu mượn không được để trống!";
            }
            
            if(!book_details_dao.check_Book(ID_Book)){
                return "ID sách không tồn tại!";
            }

            if(!book_details_dao.check_Status_Book(ID_Book)){
                return "Sách hiện không thể mượn! Vui lòng kiểm tra lại!";
            }
            
            borrow_details_dao.addBorrow_Detail(ID_Book, ID_Loan_slip);
            return "Thêm chi tiết phiếu mượn thành công!";
        } catch (SQLException e) {
            return "Lỗi thêm chi tiết phiếu mượn: " + e.getMessage();
        } catch (Exception e) {
            return "Lỗi không xác định: " + e.getMessage();
        }
        
    }
    
    // Cập nhập 1 chi tiết phiếu mượn
    public String updateBorrow_Details(String ID_Book_f, String ID_Loan_slip_f, String ID_Book, String ID_Loan_slip){
        try {
            if (ID_Book == null || ID_Book.isEmpty()){
                return "ID sách không được để trống!";
            }
            
            if(!book_details_dao.check_Book(ID_Book)){
                return "ID sách không tồn tại!";
            }

            if(!book_details_dao.check_Status_Book(ID_Book)){
                return "Sách hiện không thể mượn! Vui lòng kiểm tra lại!";
            }
            
            borrow_details_dao.addBorrow_Detail(ID_Book, ID_Loan_slip);
            return "Thêm chi tiết phiếu mượn thành công!";
        } catch (SQLException e) {
            return "Lỗi thêm chi tiết phiếu mượn: " + e.getMessage();
        } catch (Exception e) {
            return "Lỗi không xác định: " + e.getMessage();
        }
    }
    
    // Xóa 1 chi tiết phiếu mượn
    public String deleteBorrow_Detail(String ID_Book, String ID_Loan_slip){
        try {
            
            borrow_details_dao.deleteBorrow_Deatils(ID_Loan_slip, ID_Book);
            return "Xóa chi tiết phiếu mượn thành công!";
        } catch (SQLException e) {
            e.printStackTrace();
            return "Lỗi SQL: " + e.getMessage();
        } catch (Exception e) {
            e.printStackTrace();
            return "Lỗi không xác định: " + e.getMessage();
        }
    }
}
