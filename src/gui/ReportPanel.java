package gui;

import BLL.Report_BLL;
import java.awt.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
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

    public ReportPanel() {
        reportBLL = new Report_BLL();
        setLayout(new BorderLayout(0, 0));
        setBackground(new Color(230, 236, 243));

        // --- Bảng hiển thị sách mượn và trả ---
        JPanel panelTables = new JPanel(new GridLayout(1, 2));

        // Loan table with header
        JPanel loanPanel = new JPanel(new BorderLayout());
        JLabel loanHeader = new JLabel("Danh sách sách mượn", JLabel.CENTER);
        loanHeader.setFont(new Font("Arial", Font.BOLD, 14));
        loanPanel.add(loanHeader, BorderLayout.NORTH);

        String[] loanColumnNames = {"ID phiếu mượn", "ID Sách", "Tên Sách"};
        loanTableModel = new DefaultTableModel(loanColumnNames, 0);
        loanTable = new JTable(loanTableModel);
        loanPanel.add(new JScrollPane(loanTable), BorderLayout.CENTER);

        // Return table with header
        JPanel returnPanel = new JPanel(new BorderLayout());
        JLabel returnHeader = new JLabel("Danh sách sách trả", JLabel.CENTER);
        returnHeader.setFont(new Font("Arial", Font.BOLD, 14));
        returnPanel.add(returnHeader, BorderLayout.NORTH);

        String[] returnColumnNames = {"ID phiếu trả","ID Sách", "Tên Sách"};
        returnTableModel = new DefaultTableModel(returnColumnNames, 0);
        returnTable = new JTable(returnTableModel);
        returnPanel.add(new JScrollPane(returnTable), BorderLayout.CENTER);

        panelTables.add(loanPanel);
        panelTables.add(returnPanel);
        add(panelTables, BorderLayout.CENTER);

        // --- Chi tiết thống kê ---
        JPanel panelSummary = new JPanel(new GridLayout(3, 1));
        panelSummary.setBorder(BorderFactory.createEmptyBorder(15, 20, 15, 20)); // Thêm khoảng cách padding
        lblTotalLoans = new JLabel("Số sách mượn: 0");
        lblTotalLoans.setFont(new Font("Arial", Font.BOLD, 16)); // Tăng kích thước chữ
        lblTotalReturns = new JLabel("Số sách trả: 0");
        lblTotalReturns.setFont(new Font("Arial", Font.BOLD, 16));
        lblTotalRevenue = new JLabel("Doanh thu: 0.00 VND");
        lblTotalRevenue.setFont(new Font("Arial", Font.BOLD, 16));

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
        txtDateInput.setBorder(BorderFactory.createTitledBorder("Nhập thời gian (chọn tháng thì nhập MM/yyyy, chọn năm thì nhập yyyy)"));

        panelInput.add(cbTimeFrame);
        panelInput.add(txtDateInput);

        add(panelInput, BorderLayout.NORTH);

        // --- Xử lý sự kiện ---
        cbTimeFrame.addActionListener(e -> generateReport());
        txtDateInput.getDocument().addDocumentListener(new DocumentListener() {
            @Override
            public void insertUpdate(DocumentEvent e) {
                generateReport();
            }

            @Override
            public void removeUpdate(DocumentEvent e) {
                generateReport();
            }

            @Override
            public void changedUpdate(DocumentEvent e) {
                generateReport();
            }
        });
    }

    private void generateReport() {
        String timeFrame = cbTimeFrame.getSelectedItem().toString();
        String dateInput = txtDateInput.getText().trim();
        LocalDate date;

        try {
            // Correct date parsing logic
            if (timeFrame.equals("Tháng")) {
                date = LocalDate.parse("01/" + dateInput, DateTimeFormatter.ofPattern("dd/MM/yyyy"));
            } else if (timeFrame.equals("Năm")) {
                date = LocalDate.parse("01/01/" + dateInput, DateTimeFormatter.ofPattern("dd/MM/yyyy"));
            } else {
                throw new IllegalArgumentException("Invalid time frame selected.");
            }

            // Fetch loan and return details
            List<String[]> loanDetails = reportBLL.getLoanDetailsByMonth(date);
            List<String[]> returnDetails = reportBLL.getReturnDetailsByMonth(date);

            // Populate loan table
            loanTableModel.setRowCount(0); // Clear existing rows
            for (String[] loan : loanDetails) {
                loanTableModel.addRow(loan);
            }

            // Populate return table
            returnTableModel.setRowCount(0); // Clear existing rows
            for (String[] ret : returnDetails) {
                returnTableModel.addRow(ret);
            }

            // Fetch and display summary statistics
            int totalLoans = reportBLL.getTotalLoansByMonth(date);
            int totalReturns = reportBLL.getTotalReturnsByMonth(date);
            double totalRevenue = reportBLL.getTotalRevenueByMonth(date);

            lblTotalLoans.setText("Số sách mượn: " + totalLoans);
            lblTotalReturns.setText("Số sách trả: " + totalReturns);
            lblTotalRevenue.setText(String.format("Doanh thu: %.2f VND", totalRevenue));
        } catch (Exception ex) {
            // Handle invalid input or errors gracefully
            loanTableModel.setRowCount(0);
            returnTableModel.setRowCount(0);
            lblTotalLoans.setText("Số sách mượn: 0");
            lblTotalReturns.setText("Số sách trả: 0");
            lblTotalRevenue.setText("Doanh thu: 0.00 VND");

            // Optionally log or display the error for debugging
            ex.printStackTrace();
        }
    }
}
