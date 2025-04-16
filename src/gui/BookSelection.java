package gui;

import BLL.Book_BLL;
import model.Books;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class BookSelection extends JDialog {
    private Book_BLL book_BLL;
    private LoanPanel loanPanel;          // thêm
    private JTable table;
    private DefaultTableModel tableModel;
    private JButton btnOk, btnCancel, btnXemChiTiet;
    private JTextField txtTuKhoa;
    private JButton btnTim;

    private List<String> selectedBookIDs = new ArrayList<>();

    // Thêm tham số bookPanel
    public BookSelection(Frame owner, LoanPanel loanPanel) {
        super(owner, "Chọn sách", true);
        this.loanPanel = loanPanel;       // lưu lại
        book_BLL = new Book_BLL();

        setSize(900, 500);
        setLocationRelativeTo(owner);
        setLayout(new BorderLayout(10, 10));

        // --- Panel tìm kiếm ---
        JPanel pnlSearch = new JPanel(new FlowLayout(FlowLayout.LEFT));
        pnlSearch.add(new JLabel("Từ khóa:"));
        txtTuKhoa = new JTextField(20);
        pnlSearch.add(txtTuKhoa);
        btnTim = new JButton("Tìm");
        pnlSearch.add(btnTim);
        add(pnlSearch, BorderLayout.NORTH);

        // --- Panel bảng ---
        String[] cols = {"Mã sách", "Tên sách", "Thể loại", "Tác giả", "Nhà cung cấp",
                         "Năm XB", "Số trang", "Giá", "Phí mượn", "Số lượng", "Tồn tại"};
        tableModel = new DefaultTableModel(cols, 0);
        table = new JTable(tableModel);
        table.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
        add(new JScrollPane(table), BorderLayout.CENTER);

        // --- Panel nút ---
        JPanel pnlButtons = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        btnXemChiTiet = new JButton("Xem chi tiết");
        btnOk = new JButton("Chọn");
        btnCancel = new JButton("Hủy");
        pnlButtons.add(btnXemChiTiet);
        pnlButtons.add(btnOk);
        pnlButtons.add(btnCancel);
        add(pnlButtons, BorderLayout.SOUTH);

        // Load dữ liệu
        loadBook("");

        // Sự kiện tìm
        btnTim.addActionListener(e -> loadBook(txtTuKhoa.getText().trim().toLowerCase()));

        // Xem chi tiết
        btnXemChiTiet.addActionListener(e -> {
            int row = table.getSelectedRow();
            if (row == -1) {
                JOptionPane.showMessageDialog(this, "Vui lòng chọn một cuốn sách để xem chi tiết!");
                return;
            }
            Object[] data = new Object[tableModel.getColumnCount()];
            for (int i = 0; i < data.length; i++) {
                data[i] = tableModel.getValueAt(row, i);
            }
            // Truyền cả bookPanel để BookDetails có thể reload
            new BookDetails(data, this, loanPanel).setVisible(true);
        });

        // Chọn
        btnOk.addActionListener(e -> {
            int[] rows = table.getSelectedRows();
            if (rows.length == 0) {
                JOptionPane.showMessageDialog(this, "Vui lòng chọn ít nhất một cuốn sách!");
                return;
            }
            selectedBookIDs.clear();
            for (int r : rows) {
                selectedBookIDs.add(tableModel.getValueAt(r, 0).toString());
            }
            dispose();
        });

        // Hủy
        btnCancel.addActionListener(e -> {
            selectedBookIDs.clear();
            dispose();
        });
    }

    private void loadBook(String keyword) {
        tableModel.setRowCount(0);
        List<Books> books = keyword.isEmpty() ? book_BLL.getBook() : book_BLL.searchBooks(keyword);
        if (books != null) {
            for (Books b : books) {
                tableModel.addRow(new Object[]{
                    b.getID(), b.getName(), b.getCategory(), b.getAuthor(),
                    b.getPrice(), b.getLoan_fee(), b.getQuanlity(), b.getExist()
                });
            }
        }
    }

    public List<String> showDialog() {
        setVisible(true);
        return selectedBookIDs;
    }
}
