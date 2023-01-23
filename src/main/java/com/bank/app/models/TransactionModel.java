package com.bank.app.models;

import javafx.beans.property.*;

import java.math.BigDecimal;
import java.sql.Timestamp;

public class TransactionModel {
    private final StringProperty transactionType;
    private final SimpleIntegerProperty idTransaction;
    private final SimpleIntegerProperty adminFeePercent;
    private final StringProperty sender;
    private final StringProperty receiver;
    private final ObjectProperty<BigDecimal> amount;
    private final ObjectProperty<Timestamp> date;

    public TransactionModel(Integer idTransaction,Timestamp date, String transactionType, String sender, String receiver, BigDecimal amount, Integer adminFeePercent) {
        this.idTransaction = new SimpleIntegerProperty(this, "idTransaction", idTransaction);
        this.transactionType = new SimpleStringProperty(this, "transactionType", transactionType);
        this.sender = new SimpleStringProperty(this, "sender", sender);
        this.receiver = new SimpleStringProperty(this, "receiver", receiver);
        this.amount = new SimpleObjectProperty<>(this, "amount", amount);
        this.date = new SimpleObjectProperty<>(this, "date", date);
        this.adminFeePercent = new SimpleIntegerProperty(this, "adminFeePercent", adminFeePercent);
    }

    public StringProperty transactionTypeProperty() {
        return transactionType;
    }
    public SimpleIntegerProperty idTransactionProperty() {
        return idTransaction;
    }
    public SimpleIntegerProperty adminFeePercentProperty() {
        return adminFeePercent;
    }
    public StringProperty senderProperty() {
        return sender;
    }

    public StringProperty receiverProperty() {
        return receiver;
    }

    public ObjectProperty<BigDecimal> amountProperty() {
        return amount;
    }

    public ObjectProperty<Timestamp> dateProperty() {
        return date;
    }
}
