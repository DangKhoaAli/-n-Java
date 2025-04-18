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
        return executeCountQuery("SELECT SUM(so_luong) FROM Loan_slip WHERE MONTH(Borrow_Date) = ? AND YEAR(Borrow_Date) = ?", date.getMonthValue(), date.getYear());
    }

    public int getTotalReturnsByMonth(LocalDate date) {
        return executeCountQuery("SELECT SUM(so_luong) FROM Payment_slip WHERE MONTH(payment_Date) = ? AND YEAR(payment_Date) = ?", date.getMonthValue(), date.getYear());
    }

    public double getTotalRevenueByMonth(LocalDate date) {
        return executeSumQuery("SELECT SUM(ls.loan_fee) + SUM(rs.late_fee) + SUM(rs.damage_fee) AS total_revenue "
                         + "FROM Loan_slip ls "
                         + "JOIN Payment_slip ps ON ls.ID = ps.ID_Loan_slip "
                         + "WHERE MONTH(ls.Borrow_Date) = ? AND YEAR(ls.Borrow_Date) = ? "
                         + "OR MONTH(ps.payment_Date) = ? AND YEAR(rs.payment_Date) = ?", 
                         date.getMonthValue(), date.getYear(), date.getMonthValue(), date.getYear());
    }

    public List<String[]> getLoanDetailsByMonthWithBookInfo(LocalDate date) {
        String sql = """
                     SELECT bd.ID AS Book_ID, b.name AS Book_Name, ls.loan_fee
                     FROM Loan_slip ls
                     JOIN Borrowed_Book_Details bbd ON ls.ID = bbd.ID_Loan_slip
                     JOIN Book_Details bd ON bbd.ID_Book = bd.ID
                     JOIN Book b ON bd.ID_Book = b.ID
                     WHERE MONTH(ls.Borrow_Date) = ? AND YEAR(ls.Borrow_Date) = ?
                     """;
        return executeDetailQueryWithFees(sql, date.getMonthValue(), date.getYear());
    }
    
    public List<String[]> getReturnDetailsByMonth(LocalDate date) {
        String sql = """
                     SELECT bd.ID AS Book_ID, b.name AS Book_Name, ps.late_fee, ps.damage_fee
                     FROM Payment_slip ps
                     JOIN Loan_slip ls ON ps.ID_Loan_slip = ls.ID 
                     JOIN Book_Details_Return bdr ON ps.ID = bdr.ID_Payment_slip
                     JOIN Book_Details bd ON bdr.ID_Book = bd.ID
                     JOIN Book b ON bd.ID_Book = b.ID
                     WHERE MONTH(ps.payment_Date) = ? AND YEAR(ps.payment_Date) = ?
                     """;
        return executeDetailQuery(sql, date.getMonthValue(), date.getYear());
    }
    

    // public List<String[]> getLoanDetailsByMonthWithFees(LocalDate date) {
    //     String sql = "SELECT Book_ID, Book_Name, loan_fee FROM Loan_slip WHERE MONTH(Borrow_Date) = ? AND YEAR(Borrow_Date) = ?";
    //     return executeDetailQueryWithFees(sql, date.getMonthValue(), date.getYear());
    // }

    // public List<String[]> getReturnDetailsByMonthWithFees(LocalDate date) {
    //     String sql = "SELECT Book_ID, Book_Name, late_fee, damage_fee FROM Payment_slip WHERE MONTH(payment_Date) = ? AND YEAR(payment_Date) = ?";
    //     return executeDetailQueryWithFees(sql, date.getMonthValue(), date.getYear());
    // }

    // public int getTotalBooksLoanedByMonth(LocalDate date) {
    //     String sql = """
    //                  SELECT SUM(quantity) 
    //                  FROM Loan_slip 
    //                  WHERE MONTH(Borrow_Date) = ? AND YEAR(Borrow_Date) = ?
    //                  """;
    //     return executeCountQuery(sql, date.getMonthValue(), date.getYear());
    // }

    // public int getTotalBooksReturnedByMonth(LocalDate date) {
    //     String sql = """
    //                  SELECT SUM(quantity) 
    //                  FROM Payment_slip 
    //                  WHERE MONTH(payment_Date) = ? AND YEAR(payment_Date) = ?
    //                  """;
    //     return executeCountQuery(sql, date.getMonthValue(), date.getYear());
    // }

    // public double getTotalRevenueIncludingLoanAndReturnFees(LocalDate date) {
    //     String sql = """
    //                  SELECT SUM(loan_fee + late_fee + damage_fee) 
    //                  FROM Payment_slip 
    //                  WHERE MONTH(payment_Date) = ? AND YEAR(payment_Date) = ?
    //                  """;
    //     return executeSumQuery(sql, date.getMonthValue(), date.getYear());
    // }

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
