package com.bank.app.views;

import com.bank.app.controllers.client.ClientController;
import com.bank.app.controllers.client.DashboardController;
import com.bank.app.models.Model;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.IOException;

public class ViewFactory {
    //Client Views
    private final StringProperty clientSelectedMenuItem;
    private AnchorPane dashboardView;
    private AnchorPane transactionView;
    private AnchorPane profileView;
    private String username;
    public void setUsername(String username) {
        this.username = username;
    }

    public ViewFactory() {
        this.clientSelectedMenuItem = new SimpleStringProperty("");
    }

    public StringProperty getClientSelectedMenuItem() {
        return clientSelectedMenuItem;
    }

    public AnchorPane getDashboardView() {
        if(dashboardView == null) {
            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/client/Dashboard.fxml"));
                dashboardView = loader.load();

                DashboardController controller = loader.getController();
                controller.setWelcomeText(this.username);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return dashboardView;
    }

    public AnchorPane getTransactionView() {
        if(transactionView == null) {
            try {
                transactionView = new FXMLLoader(getClass().getResource("/fxml/client/Transaction.fxml")).load();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return transactionView;
    }

    public AnchorPane getProfileView() {
        if(profileView == null) {
            try {
                profileView = new FXMLLoader(getClass().getResource("/fxml/client/Profile.fxml")).load();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return profileView;
    }

    public void showLoginWindow(){
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/fxml/Login.fxml"));
        Scene scene = null;
        try {
            scene = new Scene(fxmlLoader.load(),720,403);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        Stage stage = new Stage();
        stage.setScene(scene);
        stage.setTitle("Halaman Login");
        stage.show();
    }

    public void showClientWindow(){
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/fxml/client/client.fxml"));
        ClientController clientController = new ClientController();
        fxmlLoader.setController(clientController);
        Scene scene = null;
        try {
            scene = new Scene(fxmlLoader.load());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        Stage stage = new Stage();
        stage.setScene(scene);
        stage.setTitle("Dashboard Client");
        stage.show();
        Model.getInstance().getViewFactory().getClientSelectedMenuItem().setValue("Dashboard");
    }

    public void showTransferWindow(){
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/fxml/client/Transfer.fxml"));
        Scene scene = null;
        try {
            scene = new Scene(fxmlLoader.load());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        Stage stage = new Stage();
        stage.setScene(scene);
        stage.setTitle("Transfer");
        stage.show();
    }

    public void showWithdrawWindow(){
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/fxml/client/Withdraw.fxml"));
        Scene scene = null;
        try {
            scene = new Scene(fxmlLoader.load());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        Stage stage = new Stage();
        stage.setScene(scene);
        stage.setTitle("Withdraw");
        stage.show();
    }

    public void closeStage(Stage stage){
        stage.close();
    }
}
