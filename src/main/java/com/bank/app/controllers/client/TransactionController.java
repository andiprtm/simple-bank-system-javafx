package com.bank.app.controllers.client;

import com.bank.app.models.Model;
import com.bank.app.models.TransactionModel;
import com.bank.app.views.TransactionCellFactory;
import javafx.collections.FXCollections;
import javafx.fxml.Initializable;
import javafx.scene.control.ListView;

import java.math.BigDecimal;
import java.net.URL;
import java.time.LocalDate;
import java.util.ResourceBundle;

public class TransactionController implements Initializable {
    public ListView<TransactionModel> listview_transaksi;

    /*
    * Dummy Data
    * */
    public String[] sender = {"andi", "budi", "caca", "didi", "efi", "fifi", "gigi", "haha", "ihi", "jaja", "kiki", "lili", "mimi", "nini", "opo", "pupu", "qiqi", "riri", "sisi", "titi", "uwi", "vivi", "wawa", "xixi", "yaya", "zizi"};
    public String[] receiver = {"budi", "caca", "didi", "efi", "fifi", "gigi", "haha", "ihi", "jaja", "kiki", "lili", "mimi", "nini", "opo", "pupu", "qiqi", "riri", "sisi", "titi", "uwi", "vivi", "wawa", "xixi", "yaya", "zizi", "andi"};
    public BigDecimal[] amount = {new BigDecimal(100000), new BigDecimal(200000), new BigDecimal(300000), new BigDecimal(400000), new BigDecimal(500000), new BigDecimal(600000), new BigDecimal(700000), new BigDecimal(800000), new BigDecimal(900000), new BigDecimal(1000000), new BigDecimal(1100000), new BigDecimal(1200000), new BigDecimal(1300000), new BigDecimal(1400000), new BigDecimal(1500000), new BigDecimal(1600000), new BigDecimal(1700000), new BigDecimal(1800000), new BigDecimal(1900000), new BigDecimal(2000000), new BigDecimal(2100000), new BigDecimal(2200000), new BigDecimal(2300000), new BigDecimal(2400000), new BigDecimal(2500000), new BigDecimal(2600000)};
    public LocalDate[] date = {LocalDate.of(2021, 1, 1), LocalDate.of(2021, 1, 2), LocalDate.of(2021, 1, 3), LocalDate.of(2021, 1, 4), LocalDate.of(2021, 1, 5), LocalDate.of(2021, 1, 6), LocalDate.of(2021, 1, 7), LocalDate.of(2021, 1, 8), LocalDate.of(2021, 1, 9), LocalDate.of(2021, 1, 10), LocalDate.of(2021, 1, 11), LocalDate.of(2021, 1, 12), LocalDate.of(2021, 1, 13), LocalDate.of(2021, 1, 14), LocalDate.of(2021, 1, 15), LocalDate.of(2021, 1, 16), LocalDate.of(2021, 1, 17), LocalDate.of(2021, 1, 18), LocalDate.of(2021, 1, 19), LocalDate.of(2021, 1, 20), LocalDate.of(2021, 1, 21), LocalDate.of(2021, 1, 22), LocalDate.of(2021, 1, 23), LocalDate.of(2021, 1, 24), LocalDate.of(2021, 1, 25), LocalDate.of(2021, 1, 26)};

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
}
