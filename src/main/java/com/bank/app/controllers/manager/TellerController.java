package com.bank.app.controllers.manager;

import com.bank.app.models.Model;
import com.bank.app.models.TellerModel;
import com.bank.app.views.TellerCellFactory;
import javafx.collections.FXCollections;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class TellerController implements Initializable {
    public ListView<TellerModel> listview_Teller;
    public String[] username2 = {"andi"};
    public TellerModel[] tellerModel = new TellerModel[username2.length];
    public Label tv_say_hi;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        setItem();
    }

    public void setItem(){
        for(int i = 0; i < username2.length; i++){
            //edit ini agar tidak hard code
            tellerModel[i] = new TellerModel("123","andi","andi1234","andi divangga","sidoarjo","01829120998","Manager","Active");
        }

        listview_Teller.setCellFactory(tellerListView -> new TellerCellFactory());

        listview_Teller.setItems(FXCollections.observableArrayList(tellerModel));

        listview_Teller.getSelectionModel().selectedItemProperty().addListener((observableValue, oldValue, newValue) -> {
            System.out.println(newValue.phoneProperty().getValue());
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/manager/DetailTeller.fxml"));
            Scene scene = null;
            try {
                scene = new Scene(loader.load());
            } catch (IOException e) {
                e.printStackTrace();
            }
            DetailTellerController detailTellerController = loader.getController();
            detailTellerController.setData(
                    newValue.idPegawaiProperty().getValue(),
                    newValue.usernameProperty().getValue(),
                    newValue.passwordProperty().getValue(),
                    newValue.namaProperty().getValue(),
                    newValue.addressProperty().getValue(),
                    newValue.phoneProperty().getValue(),
                    newValue.accountTypeProperty().getValue(),
                    newValue.statusProperty().getValue()
            );
            Stage stage2 = new Stage();
            stage2.setScene(scene);
            stage2.show();

            Stage stage = (Stage) listview_Teller.getScene().getWindow();
            stage.close();
        });
    }
}
