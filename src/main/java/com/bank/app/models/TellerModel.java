package com.bank.app.models;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;


public class TellerModel {
    private final StringProperty username;
    private final StringProperty phone;
    private final StringProperty address;
    private final StringProperty status;
    private final StringProperty accountType;

    public TellerModel(String username, String phone, String address, String status, String accountType) {
        this.username = new SimpleStringProperty(this, "username", username);
        this.phone = new SimpleStringProperty(this, "phone", phone);
        this.address = new SimpleStringProperty(this, "address", address);
        this.status = new SimpleStringProperty(this, "status", status);
        this.accountType = new SimpleStringProperty(this, "accountType", accountType);
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
}
