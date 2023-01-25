package com.bank.app.controllers.client;

import com.bank.app.controllers.utils.CurrencyController;
import com.bank.app.models.TransactionModel;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIconView;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.paint.Color;

import java.net.URL;
import java.util.ResourceBundle;

public class TransactionCellController implements Initializable {
    public FontAwesomeIconView transaction_in;
    public FontAwesomeIconView transaction_out;
    public Label tv_sender;
    public Label tv_receiver;
    public Label tv_amount;
    public Label tv_date;
    public Label tv_tipeTransaksi;

    private final TransactionModel transactionModel;

    public TransactionCellController(TransactionModel transactionModel) {
        this.transactionModel = transactionModel;
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        if (transactionModel != null) {
            if(transactionModel.senderProperty().getValue().equals(transactionModel.usernameProperty().getValue()) && (transactionModel.transactionTypeProperty().getValue().equals("Transfer") || transactionModel.transactionTypeProperty().getValue().equals("Withdraw"))){
                transaction_out.setFill(javafx.scene.paint.Color.valueOf("#800000"));
                transaction_in.setFill(javafx.scene.paint.Color.valueOf("#eae7b1"));
            }
            else if(transactionModel.receiverProperty().getValue().equals(transactionModel.usernameProperty().getValue()) && (transactionModel.transactionTypeProperty().getValue().equals("Transfer") || transactionModel.transactionTypeProperty().getValue().equals("Deposit"))){
                transaction_in.setFill(Color.DARKGREEN);
                transaction_out.setFill(javafx.scene.paint.Color.valueOf("#eae7b1"));
            }
            String[] date = transactionModel.dateProperty().getValue().toString().split(" ");
            tv_tipeTransaksi.setText(transactionModel.transactionTypeProperty().getValue());
            tv_sender.setText(transactionModel.senderProperty().getValue());
            tv_receiver.setText(transactionModel.receiverProperty().getValue());
//            tv_amount.setText(transactionModel.amountProperty().getValue().toString());
            tv_amount.setText(new CurrencyController().getIndonesianCurrency(transactionModel.amountProperty().getValue()));
            tv_date.setText(date[0]);
        }
    }
}
