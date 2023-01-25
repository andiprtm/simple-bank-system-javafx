package com.bank.app.controllers.admin;

import com.bank.app.controllers.utils.CurrencyController;
import com.bank.app.models.Teller;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

import java.math.BigDecimal;
import java.net.URL;
import java.util.ResourceBundle;

public class DepositController implements Initializable {
    public Teller teller;
    public TextField tf_username;
    public TextField tf_amount;
    public CheckBox ckb_verifikasi;
    public Button btn_deposit;
    public Label tv_say_hi;
    public Label tv_alert;

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
                    teller.depositBalanceToCustomerAccount(username, amount);
                    tv_alert.setVisible(true);
                    tv_alert.setText("Deposit ke nasabah " + username + " sejumlah " + new CurrencyController().getIndonesianCurrency(amount));
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

    public void setTellerData(Teller teller) {
        this.teller = teller;
        String[] name = teller.name.split(" ");

        tv_say_hi.setText("Hi, " + name[0]);
    }
}
