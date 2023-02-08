package com.bank.app.controllers.client;

import com.bank.app.ConnectionManager;
import com.bank.app.models.Customer;
import com.bank.app.models.TransactionModel;
import com.bank.app.views.TransactionCellFactory;
import javafx.collections.FXCollections;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.print.Printer;
import javafx.print.PrinterJob;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.sql.*;
import java.util.Optional;
import java.util.ResourceBundle;

public class TransactionController implements Initializable {
    public String[] transactionType = {"Transfer", "Withdraw", "Deposit"};
    public ListView<TransactionModel> listview_transaksi;
    public Customer customer;
    public Integer countRow;
    public int countDate;
    public int countType;
    public int countDateAndType;
    public Label tv_say_hi;
    public Label tv_jumlah_transaksi;
    public DatePicker search_date;
    public ChoiceBox<String> search_type_transaksi;
    public Button btn_search_transaksi;
    Connection conn = ConnectionManager.getInstance().getConnection();

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        search_type_transaksi.setItems(FXCollections.observableArrayList(transactionType));
        btn_search_transaksi.setOnAction(event -> {
            search();
        });

    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
        String[] name = customer.name.split(" ");

        tv_say_hi.setText("Hi, " + name[0]);
    }

    public void countByDate(){
        try{
            PreparedStatement ps = conn.prepareStatement("select count(*) as total_rows from (select cd.name,\n" +
                    "       id_transaction_history,\n" +
                    "       transaction_date,\n" +
                    "       transaction_type,\n" +
                    "       transaction_amount,\n" +
                    "       (SELECT cd.name FROM customer_data cd WHERE cd.id_customer=th.transaction_sender) AS transaction_sender,\n" +
                    "       (SELECT cd.name FROM customer_data cd WHERE cd.id_customer=th.transaction_receiver) AS transaction_receiver,\n" +
                    "       (ROUND((th.transaction_admin_fee / th.transaction_amount) * 100)) as admin_fee_percent from customer_data cd\n" +
                    "join transaction_history th\n" +
                    "    on (cd.id_customer = th.transaction_sender) or (cd.id_customer = th.transaction_receiver)\n" +
                    "join transaction_type tt\n" +
                    "    on th.transaction_type_id = tt.id_transaction_type\n" +
                    "where cd.username = ? and transaction_date LIKE concat(?,'%') order by transaction_date desc) as total;");

            ps.setString(1, customer.username);
            ps.setString(2, this.search_date.getValue().toString());

            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                this.countDate = rs.getInt("total_rows");
                tv_jumlah_transaksi.setText("Total Transaksi "+this.countDate);
            }
        }catch (SQLException e){
            e.printStackTrace();
        }

    }

    public void countByType(){
        try{
            PreparedStatement ps = conn.prepareStatement("select count(*) as total_rows from(select cd.name,\n" +
                    "       id_transaction_history,\n" +
                    "       transaction_date,\n" +
                    "       transaction_type,\n" +
                    "       transaction_amount,\n" +
                    "       (SELECT cd.name FROM customer_data cd WHERE cd.id_customer=th.transaction_sender) AS transaction_sender,\n" +
                    "       (SELECT cd.name FROM customer_data cd WHERE cd.id_customer=th.transaction_receiver) AS transaction_receiver,\n" +
                    "       (ROUND((th.transaction_admin_fee / th.transaction_amount) * 100)) as admin_fee_percent from customer_data cd\n" +
                    "join transaction_history th\n" +
                    "    on (cd.id_customer = th.transaction_sender) or (cd.id_customer = th.transaction_receiver)\n" +
                    "join transaction_type tt\n" +
                    "    on th.transaction_type_id = tt.id_transaction_type\n" +
                    "where cd.username =? and transaction_type =? order by transaction_date desc)as total;");

            ps.setString(1, customer.username);
            ps.setString(2, this.search_type_transaksi.getValue());

            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                this.countType = rs.getInt("total_rows");
                tv_jumlah_transaksi.setText("Total Transaksi "+this.countType);
            }
        }catch (SQLException e){
            e.printStackTrace();
        }
    }

    public void countByDateAndType(){
        try{
            PreparedStatement ps = conn.prepareStatement("select count(*) as total_rows from(select cd.name,\n" +
                    "       id_transaction_history,\n" +
                    "       transaction_date,\n" +
                    "       transaction_type,\n" +
                    "       transaction_amount,\n" +
                    "       (SELECT cd.name FROM customer_data cd WHERE cd.id_customer=th.transaction_sender) AS transaction_sender,\n" +
                    "       (SELECT cd.name FROM customer_data cd WHERE cd.id_customer=th.transaction_receiver) AS transaction_receiver,\n" +
                    "       (ROUND((th.transaction_admin_fee / th.transaction_amount) * 100)) as admin_fee_percent from customer_data cd\n" +
                    "join transaction_history th\n" +
                    "    on (cd.id_customer = th.transaction_sender) or (cd.id_customer = th.transaction_receiver)\n" +
                    "join transaction_type tt\n" +
                    "    on th.transaction_type_id = tt.id_transaction_type\n" +
                    "where cd.username =? and transaction_date LIKE concat(?,'%') and transaction_type =? order by transaction_date desc)as total;");

            ps.setString(1, customer.username);
            ps.setString(2, this.search_date.getValue().toString());
            ps.setString(3, this.search_type_transaksi.getValue());

            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                this.countDateAndType = rs.getInt("total_rows");
                tv_jumlah_transaksi.setText("Total Transaksi "+this.countDateAndType);
            }
        }catch (SQLException e){
            e.printStackTrace();
        }
    }

    public void setCountRow () {
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
                    ) as total;""");

            ps.setInt(1, customer.customerId);
            ps.setInt(2, customer.customerId);
            ps.setInt(3, customer.customerId);

            System.out.println(customer.customerId);

            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                this.countRow = rs.getInt("total_rows");
                tv_jumlah_transaksi.setText("Total Transaksi "+this.countRow.toString());
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
                    ORDER BY transaction_date desc;""");

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

            listview_transaksi.setCellFactory(tellerListView -> new TransactionCellFactory());

            listview_transaksi.setItems(FXCollections.observableArrayList(transactionModels));

            listview_transaksi.getSelectionModel().selectedItemProperty().addListener((observableValue, oldValue, newValue) -> {
                //load halaman detail transaksi
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/Client/DetailTransaksi.fxml"));
                Scene scene = null;
                try {
                    scene = new Scene(loader.load());
                } catch (IOException e) {
                    e.printStackTrace();
                }
                DetailTransaksiController detailTransactionController = loader.getController();
                detailTransactionController.setData(
                        newValue.idTransactionProperty().getValue().toString(),
                        newValue.transactionTypeProperty().getValue(),
                        newValue.dateProperty().getValue().toString(),
                        newValue.receiverProperty().getValue(),
                        newValue.senderProperty().getValue(),
                        newValue.amountProperty().getValue(),
                        newValue.adminFeePercentProperty().getValue()
                );
                Stage stage = new Stage();
                stage.setScene(scene);
                stage.show();

                Scene root = (Scene) listview_transaksi.getScene();

                // PRINT STRUK
                ChoiceDialog dialog = new ChoiceDialog(Printer.getDefaultPrinter(), Printer.getAllPrinters());
                dialog.setHeaderText("Choose the printer!");
                dialog.setContentText("Choose a printer from available printers");
                dialog.setTitle("Printer Choice");
                Optional<Printer> opt = dialog.showAndWait();
                if (opt.isPresent()) {
                    Printer printer = opt.get();
                    // start printing ...
                    printer.getPrinterAttributes();
                    PrinterJob job = PrinterJob.createPrinterJob(printer);
                    StringBuffer sbf = new StringBuffer(newValue.dateProperty().getValue().toString());
                    sbf.deleteCharAt(sbf.length() - 1);
                    sbf.deleteCharAt(sbf.length() - 1);
                    job.getJobSettings().setJobName(newValue.transactionTypeProperty().getValue()+" from "+newValue.senderProperty().getValue()+" to "+newValue.receiverProperty().getValue()+" at "+sbf+" WIB");
                    boolean success = job.printPage(stage.getScene().getRoot());
                    if (success) {
                        job.endJob();
                    }
                }
            });
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void search(){
        if(search_date.getValue() != null && search_type_transaksi.getValue() == null) {
            countByDate();
            searchByDate();
        }

        if(search_date.getValue() == null && search_type_transaksi.getValue() != null) {
            countByType();
            searchByType();
        }

        if(search_date.getValue() != null && search_type_transaksi.getValue() != null) {
            countByDateAndType();
            searchByDateAndType();
        }
    }

    public void searchByDate(){
        try {
            PreparedStatement ps = conn.prepareStatement("select cd.name,\n" +
                    "       id_transaction_history,\n" +
                    "       transaction_date,\n" +
                    "       transaction_type,\n" +
                    "       transaction_amount,\n" +
                    "       (SELECT cd.name FROM customer_data cd WHERE cd.id_customer=th.transaction_sender) AS transaction_sender,\n" +
                    "       (SELECT cd.name FROM customer_data cd WHERE cd.id_customer=th.transaction_receiver) AS transaction_receiver,\n" +
                    "       (ROUND((th.transaction_admin_fee / th.transaction_amount) * 100)) as admin_fee_percent from customer_data cd\n" +
                    "join transaction_history th\n" +
                    "    on (cd.id_customer = th.transaction_sender) or (cd.id_customer = th.transaction_receiver)\n" +
                    "join transaction_type tt\n" +
                    "    on th.transaction_type_id = tt.id_transaction_type\n" +
                    "where cd.username = ? and transaction_date LIKE concat(?,'%') order by transaction_date desc;");

            ps.setString(1, customer.username);
            ps.setString(2, this.search_date.getValue().toString());

            ResultSet rs = ps.executeQuery();

            TransactionModel[] transactionModels = new TransactionModel[this.countDate];

            int i = 0;
            while (rs.next() && i < this.countDate) {
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

            listview_transaksi.setCellFactory(tellerListView -> new TransactionCellFactory());

            listview_transaksi.setItems(FXCollections.observableArrayList(transactionModels));
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void searchByType(){

        try {
            PreparedStatement ps = conn.prepareStatement("select cd.name,\n" +
                    "       id_transaction_history,\n" +
                    "       transaction_date,\n" +
                    "       transaction_type,\n" +
                    "       transaction_amount,\n" +
                    "       (SELECT cd.name FROM customer_data cd WHERE cd.id_customer=th.transaction_sender) AS transaction_sender,\n" +
                    "       (SELECT cd.name FROM customer_data cd WHERE cd.id_customer=th.transaction_receiver) AS transaction_receiver,\n" +
                    "       (ROUND((th.transaction_admin_fee / th.transaction_amount) * 100)) as admin_fee_percent from customer_data cd\n" +
                    "join transaction_history th\n" +
                    "    on (cd.id_customer = th.transaction_sender) or (cd.id_customer = th.transaction_receiver)\n" +
                    "join transaction_type tt\n" +
                    "    on th.transaction_type_id = tt.id_transaction_type\n" +
                    "where cd.username =? and transaction_type =? order by transaction_date desc;");

            ps.setString(1, customer.username);
            ps.setString(2, this.search_type_transaksi.getValue());

            ResultSet rs = ps.executeQuery();

            TransactionModel[] transactionModels = new TransactionModel[this.countType];

            int i = 0;
            while (rs.next() && i < this.countType) {
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

            listview_transaksi.setCellFactory(tellerListView -> new TransactionCellFactory());

            listview_transaksi.setItems(FXCollections.observableArrayList(transactionModels));
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    public void searchByDateAndType(){

        try {
            PreparedStatement ps = conn.prepareStatement("select cd.name,\n" +
                    "       id_transaction_history,\n" +
                    "       transaction_date,\n" +
                    "       transaction_type,\n" +
                    "       transaction_amount,\n" +
                    "       (SELECT cd.name FROM customer_data cd WHERE cd.id_customer=th.transaction_sender) AS transaction_sender,\n" +
                    "       (SELECT cd.name FROM customer_data cd WHERE cd.id_customer=th.transaction_receiver) AS transaction_receiver,\n" +
                    "       (ROUND((th.transaction_admin_fee / th.transaction_amount) * 100)) as admin_fee_percent from customer_data cd\n" +
                    "join transaction_history th\n" +
                    "    on (cd.id_customer = th.transaction_sender) or (cd.id_customer = th.transaction_receiver)\n" +
                    "join transaction_type tt\n" +
                    "    on th.transaction_type_id = tt.id_transaction_type\n" +
                    "where cd.username =? and transaction_date LIKE concat(?,'%') and transaction_type =? order by transaction_date desc;");

            ps.setString(1, customer.username);
            ps.setString(2, this.search_date.getValue().toString());
            ps.setString(3, this.search_type_transaksi.getValue());

            ResultSet rs = ps.executeQuery();

            TransactionModel[] transactionModels = new TransactionModel[this.countDateAndType];

            int i = 0;
            while (rs.next() && i < this.countDateAndType) {
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

            listview_transaksi.setCellFactory(tellerListView -> new TransactionCellFactory());

            listview_transaksi.setItems(FXCollections.observableArrayList(transactionModels));
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }


}
