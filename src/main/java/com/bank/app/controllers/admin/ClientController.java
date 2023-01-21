package com.bank.app.controllers.admin;

import com.bank.app.models.ClientModel;
import com.bank.app.models.Model;
import com.bank.app.views.ClientCellFactory;
import javafx.collections.FXCollections;
import javafx.fxml.Initializable;
import javafx.scene.control.ListView;

import java.math.BigDecimal;
import java.net.URL;
import java.util.ResourceBundle;

public class ClientController implements Initializable {
    public String[] usernames = {"user1", "user2", "user3", "user4", "user5", "user6", "user7", "user8", "user9", "user10"};
    public String[] phones = {"08123456789", "08123456788", "08123456787", "08123456786", "08123456785", "08123456784", "08123456789=3", "08123456782", "08123456781", "08123456780"};
    public String[] addresses = {"Jl. Jalan1", "Jl. Jalan2", "Jl. Jalan3", "Jl. Jalan4", "Jl. Jalan5", "Jl. Jalan6", "Jl. Jalan7", "Jl. Jalan8", "Jl. Jalan9", "Jl. Jalan0"};
    public String[] statuses = {"Active", "Active", "Active", "Active", "Active", "Active", "Active", "Active", "Active", "Active"};
    public String[] accountTypes = {"Gold", "Silver","Platinum", "Gold", "Silver","Platinum", "Gold", "Silver","Platinum", "Gold"};
    public BigDecimal[] balances = {new BigDecimal(1000000), new BigDecimal(2000000), new BigDecimal(3000000), new BigDecimal(4000000), new BigDecimal(5000000), new BigDecimal(6000000), new BigDecimal(7000000), new BigDecimal(8000000), new BigDecimal(9000000), new BigDecimal(10000000)};
    public ListView<ClientModel> listview_Client;
    public ClientModel[] clientModels = new ClientModel[usernames.length];

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        for(int i = 0; i < usernames.length; i++){
            clientModels[i] = new ClientModel(usernames[i], phones[i], addresses[i], statuses[i], accountTypes[i], balances[i]);
        }

        listview_Client.setCellFactory(tellerListView -> new ClientCellFactory());

        listview_Client.setItems(FXCollections.observableArrayList(clientModels));

        listview_Client.getSelectionModel().selectedItemProperty().addListener((observableValue, oldValue, newValue) -> {
            System.out.println(newValue.phoneProperty().getValue());
            Model.getInstance().getViewFactory().showDetailTellerWindow();
        });
    }
}
