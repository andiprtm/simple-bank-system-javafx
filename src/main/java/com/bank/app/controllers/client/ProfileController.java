package com.bank.app.controllers.client;

import com.bank.app.models.Customer;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;

import java.net.URL;
import java.util.ResourceBundle;

public class ProfileController implements Initializable {
    public Customer customer;
    public Label tv_username;
    public Label tv_name;
    public Label tv_address;
    public Label tv_nomorhp;
    public Label tv_tipeakun;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }

    public void setCustomer (Customer customer) {
        this.customer = customer;
        tv_name.setText(customer.name);
        tv_address.setText(customer.address);
        tv_nomorhp.setText(customer.phone);
        tv_tipeakun.setText(customer.accountType);
        tv_username.setText(customer.username);
    }
}
