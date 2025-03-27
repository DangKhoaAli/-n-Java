import java.awt.*;
import java.util.List;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;

public class LoanDetails extends JFrame {
    private JTable table;
    private DefaultTableModel tableModel;

    private JTextField txtMaSach;
    private JTextField txtTenSach;
    private JTextField txtPhiMuon;

    private JButton btnThem;
    private JButton btnSua;
    private JButton btnLuu;
    private JButton btnXoa;
    private JButton btnHuy;
    private JButton btnDong;

    /**
     * Constructor hiển thị chi tiết phiếu mượn của 1 loại sách.
     * @param loanData    Mảng chứa thông tin cơ bản của phiếu mượn 
     *                    (ví dụ: [Mã phiếu mượn, Tên phiếu mượn, ...])
     * @param copyDetails Danh sách chi tiết của phiếu mượn 
     *                    (mỗi phần tử là Object[] gồm: [Mã chi tiết phiếu mượn, Mã sách, Tên sách, Phí mượn])
     */
    public LoanDetails(Object[] loanData, List<Object[]> copyDetails) {
        setTitle("Chi tiết phiếu mượn - " + loanData[0]);
        setLayout(new BorderLayout(10, 10));
        getContentPane().setBackground(Color.BLUE);

        // --- Bảng chi tiết ---
        String[] columnNames = {"Mã chi tiết phiếu mượn", "Mã sách", "Tên sách", "Phí mượn"};
        tableModel = new DefaultTableModel(columnNames, 0);

        if (copyDetails != null && !copyDetails.isEmpty()) {
            for (Object[] detail : copyDetails) {
                tableModel.addRow(detail);
            }
        }

        table = new JTable(tableModel);
        table.setBackground(Color.WHITE);
        table.setSelectionBackground(Color.YELLOW);
        JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.getViewport().setBackground(Color.WHITE);
        add(scrollPane, BorderLayout.CENTER);

        JPanel panelInput = new JPanel();
        panelInput.setLayout(new BoxLayout(panelInput, BoxLayout.Y_AXIS));
        panelInput.setBackground(Color.BLUE);

        txtMaSach = new JTextField();
        txtMaSach.setBorder(BorderFactory.createTitledBorder("Mã sách *"));
        txtTenSach = new JTextField();
        txtTenSach.setBorder(BorderFactory.createTitledBorder("Tên sách *"));
        txtPhiMuon = new JTextField();
        txtPhiMuon.setBorder(BorderFactory.createTitledBorder("Phí mượn"));

        panelInput.add(txtMaSach);
        panelInput.add(txtTenSach);
        panelInput.add(txtPhiMuon);

        // --- Panel chứa các nút chức năng ---
        JPanel panelButtons = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        panelButtons.setBackground(Color.BLUE);
        btnThem = new JButton("Thêm");
        btnSua = new JButton("Sửa");
        btnLuu = new JButton("Lưu");
        btnXoa = new JButton("Xóa");
        btnHuy = new JButton("Hủy");
        btnDong = new JButton("Đóng");

        panelButtons.add(btnThem);
        panelButtons.add(btnSua);
        panelButtons.add(btnLuu);
        panelButtons.add(btnXoa);
        panelButtons.add(btnHuy);
        panelButtons.add(btnDong);

        // Gộp panel nhập và panel nút vào panel bottom
        JPanel panelBottom = new JPanel(new BorderLayout(10, 10));
        panelBottom.setBackground(Color.BLUE);
        panelBottom.add(panelInput, BorderLayout.CENTER);
        panelBottom.add(panelButtons, BorderLayout.SOUTH);
        add(panelBottom, BorderLayout.SOUTH);

        // --- Xử lý các nút ---
        btnThem.addActionListener(e -> {
            String maSach = txtMaSach.getText().trim();
            String tenSach = txtTenSach.getText().trim();
            if (maSach.isEmpty() || tenSach.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Mã sách và Tên sách là bắt buộc!");
                return;
            }
            String phiMuonStr = txtPhiMuon.getText().trim();
            double phiMuon = 0;
            try {
                if (!phiMuonStr.isEmpty()) {
                    phiMuon = Double.parseDouble(phiMuonStr);
                }
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(this, "Phí mượn không hợp lệ, đặt mặc định là 0");
                phiMuon = 0;
            }
            String loanCode = loanData[0].toString();
            String maChiTiet = loanCode + "-" + (tableModel.getRowCount() + 1);
            
            tableModel.addRow(new Object[]{maChiTiet, maSach, tenSach, phiMuon});
            JOptionPane.showMessageDialog(this, "Thêm chi tiết phiếu mượn thành công!");

            txtMaSach.setText("");
            txtTenSach.setText("");
            txtPhiMuon.setText("");
        });

        btnSua.addActionListener(e -> {
            int selectedRow = table.getSelectedRow();
            if (selectedRow == -1) {
                JOptionPane.showMessageDialog(this, "Vui lòng chọn dòng để sửa");
                return;
            }
            txtMaSach.setText(tableModel.getValueAt(selectedRow, 1).toString());
            txtTenSach.setText(tableModel.getValueAt(selectedRow, 2).toString());
            txtPhiMuon.setText(tableModel.getValueAt(selectedRow, 3).toString());
        });

        btnLuu.addActionListener(e -> {
            int selectedRow = table.getSelectedRow();
            if (selectedRow == -1) {
                JOptionPane.showMessageDialog(this, "Vui lòng chọn dòng để lưu thay đổi");
                return;
            }
            String maSach = txtMaSach.getText().trim();
            String tenSach = txtTenSach.getText().trim();
            if (maSach.isEmpty() || tenSach.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Mã sách và Tên sách là bắt buộc!");
                return;
            }
            String phiMuonStr = txtPhiMuon.getText().trim();
            double phiMuon = 0;
            try {
                if (!phiMuonStr.isEmpty()) {
                    phiMuon = Double.parseDouble(phiMuonStr);
                }
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(this, "Phí mượn không hợp lệ, đặt mặc định là 0");
                phiMuon = 0;
            }
            tableModel.setValueAt(maSach, selectedRow, 1);
            tableModel.setValueAt(tenSach, selectedRow, 2);
            tableModel.setValueAt(phiMuon, selectedRow, 3);
            JOptionPane.showMessageDialog(this, "Cập nhật chi tiết phiếu mượn thành công!");
            txtMaSach.setText("");
            txtTenSach.setText("");
            txtPhiMuon.setText("");
        });

        btnXoa.addActionListener(e -> {
            int selectedRow = table.getSelectedRow();
            if (selectedRow == -1) {
                JOptionPane.showMessageDialog(this, "Vui lòng chọn dòng để xóa");
                return;
            }
            tableModel.removeRow(selectedRow);
        });

        btnHuy.addActionListener(e -> {
            txtMaSach.setText("");
            txtTenSach.setText("");
            txtPhiMuon.setText("");
        });

        btnDong.addActionListener(e -> dispose());

        setSize(800, 600);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setVisible(true);
    }
}
