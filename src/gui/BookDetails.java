import java.awt.*;
import java.util.List;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;

public class BookDetails extends JFrame {
    private JTable table;
    private DefaultTableModel tableModel;

    private JTextField txtTrangThai;
    private JTextField txtSoTrangHuHong;

    private JButton btnThem;
    private JButton btnSua;
    private JButton btnLuu;
    private JButton btnXoa;
    private JButton btnHuy;
    private JButton btnDong;
    
    /**
     * Constructor hiển thị chi tiết các cuốn sách của 1 loại sách.
     * @param bookData    Mảng chứa thông tin cơ bản của sách 
     *                    (ví dụ: [Mã sách, Tên sách, Thể loại, Tác giả, …, Số lượng])
     * @param copyDetails Danh sách chi tiết của từng cuốn sách 
     *                    (mỗi phần tử là Object[] gồm: [Mã chi tiết, Trạng thái, Số trang hư hỏng])
     */
    public BookDetails(Object[] bookData, List<Object[]> copyDetails) {
        setTitle("Chi tiết các cuốn của sách - " + bookData[1]);
        setLayout(new BorderLayout(10, 10));
        getContentPane().setBackground(Color.BLUE);
        
        // --- Bảng chi tiết ---
        String[] columnNames = {"Mã chi tiết sách", "Trạng thái", "Số trang hư hỏng"};
        tableModel = new DefaultTableModel(columnNames, 0);

        if (copyDetails != null && !copyDetails.isEmpty()) {
            for (Object[] detail : copyDetails) {
                tableModel.addRow(detail);
            }
        } else {
            int soLuong = 0;
            try {
                soLuong = Integer.parseInt(bookData[9].toString());
            } catch (NumberFormatException e) {
                soLuong = 0;
            }
            for (int i = 1; i <= soLuong; i++) {
                String maChiTiet = bookData[0] + "-" + i;
                tableModel.addRow(new Object[]{maChiTiet, "Có thể mượn", 0});
            }
        }
        
        table = new JTable(tableModel);
        table.setBackground(Color.WHITE);
        table.setSelectionBackground(Color.YELLOW);
        JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.getViewport().setBackground(Color.WHITE);
        add(scrollPane, BorderLayout.CENTER);
        
        // Panel nhập chi tiết
        JPanel panelInput = new JPanel();
        panelInput.setLayout(new BoxLayout(panelInput, BoxLayout.Y_AXIS));
        panelInput.setBackground(Color.BLUE);
        
        txtTrangThai = new JTextField();
        txtTrangThai.setBorder(BorderFactory.createTitledBorder("Trạng thái"));
        txtSoTrangHuHong = new JTextField();
        txtSoTrangHuHong.setBorder(BorderFactory.createTitledBorder("Số trang hư hỏng"));
        
        panelInput.add(txtTrangThai);
        panelInput.add(txtSoTrangHuHong);
        
        // Panel chứa các nút chức năng
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
        
        // Gộp các panel dưới thành một panel chính
        JPanel panelBottom = new JPanel(new BorderLayout(10, 10));
        panelBottom.setBackground(Color.BLUE);
        panelBottom.add(panelInput, BorderLayout.CENTER);
        panelBottom.add(panelButtons, BorderLayout.SOUTH);
        add(panelBottom, BorderLayout.SOUTH);
        
        // --- Xử lý các nút ---
        btnThem.addActionListener(e -> {
            String bookCode = bookData[0].toString();
            String maChiTiet = bookCode + "-" + (tableModel.getRowCount() + 1);

            String trangThai = txtTrangThai.getText().trim();
            if (trangThai.isEmpty()) {
                trangThai = "Có thể mượn";
            }
            int soTrangHuHong = 0;
            try {
                soTrangHuHong = Integer.parseInt(txtSoTrangHuHong.getText().trim());
            } catch (NumberFormatException ex) {
                soTrangHuHong = 0;
            }

            tableModel.addRow(new Object[]{maChiTiet, trangThai, soTrangHuHong});
            JOptionPane.showMessageDialog(this, "Thêm chi tiết sách mới thành công!");
            txtTrangThai.setText("");
            txtSoTrangHuHong.setText("");
        });

        btnSua.addActionListener(e -> {
            int selectedRow = table.getSelectedRow();
            if (selectedRow == -1) {
                JOptionPane.showMessageDialog(this, "Vui lòng chọn dòng để sửa");
                return;
            }

            String trangThai = tableModel.getValueAt(selectedRow, 1).toString();
            String soTrangHuHong = tableModel.getValueAt(selectedRow, 2).toString();

            txtTrangThai.setText(trangThai);
            txtSoTrangHuHong.setText(soTrangHuHong);
        });
        
        btnLuu.addActionListener(e -> {
            int selectedRow = table.getSelectedRow();
            if (selectedRow == -1) {
                JOptionPane.showMessageDialog(this, "Vui lòng chọn dòng muốn lưu thay đổi");
                return;
            }
            // Lấy dữ liệu từ các textfield
            String trangThai = txtTrangThai.getText().trim();
            String soTrangHuHongStr = txtSoTrangHuHong.getText().trim();
            int soTrangHuHong = 0;
            try {
                soTrangHuHong = Integer.parseInt(soTrangHuHongStr);
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(this, "Số trang hư hỏng không hợp lệ, đặt mặc định là 0");
                soTrangHuHong = 0;
            }
            // Cập nhật dữ liệu từ textfield lên bảng (cột 1: Trạng thái, cột 2: Số trang hư hỏng)
            tableModel.setValueAt(trangThai, selectedRow, 1);
            tableModel.setValueAt(soTrangHuHong, selectedRow, 2);
            JOptionPane.showMessageDialog(this, "Cập nhật chi tiết sách thành công!");
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
            txtTrangThai.setText("");
            txtSoTrangHuHong.setText("");
        });
        
        btnDong.addActionListener(e -> dispose());
        
        setSize(600, 600);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setVisible(true);
    }
}
