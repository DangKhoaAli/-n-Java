package BLL;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JOptionPane;

import DAO.Book_Details_DAO;
import DAO.Book_Returned_DAO;
import DAO.Borrow_Details_DAO;
import gui.BookDetails;

public class Borrow_Details_BLL {
    private Borrow_Details_DAO borrow_details_dao;
    private Book_Details_DAO book_details_dao;
    private Book_Return_BLL book_return_bll;
    
    public Borrow_Details_BLL() {
        borrow_details_dao = new Borrow_Details_DAO();
        book_details_dao = new Book_Details_DAO();
        book_return_bll = new Book_Return_BLL();
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
    public String updateBorrow_Details(String ID_Book_f, String ID_Book, String ID_Loan_slip){
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

            List<String> details = borrow_details_dao.getPay_IdByLoan_Id(ID_Loan_slip);
            for (String detail : details) {
                List<String> bookDetails = book_return_bll.getBookDetailsReturn(detail);
                for (String bookDetail : bookDetails) {
                    String[] bookData = bookDetail.split(";");
                    if (bookData[0].equals(ID_Book_f)) {
                        return "Sách đã được trả, không thể cập nhật!";
                        
                    }
                }
            }
            borrow_details_dao.updateBorrow_Details(ID_Book_f, ID_Book, ID_Loan_slip);
            book_details_dao.updateStatus_Book(ID_Book_f, "Hiện có"); 
            book_details_dao.updateStatus_Book(ID_Book, "Đang được mượn");
            return "Cập nhập phiếu mượn thành công!";
        } catch (SQLException e) {
            return "Lỗi thêm chi tiết phiếu mượn: " + e.getMessage();
        } catch (Exception e) {
            return "Lỗi không xác định: " + e.getMessage();
        }
    }
    
    // Xóa 1 chi tiết phiếu mượn
    public String deleteBorrow_Detail(String ID_Book, String ID_Loan_slip){
        try {
            List<String> details = borrow_details_dao.getPay_IdByLoan_Id(ID_Loan_slip);
            for (String detail : details) {
                List<String> bookDetails = book_return_bll.getBookDetailsReturn(detail);
                for (String bookDetail : bookDetails) {
                    String[] bookData = bookDetail.split(";");
                    if (bookData[0].equals(ID_Book)) {
                        return "Sách đã được trả, không thể xóa";
                        
                    }
                }
            }
            borrow_details_dao.deleteBorrow_Deatils(ID_Loan_slip, ID_Book);
            book_details_dao.updateStatus_Book(ID_Book, "Hiện có"); 
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
