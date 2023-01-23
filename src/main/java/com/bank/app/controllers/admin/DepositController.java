package com.bank.app.controllers.admin;

import com.bank.app.models.Teller;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

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

    }

    public void setTellerData(Teller teller) {
        this.teller = teller;
        String[] name = teller.name.split(" ");

        tv_say_hi.setText("Hi, " + name[0]);
    }
}
