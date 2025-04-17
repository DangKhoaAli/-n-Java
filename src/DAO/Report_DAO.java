package DAO;

import config.DatabaseConnection;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;

public class Report_DAO {
    private Connection conn;

    public Report_DAO() {
        conn = DatabaseConnection.getConnection();
    }

    public int getTotalLoansByDay(LocalDate date) {
        return executeCountQuery("SELECT COUNT(*) FROM Loan_slip WHERE Borrow_Date = ?", date);
    }

    public int getTotalReturnsByDay(LocalDate date) {
        return executeCountQuery("SELECT COUNT(*) FROM Payment_slip WHERE payment_Date = ?", date);
    }

    public double getTotalRevenueByDay(LocalDate date) {
        return executeSumQuery("SELECT SUM(late_fee + damage_fee) FROM Payment_slip WHERE payment_Date = ?", date);
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

    public int getTotalLoansByYear(LocalDate date) {
        return executeCountQuery("SELECT COUNT(*) FROM Loan_slip WHERE YEAR(Borrow_Date) = ?", date.getYear());
    }

    public int getTotalReturnsByYear(LocalDate date) {
        return executeCountQuery("SELECT COUNT(*) FROM Payment_slip WHERE YEAR(payment_Date) = ?", date.getYear());
    }

    public double getTotalRevenueByYear(LocalDate date) {
        return executeSumQuery("SELECT SUM(late_fee + damage_fee) FROM Payment_slip WHERE YEAR(payment_Date) = ?", date.getYear());
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
