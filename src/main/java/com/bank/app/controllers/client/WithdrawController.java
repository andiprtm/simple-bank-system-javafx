package com.bank.app.controllers.client;

import com.bank.app.models.Model;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.net.URL;
import java.util.ResourceBundle;

public class WithdrawController implements Initializable {
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
    }

    public void backToDashboard() {
        System.out.println("Back to Login");
        Stage stage = (Stage) btn_toDashboard.getScene().getWindow();
        Model.getInstance().getViewFactory().closeStage(stage);
        Model.getInstance().getViewFactory().showClientWindow();
    }
}
