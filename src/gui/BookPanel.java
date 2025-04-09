package gui;

import java.awt.*;
import java.time.LocalDate;
import java.util.*;
import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableModel;

<<<<<<< HEAD
=======
import java.util.List;


import BLL.Book_BLL;
import model.Books;
import model.Reader;
import BLL.Book_Details_BLL;
>>>>>>> deb326ed8e4e97ee4d328469e2d8c4171bd0a348

public class BookPanel extends JPanel {
    private Book_BLL book_BLL;
    private Book_Details_BLL book_details_BLL;

    private JTable table;
    private DefaultTableModel tableModel;

    private JTextField txtMaSach;
    private JTextField txtTenSach;
    private JTextField txtTheLoai;
    private JTextField txtTacGia;
    private JTextField txtNCC;
    private JTextField txtNXB;
    private JTextField txtSoTrang;
    private JTextField txtGia;
    private JTextField txtPhiMuon;
    private JTextField txtSoLuong;
    

    private JTextField txtTuKhoa;

    private JButton btnThem;
    private JButton btnSua;
    private JButton btnXoa;
    private JButton btnHuy;
    private JButton btnTim;
    private JButton btnXemChiTiet;


    public BookPanel() {
        book_BLL = new Book_BLL();
        book_details_BLL = new Book_Details_BLL();
        
        setBackground(Color.BLUE);
        setLayout(new BorderLayout(10,10));
        JPanel panelTable = new JPanel(new BorderLayout());
        panelTable.setBackground(Color.BLUE);
        String[] columnNames = {"Mã sách", "Tên sách", "Thể loại", "Tác giá", "Nhà cung cấp","Năm xuất bản", "Số trang", "Giá", "Phí mượn","Số lượng", "Tồn tại"};
        tableModel = new DefaultTableModel(columnNames,0);
        tableModel.addRow(new Object[]{"1","Pháp luật","Giáo dục","Nhiều tác giả","BGD","2005","500","15000","2000","5"});
        table = new JTable(tableModel);
        table.setBackground(Color.WHITE);
        table.setSelectionBackground(Color.YELLOW);

        JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.getViewport().setBackground(Color.WHITE);
        panelTable.add(scrollPane, BorderLayout.CENTER);

        // Tự động đẩy dữ liệu khi chọn dòng
        table.getSelectionModel().addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent e) {
                if (!e.getValueIsAdjusting()) {
                    int selectedRow = table.getSelectedRow();
                    if (selectedRow != -1) {
                        txtMaSach.setText(tableModel.getValueAt(selectedRow, 0).toString());
                        txtTenSach.setText(tableModel.getValueAt(selectedRow, 1).toString());
                        txtTheLoai.setText(tableModel.getValueAt(selectedRow, 2).toString());
                        txtTacGia.setText(tableModel.getValueAt(selectedRow, 3).toString());
                        txtNCC.setText(tableModel.getValueAt(selectedRow, 4).toString());
                        txtNXB.setText(tableModel.getValueAt(selectedRow, 5).toString());
                        txtSoTrang.setText(tableModel.getValueAt(selectedRow, 6).toString());
                        txtGia.setText(tableModel.getValueAt(selectedRow, 7).toString());
                        txtPhiMuon.setText(tableModel.getValueAt(selectedRow, 8).toString());
                        txtSoLuong.setText(tableModel.getValueAt(selectedRow, 9).toString());
                    }
                }
            }
        });

        txtMaSach = new JTextField();
        txtMaSach.setBorder(BorderFactory.createTitledBorder("Mã sách"));

        txtTenSach = new JTextField();
        txtTenSach.setBorder(BorderFactory.createTitledBorder("Tên sách"));

        txtTheLoai = new JTextField();
        txtTheLoai.setBorder(BorderFactory.createTitledBorder("Thể loại"));

        txtTacGia = new JTextField();
        txtTacGia.setBorder(BorderFactory.createTitledBorder("Tác giả"));

        txtNCC = new JTextField();
        txtNCC.setBorder(BorderFactory.createTitledBorder("Nhà cung cấp"));

        txtNXB = new JTextField();
        txtNXB.setBorder(BorderFactory.createTitledBorder("Năm xuất bản"));

        txtSoTrang = new JTextField();
        txtSoTrang.setBorder(BorderFactory.createTitledBorder("Số trang"));

        txtGia = new JTextField();
        txtGia.setBorder(BorderFactory.createTitledBorder("Giá"));

        txtPhiMuon = new JTextField();
        txtPhiMuon.setBorder(BorderFactory.createTitledBorder("Phí mượn"));

        txtSoLuong = new JTextField();
        txtSoLuong.setBorder(BorderFactory.createTitledBorder("Số lượng"));

        JPanel panelInput = new JPanel();
        panelInput.setLayout(new BoxLayout(panelInput, BoxLayout.Y_AXIS));
        panelInput.setBackground(Color.BLUE);
        panelInput.add(txtMaSach);
        panelInput.add(txtTenSach);
        panelInput.add(txtTheLoai);
        panelInput.add(txtTacGia);
        panelInput.add(txtNCC);
        panelInput.add(txtNXB);
        panelInput.add(txtSoTrang);
        panelInput.add(txtGia);
        panelInput.add(txtPhiMuon);
        panelInput.add(txtSoLuong);

        // Panel tìm kiếm
        JPanel panelSearch = new JPanel();
        panelSearch.setBackground(Color.BLUE);
        panelSearch.add(new JLabel("Từ khóa:"));
        txtTuKhoa = new JTextField(20);
        panelSearch.add(txtTuKhoa);
        btnTim = new JButton("Tìm");
        panelSearch.add(btnTim);
        
        // Panel chứa các nút
        JPanel panelButtons = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        panelButtons.setBackground(Color.BLUE);
        btnThem = new JButton("Thêm");
        btnSua = new JButton("Sửa");
        btnXoa = new JButton("Xóa");
        btnHuy = new JButton("Hủy");
        btnXemChiTiet = new JButton("Xem chi tiết");
        
        panelButtons.add(btnThem);
        panelButtons.add(btnSua);
        panelButtons.add(btnXoa);
        panelButtons.add(btnHuy);
        panelButtons.add(btnXemChiTiet);
        
        btnThem.addActionListener(e -> {
            String maSach = txtMaSach.getText().trim();
            String tenSach = txtTenSach.getText().trim();
            String theLoai = txtTheLoai.getText().trim();
            String tacGia = txtTacGia.getText().trim();
            String nhaCungCap = txtNCC.getText().trim();
            String namXuatBan = txtNXB.getText().trim();
            String soTrang = txtSoTrang.getText().trim();
            String gia = txtGia.getText().trim();
            String phiMuon = txtPhiMuon.getText().trim();
            String soLuong = txtSoLuong.getText().trim();

            String result = book_BLL.addBook(maSach, tenSach, tacGia, theLoai, nhaCungCap, namXuatBan, soTrang, soLuong, gia, phiMuon);
            JOptionPane.showMessageDialog(this, result);

            if(result.equals("Da them thanh cong sach!")) {
                int soluong = Integer.parseInt(soLuong);
                for(int i = 1; i<=soluong; i++){
                    String maChiTiet = maSach + "_" + i;
                    
                    String result1 = book_details_BLL.addBook(maChiTiet, maSach, "Hiện có", "0");
                    JOptionPane.showMessageDialog(this, result1);
                }
            }

            loadBook();
        });

        table.getSelectionModel().addListSelectionListener(event -> {
            if (!event.getValueIsAdjusting() && table.getSelectedRow() != -1) {
                int selectedRow = table.getSelectedRow();
        
                // Lấy giá trị số lượng
                String tontaiStr = tableModel.getValueAt(selectedRow, 10).toString();
                int tontai = Integer.parseInt(tontaiStr);
        
                // Hiển thị dữ liệu
                txtMaSach.setText(tableModel.getValueAt(selectedRow, 0).toString());
                txtTenSach.setText(tableModel.getValueAt(selectedRow, 1).toString());
                txtTheLoai.setText(tableModel.getValueAt(selectedRow, 2).toString());
                txtTacGia.setText(tableModel.getValueAt(selectedRow, 3).toString());
                txtNCC.setText(tableModel.getValueAt(selectedRow, 4).toString());
                txtNXB.setText(tableModel.getValueAt(selectedRow, 5).toString());
                txtSoTrang.setText(tableModel.getValueAt(selectedRow, 6).toString());
                txtGia.setText(tableModel.getValueAt(selectedRow, 7).toString());
                txtPhiMuon.setText(tableModel.getValueAt(selectedRow, 8).toString());
                txtSoLuong.setText(tableModel.getValueAt(selectedRow, 9).toString());
        
                // Nếu số lượng = 0, thì không cho chỉnh sửa
                boolean editable = tontai > 0;
        
                txtTenSach.setEnabled(editable);
                txtTheLoai.setEnabled(editable);
                txtTacGia.setEnabled(editable);
                txtNCC.setEnabled(editable);
                txtNXB.setEnabled(editable);
                txtSoTrang.setEnabled(editable);
                txtGia.setEnabled(editable);
                txtPhiMuon.setEnabled(editable);
        
                // Luôn khóa mã sách và số lượng
                txtMaSach.setEnabled(false);
                txtSoLuong.setEnabled(false);
            }
        });
        
        
        btnSua.addActionListener(e -> {
<<<<<<< HEAD
            int selectedRow = table.getSelectedRow();
            if(selectedRow == -1) {
                JOptionPane.showMessageDialog(this, "Vui lòng chọn dòng muốn lưu thay đổi");
                return;
            }
            tableModel.setValueAt(txtMaSach.getText(),selectedRow, 0);
            tableModel.setValueAt(txtTenSach.getText(),selectedRow,1);
            tableModel.setValueAt(txtTheLoai.getText(),selectedRow, 2);
            tableModel.setValueAt(txtTacGia.getText(),selectedRow, 3);
            tableModel.setValueAt(txtNCC.getText(),selectedRow, 4);
            tableModel.setValueAt(txtNXB.getText(),selectedRow, 5);
            tableModel.setValueAt(txtSoTrang.getText(),selectedRow, 6);
            tableModel.setValueAt(txtGia.getText(),selectedRow, 7);
            tableModel.setValueAt(txtPhiMuon.getText(),selectedRow, 8);
            tableModel.setValueAt(txtSoLuong.getText(),selectedRow, 9);
            JOptionPane.showMessageDialog(this, "Cập nhật sách thành công!");
=======
            try {
                // Lấy dữ liệu từ các ô nhập
                String maSach = txtMaSach.getText();
                String tenSach = txtTenSach.getText();
                String tacgia = txtTacGia.getText();
                String theloai = txtTheLoai.getText(); 
                String NCC = txtNCC.getText();
                String NXB = txtNXB.getText();
                String soluong = txtSoLuong.getText();
                String sotrang = txtSoTrang.getText();
                String gia = txtGia.getText();
                String phimuon = txtPhiMuon.getText();
                
        
                // Gọi phương thức updateReader
                String result = book_BLL.updateBooks(maSach, tenSach, tacgia, theloai, NCC, NXB, sotrang, soluong, gia, phimuon);
                
                // Hiển thị thông báo từ kết quả trả về
                JOptionPane.showMessageDialog(this, result, "Thông báo", JOptionPane.INFORMATION_MESSAGE);
                loadBook();
        
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "Lỗi: " + ex.getMessage(), "Lỗi", JOptionPane.ERROR_MESSAGE);
            }
        
>>>>>>> deb326ed8e4e97ee4d328469e2d8c4171bd0a348
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
            txtNCC.setText("");
            txtNCC.setEnabled(true);
            txtNXB.setText("");
            txtNXB.setEnabled(true);
            txtSoTrang.setText("");
            txtSoTrang.setEnabled(true);
            txtGia.setText("");
            txtGia.setEnabled(true);
            txtPhiMuon.setText("");
            txtPhiMuon.setEnabled(true);
            txtSoLuong.setText("");
<<<<<<< HEAD
            table.clearSelection();
=======
            txtSoLuong.setEnabled(true);
>>>>>>> deb326ed8e4e97ee4d328469e2d8c4171bd0a348
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
                            book.getSupplier(),
                            book.getYear(),
                            book.getPage_num(),
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

            new BookDetails(bookData, this);
        });

        loadBook();

        add(panelTable, BorderLayout.CENTER);

        JPanel panelBottom = new JPanel(new BorderLayout(10,10));
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
                    book.getSupplier(),
                    book.getYear(),
                    book.getPage_num(),
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