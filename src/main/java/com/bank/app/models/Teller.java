package com.bank.app.models;

import java.math.BigDecimal;

public class Teller extends Employee {
    public Teller(String username, String password) {
        super(username, password);
    }

    public Teller(String username) {
        super(username);
    }

    public Teller(String accountType, String username, String password, String name, String address, String phone, Boolean isActive) {
        super(accountType, username, password, name, address, phone, isActive);
    }

    public void createCustomerAccount (String accountType, String username, String password, String name, String address, String phone, BigDecimal accountBalance, Integer pin) {
        Customer customer = new Customer(accountType, username, password, name, address, phone, accountBalance, pin);
        customer.authenticate();
        customer.getCustomerData();
    }

    public void setIsActiveCustomerAccount (String username, Boolean isActive) {
        Customer customer = new Customer(username);
        customer.setIsActive(isActive);

        if (customer.isActive == null) {
            System.out.println("Gagal mengubah status rekening nasabah!");
        } else if (customer.isActive) {
            System.out.println("Berhasil buka rekening nasabah " + username + "!");
        } else {
            System.out.println("Berhasil tutup rekening nasabah " + username + "!");
        }
    }

    public void depositBalanceToCustomerAccount (String username, BigDecimal amount) {
        Customer customer = new Customer(username);
        customer.getCustomerData();
        customer.depositBalance(amount);
    }

    public void updateDataCustomerAccount (String username, String accountType, String newUsername, String password, String name, String address, String phone, Integer pin) {
        Customer customer = new Customer(username);
        customer.getCustomerData();

        customer.updateCustomerData(name, address, phone, newUsername, password);
        customer.updateCustomerBankAccount(accountType, pin);

        System.out.println("Berhasil mengupdate data nasabah!");
    }
}