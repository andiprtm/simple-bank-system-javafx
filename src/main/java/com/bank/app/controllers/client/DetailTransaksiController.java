package com.bank.app.controllers.client;

import javafx.fxml.Initializable;
import javafx.print.PrinterJob;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.stage.Stage;

import java.math.BigDecimal;
import java.net.URL;
import java.util.ResourceBundle;

public class DetailTransaksiController implements Initializable {
    public Label tv_time;
    public Label tv_nomor_transaksi;
    public Label tv_tipe_transaksi;
    public Label tv_nama_penerima_username;
    public Label tv_nama_pengirim_username;
    public Label tv_nominal_transaksi;
    public Label tv_biaya_admin;
    public Label tv_total_akhir;
    public Label tv_biaya_admin_persen;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }

    public void setData(String Time, String NamaPenerimaUsername, String NamaPengirimUsername, BigDecimal NominalTransaksi, BigDecimal BiayaAdmin) {
        StringBuffer sbf = new StringBuffer(Time);
        sbf.deleteCharAt(sbf.length() - 1);
        sbf.deleteCharAt(sbf.length() - 1);
        tv_time.setText(sbf.toString());
        tv_nama_penerima_username.setText(": "+NamaPenerimaUsername);
        tv_nama_pengirim_username.setText(": "+NamaPengirimUsername);
        tv_nominal_transaksi.setText(": "+NominalTransaksi);
        tv_biaya_admin_persen.setText("Biaya Admin x% (hardcode)");
        tv_biaya_admin.setText(": "+BiayaAdmin+" (hard code)");
        tv_total_akhir.setText(": "+ (NominalTransaksi.subtract(BiayaAdmin)));


    }
}
