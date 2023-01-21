package com.bank.app.controllers.admin;

import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.TextField;

import java.net.URL;
import java.util.ResourceBundle;

public class DepositController implements Initializable {
    public TextField tf_username;
    public TextField tf_amount;
    public TextField tf_pin;
    public CheckBox ckb_verifikasi;
    public Button btn_deposit;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }
}
