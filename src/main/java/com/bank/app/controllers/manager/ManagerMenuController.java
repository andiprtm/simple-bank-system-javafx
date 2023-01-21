package com.bank.app.controllers.manager;

import com.bank.app.models.Model;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.stage.Stage;

import java.net.URL;
import java.util.ResourceBundle;

public class ManagerMenuController implements Initializable {
    public Button btn_buatTeller;
    public Button btn_teller;
    public Button btn_logout;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        addListener();
        btn_logout.setOnAction(event -> {
            System.out.println("Logout");
            Model.getInstance().getViewFactory().closeStage((Stage) btn_logout.getScene().getWindow());
            Model.getInstance().getViewFactory().showLoginWindow();
        });
    }

    private void addListener(){
        btn_buatTeller.setOnAction(actionEvent -> onBuatTeller());
        btn_teller.setOnAction(actionEvent -> onTeller());
    }

    private void onBuatTeller(){
        Model.getInstance().getViewFactory().getManagerSelectedMenuItem().setValue("Buat Teller");
    }

    private void onTeller(){
        Model.getInstance().getViewFactory().getManagerSelectedMenuItem().setValue("Teller");
    }
}
