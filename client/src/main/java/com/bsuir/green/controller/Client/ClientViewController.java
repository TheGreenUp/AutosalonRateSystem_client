package com.bsuir.green.controller.Client;

import com.bsuir.green.controller.UserLogInController;
import com.bsuir.green.utils.ViewUtils;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.stage.Stage;

import java.io.IOException;

public class ClientViewController {

    @FXML
    private Button makeRequestButton;
    @FXML
    private Button showResolutionButton;
    @FXML
    private Button exitFromAccButton;
    public void show(Stage stage) throws IOException {
        ViewUtils.loadView(stage, "client-view.fxml", "Главное меню");
    }
    public void onExitButton() throws IOException{
        new UserLogInController().show((Stage) exitFromAccButton.getScene().getWindow());
    }
    public void onShowResolutionButton(){

    }
    public void onMakeRequestButton() throws  IOException{
        new ClientCreateRequestController().show((Stage) exitFromAccButton.getScene().getWindow());
    }

    public void onDeleteEmployeeButton(){

    }
}