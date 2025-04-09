package gui;
import java.awt.*;
import java.util.List;
import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableModel;

import BLL.Book_Details_BLL;
import model.Books;

public class BookDetails extends JFrame {
    private BookPanel bookPanel;
    private Book_Details_BLL book_BLL;
    private JTable table;
    private DefaultTableModel tableModel;

    private JTextField txtTrangThai;
    private JTextField txtSoTrangHuHong;

    private JButton btnThem;
    private JButton btnSua;
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
    public BookDetails(Object[] bookData, BookPanel bookPanel) {
        this.bookPanel = bookPanel;
        book_BLL = new Book_Details_BLL();
        setTitle("Chi tiết các cuốn của sách - " + bookData[1]);
        setLayout(new BorderLayout(10, 10));
        getContentPane().setBackground(Color.BLUE);
        
        // --- Bảng chi tiết ---
        String[] columnNames = {"Mã chi tiết sách", "Trạng thái", "Số trang hư hỏng"};
        tableModel = new DefaultTableModel(columnNames, 0);
        
        table = new JTable(tableModel);
        table.setBackground(Color.WHITE);
        table.setSelectionBackground(Color.YELLOW);
        JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.getViewport().setBackground(Color.WHITE);
        add(scrollPane, BorderLayout.CENTER);

        // Tự động nạp dữ liệu xuống textfield khi chọn dòng
        table.getSelectionModel().addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent e) {
                if (!e.getValueIsAdjusting()) {
                    int selectedRow = table.getSelectedRow();
                    if (selectedRow != -1) {
                        // Lấy trạng thái và số trang hư hỏng từ bảng
                        String trangThai = tableModel.getValueAt(selectedRow, 1).toString();
                        String soTrangHuHong = tableModel.getValueAt(selectedRow, 2).toString();
                        // Đẩy xuống textfield
                        txtTrangThai.setText(trangThai);
                        txtSoTrangHuHong.setText(soTrangHuHong);
                    }
                }
            }
        });

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
        btnXoa = new JButton("Xóa");
        btnHuy = new JButton("Hủy");
        btnDong = new JButton("Đóng");
        
        panelButtons.add(btnThem);
        panelButtons.add(btnSua);
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
            String maChiTiet = bookCode + "_" + (tableModel.getRowCount() + 1);
            String trangThai = txtTrangThai.getText().trim();
            String soTrangHuHongStr = txtSoTrangHuHong.getText().trim();

            String result = book_BLL.addBook(maChiTiet, bookCode, trangThai, soTrangHuHongStr);
            JOptionPane.showMessageDialog(this, result);

            loadBookDetails(bookCode);
            bookPanel.loadBook();
            
        });

        
        table.getSelectionModel().addListSelectionListener(event -> {
            if (!event.getValueIsAdjusting() && table.getSelectedRow() != -1) {
                int selectedRow = table.getSelectedRow();
        
                // Lấy giá trị số lượng
                String tontaiStr = tableModel.getValueAt(selectedRow, 1).toString();
                
        
                // Hiển thị dữ liệu
                txtTrangThai.setText(tableModel.getValueAt(selectedRow, 1).toString());
                txtSoTrangHuHong.setText(tableModel.getValueAt(selectedRow, 2).toString());
                // Nếu số lượng = 0, thì không cho chỉnh sửa
                boolean editable = !tontaiStr.equals("Đã hỏng");
                txtTrangThai.setEnabled(editable);
                txtSoTrangHuHong.setEnabled(editable);
            }
        });
<<<<<<< HEAD
        
        btnSua.addActionListener(e -> {
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
=======

        btnSua.addActionListener(e -> {
            int selectedRow = table.getSelectedRow();
            if (selectedRow == -1) {
                JOptionPane.showMessageDialog(this, "Vui lòng chọn dòng để sửa");
                return;
            }

            String bookCode = bookData[0].toString();
            String maChiTiet = tableModel.getValueAt(selectedRow, 0).toString();
            String trangThai = txtTrangThai.getText().trim();
            String soTrangHuHong = txtSoTrangHuHong.getText().trim();

            String result = book_BLL.updateBook(maChiTiet, bookCode, trangThai, soTrangHuHong);
            JOptionPane.showMessageDialog(this, result);
            loadBookDetails(bookCode);
        });
        
>>>>>>> deb326ed8e4e97ee4d328469e2d8c4171bd0a348

        btnXoa.addActionListener(e -> {
            int selectedRow = table.getSelectedRow();
            if (selectedRow == -1) {
                JOptionPane.showMessageDialog(this, "Vui lòng chọn dòng để xóa");
                return;
            }
        
            String result = book_BLL.deleteBook(tableModel.getValueAt(selectedRow, 0).toString(), bookData[0].toString());
            JOptionPane.showMessageDialog(this, result);
            loadBookDetails(bookData[0].toString());
        });
        
        btnHuy.addActionListener(e -> {
            txtTrangThai.setText("");
            txtTrangThai.setEnabled(true);
            txtSoTrangHuHong.setText("");
<<<<<<< HEAD
            table.clearSelection();
=======
            txtSoTrangHuHong.setEnabled(true);
>>>>>>> deb326ed8e4e97ee4d328469e2d8c4171bd0a348
        });

        try {
            loadBookDetails(bookData[0].toString());
        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Lỗi khi load chi tiết sách: " + e.getMessage(), "Lỗi", JOptionPane.ERROR_MESSAGE);
        }
        
        btnDong.addActionListener(e -> {
            dispose();
            bookPanel.loadBook();
        });
        
        setSize(600, 600);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setVisible(true);
    }

    public void loadBookDetails(String ID) {
        tableModel.setRowCount(0);
        
        List<String> books = book_BLL.getBook(ID);
        // Kiểm tra danh sách có dữ liệu không
        if (books != null) {
            for (String book  : books) {
                tableModel.addRow(new Object[]{
                    book.split(";")[0], // Mã chi tiết sách
                    book.split(";")[1], // Trạng thái
                    book.split(";")[2]  // Số trang hư hỏng
                });
            }
        } else {
            JOptionPane.showMessageDialog(this, "Không thể tải danh sách sách!", "Lỗi", JOptionPane.ERROR_MESSAGE);
        }
    }
}
