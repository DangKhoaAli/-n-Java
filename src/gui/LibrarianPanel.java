package gui;

import java.awt.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.List;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;

import BLL.Staff_BLL;
import model.Staff;

public class LibrarianPanel extends JPanel {
    private Staff_BLL staff_BLL;

    private JTable table;
    private DefaultTableModel tableModel;

    private JTextField txtMaThuThu;
    private JTextField txtTenThuThu;
    private JTextField txtGioiTinh;
    private JTextField txtNgaySinh;
    private JTextField txtSoDienThoai;
    private JTextField txtDiaChi;
    private JTextField txtEmail;
    private JTextField txtLuong;

    private JTextField txtTuKhoa;

    private JButton btnThem;
    private JButton btnSua;
    private JButton btnLuu;
    private JButton btnXoa;
    private JButton btnHuy;
    private JButton btnTim;

    public LibrarianPanel() {
        staff_BLL = new Staff_BLL();
        setBackground(Color.BLUE);
        setLayout(new BorderLayout(10,10));

        JPanel panelTable = new JPanel(new BorderLayout());
        panelTable.setBackground(Color.BLUE);

        String[] columnNames = {"Mã Thủ Thư", "Họ  và Tên", "Giới Tính", "Ngày Sinh", "Số Điện Thoại", "Địa Chỉ", "Email", "Lương"};
        tableModel = new DefaultTableModel(columnNames,0);

        table = new JTable(tableModel);
        table.setBackground(Color.WHITE);
        table.setSelectionBackground(Color.YELLOW);
        JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.getViewport().setBackground(Color.WHITE);
        panelTable.add(scrollPane, BorderLayout.CENTER);

        txtMaThuThu = new JTextField();
        txtMaThuThu.setBorder(BorderFactory.createTitledBorder("Mã thủ thư"));

        txtTenThuThu = new JTextField();
        txtTenThuThu.setBorder(BorderFactory.createTitledBorder("Họ và tên"));

        txtGioiTinh = new JTextField();
        txtGioiTinh.setBorder(BorderFactory.createTitledBorder("Giới tính"));

        txtNgaySinh = new JTextField();
        txtNgaySinh.setBorder(BorderFactory.createTitledBorder("Ngày sinh"));

        txtSoDienThoai = new JTextField();
        txtSoDienThoai.setBorder(BorderFactory.createTitledBorder("Số điện thoại"));

        txtDiaChi = new JTextField();
        txtDiaChi.setBorder(BorderFactory.createTitledBorder("Địa chỉ"));

        txtEmail = new JTextField();
        txtEmail.setBorder(BorderFactory.createTitledBorder("Email"));

        txtLuong = new JTextField();
        txtLuong.setBorder(BorderFactory.createTitledBorder("Lương"));

        JPanel panelInput = new JPanel();
        panelInput.setLayout(new BoxLayout(panelInput, BoxLayout.Y_AXIS));
        panelInput.setBackground(Color.BLUE);
        panelInput.add(txtMaThuThu);
        panelInput.add(txtTenThuThu);
        panelInput.add(txtGioiTinh);
        panelInput.add(txtNgaySinh);
        panelInput.add(txtSoDienThoai);
        panelInput.add(txtDiaChi);
        panelInput.add(txtEmail);
        panelInput.add(txtLuong);

        // Panel tìm kiếm
        JPanel panelSearch = new JPanel();
        panelSearch.setBackground(Color.BLUE);
        panelSearch.add(new JLabel("Từ khóa:"));
        txtTuKhoa = new JTextField(20);
        panelSearch.add(txtTuKhoa);
        btnTim = new JButton("Tìm");
        panelSearch.add(btnTim);
        
        // Panel chứa các nút
        JPanel panelButtons = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        panelButtons.setBackground(Color.BLUE);
        btnThem = new JButton("Thêm");
        btnSua = new JButton("Sửa");
        btnLuu = new JButton("Lưu");
        btnXoa = new JButton("Xóa");
        btnHuy = new JButton("Hủy");
        
        panelButtons.add(btnThem);
        panelButtons.add(btnSua);
        panelButtons.add(btnLuu);
        panelButtons.add(btnXoa);
        panelButtons.add(btnHuy);
        
        btnThem.addActionListener(e -> {
            try{
                String maThuThu = txtMaThuThu.getText();
                String tenThuThu = txtTenThuThu.getText();
                String gioiTinh = txtGioiTinh.getText();
                String ngaySinh = txtNgaySinh.getText();
                String soDienThoai = txtSoDienThoai.getText();
                String diaChi = txtDiaChi.getText();
                String email = txtEmail.getText();
                String Luong = txtLuong.getText();
    
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
    
                LocalDate ngaysinh = LocalDate.parse(ngaySinh, formatter);
    
                JOptionPane.showMessageDialog(null, staff_BLL.addStaff(maThuThu, tenThuThu, gioiTinh, ngaysinh, diaChi, soDienThoai, email, Float.parseFloat(Luong)));
                loadStaffTable();
            } catch (DateTimeParseException ex) {
                JOptionPane.showMessageDialog(null, "Lỗi định dạng ngày. Vui lòng nhập ngày theo định dạng dd/MM/yyyy!", "Lỗi", JOptionPane.ERROR_MESSAGE);
            } catch (Exception ex){
                JOptionPane.showMessageDialog(null, "Đã xảy ra lỗi: " + ex.getMessage(), "Lỗi", JOptionPane.ERROR_MESSAGE);
            }
        });

        btnSua.addActionListener(e -> {
            int selectedRow = table.getSelectedRow();
            if(selectedRow == -1) {
                JOptionPane.showMessageDialog(this, "Vui lòng chọn dòng để sửa");
                return;
            }
            txtMaThuThu.setText(tableModel.getValueAt(selectedRow,0).toString());
            txtTenThuThu.setText(tableModel.getValueAt(selectedRow,1).toString());
            txtGioiTinh.setText(tableModel.getValueAt(selectedRow, 2).toString());
            txtNgaySinh.setText(tableModel.getValueAt(selectedRow, 3).toString());
            txtSoDienThoai.setText(tableModel.getValueAt(selectedRow, 4).toString());
            txtDiaChi.setText(tableModel.getValueAt(selectedRow, 5).toString());
            txtEmail.setText(tableModel.getValueAt(selectedRow, 6).toString());
            txtLuong.setText(tableModel.getValueAt(selectedRow, 7).toString());
        });

        btnLuu.addActionListener(e -> {
            try{
                // Lấy dữ liệu từ các ô nhập
                String maThuthu = txtMaThuThu.getText();
                String tenThuthu = txtTenThuThu.getText();
                String gioiTinh = txtGioiTinh.getText();
                LocalDate ngaySinh = LocalDate.parse(txtNgaySinh.getText()); // Chuyển đổi từ String sang LocalDate
                String soDienThoai = txtSoDienThoai.getText();
                String diaChi = txtDiaChi.getText();
                String email = txtEmail.getText();
                Float Luong = Float.parseFloat(txtLuong.getText());

                String result = staff_BLL.updateStaff(maThuthu, tenThuthu, gioiTinh, ngaySinh, diaChi, soDienThoai, email, Luong);

                JOptionPane.showMessageDialog(this, result, "Thông báo", JOptionPane.INFORMATION_MESSAGE);
                loadStaffTable();

            } catch (Exception ex){
                JOptionPane.showMessageDialog(this, "Lỗi: " + ex.getMessage(), "Lỗi", JOptionPane.ERROR_MESSAGE);
            }
        });

        btnXoa.addActionListener(e -> {
            int selectedRow = table.getSelectedRow();
            if(selectedRow == -1) {
                JOptionPane.showMessageDialog(this, "Vui lòng chọn dòng để xóa");
                return;
            }
            try{
                String ID = tableModel.getValueAt(selectedRow,0).toString();
                
                
                String result = staff_BLL.deleteStaff(ID);
                JOptionPane.showMessageDialog(this, result, "Thông báo", JOptionPane.INFORMATION_MESSAGE);
                loadStaffTable();
            }catch(Exception ex){
                JOptionPane.showMessageDialog(this, "Lỗi" + ex.getMessage(), "Lỗi", JOptionPane.ERROR_MESSAGE);
            }
        });

        btnHuy.addActionListener(e -> {
            txtMaThuThu.setText("");
            txtTenThuThu.setText("");
            txtGioiTinh.setText("");
            txtNgaySinh.setText("");
            txtSoDienThoai.setText("");
            txtDiaChi.setText("");
            txtEmail.setText("");
            txtLuong.setText("");
        });

        btnTim.addActionListener(e -> {
            String keyword = txtTuKhoa.getText().trim().toLowerCase();
            if (keyword == null || keyword.trim().isEmpty()) {
                JOptionPane.showMessageDialog(this, "Cần nhập dữ liệu tìm kiếm!");
                loadStaffTable();
            }
            else{
                List<Staff> Staffs = staff_BLL.searchStaff(keyword);
                if (Staffs != null) {
                    tableModel.setRowCount(0);
                    for (Staff Staff : Staffs) {
                        tableModel.addRow(new Object[]{
                            Staff.getID(),
                            Staff.getName(),
                            Staff.getGender(),
                            Staff.getBirth(),
                            Staff.getPhone(),
                            Staff.getAddress(),
                            Staff.getEmail(),
                            Staff.getWage()
                        });
                    }
                } else {
                    JOptionPane.showMessageDialog(this, "Không thể tải danh sách độc giả!", "Lỗi", JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        add(panelTable, BorderLayout.CENTER);
        loadStaffTable();

        JPanel panelBottom = new JPanel(new BorderLayout(10,10));
        panelBottom.add(panelSearch, BorderLayout.NORTH);
        panelBottom.add(panelInput, BorderLayout.CENTER);
        panelBottom.add(panelButtons, BorderLayout.SOUTH);

        add(panelBottom, BorderLayout.SOUTH);
    }

    public void loadStaffTable(){
        tableModel.setRowCount(0);

        List<Staff> staffs = staff_BLL.getStaff();

        if(staffs != null){
            for (Staff staff : staffs) {
                tableModel.addRow(new Object[]{
                    staff.getID(),
                    staff.getName(),
                    staff.getGender(),
                    staff.getBirth(),
                    staff.getPhone(),
                    staff.getAddress(),
                    staff.getEmail(),
                    staff.getWage()
                });
            }
        } else {
            JOptionPane.showMessageDialog(this, "Không thể tải danh sách thủ thư", "Lỗi", JOptionPane.ERROR_MESSAGE);
        }
    }


}