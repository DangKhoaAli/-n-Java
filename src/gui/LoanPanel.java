package gui;

import BLL.Borrow_Details_BLL;
import BLL.Loan_slip_BLL;
import DAO.Book_Details_DAO;

import java.awt.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.*;
import java.util.List;
import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableModel;
import model.Loan_slip;

public class LoanPanel extends JPanel {
    private Loan_slip_BLL loan_slip_BLL;
    private Borrow_Details_BLL borrow_details_BLL;
    private Book_Details_DAO book_details_DAO;

    private JTable table;
    private DefaultTableModel tableModel;

    private JTextField txtPhieuMuon;
    private JTextField txtDocGia;
    private JTextField txtThuThu;
    private JTextField txtSoLuongSach;
    private JTextField txtNgayMuon;
    private JTextField txtNgayDuKienTra;
    private JTextField txtPhiMuon;

    private JTextField txtTuKhoa;

    private JButton btnThem;
    private JButton btnSua;
    private JButton btnXoa;
    private JButton btnHuy;
    private JButton btnTim;
    private JButton btnXemChiTiet;

    // Map lưu chi tiết phiếu mượn (key: mã phiếu mượn, value: danh sách chi tiết)
    private Map<String, java.util.List<Object[]>> loanDetailsMap = new HashMap<>();

    public LoanPanel() {
        loan_slip_BLL = new Loan_slip_BLL();
        borrow_details_BLL = new Borrow_Details_BLL();
        book_details_DAO = new Book_Details_DAO();

        setBackground(Color.BLUE);
        setLayout(new BorderLayout(10, 10));

        // --- Panel bảng hiển thị phiếu mượn ---
        JPanel panelTable = new JPanel(new BorderLayout());
        panelTable.setBackground(Color.BLUE);

        String[] columnNames = {"Mã Phiếu Mượn", "Mã độc giả", "Mã thủ thư", "Số lượng sách", "Ngày Mượn", "Ngày Dự Kiến Trả", "Phí Mượn"};
        tableModel = new DefaultTableModel(columnNames, 0);
        // Ví dụ: thêm một phiếu mượn mẫu
        tableModel.addRow(new Object[]{"L001", "DG001", "TT001", "5", "03/25/2025", "06/20/2025", "10000"});

        table = new JTable(tableModel);
        table.setBackground(Color.WHITE);
        table.setSelectionBackground(Color.YELLOW);
        JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.getViewport().setBackground(Color.WHITE);
        panelTable.add(scrollPane, BorderLayout.CENTER);

        table.getSelectionModel().addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent e) {
                // Tránh xử lý hai lần (khi bắt đầu và khi kết thúc)
                if (!e.getValueIsAdjusting()) {
                    int selectedRow = table.getSelectedRow();
                    if (selectedRow != -1) {
                        txtPhieuMuon.setText(tableModel.getValueAt(selectedRow, 0).toString());
                        txtDocGia.setText(tableModel.getValueAt(selectedRow, 1).toString());
                        txtThuThu.setText(tableModel.getValueAt(selectedRow, 2).toString());
                        txtSoLuongSach.setText(tableModel.getValueAt(selectedRow, 3).toString());
                        txtNgayMuon.setText(tableModel.getValueAt(selectedRow, 4).toString());
                        txtNgayDuKienTra.setText(tableModel.getValueAt(selectedRow, 5).toString());
                        txtPhiMuon.setText(tableModel.getValueAt(selectedRow, 6).toString());
                    }
                }
            }
        });

        // --- Panel nhập thông tin phiếu mượn ---
        txtPhieuMuon = new JTextField();
        txtPhieuMuon.setBorder(BorderFactory.createTitledBorder("Mã phiếu mượn"));
        txtDocGia = new JTextField();
        txtDocGia.setBorder(BorderFactory.createTitledBorder("Mã độc giả"));
        txtThuThu = new JTextField();
        txtThuThu.setBorder(BorderFactory.createTitledBorder("Mã thủ thư"));
        txtSoLuongSach = new JTextField();
        txtSoLuongSach.setBorder(BorderFactory.createTitledBorder("Số lượng sách"));
        txtNgayMuon = new JTextField();
        txtNgayMuon.setBorder(BorderFactory.createTitledBorder("Ngày mượn"));
        txtNgayDuKienTra = new JTextField();
        txtNgayDuKienTra.setBorder(BorderFactory.createTitledBorder("Ngày dự kiến trả"));
        txtPhiMuon = new JTextField();
        txtPhiMuon.setBorder(BorderFactory.createTitledBorder("Phí mượn"));
        txtPhiMuon.setEditable(false); // Không cho phép sửa phí mượn trực tiếp
        txtPhiMuon.setText("0");

        JPanel panelInput = new JPanel();
        panelInput.setLayout(new BoxLayout(panelInput, BoxLayout.Y_AXIS));
        panelInput.setBackground(Color.BLUE);
        panelInput.add(txtPhieuMuon);
        panelInput.add(txtDocGia);
        panelInput.add(txtThuThu);
        panelInput.add(txtSoLuongSach);
        panelInput.add(txtNgayMuon);
        panelInput.add(txtNgayDuKienTra);
        panelInput.add(txtPhiMuon);

        // --- Panel tìm kiếm ---
        JPanel panelSearch = new JPanel();
        panelSearch.setBackground(Color.BLUE);
        panelSearch.add(new JLabel("Từ khóa:"));
        txtTuKhoa = new JTextField(20);
        panelSearch.add(txtTuKhoa);
        btnTim = new JButton("Tìm");
        panelSearch.add(btnTim);

        // --- Panel chứa các nút chức năng ---
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

        // --- Gộp các panel nhập, tìm kiếm và nút ---
        JPanel panelBottom = new JPanel(new BorderLayout(10, 10));
        panelBottom.setBackground(Color.BLUE);
        panelBottom.add(panelSearch, BorderLayout.NORTH);
        panelBottom.add(panelInput, BorderLayout.CENTER);
        panelBottom.add(panelButtons, BorderLayout.SOUTH);

        // --- Giao diện chính ---
        add(panelTable, BorderLayout.CENTER);
        add(panelBottom, BorderLayout.SOUTH);

        // --- Xử lý các nút chức năng ---
        btnThem.addActionListener(e -> {
            try{
                String PhieuMuon = txtPhieuMuon.getText().trim();
                String docGia = txtDocGia.getText().trim();
                String thuThu = txtThuThu.getText().trim();
                String soLuong = txtSoLuongSach.getText().trim();
                String NgayMuon = txtNgayMuon.getText().trim();
                String NgayDuKienTra = txtNgayDuKienTra.getText().trim();

                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");

                LocalDate ngaymuon = LocalDate.parse(NgayMuon, formatter);
                LocalDate ngaytra = LocalDate.parse(NgayDuKienTra, formatter);

                String result = loan_slip_BLL.addLoan_slip(PhieuMuon, docGia, thuThu, soLuong, ngaymuon, ngaytra);

                JOptionPane.showMessageDialog(this, result);

                // Nhập chi tiết phiếu mượn bắt buộc nhập cho từng cuốn sách
                // Nếu thêm phiếu mượn thành công thì mở dialog chọn sách
                if (result.equals("Đã thêm thành công 1 phiếu mượn")) {
                    for (int i = 0; i < Integer.parseInt(soLuong); i++) {
                        String[] selectedBookID = new String[1];
                        Select dialog = new Select("",selectedBookID, "loan");
                        dialog.setVisible(true);
        
                        if (selectedBookID[0] != null && !selectedBookID[0].isEmpty()) {
                            String maSach = selectedBookID[0];
                            System.out.println("Selected Book ID: " + maSach);
                            String result1 = borrow_details_BLL.addBorrow_Detail(maSach, PhieuMuon);
                            book_details_DAO.updateStatus_Book(maSach, "Đang được mượn");
                            loan_slip_BLL.update_fee(PhieuMuon, ngaymuon, ngaytra);
                        }
                    }
                }


                loadLoan_slip();

            } catch (DateTimeParseException ex ){
                JOptionPane.showMessageDialog(this, "Lỗi định dạng ngày. Vui lòng nhập ngày theo định dạng dd/MM/yyyy!", "Lỗi", JOptionPane.ERROR_MESSAGE);
            } catch (Exception ex){
                JOptionPane.showMessageDialog(null, "Đã xảy ra lỗi: " + ex.getMessage(), "Lỗi", JOptionPane.ERROR_MESSAGE);
            }
        });

        
        table.getSelectionModel().addListSelectionListener(event -> {
            if (!event.getValueIsAdjusting() && table.getSelectedRow() != -1) {
                int selectedRow = table.getSelectedRow();
        
        
                // Hiển thị dữ liệu
                txtPhieuMuon.setText(tableModel.getValueAt(selectedRow, 0).toString());
                txtDocGia.setText(tableModel.getValueAt(selectedRow, 1).toString());
                txtThuThu.setText(tableModel.getValueAt(selectedRow, 2).toString());
                txtSoLuongSach.setText(tableModel.getValueAt(selectedRow, 3).toString());
                txtNgayMuon.setText(tableModel.getValueAt(selectedRow, 4).toString());
                txtNgayDuKienTra.setText(tableModel.getValueAt(selectedRow, 5).toString());
                txtPhiMuon.setText(tableModel.getValueAt(selectedRow, 6).toString());
                
                txtPhieuMuon.setEditable(false);
                txtSoLuongSach.setEditable(false);
                txtPhiMuon.setEditable(false);
            }
        });
        

        btnSua.addActionListener(e -> {
            try {
                String PhieuMuon = txtPhieuMuon.getText().trim();
                String docGia = txtDocGia.getText().trim();
                String thuThu = txtThuThu.getText().trim();
                String NgayMuon = txtNgayMuon.getText().trim();
                String NgayDuKienTra = txtNgayDuKienTra.getText().trim();

                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");

                LocalDate ngaymuon = LocalDate.parse(NgayMuon, formatter);
                LocalDate ngaytra = LocalDate.parse(NgayDuKienTra, formatter);

                String result = loan_slip_BLL.updateLoan_slip(PhieuMuon, docGia, thuThu, ngaymuon, ngaytra);
                loan_slip_BLL.update_fee(PhieuMuon, ngaymuon, ngaytra);
                JOptionPane.showMessageDialog(this, result);
                loadLoan_slip();
            } catch (DateTimeParseException ex ){
                JOptionPane.showMessageDialog(this, "Lỗi định dạng ngày. Vui lòng nhập ngày theo định dạng yyyy-MM-dd!", "Lỗi", JOptionPane.ERROR_MESSAGE);
            } catch (Exception ex){
                JOptionPane.showMessageDialog(this, "Đã xảy ra lỗi: " + ex.getMessage(), "Lỗi", JOptionPane.ERROR_MESSAGE);
            }
        });

        // btnXoa.addActionListener(e -> {
        //     int selectedRow = table.getSelectedRow();
        //     if (selectedRow == -1) {
        //         JOptionPane.showMessageDialog(this, "Vui lòng chọn dòng để xóa");
        //         return;
        //     }

        //     try {
        //         String PhieuMuon = tableModel.getValueAt(selectedRow, 0).toString();
        //         String result = loan_slip_BLL.deleteLoan_slip(PhieuMuon);
        //         JOptionPane.showMessageDialog(this, result);
        //         loadLoan_slip();
        //     } catch (DateTimeParseException ex ){
        //         JOptionPane.showMessageDialog(this, "Lỗi định dạng ngày. Vui lòng nhập ngày theo định dạng yyyy-MM-dd!", "Lỗi", JOptionPane.ERROR_MESSAGE);
        //     } catch (Exception ex) {
        //         JOptionPane.showMessageDialog(this, "Đã xảy ra lỗi: " + ex.getMessage(), "Lỗi", JOptionPane.ERROR_MESSAGE);
        //     }
        // });

        btnHuy.addActionListener(e -> {
            txtPhieuMuon.setText("");
            txtPhieuMuon.setEditable(true);
            txtDocGia.setText("");
            txtThuThu.setText("");
            txtSoLuongSach.setText("");
            txtSoLuongSach.setEditable(true);
            txtNgayMuon.setText("");
            txtNgayDuKienTra.setText("");
            txtPhiMuon.setText("");
            table.clearSelection();
        });

        btnTim.addActionListener(e -> {
            try {
                String keyword = txtTuKhoa.getText().trim();
                if (keyword.isEmpty()) {
                    JOptionPane.showMessageDialog(this, "Vui lòng nhập ID cần tìm kiếm!");
                    loadLoan_slip();
                    return;
                }

                Loan_slip loan = loan_slip_BLL.searchLoan_slip(keyword);
                if (loan != null) {
                    tableModel.setRowCount(0);
                    tableModel.addRow(new Object[]{
                        loan.getID(),
                        loan.getID_Reader(),
                        loan.getID_Staff(),
                        loan.getSo_luong(),
                        loan.getBorrow_Day().format(DateTimeFormatter.ofPattern("dd/MM/yyyy")),
                        loan.getExpected_Date().format(DateTimeFormatter.ofPattern("dd/MM/yyyy")),
                        loan.getFee()
                    });
                }
                else {
                    JOptionPane.showMessageDialog(this, "Không tìm thấy phiếu mượn với ID: " + keyword);
                    loadLoan_slip();
                }
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "Đã xảy ra lỗi: " + ex.getMessage(), "Lỗi", JOptionPane.ERROR_MESSAGE);
            }
        });

        btnXemChiTiet.addActionListener(e -> {
            int selectedRow = table.getSelectedRow();
            if (selectedRow == -1) {
                JOptionPane.showMessageDialog(this, "Vui lòng chọn dòng để xem chi tiết!");
                return;
            }
            Object[] loanData = new Object[tableModel.getColumnCount()];
            for (int i = 0; i < tableModel.getColumnCount(); i++) {
                loanData[i] = tableModel.getValueAt(selectedRow, i);
            }

            new LoanDetails(loanData, this);
        });

        loadLoan_slip();

        setPreferredSize(new Dimension(900, 600));
        setLayout(new BorderLayout());
        add(panelTable, BorderLayout.CENTER);
        JPanel panelOuterBottom = new JPanel(new BorderLayout(10,10));
        panelOuterBottom.setBackground(Color.BLUE);
        panelOuterBottom.add(new JPanel(), BorderLayout.NORTH); // khoảng cách cho phần tìm kiếm
        panelOuterBottom.add(panelBottom, BorderLayout.CENTER);
        add(panelOuterBottom, BorderLayout.SOUTH);
    }

    public void loadLoan_slip(){
        // Xóa dữ liệu cũ trên bảng
        tableModel.setRowCount(0);
    
        // Gọi BLL để lấy danh sách độc giả từ CSDL
        List<Loan_slip> Loan = loan_slip_BLL.getLoan_slip();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        
        // Kiểm tra danh sách có dữ liệu không
        if (Loan != null) {
            for (Loan_slip loan : Loan) {
                tableModel.addRow(new Object[]{
                    loan.getID(),
                    loan.getID_Reader(),
                    loan.getID_Staff(),
                    loan.getSo_luong(),
                    loan.getBorrow_Day().format(DateTimeFormatter.ofPattern("dd/MM/yyyy")),
                    loan.getExpected_Date().format(DateTimeFormatter.ofPattern("dd/MM/yyyy")),
                    loan.getFee()
                });
            }
        } else {
            JOptionPane.showMessageDialog(this, "Không thể tải danh sách độc giả!", "Lỗi", JOptionPane.ERROR_MESSAGE);
        }
    }
}
