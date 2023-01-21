package com.bank.app.controllers.client;

import com.bank.app.models.Model;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.net.URL;
import java.util.ResourceBundle;

public class TransferController implements Initializable {
    public TextField tf_username;
    public TextField tf_nominalTransfer;
    public TextField tf_PIN;
    public Label tv_errorTransfer;
    public Button btn_cancel;
    public Button btn_transfer;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        btn_cancel.setOnAction(event -> {
            System.out.println("Cancel");
            backToDashboard();
        });
    }

    public void backToDashboard() {
        System.out.println("Back to Login");
        Stage stage = (Stage) btn_cancel.getScene().getWindow();
        Model.getInstance().getViewFactory().closeStage(stage);

        Model.getInstance().getViewFactory().showClientWindow();
    }
}
