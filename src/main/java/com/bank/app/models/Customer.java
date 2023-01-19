package com.bank.app.models;

import com.bank.app.ConnectionManager;

import java.math.BigDecimal;
import java.sql.*;

public class Customer {
    public Integer customerId;
    public String accountType;
    public Boolean isActive;
    public Integer pin;
    public String username;
    public String password;
    public String name;
    public String address;
    public String phone;
    public BigDecimal balance;
    Connection conn = ConnectionManager.getInstance().getConnection();

    public Customer(String username, String password){
        this.username = username;
        this.password = password;
    }

    public Integer getLocalCustomerId() {
        return this.customerId;
    }

    public void authenticate() {
        try {
            PreparedStatement ps = conn.prepareStatement(
                    "SELECT cba.customer_id, cba.customer_is_active,\n" +
                            "       (SELECT customer_account_type FROM customer_account_type WHERE id_customer_account_type=cba.customer_account_type_id) as account_type\n" +
                            "FROM customer_data cd, customer_bank_account cba\n" +
                            "WHERE username=? AND password=? AND cba.customer_id=cd.id_customer;"
            );

            ps.setString(1, this.username);
            ps.setString(2, this.password);

            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                this.customerId = rs.getInt("customer_id");
                this.isActive = rs.getBoolean("customer_is_active");
                this.accountType = rs.getString("account_type");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void getCustomerData(){
        try {
            PreparedStatement ps = conn.prepareStatement("SELECT cd.username, cd.password, cd.name, cd.phone, cd.address, cba.account_balance,\n" +
                    "                (SELECT customer_account_type FROM customer_account_type WHERE customer_account_type.id_customer_account_type=cba.customer_account_type_id) AS account_type,\n" +
                    "                cba.customer_is_active, cba.account_pin\n" +
                    "FROM customer_data cd, customer_bank_account cba, customer_account_type cat\n" +
                    "WHERE cd.id_customer=? AND cba.customer_id=cd.id_customer AND cat.id_customer_account_type=cba.customer_account_type_id");

            ps.setInt(1, this.customerId);

            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                this.username = rs.getString("username");
                this.password = rs.getString("password");
                this.name = rs.getString("name");
                this.address = rs.getString("address");
                this.phone = rs.getString("phone");
                this.balance = rs.getBigDecimal("account_balance");
                this.accountType = rs.getString("account_type");
                this.isActive = rs.getBoolean("customer_is_active");
                this.pin = rs.getInt("account_pin");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public Integer getTransactionType (String transactionType) {
        Integer idTransactionType = null;

        try {
            PreparedStatement ps = conn.prepareStatement("SELECT tt.id_transaction_type FROM transaction_type tt WHERE tt.transaction_type=?");

            ps.setString(1, transactionType);

            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                idTransactionType = rs.getInt("id_transaction_type");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return idTransactionType;
    }

    public BigDecimal getTransactionFromSender (Integer senderCustomerId, String transactionType) {
        BigDecimal total = null;

        try {
            PreparedStatement ps = conn.prepareStatement("SELECT IFNULL(SUM(th.transaction_amount), 0) as transaction_transfer_total FROM transaction_history th WHERE th.transaction_sender=? AND th.transaction_type_id=?;");

            ps.setInt(1, senderCustomerId);
            ps.setInt(2, getTransactionType(transactionType));

            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                total = rs.getBigDecimal("transaction_transfer_total");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return total;
    }

    public BigDecimal getTransactionFromReceiver (Integer receiverCustomerId, String transactionType) {
        BigDecimal total = null;

        try {
            PreparedStatement ps = conn.prepareStatement("SELECT IFNULL(SUM(th.transaction_amount), 0) as transaction_transfer_total FROM transaction_history th WHERE th.transaction_receiver=? AND th.transaction_type_id=?;");

            ps.setInt(1, receiverCustomerId);
            ps.setInt(2, getTransactionType(transactionType));

            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                total = rs.getBigDecimal("transaction_transfer_total");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return total;
    }
}

