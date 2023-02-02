package com.bank.app.models;

import com.bank.app.ConnectionManager;

import java.sql.*;

public class Employee {
    public Integer employeeId;
    public String accountType;
    public String username;
    public String password;
    public String name;
    public String address;
    public String phone;
    public Boolean isActive;
    Connection conn = ConnectionManager.getInstance().getConnection();

    public Employee(String username, String password){
        this.username = username;
        this.password = password;
    }

    public Employee(String username) {
        try {
            PreparedStatement ps = conn.prepareStatement(
                    "SELECT ed.id_employee FROM employee_data ed WHERE username=?;"
            );

            ps.setString(1, username);

            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                this.employeeId = rs.getInt("id_employee");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public Employee(String accountType, String username, String password, String name, String address, String phone, Boolean isActive) {
        Integer accountTypeId = getEmployeeAccountTypeId(accountType);

        if (accountTypeId == null) {
            System.out.println("Tipe akun employee tidak ditemukan!");
        } else {
            try {
                PreparedStatement ps = conn.prepareStatement(
                        "INSERT INTO employee_data (name, address, phone, employee_account_type_id, employee_is_active, username, password) VALUES (?, ?, ?, ?, ?, ?, ?);",
                        Statement.RETURN_GENERATED_KEYS
                );

                ps.setString(1, name);
                ps.setString(2, address);
                ps.setString(3, phone);
                ps.setInt(4, accountTypeId);
                ps.setBoolean(5, isActive);
                ps.setString(6, username);
                ps.setString(7, password);

                int affectedRows = ps.executeUpdate();

                if (affectedRows == 0) {
                    throw new SQLException("Gagal menambahkan data!");
                }

                try (ResultSet generatedKeys = ps.getGeneratedKeys()) {
                    if (generatedKeys.next()) {
                        int newEmployeeId = generatedKeys.getInt(1);
                        this.name = name;
                        this.address = address;
                        this.phone = phone;
                        this.accountType = accountType;
                        this.isActive = isActive;
                        this.employeeId = newEmployeeId;
                    }
                    else {
                        throw new SQLException("Gagal mendapatkan id!");
                    }
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }

        }
    }

    public void authenticate() {
        try {
            PreparedStatement ps = conn.prepareStatement(
                    """
                            SELECT ed.id_employee, ed.employee_is_active, eat.employee_account_type
                            FROM employee_data ed, employee_account_type eat
                            WHERE username=? AND password=? AND ed.employee_account_type_id=eat.id_employee_account_type;"""
            );

            ps.setString(1, this.username);
            ps.setString(2, this.password);

            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                this.employeeId = rs.getInt("id_employee");
                this.isActive = rs.getBoolean("employee_is_active");
                this.accountType = rs.getString("employee_account_type");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void getEmployeeData(){
        try {
            PreparedStatement ps = conn.prepareStatement(
                    """
                            SELECT ed.username, ed.password, ed.name, ed.phone, ed.address, ed.employee_is_active, (SELECT employee_account_type FROM employee_account_type WHERE id_employee_account_type=ed.employee_account_type_id) AS account_type
                            FROM employee_data ed
                            WHERE ed.id_employee=?;"""
            );

            ps.setInt(1, this.employeeId);

            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                this.username = rs.getString("username");
                this.password = rs.getString("password");
                this.accountType = rs.getString("account_type");
                this.name = rs.getString("name");
                this.address = rs.getString("address");
                this.phone = rs.getString("phone");
                this.isActive = rs.getBoolean("employee_is_active");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public Integer getEmployeeAccountTypeId (String accountType) {
        Integer employeeAccountTypeId = null;

        try {
            PreparedStatement ps = conn.prepareStatement(
                    "SELECT eat.id_employee_account_type FROM employee_account_type eat WHERE eat.employee_account_type=?;"
            );

            ps.setString(1, accountType);

            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                employeeAccountTypeId = rs.getInt("id_employee_account_type");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return employeeAccountTypeId;
    }

    public void setIsActive (Boolean isActive) {
        try {
            PreparedStatement ps = conn.prepareStatement(
                    "UPDATE employee_data ed SET ed.employee_is_active=? WHERE id_employee=?;",
                    Statement.RETURN_GENERATED_KEYS
            );

            ps.setBoolean(1, isActive);
            ps.setInt(2, this.employeeId);

            int affectedRows = ps.executeUpdate();

            if (affectedRows == 0) {
                throw new SQLException("Gagal mengupdate data!");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void updateEmployeeData(String accountType, String username, String password, String name, String address, String phone, Boolean isActive) {
        Integer accountTypeId = getEmployeeAccountTypeId(accountType);

        if (accountTypeId != null) {
            try {
                PreparedStatement ps = conn.prepareStatement(
                        "UPDATE employee_data ed SET name=?, phone=?, address=?, employee_account_type_id=?, employee_is_active=?, username=?, password=? WHERE id_employee=?;",
                        Statement.RETURN_GENERATED_KEYS
                );

                ps.setString(1, name);
                ps.setString(2, phone);
                ps.setString(3, address);
                ps.setInt(4, accountTypeId);
                ps.setBoolean(5, isActive);
                ps.setString(6, username);
                ps.setString(7, password);
                ps.setInt(8, this.employeeId);

                int affectedRows = ps.executeUpdate();

                if (affectedRows == 0) {
                    throw new SQLException("Gagal mengupdate data!");
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        } else {
            System.out.println("Tipe akun tidak ditemukan!");
        }
    }
}
