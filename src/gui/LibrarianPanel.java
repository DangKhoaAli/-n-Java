package gui;

import java.awt.*;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;

public class LibrarianPanel extends JPanel {
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
        setBackground(Color.BLUE);
        setLayout(new BorderLayout(10,10));

        JPanel panelTable = new JPanel(new BorderLayout());
        panelTable.setBackground(Color.BLUE);

        String[] columnNames = {"Mã Thủ Thư", "Họ  và Tên", "Giới Tính", "Ngày Sinh", "Số Điện Thoại", "Địa Chỉ", "Email", "Lương"};
        tableModel = new DefaultTableModel(columnNames,0);

        tableModel.addRow(new Object[]{"TT001","Nguyễn Văn A", "Nam", "01/01/2001","0987654321","A","a@email.com", "1000000000"});

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
            String maThuThu = txtMaThuThu.getText();
            String tenThuThu = txtTenThuThu.getText();
            String gioiTinh = txtGioiTinh.getText();
            String ngaySinh = txtNgaySinh.getText();
            String soDienThoai = txtSoDienThoai.getText();
            String diaChi = txtDiaChi.getText();
            String email = txtEmail.getText();
            String Luong = txtLuong.getText();

            boolean isDuplicate = false;
            for(int i = 0; i < tableModel.getRowCount(); i++) {
                String existingID = tableModel.getValueAt(i, 0).toString();
                if(existingID.equalsIgnoreCase(maThuThu)) {
                    isDuplicate = true;
                    break;
                }
            }
            if(isDuplicate) {
                JOptionPane.showMessageDialog(this,
                "Mã thủ thư đã tồn tại, vui lòng nhập mã khác!",
                "Lỗi trùng mã",
                JOptionPane.ERROR_MESSAGE);
            } else {
                tableModel.addRow((new Object[]{maThuThu,tenThuThu,gioiTinh,ngaySinh,soDienThoai,diaChi,email,Luong}));
                JOptionPane.showMessageDialog(this, "Thêm thủ thư mới thành công!");
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
            int selectedRow = table.getSelectedRow();
            if(selectedRow == -1) {
                JOptionPane.showMessageDialog(this, "Vui lòng chọn dòng muốn lưu thay đổi");
                return;
            }
            tableModel.setValueAt(txtMaThuThu.getText(),selectedRow, 0);
            tableModel.setValueAt(txtTenThuThu.getText(),selectedRow,1);
            tableModel.setValueAt(txtGioiTinh.getText(), selectedRow, 2);
            tableModel.setValueAt(txtNgaySinh.getText(),selectedRow, 3);
            tableModel.setValueAt(txtSoDienThoai.getText(),selectedRow, 4);
            tableModel.setValueAt(txtDiaChi.getText(),selectedRow, 5);
            tableModel.setValueAt(txtEmail.getText(), selectedRow, 6);
            tableModel.setValueAt(txtLuong.getText(),selectedRow, 7);
            JOptionPane.showMessageDialog(this, "Cập nhật thủ thư thành công!");
        });

        btnXoa.addActionListener(e -> {
            int selectedRow = table.getSelectedRow();
            if(selectedRow == -1) {
                JOptionPane.showMessageDialog(this, "Vui lòng chọn dòng để xóa");
                return;
            }
            tableModel.removeRow(selectedRow);
            JOptionPane.showMessageDialog(this, "Xóa thủ thư thành công!");
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
            if(!keyword .isEmpty()) {
                for(int i = 0; i < tableModel.getRowCount(); i++) {
                    String matt = tableModel.getValueAt(i, 0).toString();
                    if(matt.contains(keyword)) {
                        table.setRowSelectionInterval(i, i);
                        break;
                    }
                }
            }
        });

        add(panelTable, BorderLayout.CENTER);

        JPanel panelBottom = new JPanel(new BorderLayout(10,10));
        panelBottom.add(panelSearch, BorderLayout.NORTH);
        panelBottom.add(panelInput, BorderLayout.CENTER);
        panelBottom.add(panelButtons, BorderLayout.SOUTH);

        add(panelBottom, BorderLayout.SOUTH);
    }


}