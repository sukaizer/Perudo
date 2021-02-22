package controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;
import model.Dice;
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
    @FXML public Label labelChoice;
    @FXML public Label betIsValidLabel;
    @FXML public Button lierButton;
    @FXML public Button betButton;
    @FXML public Button validateBetButton;
    @FXML public VBox vboxLierBet;
    @FXML public VBox vboxValidateBet;
    @FXML public Spinner<Integer> spinnerQuantity;
    @FXML public ChoiceBox<Dice> choiceBoxValue;

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
        this.choiceBoxValue.getItems().add(Dice.Deux);
        this.choiceBoxValue.getItems().add(Dice.Trois);
        this.choiceBoxValue.getItems().add(Dice.Quatre);
        this.choiceBoxValue.getItems().add(Dice.Cinq);
        this.choiceBoxValue.getItems().add(Dice.Six);
        this.spinnerQuantity.getStyleClass().add(Spinner.STYLE_CLASS_SPLIT_ARROWS_HORIZONTAL);
        this.spinnerQuantity.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(1,model.totalNumberDices()));
        this.validateBetButton.setDisable(true);
        this.betIsValidLabel.setVisible(false);
    }

    @FXML public void bet(ActionEvent actionEvent) {
        this.vboxLierBet.setDisable(true);
        this.vboxLierBet.setVisible(false);
        this.vboxValidateBet.setDisable(false);
        this.vboxValidateBet.setVisible(true);
        this.labelChoice.setText("Quelle mise ?");
    }

    @FXML public void validateBet(ActionEvent actionEvent) {
        Dice value = this.choiceBoxValue.getValue();
        int quantity = this.spinnerQuantity.getValue();

        if (this.model.betIsValid(value,quantity,0)){
            this.model.setPaco(value.equals(Dice.Paco));
            System.out.println(this.model.isPaco());
            this.betIsValidLabel.setVisible(false);
            this.model.setBet(value,quantity);
            if (this.model.isStart()) this.choiceBoxValue.getItems().add(Dice.Paco);
            this.model.nextTurn();
        } else {
            this.betIsValidLabel.setVisible(true);
        }

    }

    @FXML public void choiceBoxOnAction(MouseEvent mouseEvent) {
        try{
            this.validateBetButton.setDisable(this.choiceBoxValue.getValue() == null);
        }catch(NullPointerException e){
            this.validateBetButton.setDisable(true);
        }
    }

    @FXML public void spinnerOnAction(MouseEvent mouseEvent) {
        try{
            this.validateBetButton.setDisable(this.choiceBoxValue.getValue() == null);
        }catch(NullPointerException e){
            this.validateBetButton.setDisable(true);
        }
    }
}
