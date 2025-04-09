package DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import config.DatabaseConnection;
import model.Books;

public class Book_DAO {
    private Connection conn;

    public Book_DAO(){
        conn = DatabaseConnection.getConnection();
    }

    // Lấy danh sách Sách
    public List<Books> getAllBooks() throws SQLException{
        String sql = "SELECT * FROM Book ";
        List<Books> book = new ArrayList<>();
        try (PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()){
                while (rs.next()) {
                    book.add(new Books(rs.getString("ID"), rs.getString("name"), rs.getString("author"), 
                             rs.getString("category"), rs.getString("supplier"), rs.getInt("year"), 
                             rs.getInt("page_num"), rs.getInt("quanlity"), rs.getFloat("price"), 
                             rs.getFloat("loan_fee"), rs.getString("exist")));
                }
             }
        
        return book;
    }
    
    // Thêm 1 cuốn sách vào danh sách
    public void addBook(String ID, String name, String author, String category, String supplier, int year, int page_num, int quanlity, float price, float loan_fee) throws SQLException{
        String sql = "INSERT INTO Book (ID, name, author, category, supplier, year, page_num, quanlity, price, loan_fee, exist) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
        try (PreparedStatement ps = conn.prepareStatement(sql)){
            ps.setString(1, ID);
            ps.setString(2, name);
            ps.setString(3, author);
            ps.setString(4, category);
            ps.setString(5, supplier);
            ps.setInt(6, year);
            ps.setInt(7, page_num);
            ps.setInt(8, quanlity);
            ps.setFloat(9, price);
            ps.setFloat(10, loan_fee);
            ps.setString(11, "1");
            ps.executeUpdate();

        }
    }

    // Tìm kiếm Sách có trong danh sách theo tên
    public List<Books> searchBooks(String keyword) throws SQLException{
        String sql = "SELECT * FROM Book WHERE name LIKE ?";
        List<Books> book = new ArrayList<>();
        try (PreparedStatement ps = conn.prepareStatement(sql)){
            ps.setString(1, "%" + keyword + "%");
            ResultSet rs = ps.executeQuery();
            while(rs.next()){
                book.add(new Books(rs.getString("ID"), rs.getString("name"), rs.getString("author"), 
                         rs.getString("category"), rs.getString("supplier"), rs.getInt("year"), 
                         rs.getInt("page_num"), rs.getInt("quanlity"), rs.getFloat("price"), 
                         rs.getFloat("loan_fee"), rs.getString("exist")));
            }
        }
        return book;
    }

    public Boolean searchBookID(String keyword) throws SQLException{
        String sql = "SELECT * FROM Book WHERE ID LIKE ? LIMIT 1";
        try (PreparedStatement ps = conn.prepareStatement(sql)){
            ps.setString(1, keyword);
            ResultSet rs = ps.executeQuery();
            if (rs.next())
                return true;
        }
        return false;
    }


    // Cập nhập lại 1 cuốn sách
    public void updateBooks(String ID, String name, String author, String category, String supplier, int year, int page_num, int quanlity, float price, float loan_fee) throws SQLException{
        String sql = "UPDATE Book SET name = ?, author = ?, category = ?, supplier = ?, year = ?, page_num = ?, quanlity = ?, price = ?, loan_fee = ? WHERE ID = ?";
        try (PreparedStatement ps = conn.prepareStatement(sql)){
            ps.setString(1, name);
            ps.setString(2, author);
            ps.setString(3, category);
            ps.setString(4, supplier);
            ps.setInt(5, year);
            ps.setInt(6, page_num);
            ps.setInt(7, quanlity);
            ps.setFloat(8, price);
            ps.setFloat(9, loan_fee);
            ps.setString(10, ID);
            ps.executeUpdate();
        }
    }

    public void updateBook_quan(int quanlity, String ID) throws SQLException{
        String sql = "UPDATE Book SET quanlity = ? WHERE ID = ?";
        try (PreparedStatement ps = conn.prepareStatement(sql)){
            ps.setInt(1, quanlity);
            ps.setString(2, ID);
            ps.executeUpdate();
        }
    }

    public int searchQuanlity(String ID) throws SQLException{
        String sql = "SELECT * FROM Book WHERE ID = ?";
        try (PreparedStatement ps = conn.prepareStatement(sql)){
            ps.setString(1, ID);
            ResultSet rs = ps.executeQuery();
            if(rs.next()){
                return Integer.parseInt(rs.getString("quanlity"));
            }
            return -1;
        }
    }

    // Xóa 1 cuốn sách khỏi danh sách
    public void deleteBook_all(String ID) throws SQLException{
        String sql = "DELETE FROM Book WHERE ID = ?";
        try (PreparedStatement ps = conn.prepareStatement(sql)){
            ps.setString(1, ID);
            ps.executeUpdate();
        }
    }

    public void deleteBook(String ID) throws SQLException{
        String sql = "UPDATE Book SET exist = ? WHERE ID = ?";
        try(PreparedStatement ps = conn.prepareStatement(sql)){
            ps.setString(1, "0");
            ps.setString(2, ID);
            ps.executeUpdate();
        }
    }

}
