package com.bank.app.controllers.client;

import com.bank.app.controllers.utils.CurrencyController;
import com.bank.app.models.Customer;
import com.bank.app.models.Model;
import javafx.animation.FadeTransition;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.math.BigDecimal;
import java.net.URL;
import java.util.Objects;
import java.util.ResourceBundle;

public class WithdrawController implements Initializable {
    public Customer customer;
    public Button btn_toDashboard;
    public Label tv_errorWithdraw;
    public TextField tf_nominalWithdraw;
    public PasswordField wd_pin;
    public Button btn_withdraw;
    private FadeTransition fadeTransition = new FadeTransition(Duration.millis(1000));

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        fadeTransition.setNode(tv_errorWithdraw);
        fadeTransition.setFromValue(0.0);
        fadeTransition.setToValue(1.0);
        fadeTransition.setCycleCount(1);
        fadeTransition.setAutoReverse(true);
        btn_toDashboard.setOnAction(event -> {
            System.out.println("Back to Dashboard");
            backToDashboard();
        });

        btn_withdraw.setOnAction(actionEvent -> {
            if(cekInput()){
                System.out.println("Input tidak boleh kosong");
            }else {
                if(customer.balance.compareTo(new BigDecimal(tf_nominalWithdraw.getText())) <= 0){
                    tv_errorWithdraw.setText("Saldo tidak cukup. Sisa saldo: " + new CurrencyController().getIndonesianCurrency(customer.balance));
                    fadeTransition.playFromStart();
                }else{
                    Boolean isSuccess = withdraw();
                    if (isSuccess) {
                        backToDashboard();
                    }
                }
            }
        });
    }

    public Boolean cekInput(){
        boolean isEmpty = false;
        if(Objects.equals(tf_nominalWithdraw.getText(), "") || Objects.equals(wd_pin.getText(), "")) {
            tv_errorWithdraw.setText("Nominal dan PIN tidak boleh kosong");
            fadeTransition.playFromStart();
            isEmpty = true;
        }
        return isEmpty;
    }

    public void backToDashboard() {
        System.out.println("Back to Login");
        Stage stage = (Stage) btn_toDashboard.getScene().getWindow();
        Model.getInstance().getViewFactory().closeStage(stage);
        Model.getInstance().getViewFactory().showClientWindow();
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    public Boolean withdraw() {
        fadeTransition.setNode(tv_errorWithdraw);
        fadeTransition.setFromValue(0.0);
        fadeTransition.setToValue(1.0);
        fadeTransition.setCycleCount(1);
        fadeTransition.setAutoReverse(true);
        Boolean isSuccess = null;

        if (!tf_nominalWithdraw.getText().equals("") && !wd_pin.getText().equals("")) {
            try {
                BigDecimal withdrawAmount = new BigDecimal(tf_nominalWithdraw.getText());
                Integer pin = Integer.parseInt(wd_pin.getText());

                if (Objects.equals(customer.pin, pin)) {
                    isSuccess = customer.withdrawBalance(withdrawAmount, tv_errorWithdraw);
                } else {
                    tv_errorWithdraw.setText("PIN salah");
                    fadeTransition.playFromStart();
                    isSuccess =  false;
                }
            } catch (NumberFormatException e) {
                tv_errorWithdraw.setText("Input tidak sah!");
                fadeTransition.playFromStart();
                isSuccess = false;
            }
        } else {
            tv_errorWithdraw.setText("Isi semua form dibawah ini!");
            fadeTransition.playFromStart();
            isSuccess = false;
        }

        if (!isSuccess) {
            tv_errorWithdraw.setBackground(new Background(new BackgroundFill(Color.RED, null, null)));
            fadeTransition.playFromStart();
        }

        return isSuccess;
    }
}
