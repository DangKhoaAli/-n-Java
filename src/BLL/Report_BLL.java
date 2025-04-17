package BLL;

import DAO.Report_DAO;
import java.time.LocalDate;

public class Report_BLL {
    private Report_DAO reportDAO;

    public Report_BLL() {
        reportDAO = new Report_DAO();
    }

    public String generateDailyReport(LocalDate date) {
        int totalLoans = reportDAO.getTotalLoansByDay(date);
        int totalReturns = reportDAO.getTotalReturnsByDay(date);
        double totalRevenue = reportDAO.getTotalRevenueByDay(date);

        return formatReport("ngày", date.toString(), totalLoans, totalReturns, totalRevenue);
    }

    public String generateMonthlyReport(LocalDate date) {
        int totalLoans = reportDAO.getTotalLoansByMonth(date);
        int totalReturns = reportDAO.getTotalReturnsByMonth(date);
        double totalRevenue = reportDAO.getTotalRevenueByMonth(date);

        return formatReport("tháng", date.getMonthValue() + "/" + date.getYear(), totalLoans, totalReturns, totalRevenue);
    }

    public String generateYearlyReport(LocalDate date) {
        int totalLoans = reportDAO.getTotalLoansByYear(date);
        int totalReturns = reportDAO.getTotalReturnsByYear(date);
        double totalRevenue = reportDAO.getTotalRevenueByYear(date);

        return formatReport("năm", String.valueOf(date.getYear()), totalLoans, totalReturns, totalRevenue);
    }

    private String formatReport(String timeFrame, String timeValue, int totalLoans, int totalReturns, double totalRevenue) {
        return String.format("Báo cáo %s %s:\n- Tổng phiếu mượn: %d\n- Tổng phiếu trả: %d\n- Tổng tiền thu: %.2f VND",
                timeFrame, timeValue, totalLoans, totalReturns, totalRevenue);
    }
}
