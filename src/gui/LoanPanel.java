package gui;

import java.awt.*;
import java.util.*;
import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableModel;

public class LoanPanel extends JPanel {
    private JTable table;
    private DefaultTableModel tableModel;

    private JTextField txtPhieuMuon;
    private JTextField txtDocGia;
    private JTextField txtThuThu;
    private JTextField txtSoLuongSach;
    private JTextField txtNgayMuon;
    private JTextField txtNgayDuKienTra;
    private JTextField txtPhiMuon;

    private JTextField txtTuKhoa;

    private JButton btnThem;
    private JButton btnSua;
    private JButton btnXoa;
    private JButton btnHuy;
    private JButton btnTim;
    private JButton btnXemChiTiet;

    // Map lưu chi tiết phiếu mượn (key: mã phiếu mượn, value: danh sách chi tiết)
    private Map<String, java.util.List<Object[]>> loanDetailsMap = new HashMap<>();

    public LoanPanel() {
        setBackground(Color.BLUE);
        setLayout(new BorderLayout(10, 10));

        // --- Panel bảng hiển thị phiếu mượn ---
        JPanel panelTable = new JPanel(new BorderLayout());
        panelTable.setBackground(Color.BLUE);

        String[] columnNames = {"Mã Phiếu Mượn", "Mã độc giả", "Mã thủ thư", "Số lượng sách", "Ngày Mượn", "Ngày Dự Kiến Trả", "Phí Mượn"};
        tableModel = new DefaultTableModel(columnNames, 0);
        // Ví dụ: thêm một phiếu mượn mẫu
        tableModel.addRow(new Object[]{"L001", "DG001", "TT001", "5", "03/25/2025", "06/20/2025", "10000"});

        table = new JTable(tableModel);
        table.setBackground(Color.WHITE);
        table.setSelectionBackground(Color.YELLOW);
        JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.getViewport().setBackground(Color.WHITE);
        panelTable.add(scrollPane, BorderLayout.CENTER);

        table.getSelectionModel().addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent e) {
                // Tránh xử lý hai lần (khi bắt đầu và khi kết thúc)
                if (!e.getValueIsAdjusting()) {
                    int selectedRow = table.getSelectedRow();
                    if (selectedRow != -1) {
                        txtPhieuMuon.setText(tableModel.getValueAt(selectedRow, 0).toString());
                        txtDocGia.setText(tableModel.getValueAt(selectedRow, 1).toString());
                        txtThuThu.setText(tableModel.getValueAt(selectedRow, 2).toString());
                        txtSoLuongSach.setText(tableModel.getValueAt(selectedRow, 3).toString());
                        txtNgayMuon.setText(tableModel.getValueAt(selectedRow, 4).toString());
                        txtNgayDuKienTra.setText(tableModel.getValueAt(selectedRow, 5).toString());
                        txtPhiMuon.setText(tableModel.getValueAt(selectedRow, 6).toString());
                    }
                }
            }
        });

        // --- Panel nhập thông tin phiếu mượn ---
        txtPhieuMuon = new JTextField();
        txtPhieuMuon.setBorder(BorderFactory.createTitledBorder("Mã phiếu mượn"));
        txtDocGia = new JTextField();
        txtDocGia.setBorder(BorderFactory.createTitledBorder("Mã độc giả"));
        txtThuThu = new JTextField();
        txtThuThu.setBorder(BorderFactory.createTitledBorder("Mã thủ thư"));
        txtSoLuongSach = new JTextField();
        txtSoLuongSach.setBorder(BorderFactory.createTitledBorder("Số lượng sách"));
        txtNgayMuon = new JTextField();
        txtNgayMuon.setBorder(BorderFactory.createTitledBorder("Ngày mượn"));
        txtNgayDuKienTra = new JTextField();
        txtNgayDuKienTra.setBorder(BorderFactory.createTitledBorder("Ngày dự kiến trả"));
        txtPhiMuon = new JTextField();
        txtPhiMuon.setBorder(BorderFactory.createTitledBorder("Phí mượn"));

        JPanel panelInput = new JPanel();
        panelInput.setLayout(new BoxLayout(panelInput, BoxLayout.Y_AXIS));
        panelInput.setBackground(Color.BLUE);
        panelInput.add(txtPhieuMuon);
        panelInput.add(txtDocGia);
        panelInput.add(txtThuThu);
        panelInput.add(txtSoLuongSach);
        panelInput.add(txtNgayMuon);
        panelInput.add(txtNgayDuKienTra);
        panelInput.add(txtPhiMuon);

        // --- Panel tìm kiếm ---
        JPanel panelSearch = new JPanel();
        panelSearch.setBackground(Color.BLUE);
        panelSearch.add(new JLabel("Từ khóa:"));
        txtTuKhoa = new JTextField(20);
        panelSearch.add(txtTuKhoa);
        btnTim = new JButton("Tìm");
        panelSearch.add(btnTim);

        // --- Panel chứa các nút chức năng ---
        JPanel panelButtons = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        panelButtons.setBackground(Color.BLUE);
        btnThem = new JButton("Thêm");
        btnSua = new JButton("Sửa");
        btnXoa = new JButton("Xóa");
        btnHuy = new JButton("Hủy");
        btnXemChiTiet = new JButton("Xem chi tiết");

        panelButtons.add(btnThem);
        panelButtons.add(btnSua);
        panelButtons.add(btnXoa);
        panelButtons.add(btnHuy);
        panelButtons.add(btnXemChiTiet);

        // --- Gộp các panel nhập, tìm kiếm và nút ---
        JPanel panelBottom = new JPanel(new BorderLayout(10, 10));
        panelBottom.setBackground(Color.BLUE);
        panelBottom.add(panelSearch, BorderLayout.NORTH);
        panelBottom.add(panelInput, BorderLayout.CENTER);
        panelBottom.add(panelButtons, BorderLayout.SOUTH);

        // --- Giao diện chính ---
        add(panelTable, BorderLayout.CENTER);
        add(panelBottom, BorderLayout.SOUTH);

        // --- Xử lý các nút chức năng ---
        btnThem.addActionListener(e -> {
            String PhieuMuon = txtPhieuMuon.getText().trim();
            String docGia = txtDocGia.getText().trim();
            String thuThu = txtThuThu.getText().trim();
            String soLuongSach = txtSoLuongSach.getText().trim();
            String NgayMuon = txtNgayMuon.getText().trim();
            String NgayDuKienTra = txtNgayDuKienTra.getText().trim();
            String PhiMuon = txtPhiMuon.getText().trim();

            // Kiểm tra bắt buộc nhập: mã phiếu mượn và số lượng sách (bạn có thể thêm kiểm tra cho các trường khác nếu cần)
            if (PhieuMuon.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Vui lòng nhập mã phiếu mượn!");
                return;
            }
            if (soLuongSach.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Vui lòng nhập số lượng sách!");
                return;
            }

            // Kiểm tra trùng mã phiếu mượn
            boolean isDuplicate = false;
            for (int i = 0; i < tableModel.getRowCount(); i++) {
                String existingID = tableModel.getValueAt(i, 0).toString();
                if (existingID.equalsIgnoreCase(PhieuMuon)) {
                    isDuplicate = true;
                    break;
                }
            }
            if (isDuplicate) {
                JOptionPane.showMessageDialog(this,
                    "Mã phiếu mượn đã tồn tại, vui lòng nhập mã khác!",
                    "Lỗi trùng mã",
                    JOptionPane.ERROR_MESSAGE);
                return;
            }

            // Thêm phiếu mượn vào bảng chính
            tableModel.addRow(new Object[]{PhieuMuon, docGia, thuThu, soLuongSach, NgayMuon, NgayDuKienTra, PhiMuon});
            JOptionPane.showMessageDialog(this, "Thêm phiếu mượn mới thành công!");

            // Nhập chi tiết phiếu mượn bắt buộc nhập cho từng cuốn sách
            int n = 0;
            try {
                n = Integer.parseInt(soLuongSach);
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(this, "Số lượng sách không hợp lệ!");
                return;
            }
            java.util.List<Object[]> detailsList = new ArrayList<>();
            for (int i = 1; i <= n; i++) {
                // Tự tạo mã chi tiết phiếu mượn dựa trên mã phiếu mượn và số thứ tự
                String maChiTiet = PhieuMuon + "-" + i;

                // Bắt buộc nhập mã sách cho cuốn thứ i
                String maSachDetail = "";
                while (true) {
                    maSachDetail = JOptionPane.showInputDialog(this, "Nhập mã sách cho cuốn thứ " + i + ":");
                    if (maSachDetail == null || maSachDetail.trim().isEmpty()) {
                        JOptionPane.showMessageDialog(this, "Mã sách không được để trống. Vui lòng nhập lại!");
                    } else {
                        break;
                    }
                }

                // Bắt buộc nhập tên sách cho cuốn thứ i
                String tenSachDetail = "";
                while (true) {
                    tenSachDetail = JOptionPane.showInputDialog(this, "Nhập tên sách cho cuốn thứ " + i + ":");
                    if (tenSachDetail == null || tenSachDetail.trim().isEmpty()) {
                        JOptionPane.showMessageDialog(this, "Tên sách không được để trống. Vui lòng nhập lại!");
                    } else {
                        break;
                    }
                }

                // Bắt buộc nhập phí mượn cho cuốn thứ i
                double phiMuonDetail = 0;
                while (true) {
                    String phiMuonDetailStr = JOptionPane.showInputDialog(this, "Nhập phí mượn cho cuốn thứ " + i + ":");
                    if (phiMuonDetailStr == null || phiMuonDetailStr.trim().isEmpty()) {
                        JOptionPane.showMessageDialog(this, "Phí mượn không được để trống. Vui lòng nhập lại!");
                        continue;
                    }
                    try {
                        phiMuonDetail = Double.parseDouble(phiMuonDetailStr.trim());
                        break;
                    } catch (NumberFormatException ex) {
                        JOptionPane.showMessageDialog(this, "Phí mượn không hợp lệ, vui lòng nhập số!");
                    }
                }
                detailsList.add(new Object[]{maChiTiet, maSachDetail, tenSachDetail, phiMuonDetail});
            }
            loanDetailsMap.put(PhieuMuon, detailsList);
        });

        btnSua.addActionListener(e -> {
            int selectedRow = table.getSelectedRow();
            if (selectedRow == -1) {
                JOptionPane.showMessageDialog(this, "Vui lòng chọn dòng muốn lưu thay đổi");
                return;
            }
            tableModel.setValueAt(txtPhieuMuon.getText(), selectedRow, 0);
            tableModel.setValueAt(txtDocGia.getText(), selectedRow, 1);
            tableModel.setValueAt(txtThuThu.getText(), selectedRow, 2);
            tableModel.setValueAt(txtSoLuongSach.getText(), selectedRow, 3);
            tableModel.setValueAt(txtNgayMuon.getText(), selectedRow, 4);
            tableModel.setValueAt(txtNgayDuKienTra.getText(), selectedRow, 5);
            tableModel.setValueAt(txtPhiMuon.getText(), selectedRow, 6);
            JOptionPane.showMessageDialog(this, "Cập nhật phiếu mượn thành công!");
        });

        btnXoa.addActionListener(e -> {
            int selectedRow = table.getSelectedRow();
            if (selectedRow == -1) {
                JOptionPane.showMessageDialog(this, "Vui lòng chọn dòng để xóa");
                return;
            }
            tableModel.removeRow(selectedRow);
            JOptionPane.showMessageDialog(this, "Xóa phiếu mượn thành công!");
        });

        btnHuy.addActionListener(e -> {
            txtPhieuMuon.setText("");
            txtDocGia.setText("");
            txtThuThu.setText("");
            txtSoLuongSach.setText("");
            txtNgayMuon.setText("");
            txtNgayDuKienTra.setText("");
            txtPhiMuon.setText("");
            table.clearSelection();
        });

        btnTim.addActionListener(e -> {
            String keyword = txtTuKhoa.getText().trim().toLowerCase();
            if (!keyword.isEmpty()) {
                for (int i = 0; i < tableModel.getRowCount(); i++) {
                    String maPhieuMuon = tableModel.getValueAt(i, 0).toString();
                    if (maPhieuMuon.contains(keyword)) {
                        table.setRowSelectionInterval(i, i);
                        break;
                    }
                }
            }
        });

        btnXemChiTiet.addActionListener(e -> {
            int selectedRow = table.getSelectedRow();
            if (selectedRow == -1) {
                JOptionPane.showMessageDialog(this, "Vui lòng chọn dòng để xem chi tiết!");
                return;
            }
            Object[] loanData = new Object[tableModel.getColumnCount()];
            for (int i = 0; i < tableModel.getColumnCount(); i++) {
                loanData[i] = tableModel.getValueAt(selectedRow, i);
            }
            String phieuMuon = loanData[0].toString();
            java.util.List<Object[]> detailsList = loanDetailsMap.get(phieuMuon);
            // Mở cửa sổ chi tiết phiếu mượn
            new LoanDetails(loanData, detailsList);
        });

        setPreferredSize(new Dimension(900, 600));
        setLayout(new BorderLayout());
        add(panelTable, BorderLayout.CENTER);
        JPanel panelOuterBottom = new JPanel(new BorderLayout(10,10));
        panelOuterBottom.setBackground(Color.BLUE);
        panelOuterBottom.add(new JPanel(), BorderLayout.NORTH); // khoảng cách cho phần tìm kiếm
        panelOuterBottom.add(panelBottom, BorderLayout.CENTER);
        add(panelOuterBottom, BorderLayout.SOUTH);
    }
}
