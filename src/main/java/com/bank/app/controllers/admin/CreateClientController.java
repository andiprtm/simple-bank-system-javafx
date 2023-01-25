package com.bank.app.controllers.admin;

import com.bank.app.models.Customer;
import com.bank.app.models.Teller;
import javafx.fxml.Initializable;
import javafx.scene.control.*;

import java.math.BigDecimal;
import java.net.URL;
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
        if ((tf_username.getText().isEmpty() || tf_password.getText().isEmpty() || tf_nama.getText().isEmpty() || tf_alamat.getText().isEmpty() || tf_nomorHandphone.getText().isEmpty() || tf_pin.getText().isEmpty() || cb_tipeAkun.getValue() == null )) {
            tv_alert.setVisible(true);
            tv_alert.setText("Mohon isi semua data");
        } else {
            addCustomerAccount();
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
        if (ckb_verifikasi.isSelected()) {
            try {
                String username = tf_username.getText();
                String password = tf_password.getText();
                String name = tf_nama.getText();
                String address = tf_alamat.getText();
                String phone = tf_nomorHandphone.getText();
                String accountType = cb_tipeAkun.getValue().toString();
                Integer pin = Integer.parseInt(tf_pin.getText());

                Customer customer = teller.createCustomerAccount(accountType, username, password, name, address, phone, new BigDecimal(50000), pin);
                System.out.println(customer.name);
                tv_alert.setVisible(true);
                tv_alert.setText("Nasabah " + customer.name + " berhasil ditambahkan!");
                setNull();
            } catch (NumberFormatException e) {
                tv_alert.setVisible(true);
                tv_alert.setText("Input tidak sah!");
            }
        } else {
            tv_alert.setVisible(true);
            tv_alert.setText("Silahkan checklist jika data sudah benar!");
        }
    }
}
