package DAO;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.mysql.cj.conf.ConnectionUrlParser.Pair;

import config.DatabaseConnection;

public class Book_Returned_DAO {
    private Connection conn;

    public Book_Returned_DAO(){
        conn = DatabaseConnection.getConnection();
    }

    // Lấy danh sách chi tiết phiếu trả
    public List<String> getAllBook_Return(String ID) throws SQLException{
        List<String> List_Returned = new ArrayList<>();
        String sql = "SELECT bd.ID AS Book_ID, b.name AS name, bdr.status AS Status, bdr.penalty_fee AS fee " +
                    "FROM Book_Details_Returned bdr " +
                    "JOIN Book_Details bd ON bdr.ID_Book = bd.ID " +
                    "JOIN Book b ON bd.ID_Book = b.ID " +
                    "WHERE bdr.ID_Payment_slip = ?";

        try (PreparedStatement ps = conn.prepareStatement(sql)){
            ps.setString(1, ID);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                List_Returned.add(rs.getString("Book_ID") + ";" + rs.getString("name") + ";"
                                  + rs.getString("Status") + ";" + rs.getString("fee"));
            }
        }
        return List_Returned;
    }

    // Thêm 1 chi tiết phiếu trả
    public void addBook_Returned(String ID_Pay_slip, String ID_Book, String status) throws SQLException{
        String sql = "INSERT INTO Book_Details_Returned (ID_Payment_slip, ID_Book, status, penalty_fee) VALUES (?, ?, ?, ?)";
        try (PreparedStatement ps = conn.prepareStatement(sql)){
            ps.setString(1, ID_Pay_slip);
            ps.setString(2, ID_Book);
            ps.setString(3, status);
            int damagedPage = getDamagedPage(ID_Book);
            float pen_fee = (float) (Integer.parseInt(status) - damagedPage) * 3000;
            ps.setBigDecimal(4, BigDecimal.valueOf(pen_fee));
            ps.executeUpdate();
        }

    }

    // Cập nhập 1 chi tiết phiếu trả
    public void updateBook_Returned(String ID) throws SQLException{
        
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

    public void deleteAll_Book(String ID_Pay_slip) throws SQLException{
        String sql = "DELETE FROM Book_Details_Returned WHERE ID_Payment_slip = ?";
        try (PreparedStatement ps = conn.prepareStatement(sql)){
            ps.setString(1, ID_Pay_slip);
            ps.executeUpdate();
        }
    }

    public int getDamagedPage(String bookDetailID) throws SQLException {
        String sql = "SELECT num_page_dama FROM Book_Details WHERE ID = ?";

        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, bookDetailID);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt("num_page_dama");
                }
            }
        }
        return 0;
    }

    public int getStatus_Book(String ID_Book) throws SQLException {
        String sql = "SELECT status FROM Book_Details_Returned WHERE ID_Book = ?";
        try (PreparedStatement ps = conn.prepareStatement(sql)){
            ps.setString(1, ID_Book);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return rs.getInt("status");
            }
        }
        return 0;
    }

    public float updatePen_fee(String ID_Book) throws SQLException {
        int damagedPage = getDamagedPage(ID_Book);
        int status = getStatus_Book(ID_Book);
        return (float) (status - damagedPage) * 3000;
    }

}
