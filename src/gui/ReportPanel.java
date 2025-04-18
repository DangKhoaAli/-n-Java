package gui;

import BLL.Report_BLL;
import java.awt.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;

public class ReportPanel extends JPanel {
    private Report_BLL reportBLL;

    private JTable loanTable;
    private JTable returnTable;
    private DefaultTableModel loanTableModel;
    private DefaultTableModel returnTableModel;

    private JLabel lblTotalLoans;
    private JLabel lblTotalReturns;
    private JLabel lblTotalRevenue;

    private JComboBox<String> cbTimeFrame;
    private JTextField txtDateInput;
    private JButton btnGenerate;

    public ReportPanel() {
        reportBLL = new Report_BLL();
        setLayout(new BorderLayout(0, 0));
        setBackground(new Color(230, 236, 243));

        // --- Bảng hiển thị sách mượn và trả ---
        String[] loanColumnNames = {"ID Sách", "Tên Sách"};
        loanTableModel = new DefaultTableModel(loanColumnNames, 0);
        loanTable = new JTable(loanTableModel);

        String[] returnColumnNames = {"ID Sách", "Tên Sách"};
        returnTableModel = new DefaultTableModel(returnColumnNames, 0);
        returnTable = new JTable(returnTableModel);

        JPanel panelTables = new JPanel(new GridLayout(1, 2));
        panelTables.add(new JScrollPane(loanTable));
        panelTables.add(new JScrollPane(returnTable));
        add(panelTables, BorderLayout.CENTER);

        // --- Chi tiết thống kê ---
        JPanel panelSummary = new JPanel(new GridLayout(3, 1));
        lblTotalLoans = new JLabel("Số sách mượn: 0");
        lblTotalReturns = new JLabel("Số sách trả: 0");
        lblTotalRevenue = new JLabel("Doanh thu: 0.00 VND");

        panelSummary.add(lblTotalLoans);
        panelSummary.add(lblTotalReturns);
        panelSummary.add(lblTotalRevenue);
        add(panelSummary, BorderLayout.SOUTH);

        // --- Panel nhập ---
        JPanel panelInput = new JPanel();
        panelInput.setLayout(new BoxLayout(panelInput, BoxLayout.Y_AXIS));
        panelInput.setBackground(new Color(230, 236, 243));

        cbTimeFrame = new JComboBox<>(new String[]{"Tháng", "Năm"});
        cbTimeFrame.setBorder(BorderFactory.createTitledBorder("Chọn thời gian"));
        txtDateInput = new JTextField();
        txtDateInput.setBorder(BorderFactory.createTitledBorder("Nhập thời gian (MM/yyyy, yyyy)"));

        panelInput.add(cbTimeFrame);
        panelInput.add(txtDateInput);

        btnGenerate = new JButton("Tạo báo cáo");
        panelInput.add(btnGenerate);

        add(panelInput, BorderLayout.NORTH);

        // --- Xử lý sự kiện ---
        btnGenerate.addActionListener(e -> generateReport());
    }

    private void generateReport() {
        String timeFrame = cbTimeFrame.getSelectedItem().toString();
        String dateInput = txtDateInput.getText().trim();
        LocalDate date;

        try {
            if (timeFrame.equals("Tháng")) {
                date = LocalDate.parse("01/" + dateInput, DateTimeFormatter.ofPattern("dd/MM/yyyy"));
            } else {
                date = LocalDate.parse("01/01/" + dateInput, DateTimeFormatter.ofPattern("dd/MM/yyyy"));
            }

            // Load loan and return details
            List<String[]> loanDetails = reportBLL.getLoanDetailsByMonth(date);
            List<String[]> returnDetails = reportBLL.getReturnDetailsByMonth(date);

            loanTableModel.setRowCount(0);
            for (String[] loan : loanDetails) {
                loanTableModel.addRow(loan);
            }

            returnTableModel.setRowCount(0);
            for (String[] ret : returnDetails) {
                returnTableModel.addRow(ret);
            }

            // Load summary statistics
            int totalLoans = reportBLL.getTotalLoansByMonth(date);
            int totalReturns = reportBLL.getTotalReturnsByMonth(date);
            double totalRevenue = reportBLL.getTotalRevenueByMonth(date);

            lblTotalLoans.setText("Số sách mượn: " + totalLoans);
            lblTotalReturns.setText("Số sách trả: " + totalReturns);
            lblTotalRevenue.setText(String.format("Doanh thu: %.2f VND", totalRevenue));
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Lỗi: " + ex.getMessage(), "Lỗi nhập liệu", JOptionPane.ERROR_MESSAGE);
        }
    }
}
