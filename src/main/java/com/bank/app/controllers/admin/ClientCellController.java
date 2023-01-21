package com.bank.app.controllers.admin;

import com.bank.app.models.ClientModel;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;

import java.net.URL;
import java.util.ResourceBundle;

public class ClientCellController implements Initializable {
    public Label tv_username;
    public Label tv_phone;
    public Label tv_tipe_user;

    private final ClientModel clientModel;

    public ClientCellController(ClientModel clientModel) {
        this.clientModel = clientModel;
    }


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        tv_username.setText(clientModel.usernameProperty().getValue());
        tv_phone.setText(clientModel.phoneProperty().getValue());
        tv_tipe_user.setText(clientModel.accountTypeProperty().getValue());
    }
}
