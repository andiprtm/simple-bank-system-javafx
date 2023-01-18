package com.bank.app.controllers.client;

import com.bank.app.models.Model;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.stage.Stage;

import java.net.URL;
import java.util.ResourceBundle;

public class DashboardController implements Initializable {
    public Label tv_say_hi;
    public Label tv_saldo_akhir;
    public Label tv_value_deposit;
    public Label tv_transfer_masuk;
    public Label tv_transfer_keluar;
    public Label tv_tarik_tunai;
    public Button btn_transfer;
    public Button btn_tarik_tunai;
    public ListView transaction_listview;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        btn_tarik_tunai.setOnAction(event -> {
            System.out.println("Tarik Tunai");
            Stage stage = (Stage) btn_tarik_tunai.getScene().getWindow();
            Model.getInstance().getViewFactory().closeStage(stage);
            Model.getInstance().getViewFactory().showWithdrawWindow();
        });

        btn_transfer.setOnAction(event -> {
            System.out.println("Transfer");
            Stage stage = (Stage) btn_transfer.getScene().getWindow();
            Model.getInstance().getViewFactory().closeStage(stage);
            Model.getInstance().getViewFactory().showTransferWindow();
        });

    }

    public void setWelcomeText(String Username){
        tv_say_hi.setText(Username);
    }
}
