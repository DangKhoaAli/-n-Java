package DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import config.DatabaseConnection;

public class Book_Returned_DAO {
    private Connection conn;

    public Book_Returned_DAO(){
        conn = DatabaseConnection.getConnection();
    }

    // Lấy danh sách chi tiết phiếu trả
    public List<String> getAllBook_Return(String ID) throws SQLException{
        List<String> List_Returned = new ArrayList<>();
        String sql = "SELECT bd.ID AS Book_ID, b.name AS name, bdr.status AS Status, bdr.penalty_fee AS fee"+
                     "FROM Book_Details_Returned bdr "+
                     "JOIN Book_Details bd ON bdr.ID_Book = bd.ID "+
                     "JOIN Book b ON bd.ID_Book = b. ID "+
                     "WHERE bdr.ID_Loan_slip = ?";
        try (PreparedStatement ps = conn.prepareStatement(sql)){
            ps.setString(1, ID);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                List_Returned.add(rs.getString("Book_ID") + "-" + rs.getString("name") + "-"
                                  + rs.getString("Status") + "-" + rs.getString("fee"));
            }
        }
        return null;
    }

    // Thêm 1 chi tiết phiếu trả
    public void addBook_Returned(String ID_Pay_slip, String ID_Book, String status, String pen_fee) throws SQLException{
        String sql = "INSERT INTO Book_Details_Returned (ID_Payment_slip, ID_Book, status, penalty_fee) VALUES (?, ?, ?, ?)";
        try (PreparedStatement ps = conn.prepareStatement(sql)){
            ps.setString(1, ID_Pay_slip);
            ps.setString(2, ID_Book);
            ps.setString(3, status);
            ps.setString(4, pen_fee);
            ps.executeUpdate();
        }

    }

    // Cập nhập 1 chi tiết phiếu trả
    public void updateBook_Returned(String ID_) throws SQLException{
        
    }

    // Xóa chi tiết phiếu trả
    public void deleteBook_Returned(String ID_Pay_slip, String ID_Book) throws SQLException{
        String sql = "DELETE FROM Book_Details_Returned WHERE ID_Payment_slip = ? AND ID_Book = ?";
        try (PreparedStatement ps = conn.prepareStatement(sql)){
            ps.setString(1, ID_Pay_slip);
            ps.setString(2, ID_Book);
            ps.executeUpdate();
        }
    }
}
