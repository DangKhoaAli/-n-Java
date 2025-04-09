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
                                rs.getString("email"), rs.getBigDecimal("wage").floatValue()));
                }
        } catch (SQLException e){
            e.printStackTrace();
        }
        return Staffs;
    }

    // Thêm 1 độc giả
    public void addStaff(String ID, String name, String gender, LocalDate birth, String address, String phone, String email, float wage) throws SQLException{
        String sql = "INSERT INTO Staff (ID, name, gender, birth, address, phone, email, wage) VALUES(?, ?, ?, ?, ?, ?, ?, ?)";
        try (PreparedStatement ps = conn.prepareStatement(sql)){
            ps.setString(1, ID);
            ps.setString(2, name);
            ps.setString(3, gender);
            ps.setDate(4, java.sql.Date.valueOf(birth));
            ps.setString(5, address);
            ps.setString(6, phone);
            ps.setString(7, email);
            ps.setBigDecimal(8, new BigDecimal(wage));
            ps.executeUpdate();
            
        } catch (SQLException e){
            e.printStackTrace();
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
                            rs.getString("email"), rs.getBigDecimal("wage").floatValue()));
            }
        } catch (SQLException e){
            e.printStackTrace();
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
        String sql = "DELETE FROM Staff WHERE ID = ?";
        try (PreparedStatement ps = conn.prepareStatement(sql)){
            ps.setString(1, ID);
            ps.executeUpdate();
        } catch(SQLException e){
            e.printStackTrace();
        }
    }
}
