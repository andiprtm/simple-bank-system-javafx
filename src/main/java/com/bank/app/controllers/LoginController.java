package com.bank.app.controllers;

import com.bank.app.controllers.client.DashboardController;
import com.bank.app.models.Customer;
import com.bank.app.models.Model;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.net.URL;
import java.util.ResourceBundle;

public class LoginController implements Initializable {


    public Label label_pilih_tipe_user;
    public ComboBox<Object> dropdwon_tipe_user;
    public Label label_username;
    public TextField field_username;
    public Label label_password;
    public PasswordField field_password;
    public Label label_eror;
    public Button button_login;
    Object[] userType = {"Pegawai", "Nasabah"};


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        dropdwon_tipe_user.getItems().addAll(userType);
        dropdwon_tipe_user.setValue(userType[1]);

        label_username.setText("Username "+ dropdwon_tipe_user.getValue());
        label_password.setText("Password "+ dropdwon_tipe_user.getValue());

        dropdwon_tipe_user.setOnAction(event -> {
            label_username.setText("Username "+ dropdwon_tipe_user.getValue());
            label_password.setText("Password "+ dropdwon_tipe_user.getValue());
        });

        label_eror.setVisible(false);
        field_password.setOnAction(event -> login());
        button_login.setOnAction(event -> login());
    }


    public void login() {
        if (dropdwon_tipe_user.getValue().equals(userType[0])) {
            if (field_username.getText().equals("andi") && field_password.getText().equals("1234")) {
                label_eror.setVisible(false);
                System.out.println("Login Admin Berhasil");
            } else {
                label_eror.setVisible(true);
            }
        } else {
            if (!field_username.getText().equals("") && !field_password.getText().equals("")) {
                label_eror.setVisible(false);
                Stage stage = (Stage) button_login.getScene().getWindow();
                Customer customer = new Customer(field_username.getText(), field_password.getText());
                customer.authenticate();
                if (customer.getLocalCustomerId() != null) {
                    System.out.println(field_username.getText());
                    Model.getInstance().getViewFactory().closeStage(stage);
                    System.out.println("Login Client Berhasil");
                    Model.getInstance().getViewFactory().showClientWindow();
                } else {
                    label_eror.setVisible(true);
                }
            } else {
                label_eror.setVisible(true);
            }
        }
    }
}
