package com.bank.app.views;

import com.bank.app.controllers.admin.AdminController;
import com.bank.app.controllers.admin.ClientListController;
import com.bank.app.controllers.admin.CreateClientController;
import com.bank.app.controllers.admin.DepositController;
import com.bank.app.controllers.client.*;
import com.bank.app.models.Customer;
import com.bank.app.controllers.manager.ManagerController;
import com.bank.app.models.Manager;
import com.bank.app.models.Teller;
import javafx.application.Platform;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.IOException;

public class ViewFactory {
    //Client Data
    public Customer customer;
    public Teller teller;
    public Manager manager;

    //Client Views
    private final StringProperty clientSelectedMenuItem;
    private AnchorPane dashboardView;
    private AnchorPane transactionView;
    private AnchorPane profileView;

    //Admin Views
    private final StringProperty adminSelectedMenuItem;
    private AnchorPane DepositView;
    private AnchorPane ClientView;
    private AnchorPane CreateClientView;

    //Manager Views
    private final StringProperty managerSelectedMenuItem;
    private AnchorPane CreateTellerView;
    private AnchorPane TellerView;

    /*
    * initialization and setter
    * */

    public void setCustomerData(Customer customer) {
        this.customer = customer;
    }

    public void setTellerData(Teller teller) {
        this.teller = teller;
    }

    public void setManagerData(Manager manager) {
        this.manager = manager;
    }

    public ViewFactory() {
        this.clientSelectedMenuItem = new SimpleStringProperty("");
        this.adminSelectedMenuItem = new SimpleStringProperty("");
        this.managerSelectedMenuItem = new SimpleStringProperty(" ");
    }

    /*
    * Client View Section
    * */

    public StringProperty getClientSelectedMenuItem() {
        return clientSelectedMenuItem;
    }

    public AnchorPane getDashboardView() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/client/Dashboard.fxml"));
            dashboardView = loader.load();
            DashboardController controller = loader.getController();
            controller.setCustomer(customer);
            controller.setSaldoAkhir();
            controller.setSummary();
            controller.setTransactionList();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return dashboardView;
    }

    public AnchorPane getTransactionView() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/client/Transaction.fxml"));
            transactionView = loader.load();

            TransactionController controller = loader.getController();
            controller.setCustomer(customer);
            controller.setCountRow();
            controller.setTransactionList();
        } catch (IOException e) {
            e.printStackTrace();
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
        clientController.refreshDashboard();
    }

    public void showTransferWindow(){
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/fxml/client/Transfer.fxml"));
        Scene scene = null;
        try {
            scene = new Scene(fxmlLoader.load());
            TransferController transferController = fxmlLoader.getController();
            transferController.setCustomer(this.customer);
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
            WithdrawController withdrawController = fxmlLoader.getController();
            withdrawController.setCustomer(customer);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        Stage stage = new Stage();
        stage.setScene(scene);
        stage.setTitle("Withdraw");
        stage.show();
    }

    public void showDetailTransactionWindow(){
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/client/DetailTransaksi.fxml"));
        Scene scene = null;
        try{
            scene = new Scene(loader.load());
        } catch (IOException e){
            throw new RuntimeException(e);
        }
        Stage stage = new Stage();
        stage.setScene(scene);
        stage.setTitle("Detail Transaksi");
        stage.show();
    }

    /*
    * Admin (Teller) Views Section
    * */
    public StringProperty getAdminSelectedMenuItem() {
        return adminSelectedMenuItem;
    }

    public AnchorPane getDepositView() {
        if(DepositView == null) {
            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/admin/Deposit.fxml"));
                DepositView = loader.load();
                DepositController controller = loader.getController();
                controller.setTellerData(teller);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return DepositView;
    }

    public AnchorPane getClientView(){
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/admin/Clients.fxml"));
            ClientView = loader.load();
            ClientListController controller = loader.getController();
            controller.setTellerData(teller);
            controller.setCountRow();
            controller.setListCustomer();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return ClientView;
    }

    public AnchorPane getCreateClientView(){
        if(CreateClientView == null) {
            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/admin/CreateClient.fxml"));
                CreateClientView = loader.load();

                CreateClientController createClientController = loader.getController();
                createClientController.setTellerData(teller);
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
        adminController.refreshCreateClientView();
    }

    public void showDetailClientWindow(){
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/fxml/admin/DetailClient.fxml"));
        Scene scene = null;
        try {
            scene = new Scene(fxmlLoader.load());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        Stage stage = new Stage();
        stage.setScene(scene);
        stage.setTitle("Detail Client");
        stage.show();
    }

    /*
    * Manager Views Section
    * */

    public StringProperty getManagerSelectedMenuItem(){
        return managerSelectedMenuItem;
    }

    public AnchorPane getCreateTellerView(){
        if(CreateTellerView == null) {
            try {
                CreateTellerView = new FXMLLoader(getClass().getResource("/fxml/manager/CreateTeller.fxml")).load();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return CreateTellerView;
    }

    public AnchorPane getTellerView(){
            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/manager/Tellers.fxml"));
                TellerView = loader.load();
            } catch (IOException e) {
                e.printStackTrace();
            }
        return TellerView;
    }

    public void showManagerWindow(){
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/fxml/manager/Manager.fxml"));
        ManagerController managerController = new ManagerController();
        fxmlLoader.setController(managerController);
        Scene scene = null;
        try {
            scene = new Scene(fxmlLoader.load());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        Stage stage = new Stage();
        stage.setScene(scene);
        stage.setTitle("Dashboard Manager");
        stage.show();
    }

    public void showDetailTellerWindow(){
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/fxml/manager/DetailTeller.fxml"));
        Scene scene = null;
        try {
            scene = new Scene(fxmlLoader.load());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        Stage stage = new Stage();
        stage.setScene(scene);
        stage.setTitle("Detail Teller");
        stage.show();
    }

    /*
    * Close Stage
    * */

    public void closeStage(Stage stage){
        stage.setOnCloseRequest(e -> {
            Platform.exit();
        });
        stage.close();
    }
}
