package DAO;

import java.math.BigDecimal;
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
            float late_fee = Late_fee(ID);
            float damage_fee = damage_fee(ID);
            ps.setBigDecimal(6, new BigDecimal(late_fee));
            ps.setBigDecimal(7, new BigDecimal(damage_fee));
            ps.executeUpdate();

        } catch (SQLException e){
                    e.printStackTrace();
        }
    }

    // Tìm kiếm phiếu mượn
    public Payment_slip searchPayment_slip(String ID) throws SQLException{
        String sql = "SELECT * FROM Payment_slip WHERE ID LIKE ? LIMIT 1";
        try (PreparedStatement ps = conn.prepareStatement(sql)){
            ps.setString(1, ID);
            ResultSet rs = ps.executeQuery();
            if(rs.next()){
                return new Payment_slip(rs.getString("ID"), rs.getString("ID_Loan_slip"), rs.getString("ID_Staff"), rs.getInt("so_luong"), 
                           rs.getDate("payment_Date").toLocalDate(), rs.getFloat("late_fee"), rs.getFloat("damage_fee"));
            }
        }
        return null;
    }

    // Cập nhập thông tin 1 phiếu mượn
    public void updatePayment_slip(String ID, LocalDate payment_Date) throws SQLException{
        String sql = "UPDATE Payment_slip SET payment_Date = ? WHERE ID = ?";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setDate(1, java.sql.Date.valueOf(payment_Date));
            ps.setString(2, ID);
            ps.executeUpdate();
        }
    }

    // Xóa 1 phiếu mượn trong danh sách
    public void deletePayment_slip(String ID) throws SQLException{
        String sql = "DELETE FROM Payment_slip WHERE ID = ?";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, ID);
            ps.executeUpdate();
        }
    }

    // Xóa tất cả phiếu trả có cùng ID_Loan_slip
    public void deletePaymentSlipsByLoanSlipID(String ID_Loan_slip) {
        String sql = "DELETE FROM Payment_slip WHERE ID_Loan_slip = ?";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, ID_Loan_slip);
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    // Tính phí trễ hạn
    public float Late_fee(String ID) {
        float lateFee = 0;
        String sql = "SELECT ps.payment_Date, ls.Expected_Date " +
                    "FROM Payment_slip ps " +
                    "JOIN Loan_slip ls ON ps.ID_Loan_slip = ls.ID " +
                    "WHERE ps.ID = ?";

        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, ID);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                LocalDate paymentDate = rs.getDate("payment_Date").toLocalDate();
                LocalDate expectedDate = rs.getDate("Expected_Date").toLocalDate();
                long daysLate = java.time.temporal.ChronoUnit.DAYS.between(expectedDate, paymentDate);

                if (daysLate > 0) {
                    lateFee = daysLate * 1000f;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return lateFee;
    }

    public void updateLateFee(String ID) throws SQLException {
        String sql = "UPDATE Payment_slip SET late_fee = ? WHERE ID = ?";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            float lateFee = Late_fee(ID);
            ps.setBigDecimal(1, BigDecimal.valueOf(lateFee));
            ps.setString(2, ID);
            ps.executeUpdate();
        }
    }

    // Tính phí hư hại
    public float damage_fee(String ID) {
        float totalDamageFee = 0;
        String sql = "SELECT SUM(penalty_fee) AS total_damage_fee " +
                    "FROM Book_Details_Returned " +
                    "WHERE ID_Payment_slip = ?";

        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, ID);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                totalDamageFee = rs.getFloat("total_damage_fee");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return totalDamageFee;
    }

    public void updateDamageFee(String ID) throws SQLException {
        String sql = "UPDATE Payment_slip SET damage_fee = ? WHERE ID = ?";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            float damageFee = damage_fee(ID);
            ps.setBigDecimal(1, BigDecimal.valueOf(damageFee));
            ps.setString(2, ID);
            ps.executeUpdate();
        }
    }

    public void update_Quan(String ID, int quanlity) throws SQLException {
        String sql = "UPDATE Payment_slip SET so_luong = ? WHERE ID = ?";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, quanlity);
            ps.setString(2, ID);
            ps.executeUpdate();
        }
    }


}
