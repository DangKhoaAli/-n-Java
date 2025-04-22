package BLL;

import DAO.Staff_DAO;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;
import model.Staff;

public class Staff_BLL {
    private Staff_DAO Staff_DAO;

    public Staff_BLL(){
        Staff_DAO = new Staff_DAO();
    }

    // Lấy danh sách tất cả thủ thư
    public List<Staff> getStaff() {
        try {
            List<Staff> Staffs = Staff_DAO.getAll_Staff();
            if (Staffs == null || Staffs.isEmpty()) {
                return new ArrayList<>();
            }
            return Staffs;
        } catch (SQLException e) {
            return new ArrayList<>();
        }
    }

    public List<Staff> searchStaff(String key) {
        try {
            List<Staff> result = Staff_DAO.searchStaff(key);
            return result == null ? new ArrayList<>() : result;
        } catch (SQLException e) {
            return new ArrayList<>();
        }
    }

    public String addStaff(String ID, String name, String gender, LocalDate birth, String address, String phone, String email, float wage) {
        try {
            if (ID == null || ID.isEmpty() || name == null || name.isEmpty()) {
                return "ID và Tên không được để trống!";
            }

            List<Staff> existingStaffs = searchStaff(ID);
            if (!existingStaffs.isEmpty()) {
                return "ID đã tồn tại.";
            }

            if (birth.isAfter(LocalDate.now().minusYears(20))) {
                return "Thủ thư phải từ 20 tuổi trở lên.";
            }

            if (!gender.equalsIgnoreCase("Nam") && !gender.equalsIgnoreCase("Nữ")) {
                return "Giới tính phải là 'Nam' hoặc 'Nữ'.";
            }

            if (address == null || address.trim().isEmpty()) {
                return "Địa chỉ không được để trống!";
            }

            if (!Pattern.matches("^\\d{10}$", phone)) {
                return "Số điện thoại không hợp lệ! (Phải có 10 chữ số)";
            }

            if (!Pattern.matches("^[\\w-\\.]+@([\\w-]+\\.)+[\\w-]{2,4}$", email)) {
                return "Email không hợp lệ!";
            }

            if (wage < 0) {
                return "Lương không thể nhỏ hơn 0.";
            }


            // Thêm thủ thư vào cơ sở dữ liệu
            Staff_DAO.addStaff(ID, name, gender, birth, address, phone, email, wage);
            return "Đã thêm thành công 1 thủ thư.";
        } catch (SQLException e) {
            e.printStackTrace();
            return "Lỗi SQL: " + e.getMessage();
        } catch (Exception e) {
            return "Lỗi không xác định: " + e.getMessage();
        }
    }

    public String updateStaff(String ID, String name, String gender, LocalDate birth, String address, String phone, String email, float wage) {
        try {

            if (ID == null || ID.isEmpty() || name == null || name.isEmpty()) {
                return "ID và Tên không được để trống!";
            }

            Boolean existingStaffs = Staff_DAO.searchStaffID(ID);
            if (!existingStaffs) {
                return "Không tìm thấy thủ thư với ID này.";
            }

            if (birth.isAfter(LocalDate.now().minusYears(20))) {
                return "Thủ thư phải từ 20 tuổi trở lên.";
            }

            if (!gender.equalsIgnoreCase("Nam") && !gender.equalsIgnoreCase("Nữ")) {
                return "Giới tính phải là 'Nam' hoặc 'Nữ'.";
            }

            if (address == null || address.trim().isEmpty()) {
                return "Địa chỉ không được để trống!";
            }

            if (!Pattern.matches("^\\d{10}$", phone)) {
                return "Số điện thoại không hợp lệ! (Phải có 10 chữ số)";
            }

            if (!Pattern.matches("^[\\w-\\.]+@([\\w-]+\\.)+[\\w-]{2,4}$", email)) {
                return "Email không hợp lệ!";
            }
            
            if (wage < 0) {
                return "Lương không thể nhỏ hơn 0.";
            }

            // Cập nhật thủ thư trong cơ sở dữ liệu
            Staff_DAO.updateStaff(ID, name, gender, birth, address, phone, email, wage);
            return "Cập nhật thủ thư thành công.";
        } catch (SQLException e) {
            e.printStackTrace();
            return "Lỗi SQL: " + e.getMessage();
        } catch (Exception e) {
            return "Lỗi không xác định: " + e.getMessage();
        }
    }

    public String deleteStaff(String ID) {
        try {
            if (ID == null || ID.isEmpty()) {
                return "ID không được để trống!";
            }

            Boolean existingStaffs = Staff_DAO.searchStaffID(ID);
            if (!existingStaffs) {
                return "Không tìm thấy thủ thư với ID này.";
            }
    
            // Xóa thủ thư khỏi cơ sở dữ liệu
            Staff_DAO.deleteStaff(ID);
            return "Xóa thủ thư thành công.";
        } catch (SQLException e) {
            e.printStackTrace();
            return "Lỗi SQL: " + e.getMessage();
        } catch (Exception e) {
            return "Lỗi không xác định: " + e.getMessage();
        }
    }
    

}
