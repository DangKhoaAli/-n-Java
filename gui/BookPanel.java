import java.awt.*;
import java.util.*;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;

public class BookPanel extends JPanel {
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
    private JButton btnLuu;
    private JButton btnXoa;
    private JButton btnHuy;
    private JButton btnTim;
    private JButton btnXemChiTiet;

    private Map<String, java.util.List<Object[]>> bookDetailsMap = new HashMap<>();

    public BookPanel() {
        setBackground(Color.BLUE);
        setLayout(new BorderLayout(10,10));
        JPanel panelTable = new JPanel(new BorderLayout());
        panelTable.setBackground(Color.BLUE);
        String[] columnNames = {"Mã sách", "Tên sách", "Thể loại", "Tác giá", "Nhà cung cấp","Năm xuất bản", "Số trang", "Giá", "Phí mượn","Số lượng"};
        tableModel = new DefaultTableModel(columnNames,0);
        tableModel.addRow(new Object[]{"1","Pháp luật","Giáo dục","Nhiều tác giả","BGD","2005","500","15000","2000","5"});
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
        btnLuu = new JButton("Lưu");
        btnXoa = new JButton("Xóa");
        btnHuy = new JButton("Hủy");
        btnXemChiTiet = new JButton("Xem chi tiết");
        
        panelButtons.add(btnThem);
        panelButtons.add(btnSua);
        panelButtons.add(btnLuu);
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

            if(maSach.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Vui lòng nhập mã sách!");
                return;
            }

            if(tenSach.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Vui lòng nhập tên sách!");
                return;
            }
            
            if(theLoai.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Vui lòng nhập thể loại sách!");
                return;
            }

            if(tacGia.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Vui lòng nhập tác giả!");
                return;
            }

            if(nhaCungCap.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Vui lòng nhập nhà cung cấp!");
                return;
            }

            if(namXuatBan.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Vui lòng nhập năm xuất bản của sách!");
                return;
            }

            if(soTrang.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Vui lòng nhập số trang của sách!");
                return;
            }

            if(gia.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Vui lòng nhập giá của sách!");
                return;
            }

            if(phiMuon.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Vui lòng nhập phí mượn của sách!");
                return;
            }

            if(soLuong.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Vui lòng nhập số lượng sách!");
                return;
            }


            boolean isDuplicate = false;
            for(int i = 0; i < tableModel.getRowCount(); i++){
                String existingID = tableModel.getValueAt(i, 0).toString();
                if( existingID.equalsIgnoreCase(maSach)) {
                    isDuplicate = true;
                    break;
                }
            }

            if(isDuplicate) {
                JOptionPane.showMessageDialog(this,
                "Mã sách đã tồn tại, vui lòng nhập mã sách khác!",
                "Lỗi trùng mã",
                JOptionPane.ERROR_MESSAGE);
            } else {
                tableModel.addRow((new Object[]{maSach,tenSach,theLoai,tacGia,nhaCungCap,namXuatBan,soLuong,soTrang,gia,phiMuon}));
                JOptionPane.showMessageDialog(this, "Thêm sách mới thành công!");

                int n = 0;
                try {
                    n = Integer.parseInt(soLuong);
                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(this, "Số lượng sách không hợp lệ!");
                    n = 0;
                }
                java.util.List<Object[]> detailsList = new ArrayList<>();
                for(int i = 1; i <= n; i++) {
                    String maChiTiet = maSach + "-" + i;
                    String trangThai = JOptionPane.showInputDialog(this,"Nhập trại thái cho cuốn sách" +i+"(Mặc định là có thể mượn): ", "Có thể mượn");
                    if(trangThai == null || trangThai.trim().isEmpty()) {
                        trangThai = "Được mượn";
                    }
                    String soTrangHongStr = JOptionPane.showInputDialog(this,"Nhập số trang hỏng của cuốn sách" + i + "(Mặc định là 0): ", 0);
                    int soTrangHong = 0;
                    try {
                        soTrangHong = Integer.parseInt(soTrangHongStr.trim());
                    } catch (NumberFormatException ex) {
                        soTrangHong = 0;
                    }
                    detailsList.add(new Object[]{maChiTiet,trangThai,soTrangHong});
                }
                bookDetailsMap.put(maSach,detailsList);
            } 
        });

        btnSua.addActionListener(e -> {
            int selectedRow = table.getSelectedRow();
            if(selectedRow == -1) {
                JOptionPane.showMessageDialog(this, "Vui lòng chọn dòng để sửa");
                return;
            }
            txtMaSach.setText(tableModel.getValueAt(selectedRow, 0).toString());
            txtTenSach.setText(tableModel.getValueAt(selectedRow,1).toString());
            txtTheLoai.setText(tableModel.getValueAt(selectedRow, 2).toString());
            txtTacGia.setText(tableModel.getValueAt(selectedRow, 3).toString());
            txtNCC.setText(tableModel.getValueAt(selectedRow, 4).toString());
            txtNXB.setText(tableModel.getValueAt(selectedRow, 5).toString());
            txtSoTrang.setText(tableModel.getValueAt(selectedRow, 6).toString());
            txtGia.setText(tableModel.getValueAt(selectedRow, 7).toString());
            txtPhiMuon.setText(tableModel.getValueAt(selectedRow, 8).toString());
            txtSoLuong.setText(tableModel.getValueAt(selectedRow, 9).toString());
        });

        btnLuu.addActionListener(e -> {
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
        });

        btnXoa.addActionListener(e -> {
            int selectedRow = table.getSelectedRow();
            if(selectedRow == -1) {
                JOptionPane.showMessageDialog(this, "Vui lòng chọn dòng để xóa");
                return;
            }
            String maSach = tableModel.getValueAt(selectedRow, 0).toString();
            tableModel.removeRow(selectedRow);
            bookDetailsMap.remove(maSach);
            JOptionPane.showMessageDialog(this, "Xóa sách thành công!");
        });

        btnHuy.addActionListener(e -> {
            txtMaSach.setText("");
            txtTenSach.setText("");
            txtTheLoai.setText("");
            txtTacGia.setText("");
            txtNCC.setText("");
            txtNXB.setText("");
            txtSoTrang.setText("");
            txtGia.setText("");
            txtPhiMuon.setText("");
            txtSoLuong.setText("");
        });

        btnTim.addActionListener(e -> {
            String keyword = txtTuKhoa.getText().trim().toLowerCase();
            if(!keyword .isEmpty()) {
                for(int i = 0; i < tableModel.getRowCount(); i++) {
                    String tenSach = tableModel.getValueAt(i, 1).toString();
                    if(tenSach.contains(keyword)) {
                        table.setRowSelectionInterval(i, i);
                        break;
                    }
                }
            }
        });

        btnXemChiTiet.addActionListener(e -> {
            int selectedRow = table.getSelectedRow();
            if(selectedRow == -1) {
                JOptionPane.showMessageDialog(this, "Vui lòng chọn dòng để xem chi tiết!");
                return;
            }
            Object[] bookData = new Object[tableModel.getColumnCount()];
            for(int i = 0; i < tableModel.getColumnCount(); i++){
                bookData[i] = tableModel.getValueAt(selectedRow, i);
            } 
            String maSach = bookData[0].toString();
            java.util.List<Object[]> detailsList = bookDetailsMap.get(maSach);
            new BookDetails(bookData,detailsList);
        });

        add(panelTable, BorderLayout.CENTER);

        JPanel panelBottom = new JPanel(new BorderLayout(10,10));
        panelBottom.add(panelSearch, BorderLayout.NORTH);
        panelBottom.add(panelInput, BorderLayout.CENTER);
        panelBottom.add(panelButtons, BorderLayout.SOUTH);

        add(panelBottom, BorderLayout.SOUTH);
    }


}