package com.bank.app.models;

import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

import java.math.BigDecimal;

public class ClientModel {
    private final StringProperty idUser;
    private final StringProperty username;
    private final StringProperty password;
    private final StringProperty name;
    private final StringProperty pin;
    private final StringProperty phone;
    private final StringProperty address;

    private final StringProperty status;
    private final StringProperty accountType;
    private final ObjectProperty<BigDecimal> balance;

    public ClientModel(String idUser, String username, String password, String name, String address, String phone, String pin , BigDecimal balance, String accountType, String status) {
        this.username = new SimpleStringProperty(this, "username", username);
        this.phone = new SimpleStringProperty(this, "phone", phone);
        this.address = new SimpleStringProperty(this, "address", address);
        this.status = new SimpleStringProperty(this, "status", status);
        this.accountType = new SimpleStringProperty(this, "accountType", accountType);
        this.balance = new SimpleObjectProperty<>(this, "balance", balance);
        this.idUser = new SimpleStringProperty(this, "idUser", idUser);
        this.name = new SimpleStringProperty(this, "name", name);
        this.pin = new SimpleStringProperty(this, "pin", pin);
        this.password = new SimpleStringProperty(this, "password", password);
    }

    public StringProperty idUserProperty() {
        return idUser;
    }
    public StringProperty usernameProperty() {
        return username;
    }

    public StringProperty passwordProperty() {
        return password;
    }

    public StringProperty nameProperty() {
        return name;
    }

    public StringProperty pinProperty() {
        return pin;
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
