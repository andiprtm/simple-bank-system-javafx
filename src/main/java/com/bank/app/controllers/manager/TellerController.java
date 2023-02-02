package com.bank.app.controllers.manager;

import com.bank.app.ConnectionManager;
import com.bank.app.models.Manager;
import com.bank.app.models.Model;
import com.bank.app.models.TellerModel;
import com.bank.app.views.TellerCellFactory;
import javafx.collections.FXCollections;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class TellerController implements Initializable {
    Manager manager;
    Integer countRow;
    public ListView<TellerModel> listview_Teller;
    public String[] username2 = {"andi"};
    public TellerModel[] tellerModel = new TellerModel[username2.length];
    public Label tv_say_hi;
    Connection conn = ConnectionManager.getInstance().getConnection();

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }

    public void setManager(Manager manager) {
        this.manager = manager;
        String[] name = manager.name.split(" ");

        tv_say_hi.setText("Hi, " + name[0]);
    }

    public void setCountRow() {
        try {
            PreparedStatement ps = conn.prepareStatement("""
                    SELECT COUNT(*) as total_rows
                    FROM (
                             SELECT ed.id_employee, ed.username, ed.password, ed.name, ed.address, ed.phone, ed.employee_is_active, eat.employee_account_type
                             FROM employee_data ed, employee_account_type eat
                             WHERE eat.id_employee_account_type=ed.employee_account_type_id AND ed.employee_account_type_id=1
                    ) as total;""");

            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                this.countRow = rs.getInt("total_rows");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void setItem(){
        try {
            PreparedStatement ps = conn.prepareStatement("""
                    SELECT ed.id_employee, ed.username, ed.password, ed.name, ed.address, ed.phone, IF(ed.employee_is_active, 'Active', 'Not Active') as employee_is_active, eat.employee_account_type
                    FROM employee_data ed, employee_account_type eat
                    WHERE eat.id_employee_account_type=ed.employee_account_type_id AND ed.employee_account_type_id=1;""");

            ResultSet rs = ps.executeQuery();

            TellerModel[] tellerModel = new TellerModel[this.countRow];

            int i = 0;
            while (rs.next() && i < this.countRow) {
                tellerModel[i] = new TellerModel(rs.getString("id_employee"),rs.getString("username"),rs.getString("password"),rs.getString("name"),rs.getString("address"),rs.getString("phone"), rs.getString("employee_account_type"),rs.getString("employee_is_active"));
                i++;
            }

            listview_Teller.setCellFactory(tellerListView -> new TellerCellFactory());

            listview_Teller.setItems(FXCollections.observableArrayList(tellerModel));

            listview_Teller.getSelectionModel().selectedItemProperty().addListener((observableValue, oldValue, newValue) -> {
                System.out.println(newValue.phoneProperty().getValue());
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/manager/DetailTeller.fxml"));
                Scene scene = null;
                try {
                    scene = new Scene(loader.load());
                } catch (IOException e) {
                    e.printStackTrace();
                }
                DetailTellerController detailTellerController = loader.getController();
                detailTellerController.setManager(manager);
                detailTellerController.setData(
                        newValue.idPegawaiProperty().getValue(),
                        newValue.usernameProperty().getValue(),
                        newValue.passwordProperty().getValue(),
                        newValue.namaProperty().getValue(),
                        newValue.addressProperty().getValue(),
                        newValue.phoneProperty().getValue(),
                        newValue.accountTypeProperty().getValue(),
                        newValue.statusProperty().getValue()
                );
                Stage stage2 = new Stage();
                stage2.setScene(scene);
                stage2.show();

                Stage stage = (Stage) listview_Teller.getScene().getWindow();
                stage.close();
            });
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
