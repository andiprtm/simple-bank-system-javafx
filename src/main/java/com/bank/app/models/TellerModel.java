package com.bank.app.models;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;


public class TellerModel {
    private final StringProperty idPegawai;
    private final StringProperty username;
    private final StringProperty password;
    private final StringProperty nama;
    private final StringProperty phone;
    private final StringProperty address;
    private final StringProperty status;
    private final StringProperty accountType;

    public TellerModel(String idPegawai, String username, String password, String nama, String address, String phone,  String accountType, String status ) {
        this.username = new SimpleStringProperty(this, "username", username);
        this.phone = new SimpleStringProperty(this, "phone", phone);
        this.address = new SimpleStringProperty(this, "address", address);
        this.status = new SimpleStringProperty(this, "status", status);
        this.accountType = new SimpleStringProperty(this, "accountType", accountType);
        this.idPegawai = new SimpleStringProperty(this, "idPegawai", idPegawai);
        this.password = new SimpleStringProperty(this, "password", password);
        this.nama = new SimpleStringProperty(this, "nama", nama);
    }

    public StringProperty idPegawaiProperty() {
        return idPegawai;
    }
    public StringProperty usernameProperty() {
        return username;
    }

    public StringProperty passwordProperty() {
        return password;
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

    public StringProperty namaProperty() {
        return nama;
    }


}
