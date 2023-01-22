package com.bank.app.controllers.client;

import com.bank.app.ConnectionManager;
import com.bank.app.models.Customer;
import com.bank.app.models.TransactionModel;
import com.bank.app.views.TransactionCellFactory;
import javafx.collections.FXCollections;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;

import java.net.URL;
import java.sql.*;
import java.util.ResourceBundle;

public class TransactionController implements Initializable {
    public ListView<TransactionModel> listview_transaksi;
    public Customer customer;
    public Integer countRow;
    public Label tv_say_hi;
    public Label tv_jumlah_transaksi;
    Connection conn = ConnectionManager.getInstance().getConnection();

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
        String[] name = customer.name.split(" ");

        tv_say_hi.setText("Hi, " + name[0]);
    }

    public void setCountRow () {
        try {
            PreparedStatement ps = conn.prepareStatement("""
                    SELECT COUNT(*) as total_rows FROM (
                        SELECT th.transaction_date,
                           (SELECT cd.name FROM customer_data cd WHERE cd.id_customer=th.transaction_sender) AS transaction_sender,
                           (SELECT cd.name FROM customer_data cd WHERE cd.id_customer=th.transaction_receiver) AS transaction_receiver,
                           th.transaction_amount,
                           th.transaction_admin_fee,
                           (SELECT tt.transaction_type FROM transaction_type tt WHERE tt.id_transaction_type=th.transaction_type_id) AS transaction_type,
                           th.transaction_status,
                           th.transaction_message
                    FROM transaction_history th
                    WHERE (th.transaction_type_id=1 OR th.transaction_type_id=2) AND th.transaction_sender=?
                    UNION
                    SELECT th.transaction_date,
                           (SELECT cd.name FROM customer_data cd WHERE cd.id_customer=th.transaction_sender) AS transaction_sender,
                           (SELECT cd.name FROM customer_data cd WHERE cd.id_customer=th.transaction_receiver) AS transaction_receiver,
                           th.transaction_amount,
                           th.transaction_admin_fee,
                           (SELECT tt.transaction_type FROM transaction_type tt WHERE tt.id_transaction_type=th.transaction_type_id) AS transaction_type,
                           th.transaction_status,
                           th.transaction_message
                    FROM transaction_history th
                    WHERE th.transaction_type_id=3 AND th.transaction_sender=?
                    UNION
                    SELECT th.transaction_date,
                           (SELECT cd.name FROM customer_data cd WHERE cd.id_customer=th.transaction_sender) AS transaction_sender,
                           (SELECT cd.name FROM customer_data cd WHERE cd.id_customer=th.transaction_receiver) AS transaction_receiver,
                           th.transaction_amount,
                           th.transaction_admin_fee,
                           (SELECT tt.transaction_type FROM transaction_type tt WHERE tt.id_transaction_type=th.transaction_type_id) AS transaction_type,
                           th.transaction_status,
                           th.transaction_message
                    FROM transaction_history th
                    WHERE th.transaction_type_id=3 AND th.transaction_receiver=?
                    ORDER BY transaction_date desc
                    ) as total;""");

            ps.setInt(1, customer.customerId);
            ps.setInt(2, customer.customerId);
            ps.setInt(3, customer.customerId);

            System.out.println(customer.customerId);

            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                this.countRow = rs.getInt("total_rows");
                tv_jumlah_transaksi.setText(this.countRow.toString());
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void setTransactionList () {
        try {
            PreparedStatement ps = conn.prepareStatement("""
                    SELECT th.transaction_date,
                           (SELECT cd.name FROM customer_data cd WHERE cd.id_customer=th.transaction_sender) AS transaction_sender,
                           (SELECT cd.name FROM customer_data cd WHERE cd.id_customer=th.transaction_receiver) AS transaction_receiver,
                           th.transaction_amount,
                           th.transaction_admin_fee,
                           (SELECT tt.transaction_type FROM transaction_type tt WHERE tt.id_transaction_type=th.transaction_type_id) AS transaction_type,
                           th.transaction_status,
                           th.transaction_message
                    FROM transaction_history th
                    WHERE (th.transaction_type_id=1 OR th.transaction_type_id=2) AND th.transaction_sender=?
                    UNION
                    SELECT th.transaction_date,
                           (SELECT cd.name FROM customer_data cd WHERE cd.id_customer=th.transaction_sender) AS transaction_sender,
                           (SELECT cd.name FROM customer_data cd WHERE cd.id_customer=th.transaction_receiver) AS transaction_receiver,
                           th.transaction_amount,
                           th.transaction_admin_fee,
                           (SELECT tt.transaction_type FROM transaction_type tt WHERE tt.id_transaction_type=th.transaction_type_id) AS transaction_type,
                           th.transaction_status,
                           th.transaction_message
                    FROM transaction_history th
                    WHERE th.transaction_type_id=3 AND th.transaction_sender=?
                    UNION
                    SELECT th.transaction_date,
                           (SELECT cd.name FROM customer_data cd WHERE cd.id_customer=th.transaction_sender) AS transaction_sender,
                           (SELECT cd.name FROM customer_data cd WHERE cd.id_customer=th.transaction_receiver) AS transaction_receiver,
                           th.transaction_amount,
                           th.transaction_admin_fee,
                           (SELECT tt.transaction_type FROM transaction_type tt WHERE tt.id_transaction_type=th.transaction_type_id) AS transaction_type,
                           th.transaction_status,
                           th.transaction_message
                    FROM transaction_history th
                    WHERE th.transaction_type_id=3 AND th.transaction_receiver=?
                    ORDER BY transaction_date desc;""");

            ps.setInt(1, customer.customerId);
            ps.setInt(2, customer.customerId);
            ps.setInt(3, customer.customerId);

            System.out.println(customer.customerId);

            ResultSet rs = ps.executeQuery();

            TransactionModel[] transactionModels = new TransactionModel[this.countRow];

            int i = 0;
            while (rs.next() && i < this.countRow) {
                transactionModels[i] = new TransactionModel(rs.getString("transaction_sender"), rs.getString("transaction_receiver"), rs.getBigDecimal("transaction_amount"), rs.getTimestamp("transaction_date"));
                i++;
            }

            listview_transaksi.setCellFactory(tellerListView -> new TransactionCellFactory());

            listview_transaksi.setItems(FXCollections.observableArrayList(transactionModels));

            listview_transaksi.getSelectionModel().selectedItemProperty().addListener((observableValue, oldValue, newValue) -> {
                System.out.println(newValue.amountProperty().getValue());
            });
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


}
