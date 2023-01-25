package com.bank.app.controllers.manager;

import com.bank.app.models.Manager;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

import java.net.URL;
import java.util.ResourceBundle;

public class CreateTellerController implements Initializable {
    Manager manager;
    public TextField tf_username;
    public TextField tf_password;
    public TextField tf_nama;
    public TextField tf_alamat;
    public TextField tf_nomorHandphone;
    public ComboBox<Object> cb_tipeAkun;
    public Button btn_tambahPegawai;
    public Label tv_say_hi;
    public Label tv_alert;
    Object[] tipeAkun = {"Teller", "Manager"};

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        cb_tipeAkun.getItems().addAll(tipeAkun);
    }

    public void setManager(Manager manager) {
        this.manager = manager;
        String[] name = manager.name.split(" ");

        tv_say_hi.setText("Hi, " + name[0]);
    }
}
