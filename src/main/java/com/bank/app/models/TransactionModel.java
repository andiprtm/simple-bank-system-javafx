package com.bank.app.models;

import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

import java.math.BigDecimal;
import java.time.LocalDate;

public class TransactionModel {
    private final StringProperty sender;
    private final StringProperty receiver;
    private final ObjectProperty<BigDecimal> amount;
    private final ObjectProperty<LocalDate> date;

    public TransactionModel(String sender, String receiver, BigDecimal amount, LocalDate date) {
        this.sender = new SimpleStringProperty(this, "sender", sender);
        this.receiver = new SimpleStringProperty(this, "receiver", receiver);
        this.amount = new SimpleObjectProperty<>(this, "amount", amount);
        this.date = new SimpleObjectProperty<>(this, "date", date);
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

    public ObjectProperty<LocalDate> dateProperty() {
        return date;
    }
}
