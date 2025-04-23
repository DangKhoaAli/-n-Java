package gui;

import BLL.Staff_BLL;
import java.awt.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.List;
import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableModel;
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

    private RoundedTxtField txtTuKhoa;

    private RoundedButton btnThem;
    private RoundedButton btnSua;
    private RoundedButton btnXoa;
    private RoundedButton btnHuy;
    private RoundedButton btnTim;
    private RoundedButton btnDangXuat;

    public LibrarianPanel() {
        staff_BLL = new Staff_BLL();
        setBackground(new Color(230, 236, 243));
        setLayout(new BorderLayout(0,0));

        JPanel panelTable = new JPanel(new BorderLayout());
        panelTable.setBackground(new Color(230, 236, 243));

        String[] columnNames = {"Mã Thủ Thư", "Họ  và Tên", "Giới Tính", "Ngày Sinh", "Số Điện Thoại", "Địa Chỉ", "Email", "Lương", "Tồn tại"};
        tableModel = new DefaultTableModel(columnNames,0);

        table = new JTable(tableModel);
        table.setBackground(Color.WHITE);
        table.setSelectionBackground(Color.YELLOW);
        JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.getViewport().setBackground(Color.WHITE);
        panelTable.add(scrollPane, BorderLayout.CENTER);

          table.getSelectionModel().addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent e) {
                if (!e.getValueIsAdjusting()) {
                    int selectedRow = table.getSelectedRow();
                    if (selectedRow != -1) {
                        txtMaThuThu.setText(tableModel.getValueAt(selectedRow, 0).toString());
                        txtTenThuThu.setText(tableModel.getValueAt(selectedRow, 1).toString());
                        txtGioiTinh.setText(tableModel.getValueAt(selectedRow, 2).toString());
                        txtNgaySinh.setText(tableModel.getValueAt(selectedRow, 3).toString());
                        txtSoDienThoai.setText(tableModel.getValueAt(selectedRow, 4).toString());
                        txtDiaChi.setText(tableModel.getValueAt(selectedRow, 5).toString());
                        txtEmail.setText(tableModel.getValueAt(selectedRow, 6).toString());
                        txtLuong.setText(tableModel.getValueAt(selectedRow, 7).toString());
                    }
                }
            }
        });     


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
        panelInput.setBackground(new Color(230, 236, 243));
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
        panelSearch.setBackground(new Color(230, 236, 243));
        panelSearch.add(new JLabel("Từ khóa:"));
        txtTuKhoa = new RoundedTxtField(20, 16);
        txtTuKhoa.setBackground(Color.WHITE);
        txtTuKhoa.setPlaceholder("Nhập tên thủ thư muốn tìm");
        panelSearch.add(txtTuKhoa);
        btnTim = new RoundedButton("Tìm");
        panelSearch.add(btnTim);
        
        // Panel chứa các nút
        JPanel panelButtons = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        panelButtons.setBackground(new Color(230, 236, 243));
        btnThem = new RoundedButton("Thêm");
        btnSua = new RoundedButton("Sửa");
        btnXoa = new RoundedButton("Xóa");
        btnHuy = new RoundedButton("Hủy");
        btnDangXuat = new RoundedButton("Đăng xuất");
        
        panelButtons.add(btnThem);
        panelButtons.add(btnSua);
        panelButtons.add(btnXoa);
        panelButtons.add(btnHuy);
        panelButtons.add(btnDangXuat);
        
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
    
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
    
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
            try {
                String maThuthu = txtMaThuThu.getText().trim();
                if ("Admin".equals(maThuthu)) {
                    JOptionPane.showMessageDialog(this, "Không thể chỉnh sửa thông tin của thủ thư quản trị hệ thống (Admin)", "Cảnh báo", JOptionPane.WARNING_MESSAGE);
                    return;
                }
        
                String tenThuthu = txtTenThuThu.getText();
                String gioiTinh = txtGioiTinh.getText();
                String ngaySinh = txtNgaySinh.getText(); 
                String soDienThoai = txtSoDienThoai.getText();
                String diaChi = txtDiaChi.getText();
                String email = txtEmail.getText();
                Float luong = Float.parseFloat(txtLuong.getText());

                DateTimeFormatter formatter =  DateTimeFormatter.ofPattern("dd/MM/yyyy");

                LocalDate ns = LocalDate.parse(ngaySinh, formatter);
        
                String result = staff_BLL.updateStaff(maThuthu, tenThuthu, gioiTinh, ns, diaChi, soDienThoai, email, luong);
        
                JOptionPane.showMessageDialog(this, result, "Thông báo", JOptionPane.INFORMATION_MESSAGE);
                loadStaffTable();
        
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "Lỗi: " + ex.getMessage(), "Lỗi", JOptionPane.ERROR_MESSAGE);
            }
        });
        

        btnXoa.addActionListener(e -> {
            int selectedRow = table.getSelectedRow();
            if (selectedRow == -1) {
                JOptionPane.showMessageDialog(this, "Vui lòng chọn dòng để xóa");
                return;
            }

            int confirm = JOptionPane.showConfirmDialog(
                this,
                "Bạn có chắc chắn muốn xóa dòng này không?",
                "Xác nhận xóa",
                JOptionPane.YES_NO_OPTION,
                JOptionPane.WARNING_MESSAGE
            );
        
            if (confirm != JOptionPane.YES_OPTION) {
                return; // Người dùng chọn "Không"
            }
        
            String ID = tableModel.getValueAt(selectedRow, 0).toString();
            if ("Admin".equals(ID)) {
                JOptionPane.showMessageDialog(this, "Không thể xóa thủ thư quản trị hệ thống (Admin)", "Cảnh báo", JOptionPane.WARNING_MESSAGE);
                return;
            }
        
            try {
                String result = staff_BLL.deleteStaff(ID);
                JOptionPane.showMessageDialog(this, result, "Thông báo", JOptionPane.INFORMATION_MESSAGE);
                loadStaffTable();
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "Lỗi: " + ex.getMessage(), "Lỗi", JOptionPane.ERROR_MESSAGE);
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
            table.clearSelection();
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

        btnDangXuat.addActionListener(e -> {
            JFrame mainFrame = (JFrame) SwingUtilities.getWindowAncestor(this);
            mainFrame.dispose();

            LoginPanel login = new LoginPanel();
            login.addWindowListener(new java.awt.event.WindowAdapter() {
                @Override
                public void windowClosed(java.awt.event.WindowEvent e) {
                    if (login.isSucceeded()) {
                        new Main(login.getUserRole()).setVisible(true);
                    } else {
                        System.exit(0);
                    }
                }
            });
        });

        add(panelTable, BorderLayout.CENTER);
        loadStaffTable();

        JPanel panelBottom = new JPanel(new BorderLayout(0,0));
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
                    staff.getBirth().format(DateTimeFormatter.ofPattern("dd/MM/yyyy")),
                    staff.getPhone(),
                    staff.getAddress(),
                    staff.getEmail(),
                    staff.getWage(),
                    (Integer.parseInt(staff.getExist()) == 0) ? "Không" : "Có"
                });
            }
        } else {
            JOptionPane.showMessageDialog(this, "Không thể tải danh sách thủ thư", "Lỗi", JOptionPane.ERROR_MESSAGE);
        }
    }


}