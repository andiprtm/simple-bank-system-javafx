package com.bank.app.controllers.client;

import com.bank.app.ConnectionManager;
import com.bank.app.controllers.utils.CurrencyController;
import com.bank.app.models.Customer;
import com.bank.app.models.Model;
import com.bank.app.models.Transaction;
import com.bank.app.models.TransactionModel;
import com.bank.app.views.TransactionCellFactory;
import javafx.collections.FXCollections;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.stage.Stage;

import java.math.BigDecimal;
import java.net.URL;
import java.security.PublicKey;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ResourceBundle;

public class DashboardController implements Initializable {
    Customer customer;
    public Label tv_say_hi;
    public Label tv_saldo_akhir;
    public Label tv_value_deposit;
    public Label tv_transfer_masuk;
    public Label tv_transfer_keluar;
    public Label tv_tarik_tunai;
    public Button btn_transfer;
    public Button btn_tarik_tunai;
    public ListView<TransactionModel> transaction_listview;
    public String[] sender = {"andi", "budi", "caca", "didi", "efi", "fifi", "gigi", "haha", "ihi", "jaja", "kiki", "lili", "mimi", "nini", "opo", "pupu", "qiqi", "riri", "sisi", "titi", "uwi", "vivi", "wawa", "xixi", "yaya", "zizi"};
    public String[] receiver = {"budi", "caca", "didi", "efi", "fifi", "gigi", "haha", "ihi", "jaja", "kiki", "lili", "mimi", "nini", "opo", "pupu", "qiqi", "riri", "sisi", "titi", "uwi", "vivi", "wawa", "xixi", "yaya", "zizi", "andi"};
    public BigDecimal[] amount = {new BigDecimal(100000), new BigDecimal(200000), new BigDecimal(300000), new BigDecimal(400000), new BigDecimal(500000), new BigDecimal(600000), new BigDecimal(700000), new BigDecimal(800000), new BigDecimal(900000), new BigDecimal(1000000), new BigDecimal(1100000), new BigDecimal(1200000), new BigDecimal(1300000), new BigDecimal(1400000), new BigDecimal(1500000), new BigDecimal(1600000), new BigDecimal(1700000), new BigDecimal(1800000), new BigDecimal(1900000), new BigDecimal(2000000), new BigDecimal(2100000), new BigDecimal(2200000), new BigDecimal(2300000), new BigDecimal(2400000), new BigDecimal(2500000), new BigDecimal(2600000)};
    public LocalDate[] date = {LocalDate.of(2021, 1, 1), LocalDate.of(2021, 1, 2), LocalDate.of(2021, 1, 3), LocalDate.of(2021, 1, 4), LocalDate.of(2021, 1, 5), LocalDate.of(2021, 1, 6), LocalDate.of(2021, 1, 7), LocalDate.of(2021, 1, 8), LocalDate.of(2021, 1, 9), LocalDate.of(2021, 1, 10), LocalDate.of(2021, 1, 11), LocalDate.of(2021, 1, 12), LocalDate.of(2021, 1, 13), LocalDate.of(2021, 1, 14), LocalDate.of(2021, 1, 15), LocalDate.of(2021, 1, 16), LocalDate.of(2021, 1, 17), LocalDate.of(2021, 1, 18), LocalDate.of(2021, 1, 19), LocalDate.of(2021, 1, 20), LocalDate.of(2021, 1, 21), LocalDate.of(2021, 1, 22), LocalDate.of(2021, 1, 23), LocalDate.of(2021, 1, 24), LocalDate.of(2021, 1, 25), LocalDate.of(2021, 1, 26)};
    Connection conn = ConnectionManager.getInstance().getConnection();

    TransactionModel[] transactionModels = new TransactionModel[5];
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        System.out.println("Initialize method");
        for(int i = 0; i < 5; i++){
            transactionModels[i] = new TransactionModel(sender[i], receiver[i], amount[i], date[i]);
        }

        transaction_listview.setCellFactory(tellerListView -> new TransactionCellFactory());

        transaction_listview.setItems(FXCollections.observableArrayList(transactionModels));

        transaction_listview.getSelectionModel().selectedItemProperty().addListener((observableValue, oldValue, newValue) -> {
            System.out.println(newValue.amountProperty().getValue());
        });

        btn_tarik_tunai.setOnAction(event -> {
            System.out.println("Tarik Tunai");
            Stage stage = (Stage) btn_tarik_tunai.getScene().getWindow();
            Model.getInstance().getViewFactory().closeStage(stage);
            Model.getInstance().getViewFactory().showWithdrawWindow();
        });

        btn_transfer.setOnAction(event -> {
            System.out.println("Transfer");
            Stage stage = (Stage) btn_transfer.getScene().getWindow();
            Model.getInstance().getViewFactory().closeStage(stage);
            Model.getInstance().getViewFactory().showTransferWindow();
        });

    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
        String[] name = customer.name.split(" ");

        tv_say_hi.setText("Hi, " + name[0]);
    }

    public void setSaldoAkhir () {
        String parseCurrency = new CurrencyController().getIndonesianCurrency(customer.updatedBalance());
        this.tv_saldo_akhir.setText(parseCurrency);
    }


    public void setSummary () {
        Transaction transaction = new Transaction();

        BigDecimal summaryTransferIn = transaction.getTransactionFromReceiver(customer.customerId, "Transfer");
        BigDecimal summaryTransferOut = transaction.getTransactionFromSender(customer.customerId, "Transfer");
        BigDecimal summaryDeposit = transaction.getTransactionFromReceiver(customer.customerId, "Deposit");
        BigDecimal summaryWithdraw = transaction.getTransactionFromSender(customer.customerId, "Withdraw");

        this.tv_transfer_keluar.setText(new CurrencyController().getIndonesianCurrency(summaryTransferOut));
        this.tv_transfer_masuk.setText(new CurrencyController().getIndonesianCurrency(summaryTransferIn));
        this.tv_tarik_tunai.setText(new CurrencyController().getIndonesianCurrency(summaryWithdraw));
        this.tv_value_deposit.setText(new CurrencyController().getIndonesianCurrency(summaryDeposit));
    }

    public void setTransactionList () {
        try {
            PreparedStatement ps = conn.prepareStatement("""
                    SELECT th.transaction_date,
                         (SELECT cd.name FROM customer_data cd WHERE cd.id_customer=th.transaction_sender) AS transaction_sender,
                         (SELECT cd.name FROM customer_data cd WHERE cd.id_customer=th.transaction_receiver) AS transaction_receiver,
                         transaction_amount,
                         transaction_admin_fee,
                         (SELECT tt.transaction_type FROM transaction_type tt WHERE tt.id_transaction_type=th.transaction_type_id) AS transaction_type,
                         transaction_status,
                         transaction_message
                    FROM transaction_history th
                    WHERE th.transaction_sender=?;""");

            ps.setInt(1, customer.customerId);

            ResultSet rs = ps.executeQuery();
            int i = 0;
            while (rs.next() && i < 5) {
                sender[i] = rs.getString("transaction_sender");
                receiver[i] = rs.getString("transaction_receiver");
                amount[i] = rs.getBigDecimal("transaction_amount");
                i++;
            }

            transaction_listview.setCellFactory(tellerListView -> new TransactionCellFactory());

            transaction_listview.setItems(FXCollections.observableArrayList(transactionModels));

            transaction_listview.getSelectionModel().selectedItemProperty().addListener((observableValue, oldValue, newValue) -> {
                System.out.println(newValue.amountProperty().getValue());
            });
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
