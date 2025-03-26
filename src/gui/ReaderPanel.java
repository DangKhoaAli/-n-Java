package gui;

import java.awt.*;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;

public class ReaderPanel extends JPanel {
    private JTable table;
    private DefaultTableModel tableModel;

    private JTextField txtMaDocGia;
    private JTextField txtTenDocGia;
    private JTextField txtNgaySinh;
    private JTextField txtSoDienThoai;
    private JTextField txtDiaChi;
    private JTextField txtNgayDangKy;

    private JTextField txtTuKhoa;

    private JButton btnThem;
    private JButton btnSua;
    private JButton btnLuu;
    private JButton btnXoa;
    private JButton btnHuy;
    private JButton btnTim;

    public ReaderPanel() {
        setBackground(Color.GRAY);
        setLayout(new BorderLayout(10,10));

        JPanel panelTable = new JPanel(new BorderLayout());
        panelTable.setBackground(Color.GRAY);

        String[] columnNames = {"Mã Độc Giả", "Họ Tên", "Ngày Sinh", "Số Điện Thoại", "Địa Chỉ", "Ngày Đăng Ký"};
        tableModel = new DefaultTableModel(columnNames,0);

        tableModel.addRow(new Object[]{"DG001","Nguyễn Văn A","01/01/2001","0987654321","A","03/25/2025"});

        table = new JTable(tableModel);
        table.setBackground(Color.WHITE);
        table.setSelectionBackground(Color.YELLOW);
        JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.getViewport().setBackground(Color.WHITE);
        panelTable.add(scrollPane, BorderLayout.CENTER);

        JPanel panelInput = new JPanel();
        panelInput.setLayout(new GridLayout(6,2,5,5));
        panelInput.setBackground(Color.GRAY);

        panelInput.add(new JLabel("Mã độc giả:"));
        txtMaDocGia = new JTextField();
        panelInput.add(txtMaDocGia);

        panelInput.add(new JLabel("Họ và tên:"));
        txtTenDocGia = new JTextField();
        panelInput.add(txtTenDocGia);
        
        panelInput.add(new JLabel("Ngày sinh:"));
        txtNgaySinh = new JTextField();
        panelInput.add(txtNgaySinh);
        
        panelInput.add(new JLabel("Số điện thoại:"));
        txtSoDienThoai = new JTextField();
        panelInput.add(txtSoDienThoai);

        panelInput.add(new JLabel("Địa chỉ:"));
        txtDiaChi = new JTextField();
        panelInput.add(txtDiaChi);
        
        panelInput.add(new JLabel("Ngày đăng ký:"));
        txtNgayDangKy = new JTextField();
        panelInput.add(txtNgayDangKy);

        // Panel tìm kiếm
        JPanel panelSearch = new JPanel();
        panelSearch.setBackground(Color.GRAY);
        panelSearch.add(new JLabel("Từ khóa:"));
        txtTuKhoa = new JTextField(20);
        panelSearch.add(txtTuKhoa);
        btnTim = new JButton("Tìm");
        panelSearch.add(btnTim);
        
        // Panel chứa các nút
        JPanel panelButtons = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        panelButtons.setBackground(Color.GRAY);
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
            String maDocGia = txtMaDocGia.getText();
            String tenDocGia = txtTenDocGia.getText();
            String ngaySinh = txtNgaySinh.getText();
            String soDienThoai = txtSoDienThoai.getText();
            String diaChi = txtDiaChi.getText();
            String ngayDangKy = txtNgayDangKy.getText();

            boolean isDuplicate = false;
            for(int i = 0; i < tableModel.getRowCount(); i++) {
                String existingID = tableModel.getValueAt(i, 0).toString();
                if(existingID.equalsIgnoreCase(maDocGia)) {
                    isDuplicate = true;
                    break;
                }
            }
            if(isDuplicate) {
                JOptionPane.showMessageDialog(this,
                "Mã độc giả đã tồn tại, vui lòng nhập mã khác!",
                "Lỗi trùng mã",
                JOptionPane.ERROR_MESSAGE);
            } else {
                tableModel.addRow((new Object[]{maDocGia,tenDocGia,ngaySinh,soDienThoai,diaChi,ngayDangKy}));
                JOptionPane.showMessageDialog(this, "Thêm độc giả mới thành công!");
            }
        });

        btnSua.addActionListener(e -> {
            int selectedRow = table.getSelectedRow();
            if(selectedRow == -1) {
                JOptionPane.showMessageDialog(this, "Vui lòng chọn dòng để sửa");
                return;
            }
            txtMaDocGia.setText(tableModel.getValueAt(selectedRow,0).toString());
            txtTenDocGia.setText(tableModel.getValueAt(selectedRow,1).toString());
            txtNgaySinh.setText(tableModel.getValueAt(selectedRow, 2).toString());
            txtSoDienThoai.setText(tableModel.getValueAt(selectedRow, 3).toString());
            txtDiaChi.setText(tableModel.getValueAt(selectedRow, 4).toString());
            txtNgayDangKy.setText(tableModel.getValueAt(selectedRow, 5).toString());
        });

        btnLuu.addActionListener(e -> {
            int selectedRow = table.getSelectedRow();
            if(selectedRow == -1) {
                JOptionPane.showMessageDialog(this, "Vui lòng chọn dòng muốn lưu thay đổi");
                return;
            }
            tableModel.setValueAt(txtMaDocGia.getText(),selectedRow, 0);
            tableModel.setValueAt(txtTenDocGia.getText(),selectedRow,1);
            tableModel.setValueAt(txtNgaySinh.getText(),selectedRow, 2);
            tableModel.setValueAt(txtSoDienThoai.getText(),selectedRow, 3);
            tableModel.setValueAt(txtDiaChi.getText(),selectedRow, 4);
            tableModel.setValueAt(txtNgayDangKy.getText(),selectedRow, 5);
            JOptionPane.showMessageDialog(this, "Cập nhật độc giả thành công!");
        });

        btnXoa.addActionListener(e -> {
            int selectedRow = table.getSelectedRow();
            if(selectedRow == -1) {
                JOptionPane.showMessageDialog(this, "Vui lòng chọn dòng để xóa");
                return;
            }
            tableModel.removeRow(selectedRow);
            JOptionPane.showMessageDialog(this, "Xóa độc giả thành công!");
        });

        btnHuy.addActionListener(e -> {
            txtMaDocGia.setText("");
            txtTenDocGia.setText("");
            txtNgaySinh.setText("");
            txtSoDienThoai.setText("");
            txtDiaChi.setText("");
            txtNgayDangKy.setText("");
        });

        btnTim.addActionListener(e -> {
            String keyword = txtTuKhoa.getText().trim().toLowerCase();
            if(!keyword .isEmpty()) {
                for(int i = 0; i < tableModel.getRowCount(); i++) {
                    String madg = tableModel.getValueAt(i, 0).toString();
                    if(madg.contains(keyword)) {
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