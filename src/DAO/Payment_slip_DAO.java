package DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import config.DatabaseConnection;
import model.Payment_slip;

public class Payment_slip_DAO {
    private Connection conn;
    
    public Payment_slip_DAO(){
        conn = DatabaseConnection.getConnection();
    }

    // Lấy danh sách phiếu trả
    public List<Payment_slip> getAllPayment_slips() throws SQLException{
        List<Payment_slip> payments = new ArrayList<>();
        String sql = "SELECT * FROM Payment_slip";
        try (PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()){
                while(rs.next()){
                    payments.add(new Payment_slip(rs.getString("ID"), rs.getString("ID_Loan_slip"), rs.getString("ID_Staff"), rs.getInt("so_luong"), 
                                rs.getDate("payment_Date").toLocalDate(), rs.getFloat("late_fee"), rs.getFloat("damage_fee")));
            }
        } catch (SQLException e){
            e.printStackTrace();
        }
        return payments;
    }

    // Thêm 1 phiếu mượn vào danh sách
    public void addPayment_slip(String ID, String ID_Loan_slip, String ID_Staff, int so_luong, LocalDate payment_Date){
        String sql = "INSERT INTO Payment_slip (ID, ID_Loan_slip, ID_Staff, so_luong, payment_Date, late_fee, damage_fee) VALUES (?, ?, ?, ?, ?, ?, ?)";
        try (PreparedStatement ps = conn.prepareStatement(sql)){
            ps.setString(1, ID);
            ps.setString(2, ID_Loan_slip);
            ps.setString(3, ID_Staff);
            ps.setInt(4, so_luong);
            ps.setDate(5, java.sql.Date.valueOf(payment_Date));
            ps.setString(6, ID);
            ps.setString(7, ID);
            ps.executeUpdate();

        } catch (SQLException e){
                    e.printStackTrace();
        }
    }

    // Tìm kiếm phiếu mượn
    public Payment_slip searchPayment_slip(String ID){
        String sql = "SELECT * FROM Payment_slip WHERE ID LIKE ? LIMIT 1";
        try (PreparedStatement ps = conn.prepareStatement(sql)){
            ps.setString(1, ID);
            ResultSet rs = ps.executeQuery();
            if(rs.next()){
                return new Payment_slip(rs.getString("ID"), rs.getString("ID_Loan_slip"), rs.getString("ID_Staff"), rs.getInt("so_luong"), 
                           rs.getDate("payment_Date").toLocalDate(), rs.getFloat("late_fee"), rs.getFloat("damage_fee"));
            }
        } catch (SQLException e){
            e.printStackTrace();
        }
        return null;
    }

    // Cập nhập thông tin 1 phiếu mượn
    public void updatePayment_slip(String ID, String ID_Loan_slip, String ID_Staff, LocalDate payment_Date){
        String sql = "UPDATE Payment_slip SET ID_Loan_slip = ?, ID_Staff = ?, payment_Date = ? WHERE ID = ?";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, ID_Loan_slip);
            ps.setString(2, ID_Staff);
            ps.setDate(3, java.sql.Date.valueOf(payment_Date));
            ps.setString(4, ID);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Xóa 1 phiếu mượn trong danh sách
    public void deletePayment_slip(String ID){
        String sql = "DELETE FROM Payment_slip WHERE ID = ?";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, ID);
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Tính phí trễ hạn
    public float Late_fee(String ID){
        
        return 0;
    }

    // Tính phí hư hại

}
