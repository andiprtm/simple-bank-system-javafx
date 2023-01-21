package com.bank.app.controllers.manager;

import com.bank.app.models.Model;
import com.bank.app.models.TellerModel;
import com.bank.app.views.TellerCellFactory;
import javafx.collections.FXCollections;
import javafx.fxml.Initializable;
import javafx.scene.control.ListView;

import java.net.URL;
import java.util.ResourceBundle;

public class TellerController implements Initializable {
    public ListView<TellerModel> listview_Teller;

    public String[] username2 = {"andi","budi","caca","didi","eni","fifi","gigi","haha","iini","jaja","kiki","lili","mimi","nini","ono","papi","qiqi","riri","sisi","titi","uui","vivi"};
    public String[] phone = {"08123456789","08123456788","08123456787","08123456786","08123456785","08123456784","08123456783","08123456782","08123456781","08123456780","08123456779","08123456778","08123456777","08123456776","08123456775","08123456774","08123456773","08123456772","08123456771","08123456770","08123456769","08123456768"};
    public String[] address = {"Jl. A","Jl. B","Jl. C","Jl. D","Jl. E","Jl. F","Jl. G","Jl. H","Jl. I","Jl. J","Jl. K","Jl. L","Jl. M","Jl. N","Jl. O","Jl. P","Jl. Q","Jl. R","Jl. S","Jl. T","Jl. U","Jl. V"};
    public String[] status = {"aktif","aktif","aktif","aktif","aktif","aktif","aktif","aktif","aktif","aktif","aktif","aktif","aktif","aktif","aktif","aktif","aktif","aktif","aktif","aktif","aktif","aktif"};
    public String[] accountType = {"Manager","Teller","Manager","Teller","Manager","Teller","Manager","Teller","Manager","Teller","Manager","Teller","Manager","Teller","Manager","Teller","Manager","Teller","Manager","Teller","Manager","Teller"};
    public TellerModel[] tellerModel = new TellerModel[username2.length];

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        setItem();
    }

    public void setItem(){
        for(int i = 0; i < username2.length; i++){
            tellerModel[i] = new TellerModel(username2[i], phone[i], address[i], status[i], accountType[i]);
        }

        listview_Teller.setCellFactory(tellerListView -> new TellerCellFactory());

        listview_Teller.setItems(FXCollections.observableArrayList(tellerModel));

        listview_Teller.getSelectionModel().selectedItemProperty().addListener((observableValue, oldValue, newValue) -> {
            System.out.println(newValue.phoneProperty().getValue());
            Model.getInstance().getViewFactory().showDetailTellerWindow();
        });
    }
}
