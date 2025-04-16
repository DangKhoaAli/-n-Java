package BLL;

import DAO.Book_Returned_DAO;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import javax.xml.crypto.dsig.keyinfo.RetrievalMethod;
import model.Book_Details_Returned;
import model.Payment_slip;

public class Book_Return_BLL {
    private Book_Returned_DAO book_Returned_DAO;
    
    public Book_Return_BLL() {
        book_Returned_DAO = new Book_Returned_DAO();
    }

    public List<String> getBookDetailsReturn(String loanSlipID) {
        if(loanSlipID == null || loanSlipID.isEmpty()) {
            return new ArrayList<>();
        }
        try {
            List<String> details = book_Returned_DAO.getAllBook_Return(loanSlipID);
            return (details != null) ? details : new ArrayList<>(); 
        } catch (SQLException e) {
            e.printStackTrace();
            return new ArrayList<>();
        }
    }

    public String addBookReturned(String paySlipID ,String bookID ,String status) {
        try {
            if(bookID == null || bookID.isEmpty()) {
                return "ID sách không được để trống!";
            }
            if(status == null || status.isEmpty()) {
                return "Trạng thái không được để trống!";
            }

            int num_page = book_Returned_DAO.getDamagedPage(bookID);
            if(num_page > Integer.parseInt(status)) {
                return "Số trang bị hỏng hiện tại không hợp lệ!";
            }
            
            book_Returned_DAO.addBook_Returned(paySlipID, bookID, Integer.parseInt(status));
            return "Đã thêm chi tiết phiếu trả mới thành công!";
        } catch (SQLException e) {
            e.printStackTrace();
            return "Lỗi SQL: " + e.getMessage();
        } catch (Exception e) {
            return "Lỗi không xác định: " + e.getMessage();
        }
    }

    public String updateBookReturn(String paySlipID, String bookID, String status, String phi) {
        try {
            if(bookID == null || bookID.isEmpty()) {
                return "ID sách không được để trống!";
            }
            if(status == null || status.isEmpty()) {
                return "Trạng thái không được để trống!";
            }
            
            int num_page = book_Returned_DAO.getDamagedPage(bookID);
            if(num_page > Integer.parseInt(status)) {
                return "Số trang bị hỏng hiện tại không hợp lệ!";
            }
            int trangcu = book_Returned_DAO.getStatus_Book(paySlipID, bookID);
            if(trangcu < Integer.parseInt(status)){
                
            }
            boolean exist = book_Returned_DAO.check(paySlipID, bookID);
            if(exist){
                book_Returned_DAO.updateBook_Returned(paySlipID, bookID, Integer.parseInt(status));
                return "Đã cập nhật chi tiết phiếu trả thành công!";
            } else {
                return "Không tìm thấy chi tiết phiếu trả để cập nhật!";
            }
            
        } catch (SQLException e){
            return "Lỗi SQL: " + e.getMessage();
        }
    }

    public String deleteBookReturn(String ID, String ID_Book){
        try{
            if (ID == null || ID.isEmpty()) {
                return "ID không được để trống!";
            }

            boolean exist = book_Returned_DAO.check(ID, ID_Book);
            if (exist) {
                book_Returned_DAO.deleteBook_Returned(ID, ID_Book);
                return "Đã xóa 1 chi tiết phiếu mượn";
            } else {
                return "Không tìm thấy chi tiết phiếu mượn để xóa!";
            }            
        } catch (SQLException e){
            return "Lỗi SQL :" + e.getMessage();
        }
    }

    public String updatePen_fee(String ID, String ID_Book){
        try {
            if(ID == null){
                return "ID phiếu mượn không được để trống";
            }
            if(ID_Book == null || ID_Book.isEmpty()) {
                return "ID sách không được để trống!";
            }
            book_Returned_DAO.updatePen_fee(ID, ID_Book);
            return "Đã cập nhật phí phạt của 1 chi tiết phiếu trả!";
        
        } catch (SQLException e){
            return "Lỗi SQL: " + e.getMessage();
        }
    }
}
