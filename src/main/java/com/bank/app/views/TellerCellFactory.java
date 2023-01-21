package com.bank.app.views;

import com.bank.app.controllers.manager.TellerCellController;
import com.bank.app.models.TellerModel;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.ListCell;

public class TellerCellFactory extends ListCell<TellerModel> {

    @Override
    protected void updateItem(TellerModel tellerModel, boolean empty) {
        super.updateItem(tellerModel, empty);
        if (empty) {
            setGraphic(null);
            setText(null);
        } else {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/Manager/TellerCell.fxml"));
            TellerCellController tellerCellController = new TellerCellController(tellerModel);
            loader.setController(tellerCellController);
            setText(null);
            try {
                setGraphic(loader.load());
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
