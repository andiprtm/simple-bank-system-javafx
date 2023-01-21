package com.bank.app.controllers.client;

import com.bank.app.models.TransactionModel;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIconView;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;

import java.net.URL;
import java.util.ResourceBundle;

public class TransactionCellController implements Initializable {
    public FontAwesomeIconView transaction_in;
    public FontAwesomeIconView transaction_out;
    public Label tv_sender;
    public Label tv_receiver;
    public Label tv_amount;
    public Label tv_date;

    private final TransactionModel transactionModel;

    public TransactionCellController(TransactionModel transactionModel) {
        this.transactionModel = transactionModel;
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        tv_sender.setText(transactionModel.senderProperty().getValue());
        tv_receiver.setText(transactionModel.receiverProperty().getValue());
        tv_amount.setText(transactionModel.amountProperty().getValue().toString());
        tv_date.setText(transactionModel.dateProperty().getValue().toString());
    }
}
