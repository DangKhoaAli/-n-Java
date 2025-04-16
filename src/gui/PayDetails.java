package gui;
import BLL.Book_Return_BLL;
import java.awt.*;
import java.util.List;
import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableModel;

public class PayDetails extends JFrame {
    private Book_Return_BLL bookReturnBLL;
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
        this.payPanel = payPanel;
        setTitle("Chi tiết phiếu mượn - " + payData[0]);
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
                        String maSach = tableModel.getValueAt(selectedRow, 1).toString();
                        String tenSach = tableModel.getValueAt(selectedRow, 2).toString();
                        String tinhTrangHuHong = tableModel.getValueAt(selectedRow, 3).toString();
                        String phiPhat = tableModel.getValueAt(selectedRow, 4).toString();

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
        txtTenSach = new JTextField();
        txtTenSach.setBorder(BorderFactory.createTitledBorder("Tên sách *"));
        txtTinhTrangHuHong = new JTextField();
        txtTinhTrangHuHong.setBorder(BorderFactory.createTitledBorder("Tình trạng hư hỏng"));
        txtPhiPhat = new JTextField();
        txtPhiPhat.setBorder(BorderFactory.createTitledBorder("Phí phạt"));

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

        // // --- Xử lý các nút ---
        // btnThem.addActionListener(e -> {
        //     String maSach = txtMaSach.getText().trim();
        //     String tenSach = txtTenSach.getText().trim();
        //     if (maSach.isEmpty() || tenSach.isEmpty()) {
        //         JOptionPane.showMessageDialog(this, "Mã sách và Tên sách là bắt buộc!");
        //         return;
        //     }
        //     String tinhTrangHuHongStr = txtTinhTrangHuHong.getText().trim();
        //     int tinhTrangHuHong = 0;
        //     try {
        //         if(!tinhTrangHuHongStr.isEmpty()) {
        //             tinhTrangHuHong = Integer.parseInt(tinhTrangHuHongStr);
        //         }
        //     } catch (NumberFormatException ex) {
        //         JOptionPane.showMessageDialog(this, "Tình trạng hư hỏng không hợp lệ, đặt mặc định là 0");
        //         tinhTrangHuHong = 0;
        //     }
        //     String phiPhatStr = txtPhiPhat.getText().trim();
        //     double phiPhat = 0;
        //     try {
        //         if (!phiPhatStr.isEmpty()) {
        //             phiPhat = Double.parseDouble(phiPhatStr);
        //         }
        //     } catch (NumberFormatException ex) {
        //         JOptionPane.showMessageDialog(this, "Phí phạt không hợp lệ, đặt mặc định là 0");
        //         phiPhat = 0;
        //     }
        //     String payCode = payData[0].toString();
        //     String maChiTiet = payCode + "-" + (tableModel.getRowCount() + 1);
            
        //     tableModel.addRow(new Object[]{maChiTiet, maSach, tenSach,tinhTrangHuHong, phiPhat});
        //     JOptionPane.showMessageDialog(this, "Thêm chi tiết phiếu mượn thành công!");

        //     txtMaSach.setText("");
        //     txtTenSach.setText("");
        //     txtTinhTrangHuHong.setText("");
        //     txtPhiPhat.setText("");
        // });

        // btnSua.addActionListener(e -> {
        //     int selectedRow = table.getSelectedRow();
        //     if (selectedRow == -1) {
        //         JOptionPane.showMessageDialog(this, "Vui lòng chọn dòng để lưu thay đổi");
        //         return;
        //     }
        //     String maSach = txtMaSach.getText().trim();
        //     String tenSach = txtTenSach.getText().trim();
        //     if (maSach.isEmpty() || tenSach.isEmpty()) {
        //         JOptionPane.showMessageDialog(this, "Mã sách và Tên sách là bắt buộc!");
        //         return;
        //     }
        //     String tinhTrangHuHongStr = txtTinhTrangHuHong.getText().trim();
        //     int tinhTrangHuHong = 0;
        //     try {
        //         if(!tinhTrangHuHongStr.isEmpty()) {
        //             tinhTrangHuHong = Integer.parseInt(tinhTrangHuHongStr);
        //         }
        //     } catch (NumberFormatException ex) {
        //         JOptionPane.showMessageDialog(this, "Tình trạng hư hỏng không hợp lệ, đặt mặc định là 0");
        //         tinhTrangHuHong = 0;
        //     }
        //     String phiPhatStr = txtPhiPhat.getText().trim();
        //     double phiPhat = 0;
        //     try {
        //         if (!phiPhatStr.isEmpty()) {
        //             phiPhat = Double.parseDouble(phiPhatStr);
        //         }
        //     } catch (NumberFormatException ex) {
        //         JOptionPane.showMessageDialog(this, "Phí phạt không hợp lệ, đặt mặc định là 0");
        //         phiPhat = 0;
        //     }
        //     tableModel.setValueAt(maSach, selectedRow, 1);
        //     tableModel.setValueAt(tenSach, selectedRow, 2);
        //     tableModel.setValueAt(tinhTrangHuHong, selectedRow, 3);
        //     tableModel.setValueAt(phiPhat, selectedRow,4);
        //     JOptionPane.showMessageDialog(this, "Cập nhật chi tiết phiếu mượn thành công!");
        //     txtMaSach.setText("");
        //     txtTenSach.setText("");
        //     txtTinhTrangHuHong.setText("");
        //     txtPhiPhat.setText("");
        // });

        // btnXoa.addActionListener(e -> {
        //     int selectedRow = table.getSelectedRow();
        //     if (selectedRow == -1) {
        //         JOptionPane.showMessageDialog(this, "Vui lòng chọn dòng để xóa");
        //         return;
        //     }
        //     tableModel.removeRow(selectedRow);
        // });

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
