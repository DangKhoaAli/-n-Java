package DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import config.DatabaseConnection;

public class Select_DAO {
    private Connection conn;

    public Select_DAO() {
        conn = DatabaseConnection.getConnection();
    }

    public List<String> getAll_Book() throws SQLException{
        String sql = """
                    SELECT 
                        bd.ID AS Ma_Chi_Tiet,
                        b.name AS Ten_Sach,
                        b.category AS The_Loai,
                        bd.supplier AS Nha_Cung_Cap,
                        bd.year AS Nam_Xuat_Ban,
                        bd.page_num AS So_Trang,
                        b.loan_fee AS Phi_Muon,
                        bd.num_page_dama AS Trang_Hu
                    FROM 
                        Book_Details bd
                    JOIN 
                        Book b ON bd.ID_Book = b.ID
                    WHERE 
                        bd.status = 'Hiện có'
                    """;
        List<String> bookDetails = new ArrayList<>();
        try (PreparedStatement ps = conn.prepareStatement(sql)){
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                bookDetails.add(rs.getString("Ma_Chi_Tiet") + ";" +
                                rs.getString("Ten_Sach") + ";" +
                                rs.getString("The_Loai") + ";" +
                                rs.getString("Nha_Cung_Cap") + ";" +
                                rs.getString("Nam_Xuat_Ban") + ";" +
                                rs.getString("So_Trang") + ";" +
                                rs.getString("Phi_Muon") + ";" +
                                rs.getString("Trang_Hu"));
            }
        }
        return bookDetails;

    }

    public List<String> searchBooksByName(String keyword) throws SQLException {
        String sql = """
                SELECT 
                    bd.ID AS Ma_Chi_Tiet,
                    b.name AS Ten_Sach,
                    b.category AS The_Loai,
                    bd.supplier AS Nha_Cung_Cap,
                    bd.year AS Nam_Xuat_Ban,
                    bd.page_num AS So_Trang,
                    b.loan_fee AS Phi_Muon,
                    bd.num_page_dama AS Trang_Hu
                FROM 
                    Book_Details bd
                JOIN 
                    Book b ON bd.ID_Book = b.ID
                WHERE 
                    bd.status = 'Hiện có' AND b.name LIKE ?
                """;
    
        List<String> bookDetails = new ArrayList<>();
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, "%" + keyword + "%"); // tìm một phần tên
    
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                bookDetails.add(rs.getString("Ma_Chi_Tiet") + ";" +
                                rs.getString("Ten_Sach") + ";" +
                                rs.getString("The_Loai") + ";" +
                                rs.getString("Nha_Cung_Cap") + ";" +
                                rs.getInt("Nam_Xuat_Ban") + ";" +
                                rs.getInt("So_Trang") + ";" +
                                rs.getBigDecimal("Phi_Muon") + ";" +
                                rs.getString("Trang_Hu"));
            }
        }
    
        return bookDetails;
    }
    
    public List<String> getAll_Pay(String ID_Loan_slip) throws SQLException {
        String sql = """
                SELECT 
                    bd.ID AS Ma_Chi_Tiet,
                    b.name AS Ten_Sach,
                    b.category AS The_Loai,
                    bd.supplier AS Nha_Cung_Cap,
                    bd.year AS Nam_Xuat_Ban,
                    bd.page_num AS So_Trang
                FROM 
                    Borrowed_Book_Details bbd
                JOIN 
                    Book_Details bd ON bbd.ID_Book = bd.ID
                JOIN 
                        Book b ON bd.ID_Book = b.ID
                WHERE 
                    bbd.ID_Loan_slip = ? AND bd.status = 'Đang được mượn'
                """;
        List<String> paymentDetails = new ArrayList<>();
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, ID_Loan_slip);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                paymentDetails.add(rs.getString("Ma_Chi_Tiet") + ";" +
                        rs.getString("Ten_Sach") + ";" +
                        rs.getString("The_Loai") + ";" +
                        rs.getString("Nha_Cung_Cap") + ";" +
                        rs.getString("Nam_Xuat_Ban") + ";" +
                        rs.getString("So_Trang"));
            }
        }
        return paymentDetails;
    }

}
