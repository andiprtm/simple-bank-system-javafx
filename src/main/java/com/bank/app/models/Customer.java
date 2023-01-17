package com.bank.app.models;

import com.bank.app.ConnectionManager;

import java.math.BigDecimal;
import java.sql.*;

public class Customer {
    Integer customerId;
    String accountType;
    Boolean isActive;
    Integer pin;
    String username;
    String password;
    String name;
    String address;
    String phone;
    BigDecimal balance;
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
}

