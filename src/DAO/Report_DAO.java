package DAO;

import config.DatabaseConnection;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class Report_DAO {
    private Connection conn;

    public Report_DAO() {
        conn = DatabaseConnection.getConnection();
    }

    public int getTotalLoansByMonth(LocalDate date) {
        return executeCountQuery("SELECT COUNT(*) FROM Loan_slip WHERE MONTH(Borrow_Date) = ? AND YEAR(Borrow_Date) = ?", date.getMonthValue(), date.getYear());
    }

    public int getTotalReturnsByMonth(LocalDate date) {
        return executeCountQuery("SELECT COUNT(*) FROM Payment_slip WHERE MONTH(payment_Date) = ? AND YEAR(payment_Date) = ?", date.getMonthValue(), date.getYear());
    }

    public double getTotalRevenueByMonth(LocalDate date) {
        return executeSumQuery("SELECT SUM(late_fee + damage_fee) FROM Payment_slip WHERE MONTH(payment_Date) = ? AND YEAR(payment_Date) = ?", date.getMonthValue(), date.getYear());
    }

    public List<String[]> getLoanDetailsByMonth(LocalDate date) {
        String sql = "SELECT Book_ID, Book_Name FROM Loan_slip WHERE MONTH(Borrow_Date) = ? AND YEAR(Borrow_Date) = ?";
        return executeDetailQuery(sql, date.getMonthValue(), date.getYear());
    }

    public List<String[]> getReturnDetailsByMonth(LocalDate date) {
        String sql = "SELECT Book_ID, Book_Name FROM Payment_slip WHERE MONTH(payment_Date) = ? AND YEAR(payment_Date) = ?";
        return executeDetailQuery(sql, date.getMonthValue(), date.getYear());
    }

    public List<String[]> getLoanDetailsByMonthWithFees(LocalDate date) {
        String sql = "SELECT Book_ID, Book_Name, loan_fee FROM Loan_slip WHERE MONTH(Borrow_Date) = ? AND YEAR(Borrow_Date) = ?";
        return executeDetailQueryWithFees(sql, date.getMonthValue(), date.getYear());
    }

    public List<String[]> getReturnDetailsByMonthWithFees(LocalDate date) {
        String sql = "SELECT Book_ID, Book_Name, late_fee, damage_fee FROM Payment_slip WHERE MONTH(payment_Date) = ? AND YEAR(payment_Date) = ?";
        return executeDetailQueryWithFees(sql, date.getMonthValue(), date.getYear());
    }

    public int getTotalBooksLoanedByMonth(LocalDate date) {
        String sql = """
                     SELECT SUM(quantity) 
                     FROM Loan_slip 
                     WHERE MONTH(Borrow_Date) = ? AND YEAR(Borrow_Date) = ?
                     """;
        return executeCountQuery(sql, date.getMonthValue(), date.getYear());
    }

    public int getTotalBooksReturnedByMonth(LocalDate date) {
        String sql = """
                     SELECT SUM(quantity) 
                     FROM Payment_slip 
                     WHERE MONTH(payment_Date) = ? AND YEAR(payment_Date) = ?
                     """;
        return executeCountQuery(sql, date.getMonthValue(), date.getYear());
    }

    public double getTotalRevenueIncludingLoanAndReturnFees(LocalDate date) {
        String sql = """
                     SELECT SUM(loan_fee + late_fee + damage_fee) 
                     FROM Payment_slip 
                     WHERE MONTH(payment_Date) = ? AND YEAR(payment_Date) = ?
                     """;
        return executeSumQuery(sql, date.getMonthValue(), date.getYear());
    }

    private int executeCountQuery(String sql, Object... params) {
        try (PreparedStatement ps = prepareStatement(sql, params);
             ResultSet rs = ps.executeQuery()) {
            if (rs.next()) {
                return rs.getInt(1);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }

    private double executeSumQuery(String sql, Object... params) {
        try (PreparedStatement ps = prepareStatement(sql, params);
             ResultSet rs = ps.executeQuery()) {
            if (rs.next()) {
                return rs.getDouble(1);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0.0;
    }

    private List<String[]> executeDetailQuery(String sql, Object... params) {
        List<String[]> details = new ArrayList<>();
        try (PreparedStatement ps = prepareStatement(sql, params);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                details.add(new String[]{rs.getString(1), rs.getString(2)});
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return details;
    }

    private List<String[]> executeDetailQueryWithFees(String sql, Object... params) {
        List<String[]> details = new ArrayList<>();
        try (PreparedStatement ps = prepareStatement(sql, params);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                List<String> row = new ArrayList<>();
                row.add(rs.getString(1)); // Book ID
                row.add(rs.getString(2)); // Book Name
                for (int i = 3; i <= rs.getMetaData().getColumnCount(); i++) {
                    row.add(String.valueOf(rs.getObject(i))); // Fees
                }
                details.add(row.toArray(new String[0]));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return details;
    }

    private PreparedStatement prepareStatement(String sql, Object... params) throws SQLException {
        PreparedStatement ps = conn.prepareStatement(sql);
        for (int i = 0; i < params.length; i++) {
            if (params[i] instanceof Integer) {
                ps.setInt(i + 1, (Integer) params[i]);
            } else if (params[i] instanceof LocalDate) {
                ps.setDate(i + 1, java.sql.Date.valueOf((LocalDate) params[i]));
            }
        }
        return ps;
    }
}
