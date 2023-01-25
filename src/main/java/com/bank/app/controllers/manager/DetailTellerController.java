package com.bank.app.controllers.manager;

import com.bank.app.models.Model;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;

import java.net.URL;
import java.util.ResourceBundle;

public class DetailTellerController implements Initializable {
    public Button btn_Cancel;
    String[] tipeNasabah = {"Teller", "Manager"};
    String[] statusNasabah = {"Active", "Not Active"};
    public Label tv_say_hi;
    public Label tv_title_page;
    public Label tv_alert;
    public TextField tf_idPegawai;
    public TextField tf_username;
    public TextField tf_password;
    public TextField tf_nama;
    public TextField tf_alamat;
    public TextField tf_nomorHandphone;
    public ComboBox<String> cb_tipeAkun;
    public ComboBox<String> cb_statusPegawai;
    public CheckBox ckb_verifikasi;
    public HBox box_btn_to_edit;
    public Button btn_kembaliKeList;
    public Button btn_editPegawai;
    public HBox box_when_edit_click;
    public Button btn_updatePegawai;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        // SET VALUE TIPE AKUN NASABAH
        cb_tipeAkun.getItems().addAll(tipeNasabah);
        cb_tipeAkun.setValue(tipeNasabah[0]);

        // SET VALUE STATUS NASABAH
        cb_statusPegawai.getItems().addAll(statusNasabah);
        cb_statusPegawai.setValue(statusNasabah[0]);

        // SET JUDUL HALAMAN DAN HALAMAN DETAIL NASABAH
        tv_title_page.setText("DETAIL PEGAWAI");
        setFalse();

        // BUTTON EDIT TELLER DITEKAN
        btn_editPegawai.setOnAction(actionEvent -> {
            box_btn_to_edit.setVisible(false);
            box_when_edit_click.setVisible(true);
            tv_title_page.setText("UPDATE PEGAWAI");
            setTrue();

//            // kondisi jika sesama manager tidak bisa menghapus akun manager lain
//            if(isMyTypeManager() && cb_tipeAkun.getValue().equals("Manager")){
//                cb_statusPegawai.setDisable(true);
//            }else{
//                cb_statusPegawai.setDisable(false);
//            }
        });

        // BUTTON UPDATE NASABAH DITEKAN
        btn_updatePegawai.setOnAction(actionEvent -> {
            tv_alert.setVisible(false);
            cekInput();
        });

        // BUTTON KEMBALI KE LIST NASABAH DITEKAN
        btn_kembaliKeList.setOnAction(actionEvent -> {
            toList();
        });

        // BUTTON CANCEL DITEKAN
        btn_Cancel.setOnAction(actionEvent -> {
            toList();
        });

        // DOUBLE SET UNTUK CADANGAN
        if(box_when_edit_click.isVisible()) {
            setTrue();
        }else{
            setFalse();
        }
    }

    public void toList(){
        Stage stage = (Stage) btn_kembaliKeList.getScene().getWindow();
        Model.getInstance().getViewFactory().closeStage(stage);
        Model.getInstance().getViewFactory().showManagerWindow();
    }

    public void setData(String idPegawai, String username, String password, String nama, String alamat, String nomorHandphone, String tipeAkun, String statusPegawai){
        tf_idPegawai.setText(idPegawai);
        tf_username.setText(username);
        tf_password.setText(password);
        tf_nama.setText(nama);
        tf_alamat.setText(alamat);
        tf_nomorHandphone.setText(nomorHandphone);
        cb_tipeAkun.setValue(tipeAkun);
        cb_statusPegawai.setValue(statusPegawai);

    }

    public Boolean isMyTypeManager(){
        //get my tipe akun pewagai in here
        Boolean isMyTypeManager = true;
        return isMyTypeManager;
    }

    public void setFalse(){
        tf_idPegawai.setDisable(true);
        tf_username.setDisable(true);
        tf_password.setDisable(true);
        tf_nama.setDisable(true);
        tf_alamat.setDisable(true);
        tf_nomorHandphone.setDisable(true);
        cb_tipeAkun.setDisable(true);
        cb_statusPegawai.setDisable(true);
        ckb_verifikasi.setDisable(true);
    }

    public void setTrue(){
        tf_username.setDisable(false);
        tf_password.setDisable(false);
        tf_nama.setDisable(false);
        tf_alamat.setDisable(false);
        tf_nomorHandphone.setDisable(false);
        ckb_verifikasi.setDisable(false);
        cb_statusPegawai.setDisable(false);
    }

    public void cekInput(){
        if( tf_idPegawai.getText().isEmpty() || tf_username.getText().isEmpty() || tf_password.getText().isEmpty() || tf_nama.getText().isEmpty() || tf_alamat.getText().isEmpty() || tf_nomorHandphone.getText().isEmpty() || cb_tipeAkun.getValue().isEmpty() || cb_statusPegawai.getValue().isEmpty()){
            tv_alert.setVisible(true);
            tv_alert.setText("Mohon isi semua data");
        }else{
            if(!ckb_verifikasi.isSelected()){
                tv_alert.setVisible(true);
                tv_alert.setText("Mohon centang verifikasi");
            }else{
                /*
                * update data to database in here
                * */

                // view
                box_btn_to_edit.setVisible(true);
                box_when_edit_click.setVisible(false);
                tv_title_page.setText("DETAIL PEGAWAI");
                setFalse();

            }
        }
    }
}
