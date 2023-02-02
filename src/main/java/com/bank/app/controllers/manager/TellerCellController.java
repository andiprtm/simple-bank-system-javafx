package com.bank.app.controllers.manager;

import com.bank.app.models.TellerModel;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;

import java.net.URL;
import java.util.ResourceBundle;

public class TellerCellController implements Initializable {
    public Label tv_username;
    public Label tv_phone;
    public Label tv_tipe_pegawai;
    public HBox box_teller;
    public int counter;

    private final TellerModel tellerModel;

    public TellerCellController(TellerModel tellerModel) {
        this.tellerModel = tellerModel;
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        if(tellerModel.statusProperty().getValue().equals("Not Active")){
            box_teller.setStyle("-fx-background-color: #800000; -fx-background-radius: 10");
        }
        tv_tipe_pegawai.setText(tellerModel.accountTypeProperty().getValue());
        tv_username.setText(tellerModel.usernameProperty().getValue());
        tv_phone.setText(tellerModel.phoneProperty().getValue());
        counter++;
    }
}
