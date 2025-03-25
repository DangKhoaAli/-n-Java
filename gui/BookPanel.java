import java.awt.*;
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
    private JTextField txtSoLuong;
    private JTextField txtSoTrang;
    private JTextField txtGia;
    private JTextField txtPhiMuon;

    private JTextField txtTuKhoa;

    private JButton btnThem;
    private JButton btnSua;
    private JButton btnLuu;
    private JButton btnXoa;
    private JButton btnHuy;
    private JButton btnTim;

    public BookPanel() {
        setBackground(Color.GRAY);
        setLayout(new BorderLayout(10,10));
        JPanel panelTable = new JPanel(new BorderLayout());
        panelTable.setBackground(Color.GRAY);
        String[] columnNames = {"Mã sách", "Tên sách", "Thể loại", "Tác giá", "Nhà cung cấp", "Số lượng", "Số trang", "Giá", "Phí mượn"};
        tableModel = new DefaultTableModel(columnNames,0);
        tableModel.addRow(new Object[]{"1","Pháp luật","Giáo dục","Nhiều tác giả","BGD","100","500","20","5"});
        table = new JTable(tableModel);
        table.setBackground(Color.WHITE);
        table.setSelectionBackground(Color.YELLOW);
        JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.getViewport().setBackground(Color.WHITE);
        panelTable.add(scrollPane, BorderLayout.CENTER);
        JPanel panelInput = new JPanel();
        panelInput.setLayout(new GridLayout(9,2,5,5));
        panelInput.setBackground(Color.GRAY);

        panelInput.add(new JLabel("Mã sách"));
        txtMaSach = new JTextField();
        panelInput.add(txtMaSach);

        panelInput.add(new JLabel("Tên sách:"));
        txtTenSach = new JTextField();
        panelInput.add(txtTenSach);
        
        panelInput.add(new JLabel("Thể loại:"));
        txtTheLoai = new JTextField();
        panelInput.add(txtTheLoai);
        
        panelInput.add(new JLabel("Tác giả:"));
        txtTacGia = new JTextField();
        panelInput.add(txtTacGia);

        panelInput.add(new JLabel("Nhà cung cấp:"));
        txtNCC = new JTextField();
        panelInput.add(txtNCC);
        
        panelInput.add(new JLabel("Số lượng:"));
        txtSoLuong = new JTextField();
        panelInput.add(txtSoLuong);

        panelInput.add(new JLabel("Số trang:"));
        txtSoTrang = new JTextField();
        panelInput.add(txtSoTrang);

        panelInput.add(new JLabel("Giá:"));
        txtGia = new JTextField();
        panelInput.add(txtGia);

        panelInput.add(new JLabel("Phí mượn:"));
        txtPhiMuon = new JTextField();
        panelInput.add(txtPhiMuon);

        // Panel tìm kiếm
        JPanel panelSearch = new JPanel();
        panelSearch.setBackground(Color.GRAY);
        panelSearch.add(new JLabel("Từ khóa:"));
        txtTuKhoa = new JTextField(20);
        panelSearch.add(txtTuKhoa);
        btnTim = new JButton("Tìm");
        panelSearch.add(btnTim);
        
        // Panel chứa các nút
        JPanel panelButtons = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        panelButtons.setBackground(Color.GRAY);
        btnThem = new JButton("Thêm");
        btnSua = new JButton("Sửa");
        btnLuu = new JButton("Lưu");
        btnXoa = new JButton("Xóa");
        btnHuy = new JButton("Hủy");
        
        panelButtons.add(btnThem);
        panelButtons.add(btnSua);
        panelButtons.add(btnLuu);
        panelButtons.add(btnXoa);
        panelButtons.add(btnHuy);
        
        btnThem.addActionListener(e -> {
            String maSach = txtMaSach.getText();
            String tenSach = txtTenSach.getText();
            String theLoai = txtTheLoai.getText();
            String tacGia = txtTacGia.getText();
            String nhaCungCap = txtNCC.getText();
            String soLuong = txtSoLuong.getText();
            String soTrang = txtSoTrang.getText();
            String gia = txtGia.getText();
            String phiMuon = txtPhiMuon.getText();

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
                tableModel.addRow((new Object[]{maSach,tenSach,theLoai,tacGia,nhaCungCap,soLuong,soTrang,gia,phiMuon}));
                JOptionPane.showMessageDialog(this, "Thêm sách mới thành công!");
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
            txtSoLuong.setText(tableModel.getValueAt(selectedRow, 5).toString());
            txtSoTrang.setText(tableModel.getValueAt(selectedRow, 6).toString());
            txtGia.setText(tableModel.getValueAt(selectedRow, 7).toString());
            txtPhiMuon.setText(tableModel.getValueAt(selectedRow, 8).toString());
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
            tableModel.setValueAt(txtSoLuong.getText(),selectedRow, 5);
            tableModel.setValueAt(txtSoTrang.getText(),selectedRow, 6);
            tableModel.setValueAt(txtGia.getText(),selectedRow, 7);
            tableModel.setValueAt(txtPhiMuon.getText(),selectedRow, 8);
            JOptionPane.showMessageDialog(this, "Cập nhật sách thành công!");
        });

        btnXoa.addActionListener(e -> {
            int selectedRow = table.getSelectedRow();
            if(selectedRow == -1) {
                JOptionPane.showMessageDialog(this, "Vui lòng chọn dòng để xóa");
                return;
            }
            tableModel.removeRow(selectedRow);
            JOptionPane.showMessageDialog(this, "Xóa sách thành công!");
        });

        btnHuy.addActionListener(e -> {
            txtMaSach.setText("");
            txtTenSach.setText("");
            txtTheLoai.setText("");
            txtTacGia.setText("");
            txtNCC.setText("");
            txtSoLuong.setText("");
            txtSoTrang.setText("");
            txtGia.setText("");
            txtPhiMuon.setText("");
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

        add(panelTable, BorderLayout.CENTER);

        JPanel panelBottom = new JPanel(new BorderLayout(10,10));
        panelBottom.add(panelSearch, BorderLayout.NORTH);
        panelBottom.add(panelInput, BorderLayout.CENTER);
        panelBottom.add(panelButtons, BorderLayout.SOUTH);

        add(panelBottom, BorderLayout.SOUTH);
    }


}