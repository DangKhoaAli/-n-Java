package gui;

import BLL.Book_BLL;
import BLL.Book_Details_BLL;
import java.awt.*;
import java.util.List;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import model.Books;

public class BookPanel extends JPanel {
    private Book_BLL book_BLL;
    private Book_Details_BLL book_details_BLL;

    private JTable table;
    private DefaultTableModel tableModel;

    private JTextField txtMaSach;
    private JTextField txtTenSach;
    private JTextField txtTheLoai;
    private JTextField txtTacGia;
    private JTextField txtGia;
    private JTextField txtPhiMuon;
    private JTextField txtSoLuong;
    
    private RoundedTxtField txtTuKhoa;

    private RoundedButton btnThem;
    private RoundedButton btnSua;
    private RoundedButton btnXoa;
    private RoundedButton btnHuy;
    private RoundedButton btnTim;
    private RoundedButton btnXemChiTiet;
    private RoundedButton btnDangXuat;

    public BookPanel() {
        book_BLL = new Book_BLL();
        book_details_BLL = new Book_Details_BLL();
        
        setBackground(new Color(230, 236, 243));
        setOpaque(true);
        setLayout(new BorderLayout(0,0));
        JPanel panelTable = new JPanel(new BorderLayout());
        panelTable.setBackground(new Color(230, 236, 243));
        String[] columnNames = {"Mã sách", "Tên sách", "Thể loại", "Tác giá", "Giá", "Phí mượn","Số lượng", "Tồn tại"};
        tableModel = new DefaultTableModel(columnNames,0);
        table = new JTable(tableModel);
        table.setBackground(Color.WHITE);
        table.setSelectionBackground(Color.YELLOW);

        JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.getViewport().setBackground(Color.WHITE);
        panelTable.add(scrollPane, BorderLayout.CENTER);

        txtMaSach = new JTextField();
        txtMaSach.setBorder(BorderFactory.createTitledBorder("Mã sách"));

        txtTenSach = new JTextField();
        txtTenSach.setBorder(BorderFactory.createTitledBorder("Tên sách"));

        txtTheLoai = new JTextField();
        txtTheLoai.setBorder(BorderFactory.createTitledBorder("Thể loại"));

        txtTacGia = new JTextField();
        txtTacGia.setBorder(BorderFactory.createTitledBorder("Tác giả"));

        txtGia = new JTextField();
        txtGia.setBorder(BorderFactory.createTitledBorder("Giá"));

        txtPhiMuon = new JTextField();
        txtPhiMuon.setBorder(BorderFactory.createTitledBorder("Phí mượn"));

        txtSoLuong = new JTextField();
        txtSoLuong.setBorder(BorderFactory.createTitledBorder("Số lượng"));

        JPanel panelInput = new JPanel();
        panelInput.setLayout(new BoxLayout(panelInput, BoxLayout.Y_AXIS));
        panelInput.setBackground(new Color(230, 236, 243));
        panelInput.add(txtMaSach);
        panelInput.add(txtTenSach);
        panelInput.add(txtTheLoai);
        panelInput.add(txtTacGia);
        panelInput.add(txtGia);
        panelInput.add(txtPhiMuon);
        panelInput.add(txtSoLuong);

        // Panel tìm kiếm
        JPanel panelSearch = new JPanel();
        panelSearch.setBackground(new Color(230, 236, 243));
        panelSearch.add(new JLabel("Từ khóa:"));
        txtTuKhoa = new RoundedTxtField(20, 16);
        txtTuKhoa.setBackground(Color.WHITE);
        txtTuKhoa.setPlaceholder("Nhập tên sách muốn tìm");
 

        panelSearch.add(txtTuKhoa);
        btnTim = new RoundedButton("Tìm");
        panelSearch.add(btnTim);
        
        // Panel chứa các nút
        JPanel panelButtons = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        panelButtons.setBackground(new Color(230, 236, 243));
        panelButtons.setBorder(BorderFactory.createEmptyBorder());
        btnThem = new RoundedButton("Thêm");
        btnSua = new RoundedButton("Sửa");
        btnXoa = new RoundedButton("Xóa");
        btnHuy = new RoundedButton("Hủy");
        btnXemChiTiet = new RoundedButton("Xem chi tiết");
        btnDangXuat = new RoundedButton("Đăng xuất");
        
        panelButtons.add(btnThem);
        panelButtons.add(btnSua);
        panelButtons.add(btnXoa);
        panelButtons.add(btnHuy);
        panelButtons.add(btnXemChiTiet);
        panelButtons.add(btnDangXuat);
        
        btnThem.addActionListener(e -> {
            String maSach = txtMaSach.getText().trim();
            String tenSach = txtTenSach.getText().trim();
            String theLoai = txtTheLoai.getText().trim();
            String tacGia = txtTacGia.getText().trim();
            String gia = txtGia.getText().trim();
            String phiMuon = txtPhiMuon.getText().trim();
            String soLuong = txtSoLuong.getText().trim();

            String result = book_BLL.addBook(maSach, tenSach, tacGia, theLoai, soLuong, gia, phiMuon);
            JOptionPane.showMessageDialog(this, result);

            if(result.equals("Da them thanh cong sach!")) {
                int soluong = Integer.parseInt(soLuong);
                String nhaCungCap = JOptionPane.showInputDialog(this, "Nhà cung cấp:");
                String namXuatBan = JOptionPane.showInputDialog(this, "Năm xuất bản:");
                String soTrang = JOptionPane.showInputDialog(this, "Số trang:");
                for(int i = 1; i<=soluong; i++){
                    String maChiTiet = maSach + "_" + i;
                    
                    String result1 = book_details_BLL.addBook(maChiTiet, maSach, nhaCungCap, namXuatBan, soTrang, "Hiện có", "0");
                    JOptionPane.showMessageDialog(this, result1);
                }
            }

            loadBook();
        });

        table.getSelectionModel().addListSelectionListener(event -> {
            if (!event.getValueIsAdjusting() && table.getSelectedRow() != -1) {
                int selectedRow = table.getSelectedRow();
        
                // Lấy giá trị số lượng
                String tontaiStr = tableModel.getValueAt(selectedRow, 7).toString();
                int tontai = Integer.parseInt(tontaiStr);
        
                // Hiển thị dữ liệu
                txtMaSach.setText(tableModel.getValueAt(selectedRow, 0).toString());
                txtTenSach.setText(tableModel.getValueAt(selectedRow, 1).toString());
                txtTheLoai.setText(tableModel.getValueAt(selectedRow, 2).toString());
                txtTacGia.setText(tableModel.getValueAt(selectedRow, 3).toString());
                txtGia.setText(tableModel.getValueAt(selectedRow, 4).toString());
                txtPhiMuon.setText(tableModel.getValueAt(selectedRow, 5).toString());
                txtSoLuong.setText(tableModel.getValueAt(selectedRow, 6).toString());
        
                // Nếu số lượng = 0, thì không cho chỉnh sửa
                boolean editable = tontai > 0;
        
                txtTenSach.setEnabled(editable);
                txtTheLoai.setEnabled(editable);
                txtTacGia.setEnabled(editable);
                txtGia.setEnabled(editable);
                txtPhiMuon.setEnabled(editable);
        
                // Luôn khóa mã sách và số lượng
                txtMaSach.setEnabled(false);
                txtSoLuong.setEnabled(false);
            }
        });
        
        
        btnSua.addActionListener(e -> {
            try {
                // Lấy dữ liệu từ các ô nhập
                String maSach = txtMaSach.getText();
                String tenSach = txtTenSach.getText();
                String tacgia = txtTacGia.getText();
                String theloai = txtTheLoai.getText(); 
                String soluong = txtSoLuong.getText();
                String gia = txtGia.getText();
                String phimuon = txtPhiMuon.getText();
                
        
                // Gọi phương thức updateReader
                String result = book_BLL.updateBooks(maSach, tenSach, tacgia, theloai, soluong, gia, phimuon);
                
                // Hiển thị thông báo từ kết quả trả về
                JOptionPane.showMessageDialog(this, result, "Thông báo", JOptionPane.INFORMATION_MESSAGE);
                loadBook();
        
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "Lỗi: " + ex.getMessage(), "Lỗi", JOptionPane.ERROR_MESSAGE);
            }
        
        });

        btnXoa.addActionListener(e -> {
            int selectedRow = table.getSelectedRow();
            if(selectedRow == -1) {
                JOptionPane.showMessageDialog(this, "Vui lòng chọn dòng để xóa");
                return;
            }
            
            try{
                String ID = tableModel.getValueAt(selectedRow,0).toString();
                
                String result = book_BLL.deleteBook(ID);
                JOptionPane.showMessageDialog(this, result, "Thông báo", JOptionPane.INFORMATION_MESSAGE);
                loadBook();
            }catch(Exception ex){
                JOptionPane.showMessageDialog(this, "Lỗi" + ex.getMessage(), "Lỗi", JOptionPane.ERROR_MESSAGE);
            }
        });

        btnHuy.addActionListener(e -> {
            txtMaSach.setText("");
            txtMaSach.setEnabled(true);
            txtTenSach.setText("");
            txtTenSach.setEnabled(true);
            txtTheLoai.setText("");
            txtTheLoai.setEnabled(true);
            txtTacGia.setText("");
            txtTacGia.setEnabled(true);
            txtGia.setText("");
            txtGia.setEnabled(true);
            txtPhiMuon.setText("");
            txtPhiMuon.setEnabled(true);
            txtSoLuong.setText("");
            txtSoLuong.setEnabled(true);
            table.clearSelection();
        });

        btnTim.addActionListener(e -> {
            String keyword = txtTuKhoa.getText().trim().toLowerCase();
            if (keyword == null || keyword.trim().isEmpty()) {
                JOptionPane.showMessageDialog(this, "Cần nhập dữ liệu tìm kiếm!");
                loadBook();
            }
            else{
                List<Books> books = book_BLL.searchBooks(keyword);
                if (books != null) {
                    tableModel.setRowCount(0);
                    for (Books book : books) {
                        tableModel.addRow(new Object[]{
                            book.getID(),
                            book.getName(),
                            book.getCategory(),
                            book.getAuthor(),
                            book.getPrice(),
                            book.getLoan_fee(),
                            book.getQuanlity(),
                            book.getExist()
                        });
                    }
                } else {
                    JOptionPane.showMessageDialog(this, "Không thể tải danh sách độc giả!", "Lỗi", JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        btnXemChiTiet.addActionListener(e -> {
            int selectedRow = table.getSelectedRow();
            if(selectedRow == -1) {
                JOptionPane.showMessageDialog(this, "Vui lòng chọn dòng để xem chi tiết!");
                return;
            }
            
            Object[] bookData = new Object[table.getColumnCount()];
            for (int i = 0; i < table.getColumnCount(); i++) {
                bookData[i] = table.getValueAt(selectedRow, i);
            }
            Frame ownerFrame = (Frame) SwingUtilities.getWindowAncestor(this);
            new BookDetails(bookData,ownerFrame,this).setVisible(true);
        });

        btnDangXuat.addActionListener(e -> {
            // đóng Main
            JFrame mainFrame = (JFrame) SwingUtilities.getWindowAncestor(this);
            mainFrame.dispose();
        
            // tạo Login mới
            LoginPanel login = new LoginPanel();
            // đăng ký listener để khi login dispose→Main lại khởi chạy
            login.addWindowListener(new java.awt.event.WindowAdapter() {
                @Override
                public void windowClosed(java.awt.event.WindowEvent e) {
                    if (login.isSucceeded()) {
                        new Main(login.getUserRole()).setVisible(true);
                    } else {
                        System.exit(0);
                    }
                }
            });
        });
        
        loadBook();

        add(panelTable, BorderLayout.CENTER);

        JPanel panelBottom = new JPanel(new BorderLayout(0,0));
        panelBottom.setBackground(new Color(230,236,243));
        panelBottom.setBorder(BorderFactory.createEmptyBorder()); 
        panelSearch.setBackground(new Color(230,236,243));
        panelInput.setBackground(new Color(230,236,243));
        panelButtons.setBackground(new Color(230,236,243));
        
        panelBottom.add(panelSearch, BorderLayout.NORTH);
        panelBottom.add(panelInput, BorderLayout.CENTER);
        panelBottom.add(panelButtons, BorderLayout.SOUTH);

        add(panelBottom, BorderLayout.SOUTH);
    }

    public void loadBook(){
        tableModel.setRowCount(0);
        
        List<Books> books = book_BLL.getBook();
        // Kiểm tra danh sách có dữ liệu không
        if (books != null) {
            for (Books book  : books) {
                tableModel.addRow(new Object[]{
                    book.getID(),
                    book.getName(),
                    book.getCategory(),
                    book.getAuthor(),
                    book.getPrice(),
                    book.getLoan_fee(),
                    book.getQuanlity(),
                    book.getExist()
                });
            }
        } else {
            JOptionPane.showMessageDialog(this, "Không thể tải danh sách độc giả!", "Lỗi", JOptionPane.ERROR_MESSAGE);
        }
    }


}