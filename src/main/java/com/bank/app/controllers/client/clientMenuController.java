package com.bank.app.controllers.client;

import com.bank.app.models.Model;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.stage.Stage;

import java.net.URL;
import java.util.ResourceBundle;

public class clientMenuController implements Initializable {
    public Button btn_dashboard;
    public Button btn_transaction;
    public Button btn_profle;
    public Button btn_logout;


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        addListener();

        btn_logout.setOnAction(event -> {
            System.out.println("Logout");
            Stage stage = (Stage) btn_logout.getScene().getWindow();
            Model.getInstance().getViewFactory().closeStage(stage);
            Model.getInstance().getViewFactory().showLoginWindow();
        });
    }

    private void addListener(){
        btn_dashboard.setOnAction(actionEvent -> onDashboard());
        btn_transaction.setOnAction(actionEvent -> onTransaction());
        btn_profle.setOnAction(actionEvent -> onProfile());
    }

    private void onDashboard(){
        Model.getInstance().getViewFactory().getClientSelectedMenuItem().setValue("Dashboard");
    }

    private void onTransaction(){
        Model.getInstance().getViewFactory().getClientSelectedMenuItem().setValue("Transaction");
    }

    private void onProfile(){
        Model.getInstance().getViewFactory().getClientSelectedMenuItem().setValue("Profile");
    }

}
