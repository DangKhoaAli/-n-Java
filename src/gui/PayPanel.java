package gui;

import java.awt.*;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;

public class PayPanel extends JPanel {
    private JTable table;
    private DefaultTableModel tableModel;

    private JTextField txtPhieuTra;
    private JTextField txtNgayTra;
    private JTextField txtPhiTreHan;
    private JTextField txtPhiHuHai;

    private JTextField txtTuKhoa;

    private JButton btnThem;
    private JButton btnSua;
    private JButton btnLuu;
    private JButton btnXoa;
    private JButton btnHuy;
    private JButton btnTim;

    public PayPanel() {
        setBackground(Color.GRAY);
        setLayout(new BorderLayout(10,10));

        JPanel panelTable = new JPanel(new BorderLayout());
        panelTable.setBackground(Color.GRAY);

        String[] columnNames = {"Mã Phiếu Trả", "Ngày Trả", "Phí Trễ Hạn", "Phí Hư Hại"};
        tableModel = new DefaultTableModel(columnNames,0);

        tableModel.addRow(new Object[]{"L001","03/25/2025", "100000", "2000"});

        table = new JTable(tableModel);
        table.setBackground(Color.WHITE);
        table.setSelectionBackground(Color.YELLOW);
        JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.getViewport().setBackground(Color.WHITE);
        panelTable.add(scrollPane, BorderLayout.CENTER);

        JPanel panelInput = new JPanel();
        panelInput.setLayout(new GridLayout(4,2,5,5));
        panelInput.setBackground(Color.GRAY);

        panelInput.add(new JLabel("Mã phiếu trả:"));
        txtPhieuTra = new JTextField();
        panelInput.add(txtPhieuTra);

        panelInput.add(new JLabel("Ngày trả:"));
        txtNgayTra = new JTextField();
        panelInput.add(txtNgayTra);

        panelInput.add(new JLabel("Phí trễ hạn:"));
        txtPhiTreHan = new JTextField();
        panelInput.add(txtPhiTreHan);
        
        panelInput.add(new JLabel("Phí hư hại:"));
        txtPhiHuHai = new JTextField();
        panelInput.add(txtPhiHuHai);
        
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
            String PhieuTra = txtPhieuTra.getText();
            String NgayTra = txtNgayTra.getText();
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
                tableModel.addRow((new Object[]{PhieuTra,NgayTra,PhiTreHan,PhiHuHai}));
                JOptionPane.showMessageDialog(this, "Thêm phiếu trả mới thành công!");
            }
        });

        btnSua.addActionListener(e -> {
            int selectedRow = table.getSelectedRow();
            if(selectedRow == -1) {
                JOptionPane.showMessageDialog(this, "Vui lòng chọn dòng để sửa");
                return;
            }
            txtPhieuTra.setText(tableModel.getValueAt(selectedRow,0).toString());
            txtNgayTra.setText(tableModel.getValueAt(selectedRow,1).toString());
            txtPhiTreHan.setText(tableModel.getValueAt(selectedRow, 2).toString());
            txtPhiHuHai.setText(tableModel.getValueAt(selectedRow, 3).toString());
        });

        btnLuu.addActionListener(e -> {
            int selectedRow = table.getSelectedRow();
            if(selectedRow == -1) {
                JOptionPane.showMessageDialog(this, "Vui lòng chọn dòng muốn lưu thay đổi");
                return;
            }
            tableModel.setValueAt(txtPhieuTra.getText(),selectedRow, 0);
            tableModel.setValueAt(txtNgayTra.getText(),selectedRow,1);
            tableModel.setValueAt(txtPhiTreHan.getText(), selectedRow, 2);
            tableModel.setValueAt(txtPhiHuHai.getText(),selectedRow, 3);
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
            txtNgayTra.setText("");
            txtPhiTreHan.setText("");
            txtPhiHuHai.setText("");
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

        add(panelTable, BorderLayout.CENTER);

        JPanel panelBottom = new JPanel(new BorderLayout(10,10));
        panelBottom.add(panelSearch, BorderLayout.NORTH);
        panelBottom.add(panelInput, BorderLayout.CENTER);
        panelBottom.add(panelButtons, BorderLayout.SOUTH);

        add(panelBottom, BorderLayout.SOUTH);
    }


}