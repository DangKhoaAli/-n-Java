package gui;

import BLL.Select_BLL;
import java.awt.*;
import java.util.List;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;

public class Select extends JDialog {           // để reload bảng khi cần
    private Select_BLL book_BLL;
    private JTable table;
    private DefaultTableModel tableModel;

    private JTextField txtTuKhoa;

    private JButton btnChon;
    private JButton btnTim;

    public Select(String ID, String[] book, String className) {
        if(className.equals("loan")){
            LoanBook(book);
            setModal(true);
        }
        else if(className.equals("pay")){
            PayBook(ID, book);
            setModal(true);
        }
    }

    private void LoanBook(String[] book){
        book_BLL = new Select_BLL();
        setModal(true);
        setTitle("Chọn sách để mượn");
        // setIconImage(Toolkit.getDefaultToolkit().getImage(getClass().getResource("/icon/book.png")));
        setLayout(new BorderLayout(10, 10));
        getContentPane().setBackground(Color.BLUE);

        // --- Bảng chi tiết ---
        String[] columnNames = {"Mã chi tiết sách", "Tên sách", "Thể loại", "Nhà cung câp", "Năm xuất bản", "Số trang", "Phí mượn", "Số trang hư hỏng"};
        tableModel = new DefaultTableModel(columnNames, 0);
        table = new JTable(tableModel);
        table.setBackground(Color.WHITE);
        table.setSelectionBackground(Color.YELLOW);
        JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.getViewport().setBackground(Color.WHITE);
        add(scrollPane, BorderLayout.CENTER);

        

        // --- Panel nhập ---
        JPanel panelInput = new JPanel();
        panelInput.setLayout(new BoxLayout(panelInput, BoxLayout.Y_AXIS));
        panelInput.setBackground(Color.BLUE);


        // --- Panel nút ---
        JPanel panelSearch = new JPanel();
        panelSearch.setBackground(Color.BLUE);
        panelSearch.add(new JLabel("Từ khóa:"));
        txtTuKhoa = new JTextField(20);
        panelSearch.add(txtTuKhoa);
        btnTim = new JButton("Tìm");
        panelSearch.add(btnTim);
        btnChon = new JButton("Chọn");
        panelSearch.add(btnChon);

        JPanel panelBottom = new JPanel(new BorderLayout(10,10));
        panelBottom.setBackground(Color.BLUE);
        panelBottom.add(panelInput, BorderLayout.CENTER);
        add(panelBottom, BorderLayout.SOUTH);

        // --- ActionListeners ---

        btnTim.addActionListener(e -> {
            String keyword = txtTuKhoa.getText().trim();
            if (keyword.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Vui lòng nhập từ khóa để tìm kiếm!", "Thông báo", JOptionPane.INFORMATION_MESSAGE);
                return;
            }
        
            loadBookDetailsByName(keyword);
        });

        btnChon.addActionListener(e -> {
            int selectedRow = table.getSelectedRow();
            if (selectedRow != -1) {
                book[0] = tableModel.getValueAt(selectedRow, 0).toString();
                dispose();
            } else {
                JOptionPane.showMessageDialog(this, "Vui lòng chọn một cuốn sách!", "Thông báo", JOptionPane.INFORMATION_MESSAGE);
            }
        });

        // Load chi tiết lần đầu
        loadBookDetails();

        setSize(1000, 600);
        setLocationRelativeTo(getOwner());
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);

        panelBottom.add(panelSearch, BorderLayout.NORTH);
        panelBottom.add(panelInput, BorderLayout.CENTER);

        add(panelBottom, BorderLayout.SOUTH);
    }

    // Phương thức load các sách có mã phiếu mượn là ID
    private void PayBook(String ID, String[] book){
        book_BLL = new Select_BLL();
        setModal(true);
        setTitle("Chọn sách để trả");
        // setIconImage(Toolkit.getDefaultToolkit().getImage(getClass().getResource("/icon/book.png")));
        setLayout(new BorderLayout(10, 10));
        getContentPane().setBackground(Color.BLUE);

        // --- Bảng chi tiết ---
        String[] columnNames = {"Mã chi tiết sách", "Tên sách", "Thể loại", "Nhà cung câp", "Năm xuất bản", "Số trang", "Số trang hư hỏng"};
        tableModel = new DefaultTableModel(columnNames, 0);
        table = new JTable(tableModel);
        table.setBackground(Color.WHITE);
        table.setSelectionBackground(Color.YELLOW);
        JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.getViewport().setBackground(Color.WHITE);
        add(scrollPane, BorderLayout.CENTER);

        

        // --- Panel nhập ---
        JPanel panelInput = new JPanel();
        panelInput.setLayout(new BoxLayout(panelInput, BoxLayout.Y_AXIS));
        panelInput.setBackground(Color.BLUE);


        // --- Panel nút ---
        JPanel panelSearch = new JPanel();
        panelSearch.setBackground(Color.BLUE);
        panelSearch.add(new JLabel("Từ khóa:"));
        txtTuKhoa = new JTextField(20);
        panelSearch.add(txtTuKhoa);
        btnTim = new JButton("Tìm");
        panelSearch.add(btnTim);
        btnChon = new JButton("Chọn");
        panelSearch.add(btnChon);

        JPanel panelBottom = new JPanel(new BorderLayout(10,10));
        panelBottom.setBackground(Color.BLUE);
        panelBottom.add(panelInput, BorderLayout.CENTER);
        add(panelBottom, BorderLayout.SOUTH);

        btnChon.addActionListener(e -> {
            int selectedRow = table.getSelectedRow();
            if (selectedRow != -1) {
                book[0] = tableModel.getValueAt(selectedRow, 0).toString();
                book[1] = tableModel.getValueAt(selectedRow, 1).toString();
                book[2] = tableModel.getValueAt(selectedRow, 2).toString();
                book[3] = tableModel.getValueAt(selectedRow, 3).toString();
                book[4] = tableModel.getValueAt(selectedRow, 4).toString();
                book[5] = tableModel.getValueAt(selectedRow, 5).toString();
                book[6] = tableModel.getValueAt(selectedRow, 6).toString();
                dispose();
            } else {
                JOptionPane.showMessageDialog(this, "Vui lòng chọn một cuốn sách!", "Thông báo", JOptionPane.INFORMATION_MESSAGE);
            }
        });

        // Load chi tiết lần đầu
        loadPay(ID);

        setSize(1000, 600);
        setLocationRelativeTo(getOwner());
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);

        panelBottom.add(panelSearch, BorderLayout.NORTH);
        panelBottom.add(panelInput, BorderLayout.CENTER);

        add(panelBottom, BorderLayout.SOUTH);
    }

    public void loadBookDetails() {
        tableModel.setRowCount(0);
        List<String> books = book_BLL.getAll_Book();
        if (books != null) {
            for (String book : books) {
                String[] data = book.split(";");
                tableModel.addRow(new Object[]{
                    data[0], // Mã chi tiết
                    data[1], // Tên sách
                    data[2], // Thể loại
                    data[3], // Nhà cung cấp
                    data[4], // Năm xuất bản
                    data[5], // Số trang
                    data[6], // Phí mượn
                    data[7]  // Trang hư
                });
            }
        } else {
            JOptionPane.showMessageDialog(this, "Không thể tải danh sách sách!", "Lỗi", JOptionPane.ERROR_MESSAGE);
        }
    }

    public void loadBookDetailsByName(String keyword) {
        tableModel.setRowCount(0);
        List<String> books = book_BLL.searchBooksByName(keyword);
        if (books != null && !books.isEmpty()) {
            for (String book : books) {
                String[] data = book.split(";");
                tableModel.addRow(new Object[]{
                    data[0], data[1], data[2], data[3], data[4], data[5], data[6], data[7]
                });
            }
        } else {
            JOptionPane.showMessageDialog(this, "Không tìm thấy kết quả nào.", "Thông báo", JOptionPane.INFORMATION_MESSAGE);
        }
    }

    public void loadPay(String ID){
        tableModel.setRowCount(0);
        List<String> books = book_BLL.getBookByLoan(ID);
        if (books != null && !books.isEmpty()) {
            for (String book : books) {
                String[] data = book.split(";");
                tableModel.addRow(new Object[]{
                    data[0], data[1], data[2], data[3], data[4], data[5], data[6]
                });
            }
        } else {
            JOptionPane.showMessageDialog(this, "Không tìm thấy kết quả nào.", "Thông báo", JOptionPane.INFORMATION_MESSAGE);
        }
    }

}


