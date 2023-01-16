package com.bank.app.controllers.client;

import com.bank.app.models.Model;
import javafx.fxml.Initializable;
import javafx.scene.layout.BorderPane;

import java.net.URL;
import java.util.ResourceBundle;

public class ClientController implements Initializable {
    public BorderPane client_parent;
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        Model.getInstance().getViewFactory().getClientSelectedMenuItem().addListener((observableValue, oldValue, newValue) -> {
            if (newValue != null) {
                switch (newValue) {
                    case "Transaction" -> client_parent.setCenter(Model.getInstance().getViewFactory().getTransactionView());
                    case "Profile" -> client_parent.setCenter(Model.getInstance().getViewFactory().getProfileView());
                    default -> client_parent.setCenter(Model.getInstance().getViewFactory().getDashboardView());
                }
            }
        });
    }
}
