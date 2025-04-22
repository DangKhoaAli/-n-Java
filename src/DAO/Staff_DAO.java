package DAO;

import config.DatabaseConnection;
import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import model.Staff;

public class Staff_DAO {
    private Connection conn;

    public Staff_DAO(){
        conn = DatabaseConnection.getConnection();
    }

    // Lấy danh sách độc giả
    public List<Staff> getAll_Staff() throws SQLException{
        List<Staff> Staffs = new ArrayList<>();
        String sql = "SELECT * FROM Staff";
        try (PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()){
                while(rs.next()){
                    Staffs.add(new Staff(rs.getString("ID"), rs.getString("name"), rs.getString("gender"), 
                                rs.getDate("birth").toLocalDate(), rs.getString("address"), rs.getString("phone"), 
                                rs.getString("email"), rs.getString("exist"), rs.getBigDecimal("wage").floatValue()));
                }
        } catch (SQLException e){
            e.printStackTrace();
        }
        return Staffs;
    }

    // Thêm 1 độc giả
    public void addStaff(String ID, String name, String gender, LocalDate birth, String address, String phone, String email, float wage) throws SQLException{
        String sql = "INSERT INTO Staff (ID, name, gender, birth, address, phone, email, wage, exist) VALUES(?, ?, ?, ?, ?, ?, ?, ?, ?)";
        try (PreparedStatement ps = conn.prepareStatement(sql)){
            ps.setString(1, ID);
            ps.setString(2, name);
            ps.setString(3, gender);
            ps.setDate(4, java.sql.Date.valueOf(birth));
            ps.setString(5, address);
            ps.setString(6, phone);
            ps.setString(7, email);
            ps.setBigDecimal(8, new BigDecimal(wage));
            ps.setString(9, "1");
            ps.executeUpdate();
            
        }
    }

    // Tìm kiếm đọc gỉa theo tên
    public List<Staff> searchStaff(String key) throws SQLException{
        List<Staff> Staffs = new ArrayList<>();
        String sql = "SELECT * FROM Staff WHERE name LIKE ?";
        try (PreparedStatement ps = conn.prepareStatement(sql)){
            ps.setString(1, "%" + key + "%");
            ResultSet rs = ps.executeQuery();
            while(rs.next()){
                Staffs.add(new Staff(rs.getString("ID"), rs.getString("name"), rs.getString("gender"), 
                            rs.getDate("birth").toLocalDate(), rs.getString("address"), rs.getString("phone"), 
                            rs.getString("email"), rs.getString("exist"), rs.getBigDecimal("wage").floatValue()));
            }
        }
        return Staffs;
    }

    public Boolean searchStaffID(String key) throws SQLException{
    String sql = "SELECT * FROM Staff WHERE ID LIKE ? LIMIT 1";
    try (PreparedStatement ps = conn.prepareStatement(sql)){
        ps.setString(1, key);
        ResultSet rs = ps.executeQuery();
        if(rs.next())
            return true;
    }
    return false;
        
    }

    // Cập nhập thông tin độc giả
    public void updateStaff(String ID, String name, String gender, LocalDate birth, String address, String phone, String email, float wage) throws SQLException{
        String sql = "UPDATE Staff SET name = ?, gender = ?, birth = ?, address = ?, phone = ?, email = ?, wage = ? WHERE ID = ?";
        try (PreparedStatement ps = conn.prepareStatement(sql)){
            ps.setString(1, name);
            ps.setString(2, gender);
            ps.setDate(3, java.sql.Date.valueOf(birth));
            ps.setString(4, address);
            ps.setString(5, phone);
            ps.setString(6, email);
            ps.setBigDecimal(7, new BigDecimal(String.valueOf(wage)));
            ps.setString(8, ID);
            ps.executeUpdate();

        } catch (SQLException e){
            e.printStackTrace();
        }
    }

    // Xóa thông tin độc giả
    public void deleteStaff(String ID) throws SQLException{
        String sql = "UPDATE Staff SET exist = ? WHERE ID = ?";
        try (PreparedStatement ps = conn.prepareStatement(sql)){
            ps.setString(1, "0");
            ps.setString(2, ID);
            ps.executeUpdate();
        }
    }

    // Tạo tài khoản đăng nhập cho thủ thư với user là ID và pass là số điện thoại
    public String[] generateAccountByStaffID(String staffID) throws SQLException {
        String sql = "SELECT phone FROM Staff WHERE ID = ? AND exist = '1'";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, staffID);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                String username = staffID;
                String password = rs.getString("phone");
                return new String[] { username, password };
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null; 
    }

    public String getStaffName(String staffID){
        String sql = "SELECT name FROM Staff WHERE ID = ? AND exist = '1'";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, staffID);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return rs.getString("name");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null; 
    }
    
}

