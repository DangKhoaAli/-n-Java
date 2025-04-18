package gui;

import BLL.Book_Details_BLL;
import java.awt.*;
import java.util.List;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;

public class BookDetails extends JDialog {
    private BookPanel bookPanel;             // để reload bảng khi cần
    private Book_Details_BLL book_BLL;
    private JTable table;
    private DefaultTableModel tableModel;

    private JTextField txtNhaCungCap;
    private JTextField txtNamXuatBan;
    private JTextField txtSoTrang;
    private JTextField txtTrangThai;
    private JTextField txtSoTrangHuHong;

    private JButton btnThem;
    private JButton btnSua;
    private JButton btnXoa;
    private JButton btnHuy;
    private JButton btnDong;

    // Constructor dành cho BookPanel / LoanPanel (Frame parent)
    public BookDetails(Object[] bookData, Frame owner, BookPanel bookPanel) {
        super(owner, "Chi tiết các cuốn của sách - " + bookData[1], true);
        this.bookPanel = bookPanel;
        initUI(bookData);
    }

    // Constructor dành cho BookSelection (Dialog parent)
    public BookDetails(Object[] bookData, Dialog owner, BookPanel bookPanel) {
        super(owner, "Chi tiết các cuốn của sách - " + bookData[1], true);
        this.bookPanel = bookPanel;
        initUI(bookData);
    }

    private void initUI(Object[] bookData) {
        book_BLL = new Book_Details_BLL();
        setLayout(new BorderLayout(0, 0));
        getContentPane().setBackground(new Color(230, 236, 243));

        // --- Bảng chi tiết ---
        String[] columnNames = {"Mã chi tiết sách", "Nhà cung câp", "Năm xuất bản", "Số trang", "Trạng thái", "Số trang hư hỏng"};
        tableModel = new DefaultTableModel(columnNames, 0);
        table = new JTable(tableModel);
        table.setBackground(Color.WHITE);
        table.setSelectionBackground(Color.YELLOW);
        JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.getViewport().setBackground(Color.WHITE);
        add(scrollPane, BorderLayout.CENTER);

        // Khi chọn dòng, đẩy dữ liệu xuống textfield
        table.getSelectionModel().addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting() && table.getSelectedRow() != -1) {
                int r = table.getSelectedRow();
                txtTrangThai.setText(tableModel.getValueAt(r, 1).toString());
                txtSoTrangHuHong.setText(tableModel.getValueAt(r, 2).toString());
                boolean editable = !"Đã hỏng".equals(tableModel.getValueAt(r, 1));
                txtTrangThai.setEnabled(editable);
                txtSoTrangHuHong.setEnabled(editable);
            }
        });

        // --- Panel nhập ---
        JPanel panelInput = new JPanel();
        panelInput.setLayout(new BoxLayout(panelInput, BoxLayout.Y_AXIS));
        panelInput.setBackground(new Color(230, 236, 243));
        txtNhaCungCap = new JTextField();
        txtNhaCungCap.setBorder(BorderFactory.createTitledBorder("Nhà cung cấp"));
        txtNamXuatBan = new JTextField();
        txtNamXuatBan.setBorder(BorderFactory.createTitledBorder("Năm xuất bản"));
        txtSoTrang = new JTextField();
        txtSoTrang.setBorder(BorderFactory.createTitledBorder("Số trang"));
        txtTrangThai = new JTextField();
        txtTrangThai.setBorder(BorderFactory.createTitledBorder("Trạng thái"));
        txtSoTrangHuHong = new JTextField();
        txtSoTrangHuHong.setBorder(BorderFactory.createTitledBorder("Số trang hư hỏng"));
        panelInput.add(txtNhaCungCap);
        panelInput.add(txtNamXuatBan);
        panelInput.add(txtSoTrang);
        panelInput.add(txtTrangThai);
        panelInput.add(txtSoTrangHuHong);

        // --- Panel nút ---
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

        JPanel panelBottom = new JPanel(new BorderLayout(0,0));
        panelBottom.setBackground(new Color(230, 236, 243));
        panelBottom.add(panelInput, BorderLayout.CENTER);
        panelBottom.add(panelButtons, BorderLayout.SOUTH);
        add(panelBottom, BorderLayout.SOUTH);

        // --- ActionListeners ---

        // Thêm bản sao mới
        btnThem.addActionListener(e -> {
            String bookCode = bookData[0].toString();
            String maChiTiet = bookCode + "_" + (tableModel.getRowCount() + 1);
            String nhaCungCap = txtNhaCungCap.getText().trim();
            String namXuatBan = txtNamXuatBan.getText().trim();
            String soTrang = txtSoTrang.getText().trim();
            String trangThai = txtTrangThai.getText().trim();
            String soTrangHuHongStr = txtSoTrangHuHong.getText().trim();

            String result = book_BLL.addBook(maChiTiet, bookCode, nhaCungCap, namXuatBan, soTrang, trangThai, soTrangHuHongStr);
            JOptionPane.showMessageDialog(this, result);
            loadBookDetails(bookCode);
            if (bookPanel != null) bookPanel.loadBook();
        });

        // Sửa bản sao đã chọn
        btnSua.addActionListener(e -> {
            int r = table.getSelectedRow();
            if (r == -1) {
                JOptionPane.showMessageDialog(this, "Vui lòng chọn dòng để sửa");
                return;
            }
            String bookCode = bookData[0].toString();
            String maChiTiet = tableModel.getValueAt(r, 0).toString();
            String nhaCungCap = txtNhaCungCap.getText().trim();
            String namXuatBan = txtNamXuatBan.getText().trim();
            String soTrang = txtSoTrang.getText().trim();
            String trangThai = txtTrangThai.getText().trim();
            String soTrangHuHong = txtSoTrangHuHong.getText().trim();

            String result = book_BLL.updateBook(maChiTiet, bookCode, nhaCungCap, namXuatBan, soTrang, trangThai, soTrangHuHong);
            JOptionPane.showMessageDialog(this, result);
            loadBookDetails(bookCode);
            if (bookPanel != null) bookPanel.loadBook();
        });

        // Xóa bản sao đã chọn
        btnXoa.addActionListener(e -> {
            int r = table.getSelectedRow();
            if (r == -1) {
                JOptionPane.showMessageDialog(this, "Vui lòng chọn dòng để xóa");
                return;
            }
            String bookCode = bookData[0].toString();
            String result = book_BLL.deleteBook(tableModel.getValueAt(r, 0).toString(), bookCode);
            JOptionPane.showMessageDialog(this, result);
            loadBookDetails(bookCode);
            if (bookPanel != null) bookPanel.loadBook();
        });

        // Hủy input
        btnHuy.addActionListener(e -> {
            txtTrangThai.setText("");
            txtTrangThai.setEnabled(true);
            txtSoTrangHuHong.setText("");
            txtSoTrangHuHong.setEnabled(true);
            table.clearSelection();
        });

        // Đóng dialog
        btnDong.addActionListener(e -> {
            dispose();
            if (bookPanel != null) bookPanel.loadBook();
        });

        // Load chi tiết lần đầu
        loadBookDetails(bookData[0].toString());

        setSize(600, 600);
        setLocationRelativeTo(getOwner());
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
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
                    book.split(";")[1], // Nhà cung cấp
                    book.split(";")[2], // Năm xuất bản
                    book.split(";")[3], // Số trang
                    book.split(";")[4], // Trạng thái
                    book.split(";")[5]  // Số trang hư hỏng
                    
                });
            }
        } else {
            JOptionPane.showMessageDialog(this, "Không thể tải danh sách sách!", "Lỗi", JOptionPane.ERROR_MESSAGE);
        }
    }

}
