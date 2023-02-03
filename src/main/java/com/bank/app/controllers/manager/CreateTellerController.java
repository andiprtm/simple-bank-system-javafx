package com.bank.app.controllers.manager;

import com.bank.app.ConnectionManager;
import com.bank.app.models.Manager;
import com.bank.app.models.Teller;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
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
    Connection conn = ConnectionManager.getInstance().getConnection();
    Object[] tipeAkun = {"Teller", "Manager"};

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        cb_tipeAkun.getItems().addAll(tipeAkun);

        btn_tambahPegawai.setOnAction(event -> {
            System.out.println("btn_tambahPegawai ditekan");
            cekInput();
        });
    }

    public void cekInput(){
        if( (tf_username.getText().isEmpty() || tf_password.getText().isEmpty() || tf_nama.getText().isEmpty() || tf_alamat.getText().isEmpty() || tf_nomorHandphone.getText().isEmpty() || cb_tipeAkun.getValue() == null ) ){
            tv_alert.setVisible(true);
            tv_alert.setText("Mohon isi semua data");
        } else {
            if(findUser(tf_username.getText())){
                System.out.println("Username sudah ada");
            }else{
                addTellerAccount();
            }
        }
    }

    public void addTellerAccount(){
        String username = tf_username.getText();
        String password = tf_password.getText();
        String name = tf_nama.getText();
        String address = tf_alamat.getText();
        String phone = tf_nomorHandphone.getText();
        String accountType = cb_tipeAkun.getValue().toString();

        Teller teller = manager.createTellerAccount(accountType, username, password, name, address, phone);
        teller.authenticate();
        teller.getEmployeeData();

        if (teller.employeeId != null) {
            tv_alert.setVisible(true);
            tv_alert.setText("Berhasil membuat akun " + teller.accountType + " " + teller.name);
        }
    }

    public void setManager(Manager manager) {
        this.manager = manager;
        String[] name = manager.name.split(" ");

        tv_say_hi.setText("Hi, " + name[0]);
    }

    public Boolean findUser(String usernameInput){
        boolean isExist = false;
        String id = null;
        try {
            PreparedStatement ps = conn.prepareStatement(
                    "SELECT id_employee FROM employee_data ed WHERE ed.username=?;"
            );
            ps.setString(1, usernameInput);
            ResultSet rs = ps.executeQuery();

            while(rs.next()){
                System.out.println(rs.getString("id_employee"));
                id = rs.getString("id_employee");
            }
            System.out.println(id);

            if(id != null){
                tv_alert.setVisible(true);
                tv_alert.setText("Username sudah ada, silahkan gunakan username lain");
                isExist = true;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return isExist;
    }
}
