package com.bank.app.controllers.client;

import com.bank.app.models.Model;
import com.bank.app.models.TransactionModel;
import com.bank.app.views.TransactionCellFactory;
import javafx.collections.FXCollections;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;

import java.math.BigDecimal;
import java.net.URL;
import java.sql.Timestamp;
import java.util.ResourceBundle;

public class TransactionController implements Initializable {
    public ListView<TransactionModel> listview_transaksi;

    /*
    * Dummy Data
    * */
    public String[] sender ;
    public String[] receiver ;
    public BigDecimal[] amount ;
    public Timestamp[] date;
    public Label tv_say_hi;

    TransactionModel[] transactionModels = new TransactionModel[sender.length];

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        for(int i = 0; i < sender.length; i++){
            transactionModels[i] = new TransactionModel(sender[i], receiver[i], amount[i], date[i]);
        }

        listview_transaksi.setCellFactory(tellerListView -> new TransactionCellFactory());

        listview_transaksi.setItems(FXCollections.observableArrayList(transactionModels));

        listview_transaksi.getSelectionModel().selectedItemProperty().addListener((observableValue, oldValue, newValue) -> {
            System.out.println(newValue.amountProperty().getValue());
            Model.getInstance().getViewFactory().showDetailTransactionWindow();
        });
    }

    public void setWelcomeText(String Username){
        System.out.println("wc text" + Username);
        tv_say_hi.setText(Username);
    }
}
