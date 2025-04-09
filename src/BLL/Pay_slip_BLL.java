package BLL;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import DAO.Payment_slip_DAO;
import model.Payment_slip;

public class Pay_slip_BLL {
    private Payment_slip_DAO payment_slip_dao;

    public Pay_slip_BLL() {
        payment_slip_dao = new Payment_slip_DAO();
    }

    public List<Payment_slip> getPaymentSlip(String ID) {
        try {
            List<Payment_slip> paymentSlip = payment_slip_dao.getAllPayment_slips();
            if (paymentSlip == null || paymentSlip.isEmpty()) {
                return new ArrayList<>();
            }
            return paymentSlip;
        } catch (SQLException e) {
            return new ArrayList<>();
        }
    }

    public Payment_slip search_Pay(String ID){
        try {
            Payment_slip paymentSlip = payment_slip_dao.searchPayment_slip(ID);
            return paymentSlip == null ? null : paymentSlip;
        } catch (SQLException e){
            return null;
        }
    }
    
}
