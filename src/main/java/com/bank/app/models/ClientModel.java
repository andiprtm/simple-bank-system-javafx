package com.bank.app.models;

import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

import java.math.BigDecimal;

public class ClientModel {
    private final StringProperty username;
    private final StringProperty phone;
    private final StringProperty address;
    private final StringProperty status;
    private final StringProperty accountType;
    private final ObjectProperty<BigDecimal> balance;

    public ClientModel(String username, String phone, String address, String status, String accountType, BigDecimal balance) {
        this.username = new SimpleStringProperty(this, "username", username);
        this.phone = new SimpleStringProperty(this, "phone", phone);
        this.address = new SimpleStringProperty(this, "address", address);
        this.status = new SimpleStringProperty(this, "status", status);
        this.accountType = new SimpleStringProperty(this, "accountType", accountType);
        this.balance = new SimpleObjectProperty<>(this, "balance", balance);
    }

    public StringProperty usernameProperty() {
        return username;
    }

    public StringProperty phoneProperty() {
        return phone;
    }

    public StringProperty addressProperty() {
        return address;
    }

    public StringProperty statusProperty() {
        return status;
    }

    public StringProperty accountTypeProperty() {
        return accountType;
    }

    public ObjectProperty<BigDecimal> balanceProperty() {
        return balance;
    }
}
