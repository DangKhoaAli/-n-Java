package DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import config.DatabaseConnection;

public class Book_Details_DAO {
    private Connection conn;

    public Book_Details_DAO(){
        conn = DatabaseConnection.getConnection();
    }

    // Lấy danh sách chi tiết sách
    public List<String> getALL_Book_Details(String ID) throws SQLException{
        String sql = "SELECT ID, supplier, year, page_num, status, num_page_dama FROM Book_Details "+
                      "WHERE ID_Book = ?";
        List<String> Book_Details = new ArrayList<>();
        try (PreparedStatement ps = conn.prepareStatement(sql)){
            ps.setString(1, ID);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Book_Details.add(rs.getString("ID") + ";" + rs.getString("supplier") + ";" + rs.getInt("year") + ";" + 
                                 rs.getInt("page_num") + ";" + rs.getString("status") + ";" + rs.getInt("num_page_dama"));

            }
        }
        return Book_Details;
    }

    public int count_Details(String ID) throws SQLException{
        String sql = "SELECT COUNT(*) FROM Book_Details WHERE ID_Book = ?";
    try (PreparedStatement ps = conn.prepareStatement(sql)) {
        ps.setString(1, ID);
        try (ResultSet rs = ps.executeQuery()) {
            if (rs.next()) {
                return rs.getInt(1);
            }
        }
    }
    return 0;
    }

    // Thêm 1 chi tiết sách
    public void addBook_Details(String ID, String ID_Book, String supplier, int year, int page_num, String status, int num_page_dama) throws SQLException{
        String sql = "INSERT INTO Book_Details (ID, ID_Book, supplier, year, page_num, status, num_page_dama) VALUES (?, ?, ?, ?, ?, ?, ?)";
         // Kiểm tra ID có tồn tại trong bảng Book_Details không
        try (PreparedStatement ps = conn.prepareStatement(sql)){
            ps.setString(1, ID);
            ps.setString(2, ID_Book);
            ps.setString(3, supplier);
            ps.setInt(4, year); 
            ps.setInt(5, page_num);
            ps.setString(6, status);
            ps.setInt(7, num_page_dama);
            ps.executeUpdate();

        }
    }

    // Cập nhập 1 chi tiết sách
    public void updateBook_Detail(String ID, String ID_Book, String supplier, int year, int page_num, String status, int num_page_dama) throws SQLException{
        String sql = "UPDATE Book_Details SET supplier = ?, year = ?, page_num = ?, status = ?, num_page_dama = ? WHERE ID = ? AND ID_Book = ?";
        try(PreparedStatement ps = conn.prepareStatement(sql)){
            ps.setString(1, supplier);
            ps.setInt(2, year);
            ps.setInt(3, page_num);
            ps.setString(4, status);
            ps.setInt(5, num_page_dama);
            ps.setString(6, ID);
            ps.setString(7, ID_Book);
            ps.executeUpdate();
        }
    }

    // Xóa 1 chi tiết sách
    public void deleteBook_Details(String ID, String ID_Book) throws SQLException{
        String sql = "UPDATE Book_Details SET status = ? WHERE ID = ? AND ID_Book = ?";
        try (PreparedStatement ps = conn.prepareStatement(sql)){
            ps.setString(1, "Đã hỏng");
            ps.setString(2, ID);
            ps.setString(3, ID_Book);
            ps.executeUpdate();
        }
    }

    // Xóa tất cả chi tiết sách
    public void deleteAllBook_Details(String ID_Book) throws SQLException{
        String sql = "UPDATE Book_Details SET status = ? WHERE ID_Book = ?";
        try (PreparedStatement ps = conn.prepareStatement(sql)){
            ps.setString(1, "Đã hỏng");
            ps.setString(2, ID_Book);
            ps.executeUpdate();
        }
    }

    public Boolean check_Book(String ID) throws SQLException{
        String sql = "SELECT 1 FROM Book_Details WHERE ID = ?";
        try (PreparedStatement ps = conn.prepareStatement(sql)){
            ps.setString(1, ID);
            ResultSet rs = ps.executeQuery();
            if (rs.next()){
                return true;
            }
        }
        return false;
    }

    public Boolean check_Status_Book(String ID) throws SQLException{
        String sql = "SELECT status FROM Book_Details WHERE ID = ?";
        try (PreparedStatement ps = conn.prepareStatement(sql)){
            ps.setString(1, ID);
            ResultSet rs = ps.executeQuery();
            if (rs.next()){
                return rs.getString("status").equalsIgnoreCase("Hiện có");
            }
        }
        return false;
    }

    public void updateStatus_Book(String ID, String status) throws SQLException{
        String sql = "UPDATE Book_Details SET status = ? WHERE ID = ?";
        try (PreparedStatement ps = conn.prepareStatement(sql)){
            ps.setString(1, status);
            ps.setString(2, ID);
            ps.executeUpdate();
        }
    }

    public int getStatus_Book(String ID) throws SQLException{
        String sql = "SELECT status FROM Book_Details WHERE ID = ?";
        try (PreparedStatement ps = conn.prepareStatement(sql)){
            ps.setString(1, ID);
            ResultSet rs = ps.executeQuery();
            if (rs.next()){
                return rs.getInt("status");
            }
        }
        return 0;
    }
}
