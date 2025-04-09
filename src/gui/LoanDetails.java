package gui;
import java.awt.*;
import java.util.List;
import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableModel;

import BLL.Borrow_Details_BLL;

public class LoanDetails extends JFrame {
    private Borrow_Details_BLL borrowDetailsBLL;
    private JTable table;
    private DefaultTableModel tableModel;

    private JTextField txtMaSach;
    private JTextField txtTenSach;
    private JTextField txtPhiMuon;

    private JButton btnThem;
    private JButton btnSua;
    private JButton btnXoa;
    private JButton btnHuy;
    private JButton btnDong;

    
    public LoanDetails(Object[] loanData, LoanPanel loanPanel) {
        this.borrowDetailsBLL = new Borrow_Details_BLL();
        setTitle("Chi tiết phiếu mượn - " + loanData[0]);
        setLayout(new BorderLayout(10, 10));
        getContentPane().setBackground(Color.BLUE);

        // --- Bảng chi tiết ---
        String[] columnNames = {"Mã chi tiết phiếu mượn", "Mã sách", "Tên sách", "Phí mượn"};
        tableModel = new DefaultTableModel(columnNames, 0);

        table = new JTable(tableModel);
        table.setBackground(Color.WHITE);
        table.setSelectionBackground(Color.YELLOW);
        JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.getViewport().setBackground(Color.WHITE);
        add(scrollPane, BorderLayout.CENTER);

        JPanel panelInput = new JPanel();
        panelInput.setLayout(new BoxLayout(panelInput, BoxLayout.Y_AXIS));
        panelInput.setBackground(Color.BLUE);

        table.getSelectionModel().addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent e) {
                if (!e.getValueIsAdjusting()) {
                    int selectedRow = table.getSelectedRow();
                    if (selectedRow != -1) {
                        // Lấy trạng thái và số trang hư hỏng từ bảng
                        String maSach = tableModel.getValueAt(selectedRow, 1).toString();
                        String tenSach = tableModel.getValueAt(selectedRow, 2).toString();
                        String phiMuon = tableModel.getValueAt(selectedRow, 3).toString();
                        // Đẩy xuống textfield
                        txtMaSach.setText(maSach);
                        txtTenSach.setText(tenSach);
                        txtPhiMuon.setText(phiMuon);
                    }
                }
            }
        });

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
        btnXoa = new JButton("Xóa");
        btnHuy = new JButton("Hủy");
        btnDong = new JButton("Đóng");

        panelButtons.add(btnThem);
        panelButtons.add(btnSua);
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

        table.getSelectionModel().addListSelectionListener(event -> {
            if (!event.getValueIsAdjusting() && table.getSelectedRow() != -1) {
                int selectedRow = table.getSelectedRow();
        
        
                // Hiển thị dữ liệu
                txtMaSach.setText(tableModel.getValueAt(selectedRow, 0).toString());
                txtTenSach.setText(tableModel.getValueAt(selectedRow, 1).toString());
                txtPhiMuon.setText(tableModel.getValueAt(selectedRow, 2).toString());
                
            }
        });

        btnSua.addActionListener(e -> {
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
            table.clearSelection();
        });

        btnDong.addActionListener(e -> dispose());

        loadBorrow_Details(loanData[0].toString());

        setSize(800, 600);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setVisible(true);
    }

    public void loadBorrow_Details(String ID){
        tableModel.setRowCount(0);

        List<String> details = borrowDetailsBLL.getBorrow_Details(ID);

        if(details != null) {
            for (String detail : details) {
                tableModel.addRow(new Object[]{
                    detail.split(";")[0],
                    detail.split(";")[1],
                    detail.split(";")[2]
                });
            }
        } else {
            JOptionPane.showMessageDialog(this, "Không có chi tiết nào cho phiếu mượn này.");
        }
    }
}
