package com.bank.app.controllers.client;

import com.bank.app.ConnectionManager;
import com.bank.app.controllers.utils.CurrencyController;
import com.bank.app.models.Customer;
import com.bank.app.models.Model;
import javafx.animation.FadeTransition;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.math.BigDecimal;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Objects;
import java.util.ResourceBundle;

public class TransferController implements Initializable {
    public Customer customer;
    public TextField tf_username;
    public TextField tf_nominalTransfer;
    public TextField tf_PIN;
    public Label tv_errorTransfer;
    public Button btn_cancel;
    public Button btn_transfer;

    private FadeTransition fadeTransition = new FadeTransition(Duration.millis(1000));

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        fadeTransition.setNode(tv_errorTransfer);
        fadeTransition.setFromValue(0.0);
        fadeTransition.setToValue(1.0);
        fadeTransition.setCycleCount(1);
        fadeTransition.setAutoReverse(true);

        btn_cancel.setOnAction(event -> {
            System.out.println("Cancel");
            backToDashboard();
        });

        btn_transfer.setOnAction(event -> {
            if(cekInput()){
                System.out.println("Input tidak boleh kosong");
            }else {
                try{
                    if(customer.balance.compareTo(new BigDecimal(tf_nominalTransfer.getText())) <= 0){
                        tv_errorTransfer.setText("Saldo tidak cukup. Sisa saldo: " + new CurrencyController().getIndonesianCurrency(customer.balance));
                        fadeTransition.playFromStart();
                    }else{
                        System.out.println("Transfer");
                        Boolean isSuccess = transfer();
                        if (isSuccess) {
                            backToDashboard();
                        }
                    }
                }catch (NumberFormatException e){
                    tv_errorTransfer.setText("Input tidak sah!, Nominal transfer dan pin harus berupa angka");
                    fadeTransition.playFromStart();
                }
            }

        });
    }

    public Boolean cekInput(){
        boolean isEmpty = false;
        if(Objects.equals(tf_nominalTransfer.getText(), "") || Objects.equals(tf_PIN.getText(), "")) {
            tv_errorTransfer.setText("Nominal dan PIN tidak boleh kosong");
            fadeTransition.playFromStart();
            isEmpty = true;
        }
        return isEmpty;
    }

    public void backToDashboard() {
        System.out.println("Back to Login");
        Stage stage = (Stage) btn_cancel.getScene().getWindow();
        Model.getInstance().getViewFactory().closeStage(stage);

        Model.getInstance().getViewFactory().showClientWindow();
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    public Boolean transfer() {
        Boolean isSuccess = true;
        
        if (!tf_username.getText().equals("") && !tf_nominalTransfer.getText().equals("") && !tf_PIN.getText().equals("")) {
            try {
                BigDecimal transferAmount = new BigDecimal(tf_nominalTransfer.getText());
                Integer pin = Integer.parseInt(tf_PIN.getText());

                if (Objects.equals(customer.pin, pin)) {
                    isSuccess = customer.transferToAnotherBankAccount(tf_username.getText(), transferAmount, tv_errorTransfer);
                } else {
                    tv_errorTransfer.setText("PIN salah");
                    fadeTransition.playFromStart();
                    isSuccess =  false;
                }
            } catch (NumberFormatException e) {
                tv_errorTransfer.setText("Input tidak sah!, Nominal transfer dan pin harus berupa angka");
                fadeTransition.playFromStart();
                isSuccess = false;
            }
        } else {
            tv_errorTransfer.setText("Isi semua form dibawah ini!");
            fadeTransition.playFromStart();
            isSuccess = false;
        }

        if (!isSuccess) {
            tv_errorTransfer.setBackground(new Background(new BackgroundFill(Color.RED, null, null)));
            fadeTransition.playFromStart();
        }

        return isSuccess;
    }


}
