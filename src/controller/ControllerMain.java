package controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import model.Model;

import java.awt.*;
import java.net.URL;
import java.util.ResourceBundle;

public class ControllerMain implements Initializable {


    @FXML public Label nplayer1; //the labels, order of graphical apparition
    @FXML public Label nplayer2;
    @FXML public Label nplayer3;
    @FXML public Label nplayer4;

    @FXML public Label playerLastBet;
    @FXML public Button lierButton;
    @FXML public Button betButton;
    @FXML public VBox vboxLierBet;
    @FXML public VBox vboxValidateBet;

    private String player1; //the players names, won't change
    private String player2;
    private String player3;
    private String player4;
    private Model model;

    public void initData(String player1Name, String player2Name, String player3Name, String player4Name){
        this.player1 = player1Name;
        this.player2 = player2Name;
        this.player3 = player3Name;
        this.player4 = player4Name;
        this.nplayer1.setText(player1Name);
        this.nplayer2.setText(player2Name);
        this.nplayer3.setText(player3Name);
        this.nplayer4.setText(player4Name);
        this.playerLastBet.setVisible(false);
    }




    @Override
    public void initialize(URL location, ResourceBundle resources) {
        this.model = new Model();
        this.lierButton.setDisable(true);
    }

    public void bet(ActionEvent actionEvent) {
        this.vboxLierBet.setDisable(true);
        this.vboxLierBet.setVisible(false);
        this.vboxValidateBet.setDisable(false);
        this.vboxValidateBet.setVisible(true);
    }
}
