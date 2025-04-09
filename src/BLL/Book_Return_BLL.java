package BLL;

import DAO.Book_Returned_DAO;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import javax.xml.crypto.dsig.keyinfo.RetrievalMethod;
import model.Book_Details_Returned;

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

    public String addBookReturned(String paySlipID ,String bookID ,String status ,String penFee) {
        try {
            if(bookID == null || bookID.isEmpty()) {
                return "ID sách không được để trống!";
            }
            if(status == null || status.isEmpty()) {
                return "Trạng thái không được để trống!";
            }
            if(penFee == null || penFee.isEmpty()) {
                return "Phí trễ hạn không được để trống!";
            }
            book_Returned_DAO.addBook_Returned(paySlipID, bookID, status, penFee);
            return "Đã thêm chi tiết phiếu trả mới thành công!";
        } catch (SQLException e) {
            e.printStackTrace();
            return "Lỗi SQL: " + e.getMessage();
        } catch (Exception e) {
            return "Lỗi không xác định: " + e.getMessage();
        }
    }

    public String updateBookReturn(String paySlipID, String bookID, String status, String penFee) {
        try {
            if()
        }
    }
}
