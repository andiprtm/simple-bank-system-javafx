package com.bank.app.controllers;

import com.bank.app.models.*;
import javafx.animation.FadeTransition;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.stage.Stage;
import javafx.util.Duration;

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

    public String statusCheck;

    public String[] statusPegawai = {"Teller","Manager"};

    private FadeTransition fadeTransition = new FadeTransition(Duration.millis(1000));


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {


        statusCheck = statusPegawai[1];

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
        //ANIMATION FADE IN
        fadeTransition.setNode(label_eror);
        fadeTransition.setFromValue(0.0);
        fadeTransition.setToValue(1.0);
        fadeTransition.setCycleCount(1);
        fadeTransition.setAutoReverse(true);

        //LOGIN
        String username = field_username.getText();
        String password = field_password.getText();
        label_eror.setText("Username atau Password Salah");

        if (dropdwon_tipe_user.getValue().equals(userType[0])) {
            if (!username.equals("") && !password.equals("")) {
                label_eror.setVisible(false);
                Employee employee = new Employee(username, password);
                employee.authenticate();
                if (employee.employeeId != null){
                    if(employee.isActive){
                        if (employee.accountType.equals(statusPegawai[0])) {
                            Teller teller = new Teller(username, password);
                            teller.authenticate();
                            teller.getEmployeeData();
                            Stage stage = (Stage) button_login.getScene().getWindow();
                            Model.getInstance().getViewFactory().closeStage(stage);
                            Model.getInstance().getViewFactory().setTellerData(teller);
                            Model.getInstance().getViewFactory().showAdminWindow();
                        } else if (employee.accountType.equals(statusPegawai[1])) {
                            Manager manager = new Manager(username, password);
                            manager.authenticate();
                            manager.getEmployeeData();
                            Stage stage = (Stage) button_login.getScene().getWindow();
                            Model.getInstance().getViewFactory().closeStage(stage);
                            Model.getInstance().getViewFactory().setManagerData(manager);
                            Model.getInstance().getViewFactory().showManagerWindow();
                        }
                    }else {
                        label_eror.setText("Akun Anda Telah di Non-aktifkan");
                        label_eror.setVisible(true);
                        fadeTransition.playFromStart();
                    }
                } else {
                    label_eror.setVisible(true);
                    fadeTransition.playFromStart();
                }
            } else {
                label_eror.setVisible(true);
                fadeTransition.playFromStart();
            }
        } else {
            if (!username.equals("") && !password.equals("")) {
                label_eror.setVisible(false);
                Stage stage = (Stage) button_login.getScene().getWindow();
                Customer customer = new Customer(username, password);
                customer.authenticate();
                if (customer.customerId != null) {
                    if(customer.isActive){
                        customer.getCustomerData();

                        Model.getInstance().getViewFactory().closeStage(stage);
                        System.out.println("Login Client Berhasil");

                        Model.getInstance().getViewFactory().setCustomerData(customer);
                        Model.getInstance().getViewFactory().showClientWindow();
                    }else {
                        label_eror.setText("Akun Anda Telah di Non-aktifkan");
                        label_eror.setVisible(true);
                        fadeTransition.playFromStart();
                    }

                } else {
                    label_eror.setVisible(true);
                    fadeTransition.playFromStart();
                }
            } else {
                label_eror.setVisible(true);
                fadeTransition.playFromStart();
            }
        }
    }
}
