package com.bank.app.controllers.admin;

import com.bank.app.ConnectionManager;
import com.bank.app.controllers.utils.CurrencyController;
import com.bank.app.models.Teller;
import javafx.animation.FadeTransition;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.util.Duration;

import java.math.BigDecimal;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class DepositController implements Initializable {
    public Teller teller;
    public TextField tf_username;
    public TextField tf_amount;
    public CheckBox ckb_verifikasi;
    public Button btn_deposit;
    public Label tv_say_hi;
    public Label tv_alert;
    public int maxDeposit;
    Connection conn = ConnectionManager.getInstance().getConnection();
    private FadeTransition fadeTransition = new FadeTransition(Duration.millis(1000));

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        btn_deposit.setOnAction(event -> {
            cekInput();
        });

    }

    public BigDecimal getBalance(String username) throws SQLException {
        String sql = "select cba.account_balance from customer_bank_account cba where cba.customer_id = (select id_customer from customer_data where username=?);";
        PreparedStatement stmt = conn.prepareStatement(sql);
        stmt.setString(1, username);
        ResultSet rs = stmt.executeQuery();
        BigDecimal balance = null;
        while (rs.next()) {
            balance = rs.getBigDecimal("account_balance");
        }
        return balance;
    }

    public BigDecimal getMaxBalanceLimit(String username) throws SQLException {
        String sql = "SELECT cat.max_balance_limit from customer_bank_account cba\n" +
                "join customer_account_type cat\n" +
                "on cba.customer_account_type_id = cat.id_customer_account_type\n" +
                "where cba.customer_id = (select id_customer from customer_data where username=?);";
        PreparedStatement stmt = conn.prepareStatement(sql);
        stmt.setString(1, username);
        ResultSet rs = stmt.executeQuery();
        BigDecimal balance = null;
        while (rs.next()) {
            balance = rs.getBigDecimal("max_balance_limit");
        }
        return balance;
    }

    public void cekInput(){
        fadeTransition.setNode(tv_alert);
        fadeTransition.setFromValue(0.0);
        fadeTransition.setToValue(1.0);
        fadeTransition.setCycleCount(1);
        fadeTransition.setAutoReverse(true);
        String username = tf_username.getText();
        String amountString = tf_amount.getText();

        if ((username.isEmpty() || amountString.isEmpty())) {
            tv_alert.setVisible(true);
            fadeTransition.playFromStart();
            tv_alert.setText("Mohon isi semua data");
        } else {
            BigDecimal amount = new BigDecimal(amountString);

            try {
                if(ckb_verifikasi.isSelected()){
                    if(checkMaksimunDeposit(username, amount)){
                        System.out.println("deposit gagal");
                    }else{
                        teller.depositBalanceToCustomerAccount(username, amount);
                        tv_alert.setVisible(true);
                        fadeTransition.playFromStart();
                        tv_alert.setText("Sukses melakukan deposit sejumlah " + new CurrencyController().getIndonesianCurrency(amount));
                    }

                } else {
                    tv_alert.setVisible(true);
                    fadeTransition.playFromStart();
                    tv_alert.setText("Silahkan checklist jika data sudah benar!");
                }
            } catch (NumberFormatException e) {
                tv_alert.setVisible(true);
                fadeTransition.playFromStart();
                tv_alert.setText("Input tidak sah!");
            }
        }
    }

    public Boolean checkMaksimunDeposit(String usernameInput, BigDecimal amountInput){
        fadeTransition.setNode(tv_alert);
        fadeTransition.setFromValue(0.0);
        fadeTransition.setToValue(1.0);
        fadeTransition.setCycleCount(1);
        fadeTransition.setAutoReverse(true);
        String amountString = tf_amount.getText();
        BigDecimal amount = new BigDecimal(amountString);
        boolean isDepositMax = false;
        BigDecimal maxDeposit = new BigDecimal(0);
        try {
            PreparedStatement ps = conn.prepareStatement(
                    "SELECT cat.max_deposit_limit from customer_bank_account cba\n" +
                            "join customer_account_type cat\n" +
                            "on cba.customer_account_type_id = cat.id_customer_account_type\n" +
                            "where cba.customer_id = (select id_customer from customer_data where username=?);"
            );
            ps.setString(1, usernameInput);
            ResultSet rs = ps.executeQuery();

            while(rs.next()){
                maxDeposit = rs.getBigDecimal("max_deposit_limit");
                this.maxDeposit = maxDeposit.intValue();
            }
            System.out.println(maxDeposit);

            if (maxDeposit.compareTo(new BigDecimal(0)) == 0){
                tv_alert.setVisible(true);
                fadeTransition.playFromStart();
                tv_alert.setText("Rekening penerima tidak ditemukan");
                isDepositMax = true;
            }else if(amountInput.compareTo(new BigDecimal(10000)) < 0){
                tv_alert.setVisible(true);
                fadeTransition.playFromStart();
                tv_alert.setText("Deposit gagal, deposit minimal adalah Rp. 10.000");
                isDepositMax = true;
            } else if (amount.add(getBalance(usernameInput)).compareTo(getMaxBalanceLimit(usernameInput)) > 0){
                tv_alert.setVisible(true);
                fadeTransition.playFromStart();
                tv_alert.setText("Deposit gagal, deposit melebihi batas saldo maksimal");
                isDepositMax = true;
            } else if (amountInput.compareTo(maxDeposit) > 0){
                tv_alert.setVisible(true);
                fadeTransition.playFromStart();
                tv_alert.setText("Deposit gagal, deposit maksimal adalah " + new CurrencyController().getIndonesianCurrency(new BigDecimal(this.maxDeposit)) + "");
                isDepositMax = true;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return isDepositMax;
    }

    public void setTellerData(Teller teller) {
        this.teller = teller;
        String[] name = teller.name.split(" ");

        tv_say_hi.setText("Hi, " + name[0]);
    }
}
