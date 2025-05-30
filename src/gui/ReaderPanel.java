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
    private RoundedButton btnDangXuat;

    public ReaderPanel() {
        reader_BLL = new Reader_BLL();
        setBackground(new Color(230, 236, 243));
        setLayout(new BorderLayout(0,0));

        JPanel panelTable = new JPanel(new BorderLayout());
        panelTable.setBackground(new Color(230, 236, 243));

        String[] columnNames = {"Mã Độc Giả", "Họ Tên","Giới tính", "Ngày Sinh", "Số Điện Thoại", "Địa Chỉ","Email", "Ngày Đăng Ký", "Tồn tại"};
        tableModel = new DefaultTableModel(columnNames,0);

        table = new JTable(tableModel);
        table.setBackground(Color.WHITE);
        table.setSelectionBackground(Color.YELLOW);
        JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.getViewport().setBackground(Color.WHITE);
        panelTable.add(scrollPane, BorderLayout.CENTER);

        table.getSelectionModel().addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent e) {
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
        btnDangXuat = new RoundedButton("Đăng xuất");
        
        panelButtons.add(btnThem);
        panelButtons.add(btnSua);
        panelButtons.add(btnXoa);
        panelButtons.add(btnHuy);
        panelButtons.add(btnDangXuat);
        
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

                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");

                LocalDate ngaySinh = LocalDate.parse(ngaySinhStr, formatter);
                LocalDate ngayDangKy = LocalDate.parse(ngayDangKyStr, formatter);

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
                String maDocGia = txtMaDocGia.getText();
                String tenDocGia = txtTenDocGia.getText();
                String gioiTinh = txtGioiTinh.getText();
                String ngaySinh = txtNgaySinh.getText(); 
                String soDienThoai = txtSoDienThoai.getText();
                String diaChi = txtDiaChi.getText();
                String email = txtEmail.getText();
                String ngayDangKy = txtNgayDangKy.getText();
                
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");

                LocalDate ns = LocalDate.parse(ngaySinh, formatter);
                LocalDate ndk = LocalDate.parse(ngayDangKy, formatter);
                
    
                String result = reader_BLL.updateReader(maDocGia, tenDocGia, gioiTinh, ns, diaChi, soDienThoai, email, ndk);
                
                
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

            int confirm = JOptionPane.showConfirmDialog(
                this,
                "Bạn có chắc chắn muốn xóa dòng này không?",
                "Xác nhận xóa",
                JOptionPane.YES_NO_OPTION,
                JOptionPane.WARNING_MESSAGE
            );
        
            if (confirm != JOptionPane.YES_OPTION) {
                return; // Người dùng chọn "Không"
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

        btnDangXuat.addActionListener(e -> {
            JFrame mainFrame = (JFrame) SwingUtilities.getWindowAncestor(this);
            mainFrame.dispose();
        
            LoginPanel login = new LoginPanel();
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

        loadReaderTable();
        add(panelTable, BorderLayout.CENTER);

        JPanel panelBottom = new JPanel(new BorderLayout(0,0));
        panelBottom.add(panelSearch, BorderLayout.NORTH);
        panelBottom.add(panelInput, BorderLayout.CENTER);
        panelBottom.add(panelButtons, BorderLayout.SOUTH);

        add(panelBottom, BorderLayout.SOUTH);
    }

    private void loadReaderTable() {
        
        tableModel.setRowCount(0);
    
        
        List<Reader> readers = reader_BLL.getReader();
        

        if (readers != null) {
            for (Reader reader : readers) {
                tableModel.addRow(new Object[]{
                    reader.getID(),
                    reader.getName(),
                    reader.getGender(),
                    reader.getBirth().format(DateTimeFormatter.ofPattern("dd/MM/yyyy")),
                    reader.getPhone(),
                    reader.getAddress(),
                    reader.getEmail(),
                    reader.getRegistrationDate().format(DateTimeFormatter.ofPattern("dd/MM/yyyy")),
                    (Integer.parseInt(reader.getExist()) == 1) ? "Có" : "Không"
                });
            }
        } else {
            JOptionPane.showMessageDialog(this, "Không thể tải danh sách độc giả!", "Lỗi", JOptionPane.ERROR_MESSAGE);
        }
    }
    

}