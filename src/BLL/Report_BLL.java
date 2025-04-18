package BLL;

import DAO.Report_DAO;
import java.time.LocalDate;
import java.util.List;

public class Report_BLL {
    private Report_DAO reportDAO;

    public Report_BLL() {
        reportDAO = new Report_DAO();
    }

    public List<String[]> getLoanDetailsByMonth(LocalDate date) {
        return reportDAO.getLoanDetailsByMonthWithFees(date);
    }

    public List<String[]> getReturnDetailsByMonth(LocalDate date) {
        return reportDAO.getReturnDetailsByMonthWithFees(date);
    }

    public int getTotalLoansByMonth(LocalDate date) {
        return reportDAO.getTotalLoansByMonth(date);
    }

    public int getTotalReturnsByMonth(LocalDate date) {
        return reportDAO.getTotalReturnsByMonth(date);
    }

    public double getTotalRevenueByMonth(LocalDate date) {
        return reportDAO.getTotalRevenueByMonth(date);
    }
}
