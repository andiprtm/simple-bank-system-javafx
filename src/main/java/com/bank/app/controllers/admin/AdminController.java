package com.bank.app.controllers.admin;

import com.bank.app.models.Model;
import javafx.fxml.Initializable;
import javafx.scene.layout.BorderPane;

import java.net.URL;
import java.util.ResourceBundle;

public class AdminController implements Initializable {
    public BorderPane admin_parent;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        Model.getInstance().getViewFactory().getAdminSelectedMenuItem().addListener((observableValue, oldValue, newValue) -> {
            if(newValue!= null) {
                switch (newValue) {
                    case "Deposit" -> admin_parent.setCenter(Model.getInstance().getViewFactory().getDepositView());
                    case "AddNasabah" -> admin_parent.setCenter(Model.getInstance().getViewFactory().getCreateClientView());
                    default -> admin_parent.setCenter(Model.getInstance().getViewFactory().getClientView());
                }
            }
        });
    }

    public void refreshListNasabah() {
        admin_parent.setCenter(Model.getInstance().getViewFactory().getClientView());
    }
}
