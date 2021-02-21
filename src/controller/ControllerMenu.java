package controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class ControllerMenu implements Initializable {

    @FXML public Button button;
    @FXML public TextField textField1;
    @FXML public TextField textField2;
    @FXML public TextField textField3;
    @FXML public TextField textField4;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        button.setDisable(true);
    }

    @FXML
    public void playButtonAction(ActionEvent actionEvent) throws IOException {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("../ressources/fxmlFiles/Main.fxml"));
        Parent parent = loader.load();
        Node source = (Node) actionEvent.getSource();
        Stage stage = (Stage) source.getScene().getWindow();
        stage.getScene().setRoot(parent);
        ControllerMain controllerMain = loader.getController();
        controllerMain.initData(textField1.getText(),textField2.getText(),textField3.getText(),textField4.getText());
    }

    public void testButtonDisable(KeyEvent actionEvent) {
        if (textField1.getText().equals("") || textField2.getText().equals("") || textField3.getText().equals("") || textField4.getText().equals("")) {
            button.setDisable(true);
        } else if (!textField1.getText().equals("") && !textField2.getText().equals("") && !textField3.getText().equals("") && !textField4.getText().equals("")) {
            if(!textField1.getText().equals(textField2.getText()) && !textField2.getText().equals(textField3.getText()) && !textField3.getText().equals(textField4.getText()))
                button.setDisable(false);
        }
    }
}
