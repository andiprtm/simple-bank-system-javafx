package com.bank.app.views;

import com.bank.app.controllers.client.TransactionCellController;
import com.bank.app.models.TransactionModel;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.ListCell;

public class TransactionCellFactory extends ListCell<TransactionModel> {
    @Override
    protected void updateItem(TransactionModel transactionModel, boolean empty) {
        super.updateItem(transactionModel, empty);
        if (empty) {
            setGraphic(null);
            setText(null);
        } else {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/Client/TransactionCell.fxml"));
            TransactionCellController transactionCellController = new TransactionCellController(transactionModel);
            loader.setController(transactionCellController);
            setText(null);
            try {
                setGraphic(loader.load());
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
