package gui;
import BLL.Book_Return_BLL;
import java.awt.*;
import java.util.List;
import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableModel;

import BLL.Book_Return_BLL;
import DAO.Book_Details_DAO;
import DAO.Payment_slip_DAO;

public class PayDetails extends JFrame {
    private Book_Return_BLL bookReturnBLL;
    private Payment_slip_DAO paymentSlipDAO;
    private Book_Details_DAO bookDetailsDAO;
    private PayPanel payPanel;

    private JTable table;
    private DefaultTableModel tableModel;

    private JTextField txtMaSach;
    private JTextField txtTenSach;
    private JTextField txtTinhTrangHuHong;
    private JTextField txtPhiPhat;

    private JButton btnThem;
    private JButton btnSua;
    private JButton btnXoa;
    private JButton btnHuy;
    private JButton btnDong;

    public PayDetails(Object[] payData, PayPanel payPanel) {
        this.bookReturnBLL = new Book_Return_BLL();
        this.paymentSlipDAO = new Payment_slip_DAO();
        this.bookDetailsDAO = new Book_Details_DAO();
        // this.setIconImage(new ImageIcon("src/icon/logo.png").getImage());
        this.payPanel = payPanel;
        setTitle("Chi tiết phiếu trả - " + payData[0]);
        setLayout(new BorderLayout(0, 0));
        getContentPane().setBackground(new Color(230, 236, 243));

        // --- Bảng chi tiết ---
        String[] columnNames = {"Mã sách", "Tên sách","Tình trạng hư hỏng", "Phí phạt"};
        tableModel = new DefaultTableModel(columnNames, 0);


        table = new JTable(tableModel);
        table.setBackground(Color.WHITE);
        table.setSelectionBackground(Color.YELLOW);
        JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.getViewport().setBackground(Color.WHITE);
        add(scrollPane, BorderLayout.CENTER);

        JPanel panelInput = new JPanel();
        panelInput.setLayout(new BoxLayout(panelInput, BoxLayout.Y_AXIS));
        panelInput.setBackground(new Color(230, 236, 243));

        table.getSelectionModel().addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent e) {
                if (!e.getValueIsAdjusting()) {
                    int selectedRow = table.getSelectedRow();
                    if (selectedRow != -1) {
                        // Lấy trạng thái và số trang hư hỏng từ bảng
                        String maSach = tableModel.getValueAt(selectedRow, 0).toString();
                        String tenSach = tableModel.getValueAt(selectedRow, 1).toString();
                        String tinhTrangHuHong = tableModel.getValueAt(selectedRow, 2).toString();
                        String phiPhat = tableModel.getValueAt(selectedRow, 3).toString();

                        // Đẩy xuống textfield
                        txtMaSach.setText(maSach);
                        txtTenSach.setText(tenSach);
                        txtTinhTrangHuHong.setText(tinhTrangHuHong);
                        txtPhiPhat.setText(phiPhat);
                    }
                }
            }
        });

        txtMaSach = new JTextField();
        txtMaSach.setBorder(BorderFactory.createTitledBorder("Mã sách *"));
        txtMaSach.setEditable(false);
        txtTenSach = new JTextField();
        txtTenSach.setBorder(BorderFactory.createTitledBorder("Tên sách *"));
        txtTenSach.setEditable(false);
        txtTinhTrangHuHong = new JTextField();
        txtTinhTrangHuHong.setBorder(BorderFactory.createTitledBorder("Tình trạng hư hỏng"));
        txtPhiPhat = new JTextField();
        txtPhiPhat.setBorder(BorderFactory.createTitledBorder("Phí phạt"));
        txtPhiPhat.setEditable(false);

        panelInput.add(txtMaSach);
        panelInput.add(txtTenSach);
        panelInput.add(txtTinhTrangHuHong);
        panelInput.add(txtPhiPhat);

        // --- Panel chứa các nút chức năng ---
        JPanel panelButtons = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        panelButtons.setBackground(new Color(230, 236, 243));
        btnThem = new RoundedButton("Thêm");
        btnSua = new RoundedButton("Sửa");
        btnXoa = new RoundedButton("Xóa");
        btnHuy = new RoundedButton("Hủy");
        btnDong = new RoundedButton("Đóng");

        panelButtons.add(btnThem);
        panelButtons.add(btnSua);
        panelButtons.add(btnXoa);
        panelButtons.add(btnHuy);
        panelButtons.add(btnDong);

        // Gộp panel nhập và panel nút vào panel bottom
        JPanel panelBottom = new JPanel(new BorderLayout(0, 0));
        panelBottom.setBackground(new Color(230, 236, 243));
        panelBottom.add(panelInput, BorderLayout.CENTER);
        panelBottom.add(panelButtons, BorderLayout.SOUTH);
        add(panelBottom, BorderLayout.SOUTH);

        String maPhieuTra = payData[0].toString();
        String maPhieuMuon = payData[1].toString();

        txtMaSach.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                String[] selectedBookID = new String[8];

                Select dialog = new Select(maPhieuMuon, selectedBookID, "pay");
                dialog.setVisible(true);        
                if (selectedBookID[0] != null) {
                    txtMaSach.setText(selectedBookID[0]);
                    txtTenSach.setText(selectedBookID[1]);
                    txtTinhTrangHuHong.setText(selectedBookID[6]);
                    
                }
            }
        });

        // // --- Xử lý các nút ---
        btnThem.addActionListener(e -> {
            try{
                String maSach = txtMaSach.getText().trim();
                String status = txtTinhTrangHuHong.getText().trim();

                String message = bookReturnBLL.addBookReturned(maPhieuTra, maSach, status);
                JOptionPane.showMessageDialog(this, message);

                loadPayDetails(maPhieuTra);
                
                int rowCount = tableModel.getRowCount();
                paymentSlipDAO.update_Quan(maPhieuTra, rowCount);
                paymentSlipDAO.updateDamageFee(maPhieuTra);
                bookDetailsDAO.updateNum_page(Integer.parseInt(status), maSach);
                this.payPanel.loadPayDetails();
            } catch (Exception ex) {
                ex.printStackTrace();
                JOptionPane.showMessageDialog(this, "Lỗi thêm chi tiết phiếu trả: " + ex.getMessage());
            }
        });

        btnSua.addActionListener(e -> {
            try{
                int selectedRow = table.getSelectedRow();
                if (selectedRow == -1) {
                    JOptionPane.showMessageDialog(this, "Vui lòng chọn dòng để lưu thay đổi");
                    return;
                }
                String maSach = txtMaSach.getText().trim();
                String maSach_old = tableModel.getValueAt(selectedRow, 0).toString().trim();
                String status = txtTinhTrangHuHong.getText().trim();
                String phi = tableModel.getValueAt(selectedRow, 3).toString().trim();
                String result = bookReturnBLL.updateBookReturn(maPhieuTra, maSach, maSach_old, status, phi);

                JOptionPane.showMessageDialog(this, result);
                if(result.equals("Đã cập nhật chi tiết phiếu trả thành công!")){
                    paymentSlipDAO.updateDamageFee(maPhieuTra);
                    loadPayDetails(maPhieuTra);
                    payPanel.loadPayDetails();
                }
            } catch (Exception ex){
                JOptionPane.showMessageDialog(this, "Lỗi : " + ex.getMessage());
            }
        });

        btnXoa.addActionListener(e -> {
            try {
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

                String masach = txtMaSach.getText().trim();
                String result = bookReturnBLL.deleteBookReturn(maPhieuTra, masach);
                JOptionPane.showMessageDialog(this, result);
                loadPayDetails(maPhieuTra);
                
                
                int rowCount = tableModel.getRowCount();
                paymentSlipDAO.update_Quan(maPhieuTra, rowCount);
                paymentSlipDAO.updateDamageFee(maPhieuTra);
                payPanel.loadPayDetails();
            } catch(Exception ex){
                JOptionPane.showMessageDialog(this, "Lỗi xóa chi tiết phiếu trả: " + ex.getMessage());
            }
        });

        btnHuy.addActionListener(e -> {
            txtMaSach.setText("");
            txtTenSach.setText("");
            txtTinhTrangHuHong.setText("");
            txtPhiPhat.setText("");
            table.clearSelection();
        });

        loadPayDetails(payData[0].toString());

        btnDong.addActionListener(e -> dispose());

        setSize(800, 600);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setVisible(true);
    }

    public void loadPayDetails(String payCode) {
        tableModel.setRowCount(0);
        List<String> details = bookReturnBLL.getBookDetailsReturn(payCode);
        if (details != null) {
            for (String detail : details) {
                tableModel.addRow(new Object[]{
                    detail.split(";")[0],
                    detail.split(";")[1],
                    detail.split(";")[2],
                    detail.split(";")[3]
                });
            }
        } else {
            JOptionPane.showMessageDialog(this, "Không có chi tiết nào cho phiếu trả này.");
        }
    }
}
