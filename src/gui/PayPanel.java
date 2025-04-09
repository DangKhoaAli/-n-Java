package gui;

import java.awt.*;
import java.util.*;
import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableModel;

public class PayPanel extends JPanel {
    private JTable table;
    private DefaultTableModel tableModel;

    private JTextField txtPhieuTra;
    private JTextField txtPhieuMuon;
    private JTextField txtThuThu;
    private JTextField txtSoLuongSach;
    private JTextField txtNgayTra;
    private JTextField txtPhiTreHan;
    private JTextField txtPhiHuHai;

    private JTextField txtTuKhoa;

    private JButton btnThem;
    private JButton btnSua;
    private JButton btnXoa;
    private JButton btnHuy;
    private JButton btnTim;
    private JButton btnXemChiTiet;

    private Map<String, java.util.List<Object[]>> payDetailsMap = new HashMap<>();

    public PayPanel() {
        setBackground(Color.BLUE);
        setLayout(new BorderLayout(10,10));

        JPanel panelTable = new JPanel(new BorderLayout());
        panelTable.setBackground(Color.BLUE);

        String[] columnNames = {"Mã Phiếu Trả","Mã phiếu mượn","Mã thủ thư","Số lượng sách", "Ngày Trả", "Phí Trễ Hạn", "Phí Hư Hại"};
        tableModel = new DefaultTableModel(columnNames,0);

        tableModel.addRow(new Object[]{"P001","L001","TT001","2","03/25/2025", "100000", "2000"});

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
                        txtPhieuTra.setText(tableModel.getValueAt(selectedRow, 0).toString());
                        txtPhieuMuon.setText(tableModel.getValueAt(selectedRow, 1).toString());
                        txtThuThu.setText(tableModel.getValueAt(selectedRow, 2).toString());
                        txtSoLuongSach.setText(tableModel.getValueAt(selectedRow, 3).toString());
                        txtNgayTra.setText(tableModel.getValueAt(selectedRow, 4).toString());
                        txtPhiTreHan.setText(tableModel.getValueAt(selectedRow, 5).toString());
                        txtPhiHuHai.setText(tableModel.getValueAt(selectedRow, 6).toString());
                    }
                }
            }
        });

        txtPhieuTra = new JTextField();
        txtPhieuTra.setBorder(BorderFactory.createTitledBorder("Mã phiếu trả"));

        txtPhieuMuon = new JTextField();
        txtPhieuMuon.setBorder(BorderFactory.createTitledBorder("Mã phiếu mượn"));

        txtThuThu = new JTextField();
        txtThuThu.setBorder(BorderFactory.createTitledBorder("Mã thủ thư"));

        txtSoLuongSach = new JTextField();
        txtSoLuongSach.setBorder(BorderFactory.createTitledBorder("Số lượng sách"));

        txtNgayTra = new JTextField();
        txtNgayTra.setBorder(BorderFactory.createTitledBorder("Ngày trả"));

        txtPhiTreHan = new JTextField();
        txtPhiTreHan.setBorder(BorderFactory.createTitledBorder("Phí trễ hạn"));
        txtPhiTreHan.setEditable(false);
        txtPhiTreHan.setText("0");

        txtPhiHuHai = new JTextField();
        txtPhiHuHai.setBorder(BorderFactory.createTitledBorder("Phí hư hại"));
        txtPhiHuHai.setEditable(false);
        txtPhiHuHai.setText("0");

        JPanel panelInput = new JPanel();
        panelInput.setLayout(new BoxLayout(panelInput, BoxLayout.Y_AXIS));
        panelInput.setBackground(Color.BLUE);
        panelInput.add(txtPhieuTra);
        panelInput.add(txtPhieuMuon);
        panelInput.add(txtThuThu);
        panelInput.add(txtSoLuongSach);
        panelInput.add(txtNgayTra);
        panelInput.add(txtPhiTreHan);
        panelInput.add(txtPhiHuHai);
        
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
        btnXoa = new JButton("Xóa");
        btnHuy = new JButton("Hủy");
        btnXemChiTiet = new JButton("Xem chi tiết");
        
        panelButtons.add(btnThem);
        panelButtons.add(btnSua);
        panelButtons.add(btnXoa);
        panelButtons.add(btnHuy);
        panelButtons.add(btnXemChiTiet);

        JPanel panelBottom = new JPanel(new BorderLayout(10, 10));
        panelBottom.setBackground(Color.BLUE);
        panelBottom.add(panelSearch, BorderLayout.NORTH);
        panelBottom.add(panelInput, BorderLayout.CENTER);
        panelBottom.add(panelButtons, BorderLayout.SOUTH);

        // --- Giao diện chính ---
        add(panelTable, BorderLayout.CENTER);
        add(panelBottom, BorderLayout.SOUTH);
        
        btnThem.addActionListener(e -> {
            String PhieuTra = txtPhieuTra.getText();
            String NgayTra = txtNgayTra.getText();
            String PhieuMuon = txtPhieuMuon.getText();
            String thuThu = txtThuThu.getText();
            String soLuongSach = txtSoLuongSach.getText();
            String PhiTreHan = txtPhiTreHan.getText();
            String PhiHuHai = txtPhiHuHai.getText();

            boolean isDuplicate = false;
            for(int i = 0; i < tableModel.getRowCount(); i++) {
                String existingID = tableModel.getValueAt(i, 0).toString();
                if(existingID.equalsIgnoreCase(PhieuTra)) {
                    isDuplicate = true;
                    break;
                }
            }
            if(isDuplicate) {
                JOptionPane.showMessageDialog(this,
                "Mã phiếu trả đã tồn tại, vui lòng nhập mã khác!",
                "Lỗi trùng mã",
                JOptionPane.ERROR_MESSAGE);
            } else {
                tableModel.addRow((new Object[]{PhieuTra,PhieuMuon,thuThu,soLuongSach,NgayTra,PhiTreHan,PhiHuHai}));
                JOptionPane.showMessageDialog(this, "Thêm phiếu trả mới thành công!");

                int n = 0;
                try {
                    n = Integer.parseInt(soLuongSach);
                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(this, "Số lượng sách không hợp lệ!");
                    return;
                }
                java.util.List<Object[]> detailsList = new ArrayList<>();
                for(int i = 1; i <=n; i++) {
                    String maChiTiet = PhieuTra + "-" + i;
                    String maSachDetail = "";
                    while (true) { 
                        maSachDetail = JOptionPane.showInputDialog(this, "Nhập mã sách cho cuốn thứ " + i +":");
                        if(maSachDetail == null || maSachDetail.trim().isEmpty()) {
                            JOptionPane.showMessageDialog(this, "Mã sách không được để trống. Vui lòng nhập lại!");
                        } else {
                            break;
                        }
                    }
                    String tenSachDetail = "";
                    while (true) { 
                        tenSachDetail = JOptionPane.showInputDialog(this,"Nhập tên sách cho cuốn thứ " + i + ":");
                        if(tenSachDetail == null || tenSachDetail.trim().isEmpty()) {
                            JOptionPane.showInputDialog(this, "Tên sách không được để trống. Vui lòng nhập lại!");
                        } else {
                            break;
                        }
                    }
                    String tinhTrangHuHongStr = JOptionPane.showInputDialog(this, "Nhập tình trạng hư hỏng của cuốn sách thứ " + i + "(mặc định là 0):");
                    int tinhTrangHuHong = 0;
                    try {
                        tinhTrangHuHong = Integer.parseInt(tinhTrangHuHongStr.trim());
                    } catch (NumberFormatException ex) {
                        tinhTrangHuHong = 0;
                    }
                    String phiPhatStr = JOptionPane.showInputDialog(this, "Nhập phí phạt của cuốn sách thứ " + i + "(mặc định là 0):");
                    double phiPhat = 0;
                    try {
                        phiPhat = Double.parseDouble(phiPhatStr);
                    } catch (NumberFormatException ex) {
                        phiPhat = 0;
                    }
                    detailsList.add(new Object[]{maChiTiet,maSachDetail,tenSachDetail,tinhTrangHuHong,phiPhat});
                }
                payDetailsMap.put(PhieuTra, detailsList);
            }
        });

        btnSua.addActionListener(e -> {
            int selectedRow = table.getSelectedRow();
            if(selectedRow == -1) {
                JOptionPane.showMessageDialog(this, "Vui lòng chọn dòng muốn lưu thay đổi");
                return;
            }
            tableModel.setValueAt(txtPhieuTra.getText(),selectedRow, 0);
            tableModel.setValueAt(txtPhieuMuon.getText(), selectedRow, 1);
            tableModel.setValueAt(txtThuThu.getText(), selectedRow, 2);
            tableModel.setValueAt(txtSoLuongSach.getText(), selectedRow, 3);
            tableModel.setValueAt(txtNgayTra.getText(),selectedRow,4);
            tableModel.setValueAt(txtPhiTreHan.getText(), selectedRow, 5);
            tableModel.setValueAt(txtPhiHuHai.getText(),selectedRow, 6);
            JOptionPane.showMessageDialog(this, "Cập nhật phiếu trả thành công!");
        });

        btnXoa.addActionListener(e -> {
            int selectedRow = table.getSelectedRow();
            if(selectedRow == -1) {
                JOptionPane.showMessageDialog(this, "Vui lòng chọn dòng để xóa");
                return;
            }
            tableModel.removeRow(selectedRow);
            JOptionPane.showMessageDialog(this, "Xóa phiếu trả thành công!");
        });

        btnHuy.addActionListener(e -> {
            txtPhieuTra.setText("");
            txtPhieuMuon.setText("");
            txtThuThu.setText("");
            txtSoLuongSach.setText("");
            txtNgayTra.setText("");
            txtPhiTreHan.setText("");
            txtPhiHuHai.setText("");
            table.clearSelection();
        });

        btnTim.addActionListener(e -> {
            String keyword = txtTuKhoa.getText().trim().toLowerCase();
            if(!keyword .isEmpty()) {
                for(int i = 0; i < tableModel.getRowCount(); i++) {
                    String maPhieuTra = tableModel.getValueAt(i, 0).toString();
                    if(maPhieuTra.contains(keyword)) {
                        table.setRowSelectionInterval(i, i);
                        break;
                    }
                }
            }
        });

        btnXemChiTiet.addActionListener(e -> {
            int selectedRow = table.getSelectedRow();
            if(selectedRow == -1) {
                JOptionPane.showMessageDialog(this, "Vui lòng chọn dòng để xem chi tiết!");
                return;
            }
            Object[] payData = new Object[tableModel.getColumnCount()];
            for(int i = 0; i < tableModel.getColumnCount(); i++) {
                payData[i] = tableModel.getValueAt(selectedRow, i);
            }
            String phieuTra = payData[0].toString();
            java.util.List<Object[]> detailsList = payDetailsMap.get(phieuTra);
            new PayDetails(payData, detailsList);
        });

        add(panelTable, BorderLayout.CENTER);

        // JPanel panelBottom = new JPanel(new BorderLayout(10,10));
        panelBottom.add(panelSearch, BorderLayout.NORTH);
        panelBottom.add(panelInput, BorderLayout.CENTER);
        panelBottom.add(panelButtons, BorderLayout.SOUTH);

        add(panelBottom, BorderLayout.SOUTH);
    }


}