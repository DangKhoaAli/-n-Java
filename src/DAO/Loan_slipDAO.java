package DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import config.DatabaseConnection;
import model.Loan_slip;

public class Loan_slipDAO {
    private Connection conn;

    public Loan_slipDAO(){
        conn = DatabaseConnection.getConnection();
    }

    public List<Loan_slip> getAllLoan_slips() throws SQLException{
        List<Loan_slip> loan = new ArrayList<>();
        String sql = "SELECT * FROM phieu_muon";
        try(PreparedStatement ps = conn.prepareStatement(sql);
            ResultSet rs = ps.executeQuery()){
                while(rs.next()){}
                    // loan.add(new Loan_slip(rs.getString(0)))
            }
        return loan;
    }
}
