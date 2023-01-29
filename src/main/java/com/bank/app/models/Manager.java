package com.bank.app.models;

public class Manager extends Employee {

    public Manager(String username, String password) {
        super(username, password);
    }

    public Teller createTellerAccount(String accountType, String username, String password, String name, String address, String phone) {
        return new Teller(accountType, username, password, name, address, phone, true);
    }

    public void setIsActiveTellerAccount(String username, Boolean isActive) {
        Teller teller = new Teller(username);
        teller.setIsActive(isActive);
    }

    public Teller updateTellerDataAccount(String username, String accountType, String newUsername, String password, String name, String address, String phone, Boolean isActive) {
        Teller teller = new Teller(username);
        teller.updateEmployeeData(accountType, newUsername,password, name, address, phone, isActive);
        teller.authenticate();
        teller.getEmployeeData();

        return teller;
    }
}