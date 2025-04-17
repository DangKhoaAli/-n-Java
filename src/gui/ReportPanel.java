package gui;

import BLL.Report_BLL;
import java.awt.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;

public class ReportPanel extends JPanel {
    private Report_BLL reportBLL;
    private JTable table;
    private DefaultTableModel tableModel;

    private JComboBox<String> cbTimeFrame;
    private JTextField txtDateInput;

    private JButton btnGenerate;
    private JButton btnClear;

    public ReportPanel() {
        reportBLL = new Report_BLL();
        setLayout(new BorderLayout(0, 0));
        setBackground(new Color(230, 236, 243));

        // --- Bảng hiển thị báo cáo ---
        String[] columnNames = {"Tiêu đề", "Nội dung"};
        tableModel = new DefaultTableModel(columnNames, 0);
        table = new JTable(tableModel);
        table.setBackground(Color.WHITE);
        table.setSelectionBackground(Color.YELLOW);
        JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.getViewport().setBackground(Color.WHITE);
        add(scrollPane, BorderLayout.CENTER);

        // --- Panel nhập ---
        JPanel panelInput = new JPanel();
        panelInput.setLayout(new BoxLayout(panelInput, BoxLayout.Y_AXIS));
        panelInput.setBackground(new Color(230, 236, 243));

        cbTimeFrame = new JComboBox<>(new String[]{"Ngày", "Tháng", "Năm"});
        cbTimeFrame.setBorder(BorderFactory.createTitledBorder("Chọn thời gian"));
        txtDateInput = new JTextField();
        txtDateInput.setBorder(BorderFactory.createTitledBorder("Nhập thời gian (dd/MM/yyyy, MM/yyyy, yyyy)"));

        panelInput.add(cbTimeFrame);
        panelInput.add(txtDateInput);

        // --- Panel nút ---
        JPanel panelButtons = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        panelButtons.setBackground(new Color(230, 236, 243));

        btnGenerate = new RoundedButton("Tạo báo cáo");
        btnClear = new RoundedButton("Xóa");

        panelButtons.add(btnGenerate);
        panelButtons.add(btnClear);

        // --- Gộp panel nhập và panel nút ---
        JPanel panelBottom = new JPanel(new BorderLayout(0, 0));
        panelBottom.setBackground(new Color(230, 236, 243));
        panelBottom.add(panelInput, BorderLayout.CENTER);
        panelBottom.add(panelButtons, BorderLayout.SOUTH);
        add(panelBottom, BorderLayout.SOUTH);

        // --- Xử lý sự kiện ---
        btnGenerate.addActionListener(e -> generateReport());
        btnClear.addActionListener(e -> clearInput());
    }

    private void generateReport() {
        String timeFrame = cbTimeFrame.getSelectedItem().toString();
        String dateInput = txtDateInput.getText().trim();
        DateTimeFormatter formatter;
        LocalDate date;

        try {
            switch (timeFrame) {
                case "Ngày":
                    formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
                    date = LocalDate.parse(dateInput, formatter);
                    loadReportData(reportBLL.generateDailyReport(date));
                    break;
                case "Tháng":
                    formatter = DateTimeFormatter.ofPattern("MM/yyyy");
                    date = LocalDate.parse("01/" + dateInput, DateTimeFormatter.ofPattern("dd/MM/yyyy"));
                    loadReportData(reportBLL.generateMonthlyReport(date));
                    break;
                case "Năm":
                    formatter = DateTimeFormatter.ofPattern("yyyy");
                    date = LocalDate.parse("01/01/" + dateInput, DateTimeFormatter.ofPattern("dd/MM/yyyy"));
                    loadReportData(reportBLL.generateYearlyReport(date));
                    break;
                default:
                    throw new IllegalArgumentException("Thời gian không hợp lệ!");
            }
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Lỗi: " + ex.getMessage(), "Lỗi nhập liệu", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void loadReportData(String reportContent) {
        tableModel.setRowCount(0);
        String[] lines = reportContent.split("\n");
        for (String line : lines) {
            String[] parts = line.split(":", 2);
            if (parts.length == 2) {
                tableModel.addRow(new Object[]{parts[0].trim(), parts[1].trim()});
            }
        }
    }

    private void clearInput() {
        cbTimeFrame.setSelectedIndex(0);
        txtDateInput.setText("");
        tableModel.setRowCount(0);
    }
}
