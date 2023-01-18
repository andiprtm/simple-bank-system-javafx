package com.bank.app.views;

import com.bank.app.controllers.admin.AdminController;
import com.bank.app.controllers.client.ClientController;
import com.bank.app.controllers.client.DashboardController;
import javafx.application.Platform;
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
    public String username;

    //Admin Views
    private final StringProperty adminSelectedMenuItem;
    private AnchorPane DepositView;
    private AnchorPane ClientView;
    private AnchorPane CreateClientView;

    public void setUsername(String username) {
        this.username = username;
        System.out.println(this.username);
    }

    public ViewFactory() {
        this.clientSelectedMenuItem = new SimpleStringProperty("");
        this.adminSelectedMenuItem = new SimpleStringProperty("");
    }

    /*
    * Client View Section
    * */
    public StringProperty getClientSelectedMenuItem() {
        return clientSelectedMenuItem;
    }

    public AnchorPane getDashboardView() {
        //if(dashboardView == null) {
            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/client/Dashboard.fxml"));
                dashboardView = loader.load();

                DashboardController controller = loader.getController();
                controller.setWelcomeText(this.username);
                System.out.println("Welcome " + this.username);
            } catch (IOException e) {
                e.printStackTrace();
            }
        //}
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
        System.out.println("ini dipanggil : " + this.username);
        Stage stage = new Stage();
        stage.setScene(scene);
        stage.setTitle("Dashboard Client");
        stage.show();
        clientController = fxmlLoader.getController();
        clientController.refreshDashboard();
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
        stage.setOnCloseRequest(e -> {
            Platform.exit();
        });
        stage.close();
    }

    //Admin (Teller & Manager) Views Section
    public StringProperty getAdminSelectedMenuItem() {
        return adminSelectedMenuItem;
    }

    public AnchorPane getDepositView() {
        if(DepositView == null) {
            try {
                DepositView = new FXMLLoader(getClass().getResource("/fxml/admin/Deposit.fxml")).load();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return DepositView;
    }

    public AnchorPane getClientView(){
        if(ClientView == null) {
            try {
                ClientView = new FXMLLoader(getClass().getResource("/fxml/admin/Clients.fxml")).load();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return ClientView;
    }

    public AnchorPane getCreateClientView(){
        if(CreateClientView == null) {
            try {
                CreateClientView = new FXMLLoader(getClass().getResource("/fxml/admin/CreateClient.fxml")).load();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return CreateClientView;
    }

    public void showAdminWindow(){
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/fxml/admin/admin.fxml"));
        AdminController adminController = new AdminController();
        fxmlLoader.setController(adminController);
        Scene scene = null;
        try {
            scene = new Scene(fxmlLoader.load());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        Stage stage = new Stage();
        stage.setScene(scene);
        stage.setTitle("Dashboard Admin");
        stage.show();
    }
}
