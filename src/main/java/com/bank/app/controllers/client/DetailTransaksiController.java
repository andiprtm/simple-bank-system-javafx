package com.bank.app.controllers.client;

import com.bank.app.controllers.utils.CurrencyController;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;

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

    public void setData(String idTransaction, String transactionType, String Time, String NamaPenerimaUsername, String NamaPengirimUsername, BigDecimal NominalTransaksi, Integer BiayaAdminPersen) {
        StringBuffer sbf = new StringBuffer(Time);
        sbf.deleteCharAt(sbf.length() - 1);
        sbf.deleteCharAt(sbf.length() - 1);
        tv_time.setText(sbf.toString());
        tv_nomor_transaksi.setText(": "+idTransaction);
        tv_tipe_transaksi.setText(": "+transactionType);
        tv_nama_penerima_username.setText(": "+NamaPenerimaUsername);
        tv_nama_pengirim_username.setText(": "+NamaPengirimUsername);
        tv_nominal_transaksi.setText(": "+new CurrencyController().getIndonesianCurrency(NominalTransaksi));
        tv_biaya_admin_persen.setText("Biaya Admin ("+BiayaAdminPersen+"%)");
        tv_biaya_admin.setText(": "+ new CurrencyController().getIndonesianCurrency(NominalTransaksi.multiply(new BigDecimal(BiayaAdminPersen)).divide(new BigDecimal(100))));
        tv_total_akhir.setText(": "+ new CurrencyController().getIndonesianCurrency(NominalTransaksi.add(NominalTransaksi.multiply(new BigDecimal(BiayaAdminPersen)).divide(new BigDecimal(100)))));


    }
}
