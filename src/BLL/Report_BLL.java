package BLL;

import DAO.Report_DAO;
import java.time.LocalDate;
import java.util.List;

public class Report_BLL {
    private Report_DAO reportDAO;

    public Report_BLL() {
        reportDAO = new Report_DAO();
    }

    public List<String[]> getLoanDetails(LocalDate date) {
        return reportDAO.getLoanDetails(date);
    }

    public List<String[]> getReturnDetails(LocalDate date) {
        return reportDAO.getReturnDetails(date);
    }

    public int getTotalLoans(LocalDate date) {
        return reportDAO.getTotalLoans(date);
    }

    public int getTotalReturns(LocalDate date) {
        return reportDAO.getTotalReturns(date);
    }

    public double getTotalRevenue(LocalDate date) {
        return reportDAO.getTotalRevenue(date);
    }
}
