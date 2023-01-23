package com.bank.app.controllers.admin;

import com.bank.app.models.Model;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.stage.Stage;

import java.net.URL;
import java.util.ResourceBundle;

public class AdminMenuController implements Initializable {
    public Button btn_buatNasabah;
    public Button btn_nasabah;
    public Button btn_deposit;
    public Button btn_logout;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        btn_buatNasabah.setOnAction(actionEvent -> onBuatNasabah());
        btn_nasabah.setOnAction(actionEvent -> onNasabah());
        btn_deposit.setOnAction(actionEvent -> onDeposit());
        btn_logout.setOnAction(event -> {
            System.out.println("Logout");
            Stage stage = (Stage) btn_logout.getScene().getWindow();
            Model.getInstance().getViewFactory().closeStage(stage);
            Model.getInstance().getViewFactory().showLoginWindow();
        });
    }

    private void addListener(){
        btn_buatNasabah.setOnAction(actionEvent -> onBuatNasabah());
        btn_nasabah.setOnAction(actionEvent -> onNasabah());
        btn_deposit.setOnAction(actionEvent -> onDeposit());
    }

    public void onBuatNasabah(){
        Model.getInstance().getViewFactory().getAdminSelectedMenuItem().setValue("AddNasabah");
    }

    public void onNasabah(){
        Model.getInstance().getViewFactory().getAdminSelectedMenuItem().setValue("Nasabah");
    }

    public void onDeposit(){
        Model.getInstance().getViewFactory().getAdminSelectedMenuItem().setValue("Deposit");
    }

}
