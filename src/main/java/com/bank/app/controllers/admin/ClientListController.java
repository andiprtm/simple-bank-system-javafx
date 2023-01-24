package com.bank.app.controllers.admin;

import com.bank.app.ConnectionManager;
import com.bank.app.models.ClientModel;
import com.bank.app.models.Model;
import com.bank.app.models.Teller;
import com.bank.app.views.ClientCellFactory;
import javafx.collections.FXCollections;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.stage.Stage;

import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class ClientListController implements Initializable {
    public Teller teller;
    public Integer countRow;
    public ListView<ClientModel> listview_Client;

    public Label tv_say_hi;
    Connection conn = ConnectionManager.getInstance().getConnection();

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
    }

    public void setTellerData(Teller teller) {
        this.teller = teller;
        String[] name = teller.name.split(" ");

        tv_say_hi.setText("Hi, " + name[0]);
    }

    public void setCountRow() {
        try {
            PreparedStatement ps = conn.prepareStatement("""
                    SELECT COUNT(*) as total_rows FROM (
                        SELECT DISTINCT cd.id_customer, cd.name, cd.address, cd.phone, cd.username, cba.account_balance, IF(cba.customer_is_active, 'Active', 'Not Active') as status, cat.customer_account_type
                        FROM customer_bank_account cba, customer_account_type cat, customer_data cd
                        WHERE cat.id_customer_account_type=cba.customer_account_type_id AND cba.customer_id=cd.id_customer
                    ) AS total;""");

            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                this.countRow = rs.getInt("total_rows");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void setListCustomer() {
        try {
            PreparedStatement ps = conn.prepareStatement("""
                    SELECT DISTINCT cd.id_customer, cd.name, cd.address, cd.phone, cd.username, cba.account_balance, cba.account_pin, IF(cba.customer_is_active, 'Active', 'Not Active') as status, cat.customer_account_type
                    FROM customer_bank_account cba, customer_account_type cat, customer_data cd
                    WHERE cat.id_customer_account_type=cba.customer_account_type_id AND cba.customer_id=cd.id_customer;""");

            ResultSet rs = ps.executeQuery();

            ClientModel[] clientModels = new ClientModel[this.countRow];

            int i = 0;
            while (rs.next() && i < this.countRow) {
                clientModels[i] = new ClientModel(
                        rs.getString("id_customer"),
                        rs.getString("username"),
                        rs.getString("password"),
                        rs.getString("name"),
                        rs.getString("address"),
                        rs.getString("phone"),
                        rs.getString("account_pin"),
                        rs.getBigDecimal("customer_balance"),
                        rs.getString("customer_account_type"),
                        rs.getString("status")
                );
                i++;
            }

            listview_Client.setCellFactory(tellerListView -> new ClientCellFactory());

            listview_Client.setItems(FXCollections.observableArrayList(clientModels));

            listview_Client.getSelectionModel().selectedItemProperty().addListener((observableValue, oldValue, newValue) -> {
                //load halaman detail client atau update client
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/admin/DetailClient.fxml"));
                Scene scene = null;
                try {
                    scene = new Scene(loader.load());
                } catch (Exception e) {
                    e.printStackTrace();
                }
                DetailClientController detailClientController = loader.getController();
                detailClientController.setData(
                        newValue.idUserProperty().getValue(),
                        newValue.usernameProperty().getValue(),
                        newValue.passwordProperty().getValue(),
                        newValue.nameProperty().getValue(),
                        newValue.addressProperty().getValue(),
                        newValue.phoneProperty().getValue(),
                        newValue.pinProperty().getValue(),
                        newValue.balanceProperty().getValue().toString(),
                        newValue.accountTypeProperty().getValue(),
                        newValue.statusProperty().getValue()
                );

                // close stage list client
                Stage stage = (Stage) listview_Client.getScene().getWindow();
                stage.close();
            });
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
