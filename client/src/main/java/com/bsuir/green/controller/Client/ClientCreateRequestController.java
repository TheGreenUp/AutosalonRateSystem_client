package com.bsuir.green.controller.Client;

import com.bsuir.green.Client;
import com.bsuir.green.common.command.CreateDetailCommand;
import com.bsuir.green.common.command.CreateRequestCommand;
import com.bsuir.green.common.command.GetDetailCommand;
import com.bsuir.green.common.command.StuffListCommand;
import com.bsuir.green.common.model.Detail;
import com.bsuir.green.common.model.Request;
import com.bsuir.green.common.model.Stuff;
import com.bsuir.green.common.response.CreateDetailResponse;
import com.bsuir.green.common.response.CreateRequestResponse;
import com.bsuir.green.common.response.GetDetailResponse;
import com.bsuir.green.common.response.StuffListResponse;
import com.bsuir.green.enums.DetailType;
import com.bsuir.green.utils.ViewUtils;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class ClientCreateRequestController implements Initializable {
    @FXML
    TextField detailName;
    @FXML
    ComboBox<String> cbDetailType = new ComboBox();
    @FXML
    ComboBox cbChosenStuff = new ComboBox<>();
    @FXML
    Button backButton;
    @FXML
    Button createRequest;


    static com.bsuir.green.common.model.Client currentClient = null;
    static Detail currentDetail = null;
    static ArrayList<Stuff> stuffArrayList = new ArrayList<>();


    public void show(Stage stage, com.bsuir.green.common.model.Client client) throws IOException {
        currentClient = client;
        ViewUtils.loadView(stage, "client-make-request.fxml", "Создание запроса");
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        cbDetailType.getItems().addAll(DetailType.getLables());//закидываем значения в комбобокс с типами деталей
        //region Инициализируем comboBox с сотрудниками

        StuffListCommand stuffListCommand = new StuffListCommand();
        Client.writeObject(stuffListCommand);
        Object response = Client.readObject();
        if (response instanceof StuffListResponse) {
            stuffArrayList = ((StuffListResponse) response).getStuff();
        }
        cbChosenStuff.getItems().addAll(stuffArrayList);
        //endregion
    }

    public void onCreateRequestButton() {
        createDetail();
        getDetail(new Detail(cbDetailType.getValue(), detailName.getText()));
        Stuff chosenStuff = (Stuff)cbChosenStuff.getValue();
        CreateRequestCommand createRequestCommand = new CreateRequestCommand(
                new Request(currentClient.getId(), currentDetail.getId(), chosenStuff.getId()));
        Client.writeObject(createRequestCommand);
        Object response = Client.readObject();
        if (response instanceof CreateRequestResponse) {
            //todo поп-ап "Все гуд"
        }

    }

    public void onBackButton() throws IOException {
        new ClientViewController().show((Stage) backButton.getScene().getWindow(), currentClient);
    }

    public void createDetail() {
        CreateDetailCommand createDetailCommand =
                new CreateDetailCommand(new Detail(cbDetailType.getValue(), detailName.getText()));
        Client.writeObject(createDetailCommand);
        Object response = Client.readObject();
        if (response instanceof CreateDetailResponse) {
            //todo good
        }
    }

    public void getDetail(Detail detail) {
        GetDetailCommand getDetailCommand = new GetDetailCommand(detail);
        Client.writeObject(getDetailCommand);
        Object response = Client.readObject();
        if (response instanceof GetDetailResponse) {
            currentDetail = ((GetDetailResponse) response).getDetail();
        }
    }
}
