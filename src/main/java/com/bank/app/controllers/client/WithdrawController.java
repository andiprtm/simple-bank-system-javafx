package com.bank.app.controllers.client;

import com.bank.app.models.Customer;
import com.bank.app.models.Model;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

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

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        btn_toDashboard.setOnAction(event -> {
            System.out.println("Back to Dashboard");
            backToDashboard();
        });

        btn_withdraw.setOnAction(actionEvent -> {
            if(customer.balance.compareTo(new BigDecimal(tf_nominalWithdraw.getText())) <= 0){
                tv_errorWithdraw.setText("Saldo tidak cukup. Sisa saldo: " + customer.balance);
            }else{
                Boolean isSuccess = withdraw();
                if (isSuccess) {
                    backToDashboard();
                }
            }
        });
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
        Boolean isSuccess = null;

        if (!tf_nominalWithdraw.getText().equals("") && !wd_pin.getText().equals("")) {
            try {
                BigDecimal withdrawAmount = new BigDecimal(tf_nominalWithdraw.getText());
                Integer pin = Integer.parseInt(wd_pin.getText());

                if (Objects.equals(customer.pin, pin)) {
                    isSuccess = customer.withdrawBalance(withdrawAmount, tv_errorWithdraw);
                } else {
                    tv_errorWithdraw.setText("PIN salah");
                    isSuccess =  false;
                }
            } catch (NumberFormatException e) {
                tv_errorWithdraw.setText("Input tidak sah!");
                isSuccess = false;
            }
        } else {
            tv_errorWithdraw.setText("Isi semua form dibawah ini!");
            isSuccess = false;
        }

        if (!isSuccess) {
            tv_errorWithdraw.setBackground(new Background(new BackgroundFill(Color.RED, null, null)));
        }

        return isSuccess;
    }
}
