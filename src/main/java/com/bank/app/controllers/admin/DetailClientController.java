package com.bank.app.controllers.admin;

import com.bank.app.models.Model;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;

import java.net.URL;
import java.util.ResourceBundle;

public class DetailClientController implements Initializable {
    String[] tipeNasabah = {"Silver", "Gold", "Platinum"};
    String[] statusNasabah = {"Active", "Not Active"};
    public Label tv_say_hi;
    public Label tv_alert;
    public TextField tf_password;
    public TextField tf_nama;
    public TextField tf_alamat;
    public TextField tf_nomorHandphone;
    public TextField tf_pin;
    public ComboBox<String> cb_tipeAkun;
    public CheckBox ckb_verifikasi;
    public TextField tf_idNasabah;
    public TextField tf_saldo;
    public ComboBox<String> cb_statusNasabah;
    public HBox box_btn_to_edit;
    public Button btn_kembaliKeList;
    public Button btn_editNasabah;
    public HBox box_when_edit_click;
    public Button btn_updateNasabah;
    public TextField tf_username;
    public Label tv_title_page;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        // SET VALUE TIPE AKUN NASABAH
        cb_tipeAkun.getItems().addAll(tipeNasabah);
        cb_tipeAkun.setValue(tipeNasabah[0]);

        // SET VALUE STATUS NASABAH
        cb_statusNasabah.getItems().addAll(statusNasabah);
        cb_statusNasabah.setValue(statusNasabah[0]);

        // SET JUDUL HALAMAN DAN HALAMAN DETAIL NASABAH
        tv_title_page.setText("DETAIL NASABAH");
        setFalse();

        // BUTTON EDIT NASABAH DITEKAN
        btn_editNasabah.setOnAction(actionEvent -> {
            box_btn_to_edit.setVisible(false);
            box_when_edit_click.setVisible(true);
            tv_title_page.setText("UPDATE NASABAH");
            setTrue();
        });

        // BUTTON UPDATE NASABAH DITEKAN
        btn_updateNasabah.setOnAction(actionEvent -> {
            box_btn_to_edit.setVisible(true);
            box_when_edit_click.setVisible(false);
            tv_title_page.setText("DETAIL NASABAH");
            setFalse();
        });

        // BUTTON KEMBALI KE LIST NASABAH DITEKAN
        btn_kembaliKeList.setOnAction(actionEvent -> {
            Stage stage = (Stage) btn_kembaliKeList.getScene().getWindow();
            Model.getInstance().getViewFactory().closeStage(stage);
            Model.getInstance().getViewFactory().showAdminWindow();
        });

        // DOUBLE SET UNTUK CADANGAN
        if(box_when_edit_click.isVisible()) {
            setTrue();
        }else{
            setFalse();
        }
    }

    public void setData(String idUser, String username, String password, String nama, String alamat, String nomorHandphone, String pin, String saldo, String tipeAkun, String statusNasabahh) {
        tf_idNasabah.setText(idUser);
        tf_username.setText(username);
        tf_password.setText(password);
        tf_nama.setText(nama);
        tf_alamat.setText(alamat);
        tf_nomorHandphone.setText(nomorHandphone);
        tf_pin.setText(pin);

        if(tipeAkun.equals("Silver")) {
            cb_tipeAkun.setValue(tipeNasabah[0]);
        }else if(tipeAkun.equals("Gold")) {
            cb_tipeAkun.setValue(tipeNasabah[1]);
        }else if(tipeAkun.equals("Platinum")) {
            cb_tipeAkun.setValue(tipeNasabah[2]);
        }

        if(statusNasabahh.equals("Active")) {
            cb_statusNasabah.setValue(this.statusNasabah[0]);
        }else if(statusNasabahh.equals("Not Active")) {
            cb_statusNasabah.setValue(this.statusNasabah[1]);
        }
        tf_saldo.setText(saldo);
    }

    public void setFalse(){
        tf_idNasabah.setEditable(false);
        tf_username.setEditable(false);
        tf_password.setEditable(false);
        tf_nama.setEditable(false);
        tf_alamat.setEditable(false);
        tf_nomorHandphone.setEditable(false);
        tf_pin.setEditable(false);
        cb_tipeAkun.setDisable(true);
        ckb_verifikasi.setDisable(true);
        tf_idNasabah.setEditable(false);
        tf_saldo.setEditable(false);
        cb_statusNasabah.setDisable(true);
    }

    public void setTrue(){
        tf_username.setEditable(true);
        tf_password.setEditable(true);
        tf_nama.setEditable(true);
        tf_alamat.setEditable(true);
        tf_nomorHandphone.setEditable(true);
        tf_pin.setEditable(true);
        cb_tipeAkun.setDisable(false);
        ckb_verifikasi.setDisable(false);
        cb_statusNasabah.setDisable(false);
    }
}
