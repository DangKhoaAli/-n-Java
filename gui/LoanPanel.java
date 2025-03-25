import java.awt.*;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;

public class LoanPanel extends JPanel {
    private JTable table;
    private DefaultTableModel tableModel;

    private JTextField txtPhieuMuon;
    private JTextField txtNgayMuon;
    private JTextField txtNgayDuKienTra;
    private JTextField txtPhiMuon;

    private JTextField txtTuKhoa;

    private JButton btnThem;
    private JButton btnSua;
    private JButton btnLuu;
    private JButton btnXoa;
    private JButton btnHuy;
    private JButton btnTim;

    public LoanPanel() {
        setBackground(Color.GRAY);
        setLayout(new BorderLayout(10,10));

        JPanel panelTable = new JPanel(new BorderLayout());
        panelTable.setBackground(Color.GRAY);

        String[] columnNames = {"Mã Phiếu Mượn", "Ngày Mượn", "Ngày Dự Kiến Trả", "Phí Mượn"};
        tableModel = new DefaultTableModel(columnNames,0);

        tableModel.addRow(new Object[]{"L001","03/25/2025", "06/20/2025", "10000"});

        table = new JTable(tableModel);
        table.setBackground(Color.WHITE);
        table.setSelectionBackground(Color.YELLOW);
        JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.getViewport().setBackground(Color.WHITE);
        panelTable.add(scrollPane, BorderLayout.CENTER);

        JPanel panelInput = new JPanel();
        panelInput.setLayout(new GridLayout(4,2,5,5));
        panelInput.setBackground(Color.GRAY);

        panelInput.add(new JLabel("Mã phiếu mượn:"));
        txtPhieuMuon = new JTextField();
        panelInput.add(txtPhieuMuon);

        panelInput.add(new JLabel("Ngày mượn:"));
        txtNgayMuon = new JTextField();
        panelInput.add(txtNgayMuon);

        panelInput.add(new JLabel("Ngày dự kiến trả:"));
        txtNgayDuKienTra = new JTextField();
        panelInput.add(txtNgayDuKienTra);
        
        panelInput.add(new JLabel("Phí mượn:"));
        txtPhiMuon = new JTextField();
        panelInput.add(txtPhiMuon);
        
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
            String PhieuMuon = txtPhieuMuon.getText();
            String NgayMuon = txtNgayMuon.getText();
            String NgayDuKienTra = txtNgayDuKienTra.getText();
            String PhiMuon = txtPhiMuon.getText();

            boolean isDuplicate = false;
            for(int i = 0; i < tableModel.getRowCount(); i++) {
                String existingID = tableModel.getValueAt(i, 0).toString();
                if(existingID.equalsIgnoreCase(PhieuMuon)) {
                    isDuplicate = true;
                    break;
                }
            }
            if(isDuplicate) {
                JOptionPane.showMessageDialog(this,
                "Mã phiếu mượn đã tồn tại, vui lòng nhập mã khác!",
                "Lỗi trùng mã",
                JOptionPane.ERROR_MESSAGE);
            } else {
                tableModel.addRow((new Object[]{PhieuMuon,NgayMuon,NgayDuKienTra,PhiMuon}));
                JOptionPane.showMessageDialog(this, "Thêm phiếu mượn mới thành công!");
            }
        });

        btnSua.addActionListener(e -> {
            int selectedRow = table.getSelectedRow();
            if(selectedRow == -1) {
                JOptionPane.showMessageDialog(this, "Vui lòng chọn dòng để sửa");
                return;
            }
            txtPhieuMuon.setText(tableModel.getValueAt(selectedRow,0).toString());
            txtNgayMuon.setText(tableModel.getValueAt(selectedRow,1).toString());
            txtNgayDuKienTra.setText(tableModel.getValueAt(selectedRow, 2).toString());
            txtPhiMuon.setText(tableModel.getValueAt(selectedRow, 3).toString());
        });

        btnLuu.addActionListener(e -> {
            int selectedRow = table.getSelectedRow();
            if(selectedRow == -1) {
                JOptionPane.showMessageDialog(this, "Vui lòng chọn dòng muốn lưu thay đổi");
                return;
            }
            tableModel.setValueAt(txtPhieuMuon.getText(),selectedRow, 0);
            tableModel.setValueAt(txtNgayMuon.getText(),selectedRow,1);
            tableModel.setValueAt(txtNgayDuKienTra.getText(), selectedRow, 2);
            tableModel.setValueAt(txtPhiMuon.getText(),selectedRow, 3);
            JOptionPane.showMessageDialog(this, "Cập nhật phiếu mượn thành công!");
        });

        btnXoa.addActionListener(e -> {
            int selectedRow = table.getSelectedRow();
            if(selectedRow == -1) {
                JOptionPane.showMessageDialog(this, "Vui lòng chọn dòng để xóa");
                return;
            }
            tableModel.removeRow(selectedRow);
            JOptionPane.showMessageDialog(this, "Xóa phiếu mượn thành công!");
        });

        btnHuy.addActionListener(e -> {
            txtPhieuMuon.setText("");
            txtNgayMuon.setText("");
            txtNgayDuKienTra.setText("");
            txtPhiMuon.setText("");
        });

        btnTim.addActionListener(e -> {
            String keyword = txtTuKhoa.getText().trim().toLowerCase();
            if(!keyword .isEmpty()) {
                for(int i = 0; i < tableModel.getRowCount(); i++) {
                    String maPhieumuon = tableModel.getValueAt(i, 0).toString();
                    if(maPhieumuon.contains(keyword)) {
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