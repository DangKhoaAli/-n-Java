package BLL;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import DAO.Select_DAO;

public class Select_BLL {
    private Select_DAO select_DAO;

    public Select_BLL() {
        select_DAO = new Select_DAO();
    }
    
    public List<String> getAll_Book(){
        try {
            List<String> bookDetails = select_DAO.getAll_Book();
            return bookDetails == null ? new ArrayList<>() : bookDetails;
        } catch (SQLException e) {
            return new ArrayList<>();
        }
    }

    public List<String> searchBooksByName(String keyword) {
        try {
            List<String> bookDetails = select_DAO.searchBooksByName(keyword);
            return bookDetails == null ? new ArrayList<>() : bookDetails;
        } catch (SQLException e) {
            return new ArrayList<>();
        }
    }

    public List<String> getBookByLoan(String loanSlipID) {
        try {
            List<String> bookDetails = select_DAO.getAll_Pay(loanSlipID);
            return bookDetails == null ? new ArrayList<>() : bookDetails;
        } catch (SQLException e) {
            return new ArrayList<>();
        }
    }
}
