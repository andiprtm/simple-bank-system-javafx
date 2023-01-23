package com.bank.app.controllers.admin;

import com.bank.app.models.Model;
import com.bank.app.models.Teller;
import javafx.fxml.Initializable;
import javafx.scene.layout.BorderPane;

import java.net.URL;
import java.util.ResourceBundle;

public class AdminController implements Initializable {
    public BorderPane admin_parent;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        Model.getInstance().getViewFactory().getAdminSelectedMenuItem().addListener((observableValue, oldValue, newValue) -> {
            switch (newValue) {
                case "Nasabah" -> admin_parent.setCenter(Model.getInstance().getViewFactory().getClientView());
                case "Deposit" -> admin_parent.setCenter(Model.getInstance().getViewFactory().getDepositView());
                default -> admin_parent.setCenter(Model.getInstance().getViewFactory().getCreateClientView());
            }
        });
    }

    public void refreshCreateClientView() {
        admin_parent.setCenter(Model.getInstance().getViewFactory().getCreateClientView());
    }
}
