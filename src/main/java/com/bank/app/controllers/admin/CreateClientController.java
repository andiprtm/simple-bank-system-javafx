package com.bank.app.controllers.admin;

import com.bank.app.ConnectionManager;
import com.bank.app.models.Customer;
import com.bank.app.models.Teller;
import javafx.animation.FadeTransition;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.util.Duration;

import java.math.BigDecimal;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class CreateClientController extends AdminController implements Initializable {
    public Teller teller;
    public TextField tf_username;
    public TextField tf_password;
    public TextField tf_nama;
    public TextField tf_alamat;
    public TextField tf_nomorHandphone;
    public ComboBox<Object> cb_tipeAkun;
    public Button btn_tambahNasabah;
    public TextField tf_pin;
    public CheckBox ckb_verifikasi;
    public Label tv_say_hi;
    public Label tv_alert;
    public TextField tf_saldo_awal;
    Connection conn = ConnectionManager.getInstance().getConnection();
    private FadeTransition fadeTransition = new FadeTransition(Duration.millis(1000));

    Object[] tipeAkun = {"Silver", "Gold", "Platinum"};

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        cb_tipeAkun.getItems().addAll(tipeAkun);
        btn_tambahNasabah.setOnAction(event -> {
            System.out.println("btn_tambahNasabah ditekan");
            cekInput();
        });

    }

    public void cekInput(){
        fadeTransition.setNode(tv_alert);
        fadeTransition.setFromValue(0.0);
        fadeTransition.setToValue(1.0);
        fadeTransition.setCycleCount(1);
        fadeTransition.setAutoReverse(true);
        if ((tf_username.getText().isEmpty() || tf_password.getText().isEmpty() || tf_nama.getText().isEmpty() || tf_alamat.getText().isEmpty() || tf_nomorHandphone.getText().isEmpty() || tf_pin.getText().isEmpty() || cb_tipeAkun.getValue() == null )) {
            tv_alert.setVisible(true);
            fadeTransition.playFromStart();
            tv_alert.setText("Mohon isi semua data");
        } else {
            if(findUser(tf_username.getText())){
                System.out.println("Username sudah ada");
            }else{
                addCustomerAccount();
            }
        }
    }

    public void setNull(){
        tf_username.setText(null);
        tf_password.setText(null);
        tf_nama.setText(null);
        tf_alamat.setText(null);
        tf_nomorHandphone.setText(null);
        tf_pin.setText(null);
        tf_saldo_awal.setText(null);
        cb_tipeAkun.setValue(null);
        ckb_verifikasi.setSelected(false);
    }


    public void setTellerData(Teller teller) {
        this.teller = teller;
        String[] name = teller.name.split(" ");

        tv_say_hi.setText("Hi, " + name[0]);
    }

    public void addCustomerAccount() {
        fadeTransition.setNode(tv_alert);
        fadeTransition.setFromValue(0.0);
        fadeTransition.setToValue(1.0);
        fadeTransition.setCycleCount(1);
        fadeTransition.setAutoReverse(true);
        if (ckb_verifikasi.isSelected()) {
            try {
                String username = tf_username.getText();
                String password = tf_password.getText();
                String name = tf_nama.getText();
                String address = tf_alamat.getText();
                BigDecimal phone = new BigDecimal(tf_nomorHandphone.getText());
                String accountType = cb_tipeAkun.getValue().toString();
                BigDecimal saldo = new BigDecimal(tf_saldo_awal.getText());
                Integer pin = Integer.parseInt(tf_pin.getText());

                Customer customer = teller.createCustomerAccount(accountType, username, password, name, address, phone.toString(), saldo, pin);
                System.out.println(customer.name);
                tv_alert.setVisible(true);
                fadeTransition.playFromStart();
                tv_alert.setText("Nasabah " + customer.name + " berhasil ditambahkan!");
                setNull();
            } catch (NumberFormatException e) {
                tv_alert.setVisible(true);
                fadeTransition.playFromStart();
                tv_alert.setText("Input tidak sah!, Mohon cek kembali!\nNomor Handphone, Saldo Awal dan PIN harus berupa angka");
            }
        } else {
            tv_alert.setVisible(true);
            fadeTransition.playFromStart();
            tv_alert.setText("Silahkan checklist jika data sudah benar!");
        }
    }

    public Boolean findUser(String usernameInput){
        fadeTransition.setNode(tv_alert);
        fadeTransition.setFromValue(0.0);
        fadeTransition.setToValue(1.0);
        fadeTransition.setCycleCount(1);
        fadeTransition.setAutoReverse(true);
        boolean isExist = false;
        String id = null;
        try {
            PreparedStatement ps = conn.prepareStatement(
                    "SELECT id_customer FROM customer_data CD WHERE CD.username=?;"
            );
            ps.setString(1, usernameInput);
            ResultSet rs = ps.executeQuery();

            while(rs.next()){
                System.out.println(rs.getString("id_customer"));
                id = rs.getString("id_customer");
            }
            System.out.println(id);

            if(id != null){
                tv_alert.setVisible(true);
                fadeTransition.playFromStart();
                tv_alert.setText("Username sudah ada, silahkan gunakan username lain");
                isExist = true;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return isExist;
    }
}
