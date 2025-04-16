package DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import config.DatabaseConnection;

public class Borrow_Details_DAO {
    private Connection conn;
    
    public Borrow_Details_DAO() {
        conn = DatabaseConnection.getConnection();
    }

    //Lấy danh sách chi tiết phiếu của phiếu mượn
    public List<String> getBorrow_Details(String ID) throws SQLException{
        List<String> List_Borrow = new ArrayList<>();
        String sql = "SELECT bd.ID AS Book_ID, b.name AS Name, b.loan_fee AS Loan_fee "+
                     "FROM Borrowed_Book_Details bbd "+
                     "JOIN Book_Details bd ON bbd.ID_Book = bd.ID "+
                     "JOIN Book b ON bd.ID_Book = b.ID "+
                     "WHERE bbd.ID_Loan_slip = ?";
        try (PreparedStatement ps = conn.prepareStatement(sql)){
            ps.setString(1, ID);
            ResultSet rs = ps.executeQuery();
            while(rs.next()){
                List_Borrow.add(rs.getString("Book_ID") + ";" + rs.getString("Name")
                                + ";" + rs.getFloat("Loan_fee"));

            }
        } 
        return List_Borrow;
    }

    // Thêm 1 chi tiết phiếu mượn
    public void addBorrow_Detail(String ID_Book, String ID_Loan_slip) throws SQLException{
        String sql = "INSERT INTO borrowed_book_details (ID_Book, ID_Loan_slip) VALUES (?, ?)";
        try(PreparedStatement ps = conn.prepareStatement(sql)){
            ps.setString(2, ID_Loan_slip);
            ps.setString(1, ID_Book);
            ps.executeUpdate();
        }
    }

    // Cập nhập 1 chi tiết phiếu mượn
    public void updateBorrow_Details(String ID_Book_f, String ID_Book, String ID_Loan_slip) throws SQLException{
        String sql = "UPDATE borrowed_book_details SET ID_Book = ? WHERE ID_Loan_slip = ? AND ID_Book = ?";
        try (PreparedStatement ps = conn.prepareStatement(sql)){
            ps.setString(1, ID_Book);
            ps.setString(2, ID_Loan_slip);
            ps.setString(3, ID_Book_f);
            ps.executeUpdate();
        }
    }

    // Xóa 1 chi tiết phiếu mượn
    public void deleteBorrow_Deatils(String ID_Loan_slip, String ID_Book) throws SQLException{
        String sql = "DELETE FROM borrowed_book_details WHERE ID_Loan_slip = ? AND ID_Book = ?";
        try (PreparedStatement ps = conn.prepareStatement(sql)){
            ps.setString(1, ID_Loan_slip);
            ps.setString(2, ID_Book);
            ps.executeUpdate();
        }
    }

    public Boolean check_quan(String ID_Loan){
        String sql = "SELECT COUNT(*) AS count FROM borrowed_book_details WHERE ID_Loan_slip = ?";
        try (PreparedStatement ps = conn.prepareStatement(sql)){
            ps.setString(1, ID_Loan);
            ResultSet rs = ps.executeQuery();
            if (rs.next()){
                return rs.getInt("count") <= 5;
            }
        } catch (SQLException e){
            e.printStackTrace();
        }
        return false;
    }

    public List<String> getPay_IdByLoan_Id(String ID) throws SQLException{
        String sql = """
                    SELECT p.ID 
                    FROM 
                        Payment_slip p
                    JOIN
                        Loan_slip l ON p.ID_Loan_slip = l.ID
                    WHERE
                        l.ID = ?
                    """;
        List<String> Pay_ID = new ArrayList<>();
        try (PreparedStatement ps = conn.prepareStatement(sql)){
            ps.setString(1, ID);
            ResultSet rs = ps.executeQuery();
            while(rs.next()){
                Pay_ID.add(rs.getString("ID"));
            }
            return Pay_ID;
        }
    }
}
