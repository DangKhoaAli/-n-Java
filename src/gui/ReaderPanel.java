package gui;

import BLL.Reader_BLL;
import java.awt.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.List;
import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableModel;
import model.Reader;

public class ReaderPanel extends JPanel {
    private Reader_BLL reader_BLL;

    private JTable table;
    private DefaultTableModel tableModel;

    private JTextField txtMaDocGia;
    private JTextField txtTenDocGia;
    private JTextField txtGioiTinh;
    private JTextField txtNgaySinh;
    private JTextField txtSoDienThoai;
    private JTextField txtDiaChi;
    private JTextField txtEmail;
    private JTextField txtNgayDangKy;

    private RoundedTxtField txtTuKhoa;

    private RoundedButton btnThem;
    private RoundedButton btnSua;
    private RoundedButton btnXoa;
    private RoundedButton btnHuy;
    private RoundedButton btnTim;

    public ReaderPanel() {
        reader_BLL = new Reader_BLL();
        setBackground(new Color(230, 236, 243));
        setLayout(new BorderLayout(0,0));

        JPanel panelTable = new JPanel(new BorderLayout());
        panelTable.setBackground(new Color(230, 236, 243));

        String[] columnNames = {"Mã Độc Giả", "Họ Tên","Giới tính", "Ngày Sinh", "Số Điện Thoại", "Địa Chỉ","Email", "Ngày Đăng Ký"};
        tableModel = new DefaultTableModel(columnNames,0);

        tableModel.addRow(new Object[]{"DG001","Nguyễn Văn A","Nam","01/01/2001","0987654321","A","a@email.com","03/25/2025"});

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
                        txtMaDocGia.setText(tableModel.getValueAt(selectedRow, 0).toString());
                        txtTenDocGia.setText(tableModel.getValueAt(selectedRow, 1).toString());
                        txtGioiTinh.setText(tableModel.getValueAt(selectedRow, 2).toString());
                        txtNgaySinh.setText(tableModel.getValueAt(selectedRow, 3).toString());
                        txtSoDienThoai.setText(tableModel.getValueAt(selectedRow, 4).toString());
                        txtDiaChi.setText(tableModel.getValueAt(selectedRow, 5).toString());
                        txtEmail.setText(tableModel.getValueAt(selectedRow, 6).toString());
                        txtNgayDangKy.setText(tableModel.getValueAt(selectedRow, 7).toString());
                    }
                }
            }
        });

        txtMaDocGia = new JTextField();
        txtMaDocGia.setBorder(BorderFactory.createTitledBorder("Mã độc giả"));

        txtTenDocGia = new JTextField();
        txtTenDocGia.setBorder(BorderFactory.createTitledBorder("Họ và tên"));

        txtGioiTinh = new JTextField();
        txtGioiTinh.setBorder(BorderFactory.createTitledBorder("Giới tính"));

        txtNgaySinh = new JTextField();
        txtNgaySinh.setBorder(BorderFactory.createTitledBorder("Ngày sinh"));

        txtSoDienThoai = new JTextField();
        txtSoDienThoai.setBorder(BorderFactory.createTitledBorder("Số điện thoại"));

        txtDiaChi = new JTextField();
        txtDiaChi.setBorder(BorderFactory.createTitledBorder("Địa chỉ"));

        txtEmail = new JTextField();
        txtEmail.setBorder(BorderFactory.createTitledBorder("Email"));

        txtNgayDangKy = new JTextField();
        txtNgayDangKy.setBorder(BorderFactory.createTitledBorder("Ngày đăng ký"));

        JPanel panelInput = new JPanel();
        panelInput.setLayout(new BoxLayout(panelInput, BoxLayout.Y_AXIS));
        panelInput.setBackground(new Color(230, 236, 243));
        panelInput.add(txtMaDocGia);
        panelInput.add(txtTenDocGia);
        panelInput.add(txtGioiTinh);
        panelInput.add(txtNgaySinh);
        panelInput.add(txtSoDienThoai);
        panelInput.add(txtDiaChi);
        panelInput.add(txtEmail);
        panelInput.add(txtNgayDangKy);

        // Panel tìm kiếm
        JPanel panelSearch = new JPanel();
        panelSearch.setBackground(new Color(230, 236, 243));
        panelSearch.add(new JLabel("Từ khóa:"));
        txtTuKhoa = new RoundedTxtField(20, 16);
        txtTuKhoa.setBackground(Color.WHITE);
        txtTuKhoa.setPlaceholder("Nhập tên độc giả muốn tìm");
        panelSearch.add(txtTuKhoa);
        btnTim = new RoundedButton("Tìm");
        panelSearch.add(btnTim);
        
        // Panel chứa các nút
        JPanel panelButtons = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        panelButtons.setBackground(new Color(230, 236, 243));
        btnThem = new RoundedButton("Thêm");
        btnSua = new RoundedButton("Sửa");
        btnXoa = new RoundedButton("Xóa");
        btnHuy = new RoundedButton("Hủy");
        
        panelButtons.add(btnThem);
        panelButtons.add(btnSua);
        panelButtons.add(btnXoa);
        panelButtons.add(btnHuy);
        
        btnThem.addActionListener(e -> {
            try {
                String maDocGia = txtMaDocGia.getText();
                String tenDocGia = txtTenDocGia.getText();
                String gioiTinh = txtGioiTinh.getText();
                String ngaySinhStr = txtNgaySinh.getText();
                String soDienThoai = txtSoDienThoai.getText();
                String diaChi = txtDiaChi.getText();
                String email = txtEmail.getText();
                String ngayDangKyStr = txtNgayDangKy.getText();

                // Định dạng ngày (cần khớp với dữ liệu nhập vào)
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");

                // Chuyển đổi chuỗi ngày thành LocalDate
                LocalDate ngaySinh = LocalDate.parse(ngaySinhStr, formatter);
                LocalDate ngayDangKy = LocalDate.parse(ngayDangKyStr, formatter);

                // Gọi phương thức thêm độc giả
                JOptionPane.showMessageDialog(null, reader_BLL.addReader(maDocGia, tenDocGia, gioiTinh, ngaySinh, diaChi, soDienThoai, email, ngayDangKy));
                loadReaderTable();
            } catch (DateTimeParseException ex) {
                JOptionPane.showMessageDialog(null, "Lỗi định dạng ngày. Vui lòng nhập ngày theo định dạng dd/MM/yyyy!", "Lỗi", JOptionPane.ERROR_MESSAGE);
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(null, "Đã xảy ra lỗi: " + ex.getMessage(), "Lỗi", JOptionPane.ERROR_MESSAGE);
            }
        });

        btnSua.addActionListener(e -> {
            try {
                // Lấy dữ liệu từ các ô nhập
                String maDocGia = txtMaDocGia.getText();
                String tenDocGia = txtTenDocGia.getText();
                String gioiTinh = txtGioiTinh.getText();
                LocalDate ngaySinh = LocalDate.parse(txtNgaySinh.getText()); // Chuyển đổi từ String sang LocalDate
                String soDienThoai = txtSoDienThoai.getText();
                String diaChi = txtDiaChi.getText();
                String email = txtEmail.getText();
                LocalDate ngayDangKy = LocalDate.parse(txtNgayDangKy.getText()); // Chuyển đổi từ String sang LocalDate

                
        
                // Gọi phương thức updateReader
                String result = reader_BLL.updateReader(maDocGia, tenDocGia, gioiTinh, ngaySinh, diaChi, soDienThoai, email, ngayDangKy);
                
                // Hiển thị thông báo từ kết quả trả về
                JOptionPane.showMessageDialog(this, result, "Thông báo", JOptionPane.INFORMATION_MESSAGE);
                loadReaderTable();
        
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
                
                
                String result = reader_BLL.deleteReader(ID);
                JOptionPane.showMessageDialog(this, result, "Thông báo", JOptionPane.INFORMATION_MESSAGE);
                loadReaderTable();
            }catch(Exception ex){
                JOptionPane.showMessageDialog(this, "Lỗi" + ex.getMessage(), "Lỗi", JOptionPane.ERROR_MESSAGE);
            }
        });

        btnHuy.addActionListener(e -> {
            txtMaDocGia.setText("");
            txtMaDocGia.setEditable(true);
            txtTenDocGia.setText("");
            txtGioiTinh.setText("");
            txtNgaySinh.setText("");
            txtSoDienThoai.setText("");
            txtDiaChi.setText("");
            txtEmail.setText("");
            txtNgayDangKy.setText("");
            table.clearSelection();
        });

        btnTim.addActionListener(e -> {
            String keyword = txtTuKhoa.getText().trim().toLowerCase();
            if (keyword == null || keyword.trim().isEmpty()) {
                JOptionPane.showMessageDialog(this, "Cần nhập dữ liệu tìm kiếm!");
                loadReaderTable();
            }
            else{
                List<Reader> readers = reader_BLL.searchReader(keyword);
                if (readers != null) {
                    tableModel.setRowCount(0);
                    for (Reader reader : readers) {
                        tableModel.addRow(new Object[]{
                            reader.getID(),
                            reader.getName(),
                            reader.getGender(),
                            reader.getBirth(),
                            reader.getPhone(),
                            reader.getAddress(),
                            reader.getEmail(),
                            reader.getRegistrationDate()
                        });
                    }
                } else {
                    JOptionPane.showMessageDialog(this, "Không thể tải danh sách độc giả!", "Lỗi", JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        loadReaderTable();
        add(panelTable, BorderLayout.CENTER);

        JPanel panelBottom = new JPanel(new BorderLayout(0,0));
        panelBottom.add(panelSearch, BorderLayout.NORTH);
        panelBottom.add(panelInput, BorderLayout.CENTER);
        panelBottom.add(panelButtons, BorderLayout.SOUTH);

        add(panelBottom, BorderLayout.SOUTH);
    }

    private void loadReaderTable() {
        // Xóa dữ liệu cũ trên bảng
        tableModel.setRowCount(0);
    
        // Gọi BLL để lấy danh sách độc giả từ CSDL
        List<Reader> readers = reader_BLL.getReader();
        
        // Kiểm tra danh sách có dữ liệu không
        if (readers != null) {
            for (Reader reader : readers) {
                tableModel.addRow(new Object[]{
                    reader.getID(),
                    reader.getName(),
                    reader.getGender(),
                    reader.getBirth(),
                    reader.getPhone(),
                    reader.getAddress(),
                    reader.getEmail(),
                    reader.getRegistrationDate()
                });
            }
        } else {
            JOptionPane.showMessageDialog(this, "Không thể tải danh sách độc giả!", "Lỗi", JOptionPane.ERROR_MESSAGE);
        }
    }
    

}