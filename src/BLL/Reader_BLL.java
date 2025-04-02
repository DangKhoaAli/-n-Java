package BLL;

import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

import DAO.Reader_DAO;
import model.Reader;

public class Reader_BLL {
    private Reader_DAO reader_DAO;

    public Reader_BLL(){
        reader_DAO = new Reader_DAO();
    }

    // Lấy danh sách tất cả độc giả
    public List<Reader> getReader() {
        try {
            List<Reader> readers = reader_DAO.getAll_Reader();
            if (readers == null || readers.isEmpty()) {
                return new ArrayList<>();
            }
            return readers;
        } catch (SQLException e) {
            return new ArrayList<>();
        }
    }

    public List<Reader> searchReader(String key) {
        try {
            List<Reader> result = reader_DAO.searchReader(key);
            return result == null ? new ArrayList<>() : result;
        } catch (SQLException e) {
            return new ArrayList<>();
        }
    }

    public String addReader(String ID, String name, String gender, LocalDate birth, String address, String phone, String email, LocalDate registrationDate) {
        try {
            if (ID == null || ID.isEmpty() || name == null || name.isEmpty()) {
                return "ID và Tên không được để trống!";
            }

            List<Reader> existingReaders = searchReader(ID);
            if (!existingReaders.isEmpty()) {
                return "ID đã tồn tại.";
            }

            if (birth.isAfter(LocalDate.now().minusYears(10))) {
                return "Độc giả phải từ 10 tuổi trở lên.";
            }

            if (!gender.equalsIgnoreCase("Nam") && !gender.equalsIgnoreCase("Nu")) {
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

            if (registrationDate.isBefore(birth)) {
                return "Ngày đăng ký không thể trước ngày sinh.";
            }

            if (registrationDate.isAfter(LocalDate.now())) {
                return "Ngày đăng ký không thể là ngày trong tương lai.";
            }

            // Thêm độc giả vào cơ sở dữ liệu
            reader_DAO.addReader(ID, name, gender, birth, address, phone, email, registrationDate);
            return "Đã thêm thành công 1 độc giả.";
        } catch (SQLException e) {
            e.printStackTrace();
            return "Lỗi SQL: " + e.getMessage();
        } catch (Exception e) {
            return "Lỗi không xác định: " + e.getMessage();
        }
    }

    public String updateReader(String ID, String name, String gender, LocalDate birth, String address, String phone, String email, LocalDate registrationDate) {
        try {

            if (ID == null || ID.isEmpty() || name == null || name.isEmpty()) {
                return "ID và Tên không được để trống!";
            }

            Boolean existingReaders = reader_DAO.searchReaderID(ID);
            if (!existingReaders) {
                return "Không tìm thấy độc giả với ID này.";
            }

            if (birth.isAfter(LocalDate.now().minusYears(10))) {
                return "Độc giả phải từ 10 tuổi trở lên.";
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

            if (registrationDate.isBefore(birth)) {
                return "Ngày đăng ký không thể trước ngày sinh.";
            }

            if (registrationDate.isAfter(LocalDate.now())) {
                return "Ngày đăng ký không thể là ngày trong tương lai.";
            }

            // Cập nhật độc giả trong cơ sở dữ liệu
            reader_DAO.updateReader(ID, name, gender, birth, address, phone, email, registrationDate);
            return "Cập nhật độc giả thành công.";
        } catch (SQLException e) {
            e.printStackTrace();
            return "Lỗi SQL: " + e.getMessage();
        } catch (Exception e) {
            return "Lỗi không xác định: " + e.getMessage();
        }
    }

    public String deleteReader(String ID) {
        try {
            if (ID == null || ID.isEmpty()) {
                return "ID không được để trống!";
            }

            Boolean existingReaders = reader_DAO.searchReaderID(ID);
            if (!existingReaders) {
                return "Không tìm thấy độc giả với ID này.";
            }
    
            // Xóa độc giả khỏi cơ sở dữ liệu
            reader_DAO.deleteReader(ID);
            return "Xóa độc giả thành công.";
        } catch (SQLException e) {
            e.printStackTrace();
            return "Lỗi SQL: " + e.getMessage();
        } catch (Exception e) {
            return "Lỗi không xác định: " + e.getMessage();
        }
    }
    

}
