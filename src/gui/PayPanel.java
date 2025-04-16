package gui;

import java.awt.*;
import java.util.List;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.*;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;

import BLL.Book_Return_BLL;
import BLL.Pay_slip_BLL;
import DAO.Book_Details_DAO;
import DAO.Book_Returned_DAO;
import DAO.Payment_slip_DAO;
import model.Payment_slip;

public class PayPanel extends JPanel {
    private Pay_slip_BLL paySlipBLL;
    private Book_Details_DAO book_details_DAO;
    private Book_Return_BLL book_return_BLL;
    private Payment_slip_DAO payment_slip_DAO;
    private JTable table;
    private DefaultTableModel tableModel;

    private JTextField txtPhieuTra;
    private JTextField txtPhieuMuon;
    private JTextField txtThuThu;
    private JTextField txtSoLuongSach;
    private JTextField txtNgayTra;
    private JTextField txtPhiTreHan;
    private JTextField txtPhiHuHai;

    private JTextField txtTuKhoa;

    private JButton btnThem;
    private JButton btnSua;
    private JButton btnXoa;
    private JButton btnHuy;
    private JButton btnTim;
    private JButton btnXemChiTiet;

    private Map<String, java.util.List<Object[]>> payDetailsMap = new HashMap<>();

    public PayPanel() {
        paySlipBLL = new Pay_slip_BLL();
        book_details_DAO = new Book_Details_DAO();
        book_return_BLL = new Book_Return_BLL();
        payment_slip_DAO = new Payment_slip_DAO();

        setBackground(Color.BLUE);
        setLayout(new BorderLayout(10,10));

        JPanel panelTable = new JPanel(new BorderLayout());
        panelTable.setBackground(Color.BLUE);

        String[] columnNames = {"Mã Phiếu Trả","Mã phiếu mượn","Mã thủ thư","Số lượng sách", "Ngày Trả", "Phí Trễ Hạn", "Phí Hư Hại"};
        tableModel = new DefaultTableModel(columnNames,0);


        table = new JTable(tableModel);
        table.setBackground(Color.WHITE);
        table.setSelectionBackground(Color.YELLOW);
        JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.getViewport().setBackground(Color.WHITE);
        panelTable.add(scrollPane, BorderLayout.CENTER);
        

        txtPhieuTra = new JTextField();
        txtPhieuTra.setBorder(BorderFactory.createTitledBorder("Mã phiếu trả"));

        txtPhieuMuon = new JTextField();
        txtPhieuMuon.setBorder(BorderFactory.createTitledBorder("Mã phiếu mượn"));

        txtThuThu = new JTextField();
        txtThuThu.setBorder(BorderFactory.createTitledBorder("Mã thủ thư"));

        txtSoLuongSach = new JTextField();
        txtSoLuongSach.setBorder(BorderFactory.createTitledBorder("Số lượng sách"));

        txtNgayTra = new JTextField();
        txtNgayTra.setBorder(BorderFactory.createTitledBorder("Ngày trả"));

        txtPhiTreHan = new JTextField();
        txtPhiTreHan.setBorder(BorderFactory.createTitledBorder("Phí trễ hạn"));
        txtPhiTreHan.setEditable(false);
        txtPhiTreHan.setText("0");

        txtPhiHuHai = new JTextField();
        txtPhiHuHai.setBorder(BorderFactory.createTitledBorder("Phí hư hại"));
        txtPhiHuHai.setEditable(false);
        txtPhiHuHai.setText("0");

        JPanel panelInput = new JPanel();
        panelInput.setLayout(new BoxLayout(panelInput, BoxLayout.Y_AXIS));
        panelInput.setBackground(Color.BLUE);
        panelInput.add(txtPhieuTra);
        panelInput.add(txtPhieuMuon);
        panelInput.add(txtThuThu);
        panelInput.add(txtSoLuongSach);
        panelInput.add(txtNgayTra);
        panelInput.add(txtPhiTreHan);
        panelInput.add(txtPhiHuHai);
        
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
        btnHuy = new JButton("Hủy");
        btnXoa = new JButton("Xóa");
        btnXemChiTiet = new JButton("Xem chi tiết");
        
        panelButtons.add(btnThem);
        panelButtons.add(btnSua);
        panelButtons.add(btnHuy);
        panelButtons.add(btnXoa);
        panelButtons.add(btnXemChiTiet);

        JPanel panelBottom = new JPanel(new BorderLayout(10, 10));
        panelBottom.setBackground(Color.BLUE);
        panelBottom.add(panelSearch, BorderLayout.NORTH);
        panelBottom.add(panelInput, BorderLayout.CENTER);
        panelBottom.add(panelButtons, BorderLayout.SOUTH);

        // --- Giao diện chính ---
        add(panelTable, BorderLayout.CENTER);
        add(panelBottom, BorderLayout.SOUTH);
        
        btnThem.addActionListener(e -> {
            try {
                String PhieuTra = txtPhieuTra.getText();
                String PhieuMuon = txtPhieuMuon.getText();
                String thuThu = txtThuThu.getText();
                String soLuongSach = txtSoLuongSach.getText();
                String NgayTra = txtNgayTra.getText();

                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");

                LocalDate ngaymuon = LocalDate.parse(NgayTra, formatter);
                
                String result = paySlipBLL.add_Pay(PhieuTra, PhieuMuon, thuThu, soLuongSach, NgayTra);
                JOptionPane.showMessageDialog(this, result);
                if (result.equals("Thêm phiếu trả thành công!")) {
                    for (int i = 1; i <= Integer.parseInt(soLuongSach); i++) {
                        String[] selectedBookID = new String[1];
                            Select dialog = new Select(PhieuMuon, selectedBookID, "pay");
                            dialog.setVisible(true);
            
                            if (selectedBookID[0] != null && !selectedBookID[0].isEmpty()) {
                                String maSach = selectedBookID[0];
                                String sotranghong = JOptionPane.showInputDialog(this, "Số trang hiện đang hỏng:");


                                String result1 = book_return_BLL.addBookReturned(PhieuTra, maSach, sotranghong);
                                if (result1.equals("Đã thêm chi tiết phiếu trả mới thành công!")) {
                                    if(Integer.parseInt(sotranghong) < 100){
                                        book_details_DAO.updateStatus_Book(maSach, "Hiện có");
                                    } else {
                                        book_details_DAO.updateStatus_Book(maSach, "Đã hỏng");
                                    }
                                    book_details_DAO.updateNum_page(Integer.parseInt(sotranghong), maSach);
                                }
                            }
                    }
                }

                // Cập nhật phí trễ hạn và phí hư hại
                payment_slip_DAO.updateLateFee(PhieuTra);
                payment_slip_DAO.updateDamageFee(PhieuTra);
                loadPayDetails();

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
                txtPhieuTra.setText(tableModel.getValueAt(selectedRow, 0).toString());
                txtPhieuMuon.setText(tableModel.getValueAt(selectedRow, 0).toString());
                txtThuThu.setText(tableModel.getValueAt(selectedRow, 2).toString());
                txtSoLuongSach.setText(tableModel.getValueAt(selectedRow, 3).toString());
                txtNgayTra.setText(tableModel.getValueAt(selectedRow, 4).toString());
                txtPhiTreHan.setText(tableModel.getValueAt(selectedRow, 5).toString());
                txtPhiHuHai.setText(tableModel.getValueAt(selectedRow, 6).toString());

                txtPhieuMuon.setEditable(false);
                txtSoLuongSach.setEditable(false);
                txtPhiTreHan.setEditable(false);
                txtPhiHuHai.setEditable(false);
            }
        });

        btnSua.addActionListener(e -> {
            try{
                int selectedRow = table.getSelectedRow();
                if(selectedRow == -1) {
                    JOptionPane.showMessageDialog(this, "Vui lòng chọn dòng để sửa");
                    return;
                }

                String PhieuTra = txtPhieuTra.getText();
                String NgayTra = txtNgayTra.getText();

                String result = paySlipBLL.update_Pay(PhieuTra, NgayTra);
                JOptionPane.showMessageDialog(this, result);
                if (result.equals("Cập nhật phiếu trả thành công!")) {
                    payment_slip_DAO.updateLateFee(PhieuTra);
                    loadPayDetails();
                }
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "Đã xảy ra lỗi: " + ex.getMessage(), "Lỗi", JOptionPane.ERROR_MESSAGE);
            }

        });

        btnXoa.addActionListener(e -> {
            int selectedRow = table.getSelectedRow();
            if(selectedRow == -1) {
                JOptionPane.showMessageDialog(this, "Vui lòng chọn dòng để xóa");
                return;
            }
            String PhieuTra = tableModel.getValueAt(selectedRow, 0).toString();
            String PhieuMuon = tableModel.getValueAt(selectedRow, 1).toString();
            String soLuongSach = tableModel.getValueAt(selectedRow, 3).toString();
            
            String result = paySlipBLL.delete_Pay(PhieuTra, PhieuMuon, soLuongSach);
            JOptionPane.showMessageDialog(this, result);
            if (result.equals("Xóa phiếu trả thành công!")) {
                loadPayDetails();
            }   
        });

        btnHuy.addActionListener(e -> {
            txtPhieuTra.setText("");
            txtPhieuMuon.setText("");
            txtPhieuMuon.setEditable(true);
            txtThuThu.setText("");
            txtSoLuongSach.setText("");
            txtSoLuongSach.setEditable(true);
            txtNgayTra.setText("");
            txtPhiTreHan.setText("");
            txtPhiHuHai.setText("");
        });

        btnTim.addActionListener(e -> {
            String keyword = txtTuKhoa.getText().trim().toLowerCase();
            if(keyword.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Vui lòng nhập từ khóa tìm kiếm!");
                loadPayDetails();
            }

            Payment_slip paymentSlip = paySlipBLL.search_Pay(keyword);
            if (paymentSlip != null) {
                tableModel.setRowCount(0);
                tableModel.addRow(new Object[]{
                    paymentSlip.getID(),
                    paymentSlip.getID_Loan_slip(),
                    paymentSlip.getID_Staff(),
                    paymentSlip.getSo_luong(),
                    paymentSlip.getPayment_Date().format(DateTimeFormatter.ofPattern("dd/MM/yyyy")),
                    paymentSlip.getLate_fee(),
                    paymentSlip.getDamage_fee()
                });
            } else {
                JOptionPane.showMessageDialog(this, "Không tìm thấy phiếu trả nào với từ khóa: " + keyword);
            }
        });

        btnXemChiTiet.addActionListener(e -> {
            int selectedRow = table.getSelectedRow();
            if(selectedRow == -1) {
                JOptionPane.showMessageDialog(this, "Vui lòng chọn dòng để xem chi tiết!");
                return;
            }
            Object[] payData = new Object[tableModel.getColumnCount()];
            for(int i = 0; i < tableModel.getColumnCount(); i++) {
                payData[i] = tableModel.getValueAt(selectedRow, i);
            }

            new PayDetails(payData, this);
        });
        loadPayDetails();
        add(panelTable, BorderLayout.CENTER);

        // JPanel panelBottom = new JPanel(new BorderLayout(10,10));
        panelBottom.add(panelSearch, BorderLayout.NORTH);
        panelBottom.add(panelInput, BorderLayout.CENTER);
        panelBottom.add(panelButtons, BorderLayout.SOUTH);

        add(panelBottom, BorderLayout.SOUTH);
    }

    public void loadPayDetails() {
        tableModel.setRowCount(0);
        List<Payment_slip> paymentSlips = paySlipBLL.getPaymentSlip();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");

        if(paymentSlips != null) {
            for (Payment_slip paymentSlip : paymentSlips) {
                tableModel.addRow(new Object[]{
                    paymentSlip.getID(),
                    paymentSlip.getID_Loan_slip(),
                    paymentSlip.getID_Staff(),
                    paymentSlip.getSo_luong(),
                    paymentSlip.getPayment_Date().format(formatter),
                    paymentSlip.getLate_fee(),
                    paymentSlip.getDamage_fee()
                });
            }
        }

    }

}