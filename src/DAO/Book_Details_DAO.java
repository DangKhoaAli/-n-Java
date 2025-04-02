package DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.mysql.cj.protocol.Resultset;

import config.DatabaseConnection;

public class Book_Details_DAO {
    private Connection conn;

    public Book_Details_DAO(){
        conn = DatabaseConnection.getConnection();
    }

    // Lấy danh sách chi tiết sách
    public List<String> getALL_Book_Details(String ID) throws SQLException{
        String sql = "SELECT ID, status, num_page_dama FROM Book_Details "+
                      "WHERE ID_Book = ?";
        List<String> Book_Details = new ArrayList<>();
        try (PreparedStatement ps = conn.prepareStatement(sql)){
            ps.setString(1, ID);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Book_Details.add(rs.getString("ID") + ";" + rs.getString("status") + ";" + rs.getInt("num_page_dama"));

            }
        }
        return Book_Details;
    }

    // Thêm 1 chi tiết sách
    public void addBook_Details(){
        
    }

    // Cập nhập 1 chi tiết sách
    public void updateBook_Detail(String ID, String status, int num_page_dama) throws SQLException{
        String sql = "UPDATE Book_Details SET status = ?, num_page_dama = ? WHERE ID = ?";
        try(PreparedStatement ps = conn.prepareStatement(sql)){
            ps.setString(1, status);
            ps.setInt(2, num_page_dama);
            ps.setString(3, ID);
            ps.executeUpdate();
        }
    }

    // Xóa 1 chi tiết sách
    public void deleteBook_Details(String ID) throws SQLException{
        String sql = "DELETE FROM Book_Details WHERE ID = ?";
        try (PreparedStatement ps = conn.prepareStatement(sql)){
            ps.setString(1, ID);
            ps.executeUpdate();
        }
    }
}
