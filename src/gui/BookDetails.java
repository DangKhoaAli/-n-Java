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
        panelInput.setBackground(Color.BLUE);
        txtTrangThai = new JTextField();
        txtTrangThai.setBorder(BorderFactory.createTitledBorder("Trạng thái"));
        txtSoTrangHuHong = new JTextField();
        txtSoTrangHuHong.setBorder(BorderFactory.createTitledBorder("Số trang hư hỏng"));
        panelInput.add(txtTrangThai);
        panelInput.add(txtSoTrangHuHong);

        // --- Panel nút ---
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

        JPanel panelBottom = new JPanel(new BorderLayout(10,10));
        panelBottom.setBackground(Color.BLUE);
        panelBottom.add(panelInput, BorderLayout.CENTER);
        panelBottom.add(panelButtons, BorderLayout.SOUTH);
        add(panelBottom, BorderLayout.SOUTH);

        // --- ActionListeners ---

        // Thêm bản sao mới
        btnThem.addActionListener(e -> {
            String bookCode = bookData[0].toString();
            String maChiTiet = bookCode + "_" + (tableModel.getRowCount() + 1);
            String trangThai = txtTrangThai.getText().trim();
            String soTrangHuHongStr = txtSoTrangHuHong.getText().trim();
            String result = book_BLL.addBook(maChiTiet, bookCode, trangThai, soTrangHuHongStr);
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
            String trangThai = txtTrangThai.getText().trim();
            String soTrangHuHong = txtSoTrangHuHong.getText().trim();
            String result = book_BLL.updateBook(maChiTiet, bookCode, trangThai, soTrangHuHong);
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
        List<String> details = book_BLL.getBook(ID);
        if (details != null) {
            for (String line : details) {
                String[] parts = line.split(";");
                tableModel.addRow(new Object[]{parts[0], parts[1], parts[2]});
            }
        } else {
            JOptionPane.showMessageDialog(this, "Không thể tải danh sách sách!", "Lỗi", JOptionPane.ERROR_MESSAGE);
        }
    }
}
