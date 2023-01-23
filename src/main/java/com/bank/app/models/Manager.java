package com.bank.app.models;

public class Manager extends Employee {

    public Manager(String username, String password) {
        super(username, password);
    }

    public void createTellerAccount(String username, String password, String name, String address, String phone) {
        Teller teller = new Teller("Teller", username, password, name, address, phone, true);
        System.out.println();

        System.out.println("Akun Teller berhasil di buat!");

        System.out.println();
        System.out.println("accountType: " + teller.accountType);
        System.out.println("username: " + teller.username);
        System.out.println("password: " + teller.password);
        System.out.println("address: " + teller.address);
        System.out.println("phone: " + teller.phone);
        System.out.println("isActive: " + teller.isActive);
    }

    public void setIsActiveTellerAccount(String username, Boolean isActive) {
        Teller teller = new Teller(username);
        teller.setIsActive(isActive);
    }

    public void updateTellerDataAccount(String username, String accountType, String newUsername, String password, String name, String address, String phone, Boolean isActive) {
        Teller teller = new Teller(username);
        teller.updateEmployeeData(accountType, newUsername,password, name, address, phone, isActive);
    }
}