package com.bank.app.controllers.manager;

import com.bank.app.models.Model;
import javafx.fxml.Initializable;
import javafx.scene.layout.BorderPane;

import java.net.URL;
import java.util.ResourceBundle;

public class ManagerController implements Initializable {
    public BorderPane manager_parent;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        Model.getInstance().getViewFactory().getManagerSelectedMenuItem().addListener((observableValue, oldValue, newValue) -> {
            switch (newValue) {
                case "Teller" -> manager_parent.setCenter(Model.getInstance().getViewFactory().getTellerView());
                default -> manager_parent.setCenter(Model.getInstance().getViewFactory().getCreateTellerView());
            }
        });

    }
}
