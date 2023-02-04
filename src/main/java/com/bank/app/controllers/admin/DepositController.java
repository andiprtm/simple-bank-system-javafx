package com.bank.app.controllers.admin;

import com.bank.app.ConnectionManager;
import com.bank.app.controllers.utils.CurrencyController;
import com.bank.app.models.Customer;
import com.bank.app.models.Teller;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

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

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        btn_deposit.setOnAction(event -> {
            cekInput();
        });

    }

    public void cekInput(){
        String username = tf_username.getText();
        String amountString = tf_amount.getText();

        if ((username.isEmpty() || amountString.isEmpty())) {
            tv_alert.setVisible(true);
            tv_alert.setText("Mohon isi semua data");
        } else {
            BigDecimal amount = new BigDecimal(amountString);

            try {
                if(ckb_verifikasi.isSelected()){
                    if(checkMaksimunDeposit(username, amount)){
                        tv_alert.setVisible(true);
                        tv_alert.setText("Deposit ke nasabah " + username + " sejumlah " + new CurrencyController().getIndonesianCurrency(amount) + " gagal,\ndeposit maksimal adalah " + new CurrencyController().getIndonesianCurrency(new BigDecimal(maxDeposit)) + "");
                    }else{
                        teller.depositBalanceToCustomerAccount(username, amount);
                        tv_alert.setVisible(true);
                        tv_alert.setText("Deposit ke nasabah " + username + " sejumlah " + new CurrencyController().getIndonesianCurrency(amount));
                    }

                } else {
                    tv_alert.setVisible(true);
                    tv_alert.setText("Silahkan checklist jika data sudah benar!");
                }
            } catch (NumberFormatException e) {
                tv_alert.setVisible(true);
                tv_alert.setText("Input tidak sah!");
            }
        }
    }

    public Boolean checkMaksimunDeposit(String usernameInput, BigDecimal amountInput){
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

            if(amountInput.compareTo(maxDeposit) > 0){
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
