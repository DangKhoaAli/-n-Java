package BLL;

import DAO.Book_Details_DAO;
import DAO.Book_Returned_DAO;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import model.Payment_slip;

public class Book_Return_BLL {
    private Book_Returned_DAO book_Returned_DAO;
    private Book_Details_DAO bookDetailsDAO ;
    
    public Book_Return_BLL() {
        book_Returned_DAO = new Book_Returned_DAO();
        bookDetailsDAO = new Book_Details_DAO();
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
            bookDetailsDAO.updateStatus_Book(bookID, "Hiện có");
            return "Đã thêm chi tiết phiếu trả mới thành công!";
        } catch (SQLException e) {
            e.printStackTrace();
            return "Lỗi SQL: " + e.getMessage();
        } catch (Exception e) {
            return "Lỗi không xác định: " + e.getMessage();
        }
    }

    public String updateBookReturn(String paySlipID, String bookID, String bookID_old, String status, String phi) {
        try {
            if(bookID == null || bookID.isEmpty()) {
                return "ID sách không được để trống!";
            }
            if(status == null || status.isEmpty()) {
                return "Trạng thái không được để trống!";
            }
            
            int num_page = book_Returned_DAO.getDamagedPage(bookID_old);
            if (bookID.equals(bookID_old)) {
                float temp = Float.parseFloat(phi) / 1000;
                int Status = Integer.parseInt(status);
                if (Status < num_page - temp) {
                    return "Số trang bị hỏng hiện tại không hợp lệ!";
                }
                float pen_fee = (Status - num_page) * 1000.0f + Float.parseFloat(phi);
                System.out.println("pen_fee: " + pen_fee);
                book_Returned_DAO.updateBook(paySlipID, bookID, Status, pen_fee);
            } else {
                int temp_page = (int) (num_page - Float.parseFloat(phi) / 1000);
                int num_page1 = book_Returned_DAO.getDamagedPage(bookID); // Lấy số trang hiện tại của sách mới
                int Status = Integer.parseInt(status);
                if (Status < num_page1) {
                    return "Số trang bị hỏng hiện tại không hợp lệ!";
                }
                float pen_fee = (Status - num_page1) * 1000.0f;
                System.out.println("Phiếu phạt: " + pen_fee);
                book_Returned_DAO.updateBook_Returned(paySlipID, bookID, bookID_old, Status, pen_fee);
                bookDetailsDAO.updateStatus_Book(bookID_old, "Đang được mượn");
                bookDetailsDAO.updateNum_page(temp_page, bookID_old);

            }
            bookDetailsDAO.updateStatus_Book(bookID, "Hiện có");
            bookDetailsDAO.updateNum_page(Integer.parseInt(status), bookID);
            
            return "Đã cập nhật chi tiết phiếu trả thành công!";

            
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
                bookDetailsDAO.updateStatus_Book(ID_Book, "Đang được mượn");
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
