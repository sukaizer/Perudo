package controller;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import model.Player;

import java.net.URL;
import java.util.ResourceBundle;

public class ControllerWon implements Initializable {

    @FXML public Label winningLabel;
    private Player winningPlayer;

    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }

    public void initData(Player winningPlayer) {
        this.winningPlayer = winningPlayer;
        this.winningLabel.setText("Le joueur dénommé " + this.winningPlayer.getName() + " a gagné");
    }



}
