package DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import config.DatabaseConnection;
import model.Loan_slip;

public class Loan_slip_DAO {
    private Connection conn;

    public Loan_slip_DAO(){
        conn = DatabaseConnection.getConnection();
    }

    // Lấy danh sách phiếu mượn
    public List<Loan_slip> getAllLoan_slips() throws SQLException{
        List<Loan_slip> loan = new ArrayList<>();
        String sql = "SELECT * FROM Loan_slip";
        try(PreparedStatement ps = conn.prepareStatement(sql);
            ResultSet rs = ps.executeQuery()){
                while(rs.next()){
                    loan.add(new Loan_slip(rs.getString("ID"), rs.getString("ID_Reader"), rs.getString("ID_Staff"), rs.getInt("so_luong"),
                            rs.getDate("Borrow_Date").toLocalDate(), rs.getDate("Expected_Date").toLocalDate(), rs.getFloat("loan_fee")));
            }
        } catch(SQLException e){
            e.printStackTrace();
        }
        return loan;
    }

    // Thêm 1 phiếu mượn vào danh sách
    public void addLoan_slip(String ID, String ID_Reader, String ID_Staff, int so_luong, LocalDate Borrow_Date, LocalDate Expected_Date){
        String sql = "INSERT INTO Loan_slip (ID, ID_Reader, ID_Staff, so_luong, Borrow_Date, Expected_Date, loan_fee) VALUES (?, ?, ?, ?, ?, ?, ?)";
        try (PreparedStatement ps = conn.prepareStatement(sql)){
            ps.setString(1, ID);
            ps.setString(2, ID_Reader);
            ps.setString(3, ID_Staff);
            ps.setInt(4, so_luong);
            ps.setDate(5, java.sql.Date.valueOf(Borrow_Date));
            ps.setDate(6, java.sql.Date.valueOf(Expected_Date));
            float fee = Borrow_fee(conn, ID);
            ps.setFloat(7, fee);
            ps.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Tìm kiếm phiếu mượn theo mã phiếu
    public Loan_slip searchLoan_slip(String ID){
        String sql = "SELECT * FROM Loan_slip WHERE ID LIKE ? LIMIT 1";
        try (PreparedStatement ps = conn.prepareStatement(sql)){
            ps.setString(1, ID);
            ResultSet rs = ps.executeQuery();
            if (rs.next()){
                return new Loan_slip(rs.getString("ID"), rs.getString("ID_Reader"), rs.getString("ID_Staff"),
                           rs.getInt("so_luong"), rs.getDate("Borrow_Date").toLocalDate(), rs.getDate("Expected_Date").toLocalDate(), rs.getFloat("loan_fee"));
            }
        } catch(SQLException e){
            e.printStackTrace();
        }
        return null;
    }

    // Cập nhập phiếu mượn
    public void updateLoan_slip(String ID, String ID_Reader, String ID_Staff, LocalDate Borrow_Date, LocalDate Expected_Date){
        String sql = "UPDATE Loan_slip SET ID_Reader = ?, ID_Staff = ?, Borrow_Date = ?, Expected_Date = ? WHERE ID = ?";
        try (PreparedStatement ps = conn.prepareStatement(sql)){
            ps.setString(1, ID_Reader);
            ps.setString(2, ID_Staff);
            ps.setDate(3, java.sql.Date.valueOf(Borrow_Date));
            ps.setDate(4, java.sql.Date.valueOf(Expected_Date));
            ps.setString(5, ID);
            ps.executeUpdate();
            
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Xóa phiếu mượn
    public void deleteLoan_slip(String ID){
        String sql = "DELETE FROM Loan_slip WHERE ID = ?";
        try(PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, ID);
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Tính phí mượn
    public static float Borrow_fee(Connection conn, String id){
        float totalFee = 0;
        String sql = "SELECT SUM(b.loan_fee) AS total_borrow_fee "+
                     "FROM Borrowed_Book_Details bd "+
                     "JOIN Book_Details bdt ON bd.ID_Book = bdt.ID "+
                     "JOIN Book b ON bdt.ID_Book = b.ID "+
                     "WHERE bd.ID_Loan_slip = ?";
        
        try(PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, id);
            ResultSet rs = ps.executeQuery();
            if (rs.next()){
                totalFee = rs.getFloat("total_borrow_fee");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return totalFee; 
    }

    public static void main(String [] args){
        Loan_slip_DAO a = new Loan_slip_DAO();
        LocalDate Borrow_Date = LocalDate.of(2024, 3, 25); 
        LocalDate Expected_Date = LocalDate.of(2024, 3, 30); 
        a.deleteLoan_slip("1");

    }
}
