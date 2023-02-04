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
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class DashboardController implements Initializable {
    Customer customer;
    public Integer countRow;
    public Label tv_say_hi;
    public Label tv_saldo_akhir;
    public Label tv_value_deposit;
    public Label tv_transfer_masuk;
    public Label tv_transfer_keluar;
    public Label tv_tarik_tunai;
    public Button btn_transfer;
    public Button btn_tarik_tunai;
    public ListView<TransactionModel> transaction_listview;
    Connection conn = ConnectionManager.getInstance().getConnection();

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
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

    public void setCountRow() {
        try {
            PreparedStatement ps = conn.prepareStatement("""
                    SELECT COUNT(*) as total_rows FROM (
                        SELECT th.transaction_type_id,
                               th.transaction_date,
                               (SELECT cd.name FROM customer_data cd WHERE cd.id_customer=th.transaction_sender) AS transaction_sender,
                               (SELECT cd.name FROM customer_data cd WHERE cd.id_customer=th.transaction_receiver) AS transaction_receiver,
                               th.transaction_amount,
                               (ROUND((th.transaction_admin_fee / th.transaction_amount) * 100)) as admin_fee_percent,
                               th.transaction_admin_fee,
                               (SELECT tt.transaction_type FROM transaction_type tt WHERE tt.id_transaction_type=th.transaction_type_id) AS transaction_type,
                               th.transaction_status,
                               th.transaction_message,
                               th.id_transaction_history
                        FROM transaction_history th
                        WHERE (th.transaction_type_id=1 OR th.transaction_type_id=2) AND th.transaction_sender=?
                        UNION
                        SELECT th.transaction_type_id,
                               th.transaction_date,
                               (SELECT cd.name FROM customer_data cd WHERE cd.id_customer=th.transaction_sender) AS transaction_sender,
                               (SELECT cd.name FROM customer_data cd WHERE cd.id_customer=th.transaction_receiver) AS transaction_receiver,
                               th.transaction_amount,
                               (ROUND((th.transaction_admin_fee / th.transaction_amount) * 100)) as admin_fee_percent,
                               th.transaction_admin_fee,
                               (SELECT tt.transaction_type FROM transaction_type tt WHERE tt.id_transaction_type=th.transaction_type_id) AS transaction_type,
                               th.transaction_status,
                               th.transaction_message,
                               th.id_transaction_history
                        FROM transaction_history th
                        WHERE th.transaction_type_id=3 AND th.transaction_sender=?
                        UNION
                        SELECT th.transaction_type_id,
                               th.transaction_date,
                               (SELECT cd.name FROM customer_data cd WHERE cd.id_customer=th.transaction_sender) AS transaction_sender,
                               (SELECT cd.name FROM customer_data cd WHERE cd.id_customer=th.transaction_receiver) AS transaction_receiver,
                               th.transaction_amount,
                               (ROUND((th.transaction_admin_fee / th.transaction_amount) * 100)) as admin_fee_percent,
                               th.transaction_admin_fee,
                               (SELECT tt.transaction_type FROM transaction_type tt WHERE tt.id_transaction_type=th.transaction_type_id) AS transaction_type,
                               th.transaction_status,
                               th.transaction_message,
                               th.id_transaction_history
                        FROM transaction_history th
                        WHERE th.transaction_type_id=3 AND th.transaction_receiver=?
                        ORDER BY transaction_date desc
                        LIMIT 5
                    ) as total;""");

            ps.setInt(1, customer.customerId);
            ps.setInt(2, customer.customerId);
            ps.setInt(3, customer.customerId);

            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                this.countRow = rs.getInt("total_rows");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void setTransactionList () {
        try {
            PreparedStatement ps = conn.prepareStatement("""
                    SELECT th.transaction_type_id,
                           th.transaction_date,
                           (SELECT cd.name FROM customer_data cd WHERE cd.id_customer=th.transaction_sender) AS transaction_sender,
                           (SELECT cd.name FROM customer_data cd WHERE cd.id_customer=th.transaction_receiver) AS transaction_receiver,
                           th.transaction_amount,
                           (ROUND((th.transaction_admin_fee / th.transaction_amount) * 100)) as admin_fee_percent,
                           th.transaction_admin_fee,
                           (SELECT tt.transaction_type FROM transaction_type tt WHERE tt.id_transaction_type=th.transaction_type_id) AS transaction_type,
                           th.transaction_status,
                           th.transaction_message,
                           th.id_transaction_history
                    FROM transaction_history th
                    WHERE (th.transaction_type_id=1 OR th.transaction_type_id=2) AND th.transaction_sender=?
                    UNION
                    SELECT th.transaction_type_id,
                           th.transaction_date,
                           (SELECT cd.name FROM customer_data cd WHERE cd.id_customer=th.transaction_sender) AS transaction_sender,
                           (SELECT cd.name FROM customer_data cd WHERE cd.id_customer=th.transaction_receiver) AS transaction_receiver,
                           th.transaction_amount,
                           (ROUND((th.transaction_admin_fee / th.transaction_amount) * 100)) as admin_fee_percent,
                           th.transaction_admin_fee,
                           (SELECT tt.transaction_type FROM transaction_type tt WHERE tt.id_transaction_type=th.transaction_type_id) AS transaction_type,
                           th.transaction_status,
                           th.transaction_message,
                           th.id_transaction_history
                    FROM transaction_history th
                    WHERE th.transaction_type_id=3 AND th.transaction_sender=?
                    UNION
                    SELECT th.transaction_type_id,
                           th.transaction_date,
                           (SELECT cd.name FROM customer_data cd WHERE cd.id_customer=th.transaction_sender) AS transaction_sender,
                           (SELECT cd.name FROM customer_data cd WHERE cd.id_customer=th.transaction_receiver) AS transaction_receiver,
                           th.transaction_amount,
                           (ROUND((th.transaction_admin_fee / th.transaction_amount) * 100)) as admin_fee_percent,
                           th.transaction_admin_fee,
                           (SELECT tt.transaction_type FROM transaction_type tt WHERE tt.id_transaction_type=th.transaction_type_id) AS transaction_type,
                           th.transaction_status,
                           th.transaction_message,
                           th.id_transaction_history
                    FROM transaction_history th
                    WHERE th.transaction_type_id=3 AND th.transaction_receiver=?
                    ORDER BY transaction_date desc
                    LIMIT 5;""");

            ps.setInt(1, customer.customerId);
            ps.setInt(2, customer.customerId);
            ps.setInt(3, customer.customerId);

            ResultSet rs = ps.executeQuery();

            TransactionModel[] transactionModels = new TransactionModel[this.countRow];

            int i = 0;
            while (rs.next() && i < this.countRow) {
                transactionModels[i] = new TransactionModel(
                        customer.name,
                        rs.getInt("id_transaction_history"),
                        rs.getTimestamp("transaction_date"),
                        rs.getString("transaction_type"),
                        rs.getString("transaction_sender"),
                        rs.getString("transaction_receiver"),
                        rs.getBigDecimal("transaction_amount"),
                        rs.getInt("admin_fee_percent")
                );
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


}
