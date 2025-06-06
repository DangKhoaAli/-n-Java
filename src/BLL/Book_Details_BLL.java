package BLL;

import DAO.Book_DAO;
import DAO.Book_Details_DAO;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class Book_Details_BLL {
    private Book_DAO book_DAO;
    private Book_Details_DAO book_Details_DAO;

    public Book_Details_BLL(){
        book_DAO = new Book_DAO();
        book_Details_DAO = new Book_Details_DAO();
    }

    public List<String> getBook(String ID){
        try {
            List<String> book_D = book_Details_DAO.getALL_Book_Details(ID);
            if (book_D == null || book_D.isEmpty()){
                return new ArrayList<>();
            }
            return book_D;
        } catch (SQLException e) {
            return new ArrayList<>();
        }
    }

    public String addBook(String ID, String ID_Book, String supplier, String year, String page_num, String status, String page_num_dame){
    try {
        if (status == null || status.isEmpty()){
            return "Status không được để trống!";
        }

        if (page_num_dame == null || page_num_dame.isEmpty()){
            return "Số trang hỏng không được để trống!";
        }

        
        if (supplier == null || supplier.isEmpty()){
            return "Nha cung cap khong duoc de trong!";
        }

        if (year == null){
            return "Nam xuat ban khong duoc de trong!";
        }

        if (Integer.parseInt(year) <=0){
            return "Nam xuat ban khong the nho hon 0!";
        }

        if (page_num == null){
            return "So trang khong duoc de trong!";
        }

        if (Integer.parseInt(page_num) <=0){
            return "So trang khong the nho hon 0!";
        }

        if (!status.equalsIgnoreCase("Hiện có") && !status.equalsIgnoreCase("Đang được mượn")){
            return "Status không hợp lệ! Vui lòng nhập (Hiện có, Đang được mượn)";
        }

        if(!page_num_dame.matches("\\d+")){
            return "Số trang hỏng không hợp lệ! Vui lòng nhập số nguyên dương.";
        }


        book_Details_DAO.addBook_Details(ID, ID_Book, supplier, Integer.parseInt(year), Integer.parseInt(page_num), status, Integer.parseInt(page_num_dame));
        book_DAO.updateBook_quan(book_Details_DAO.count_Details(ID_Book), ID_Book);
        return "Da them thanh cong 1 chi tiet sach!";

    } catch (SQLException e){
        e.printStackTrace();
        return "Loi SQL: " + e.getMessage();
    } catch (Exception e){
        return "Loi khong xac dinh: " + e.getMessage();
    }
}


    public String updateBook(String ID, String ID_Book, String supplier, String year, String page_num, String status, String page_num_dame){
        try {
            
            if (status == null || status.isEmpty()){
                return "Status không được để trống!";
            }

            if (page_num_dame == null || page_num_dame.isEmpty()){
                return "Số trang hỏng không được để trống!";
            }
            
            if (supplier == null || supplier.isEmpty()){
                return "Nha cung cap khong duoc de trong!";
            }

            if (year == null){
                return "Nam xuat ban khong duoc de trong!";
            }

            if (Integer.parseInt(year) <=0){
                return "Nam xuat ban khong the nho hon 0!";
            }

            if (page_num == null){
                return "So trang khong duoc de trong!";
            }

            if (Integer.parseInt(page_num) <=0){
                return "So trang khong the nho hon 0!";
            }

            if (!status.equalsIgnoreCase("Hiện có") && !status.equalsIgnoreCase("Đang được mượn")){
                return "Status không hợp lệ! Vui lòng nhập (Hiện có, Đang được mượn)";
            }

            if(!page_num_dame.matches("\\d+")){
                return "Số trang hỏng không hợp lệ! Vui lòng nhập số nguyên dương.";
            }

            book_Details_DAO.updateBook_Detail(ID, ID_Book, supplier, Integer.parseInt(year), Integer.parseInt(page_num), status, Integer.parseInt(page_num_dame));
            return "Da cap nhap thanh cong 1 chi tiet sach!";

        } catch (SQLException e){
            e.printStackTrace();
            return "Loi SQL: " + e.getMessage();
        } catch (Exception e){
            return "Loi khong xac dinh: " + e.getMessage();
        }
    }

    public String deleteBook(String ID, String ID_Book) {
        try {
            List<String> bookDetails = book_Details_DAO.getALL_Book_Details(ID_Book);
    
            for (String book : bookDetails) {
                if (book.split(";")[1].equalsIgnoreCase("Đang được mượn")) {
                    return "Không thể xóa chi tiết sách này vì sách đang được mượn!";
                }
            }
    
            book_Details_DAO.deleteBook_Details(ID, ID_Book);
    
            List<String> updatedBookDetails = book_Details_DAO.getALL_Book_Details(ID_Book);
            boolean allDamaged = !updatedBookDetails.isEmpty();
    
            for (String book : updatedBookDetails) {
                if (!book.split(";")[1].equalsIgnoreCase("Đã hỏng")) {
                    allDamaged = false;
                    break;
                }
            }
    
            if (allDamaged && !updatedBookDetails.isEmpty()) {
                book_DAO.deleteBook(ID_Book);
            }
    
            return "Đã xóa thành công 1 chi tiết sách!";
        } catch (SQLException e) {
            e.printStackTrace();
            return "Lỗi SQL: " + e.getMessage();
        } catch (Exception e) {
            return "Lỗi không xác định: " + e.getMessage();
        }
    }

    public String updateStatus_Book(String ID){
        try {
            book_Details_DAO.updateStatus_Book(ID, "Đã hỏng");
            return "Cập nhật trạng thái sách thành công!";
        } catch (SQLException e) {
            e.printStackTrace();
            return "Lỗi SQL: " + e.getMessage();
        } catch (Exception e) {
            return "Lỗi không xác định: " + e.getMessage();
        }
    }
    
}
