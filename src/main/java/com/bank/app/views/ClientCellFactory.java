package com.bank.app.views;

import com.bank.app.controllers.admin.ClientCellController;
import com.bank.app.models.ClientModel;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.ListCell;

public class ClientCellFactory extends ListCell<ClientModel> {
    @Override
    protected void updateItem(ClientModel clientModel, boolean empty) {
        super.updateItem(clientModel, empty);
        if (empty) {
            setGraphic(null);
            setText(null);
        } else {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/admin/ClientCell.fxml"));
            ClientCellController clientCellController = new ClientCellController(clientModel);
            loader.setController(clientCellController);
            setText(null);
            try {
                setGraphic(loader.load());
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}

