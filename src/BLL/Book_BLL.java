package BLL;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import DAO.Book_DAO;
import DAO.Book_Details_DAO;
import model.Books;

public class Book_BLL {
    private Book_DAO book_DAO;
    private Book_Details_DAO book_Details_DAO;

    public Book_BLL(){
        book_DAO = new Book_DAO();
        book_Details_DAO = new Book_Details_DAO();
    }

    public List<Books> getBook(){
        try {
            List<Books> books = book_DAO.getAllBooks();
            if (books == null || books.isEmpty()){
                return new ArrayList<>();
            }
            return books;
        } catch (SQLException e) {
            return new ArrayList<>();
        }
    }

    public List<Books> searchBooks(String key){
        try{
            List<Books> books = book_DAO.searchBooks(key);
            return books == null ? new ArrayList<>() : books;
        } catch (SQLException e){
            return new ArrayList<>();
        }
    }

    public String addBook(String ID, String name, String author, String categoty, String supplier, String year, String page_num, String quanlity, String price, String loan_fee){
        try {
            if (ID == null || ID.isEmpty() ) {
                return "ID không được để trống!";
            }
            
            Boolean existingReaders = book_DAO.searchBookID(ID);
            if (existingReaders) {
                return "ID sách này đã tồn tại!";
            }

            if (name == null || name.isEmpty()){
                return "Tên sách không được để trống!";
            }

            if (author == null || author.isEmpty()){
                return "Ten tac gia khong duoc de trong!";
            }

            if (categoty == null || categoty.isEmpty()){
                return "The loai khong duoc de trong!";
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

            if (quanlity == null){
                return "So luong khong duoc de trong!";
            }

            if (Integer.parseInt(quanlity) <=0){
                return "So luong khong the nho hon 0!";
            }

            if (price == null){
                return "Gia sach khong duoc de trong!";
            }

            if (Float.parseFloat(price) <=0){
                return "Gia sach khong the nho hon 0!";
            }

            if (loan_fee == null){
                return "Gia muon sach khong duoc de trong!";
            }

            if (Float.parseFloat(loan_fee) <=0){
                return "Gia muon sach khong the nho hon 0!";
            }

            book_DAO.addBook(ID, name, author, categoty, supplier, Integer.parseInt(year), Integer.parseInt(page_num), Integer.parseInt(quanlity), Float.parseFloat(price), Float.parseFloat(loan_fee));
            return "Da them thanh cong sach!";

        } catch (SQLException e){
            e.printStackTrace();
            return "Loi SQL: " + e.getMessage();
        } catch (Exception e){
            return "Loi khong xac dinh: " + e.getMessage();
        }
    }

    public String updateBooks(String ID, String name, String author, String categoty, String supplier, String year, String page_num, String quanlity, String price, String loan_fee){
        try {
            if (ID == null || ID.isEmpty() ) {
                return "ID không được để trống!";
            }
            
            Boolean existingBook = book_DAO.searchBookID(ID);
            if (!existingBook) {
                return "ID sách này không tồn tại!";
            }

            if (name == null || name.isEmpty()){
                return "Tên sách không được để trống!";
            }

            if (author == null || author.isEmpty()){
                return "Ten tac gia khong duoc de trong!";
            }

            if (categoty == null || categoty.isEmpty()){
                return "The loai khong duoc de trong!";
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

            if (quanlity == null){
                return "So luong khong duoc de trong!";
            }

            if (Integer.parseInt(quanlity) <=0){
                return "So luong khong the nho hon 0!";
            }

            if (price == null){
                return "Gia sach khong duoc de trong!";
            }

            if (Float.parseFloat(price) <=0){
                return "Gia sach khong the nho hon 0!";
            }

            if (loan_fee == null){
                return "Gia muon sach khong duoc de trong!";
            }

            if (Float.parseFloat(loan_fee) <=0){
                return "Gia muon sach khong the nho hon 0!";
            }

            book_DAO.updateBooks(ID, name, author, categoty, supplier, Integer.parseInt(year), Integer.parseInt(page_num), Integer.parseInt(quanlity), Float.parseFloat(price), Float.parseFloat(loan_fee));
            return "Da cap nhap thanh cong sach!";

        } catch (SQLException e){
            e.printStackTrace();
            return "Loi SQL: " + e.getMessage();
        } catch (Exception e){
            return "Loi khong xac dinh: " + e.getMessage();
        }
    }

    public String deleteBook(String ID){
        try {
            if (ID == null || ID.isEmpty()) {
                return "ID không được để trống!";
            }

            Boolean existingReaders = book_DAO.searchBookID(ID);
            if (!existingReaders) {
                return "Không tìm thấy sach với ID này.";
            }
    
            // Xóa độc giả khỏi cơ sở dữ liệu
            book_DAO.deleteBook(ID);
            // Xóa tất cả chi tiết sách liên quan đến ID_Book
            book_Details_DAO.deleteAllBook_Details(ID);
            return "Xóa sach thành công.";
        } catch (SQLException e) {
            e.printStackTrace();
            return "Lỗi SQL: " + e.getMessage();
        } catch (Exception e) {
            return "Lỗi không xác định: " + e.getMessage();
        }
    }
}
