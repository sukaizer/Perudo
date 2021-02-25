package controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import model.Dice;
import model.Model;
import model.Player;

import java.net.URL;
import java.util.ArrayList;
import java.util.Collections;
import java.util.ResourceBundle;

public class ControllerMain implements Initializable {


    @FXML public Label nplayer1; //the labels, order of graphical apparition
    @FXML public Label nplayer2;
    @FXML public Label nplayer3;
    @FXML public Label nplayer4;

    @FXML public Label lastBet;
    @FXML public Label playerLastBet;
    @FXML public Label labelChoice;
    @FXML public Label betIsValidLabel;
    @FXML public Label playerTurnLabel;
    @FXML public Label actualDice;

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
        setPlayerLabel();
        this.lastBet.setVisible(false);
        this.playerTurnLabel.setText( "Round du Joueur : " + playerTurnName());
        this.actualDice.setText(playerDices(this.model.getActualPlayer()));
    }


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        this.model = new Model();
        this.lierButton.setDisable(true);
        this.lastBet.setVisible(false);
        this.playerLastBet.setVisible(false);
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

    @FXML public void lierButtonAction(ActionEvent actionEvent) {
        if (this.model.lierValue()){
            this.model.getActualPlayer().loseDice();
            this.model.getActualPlayer().setJustLostADice(true);
        } else {
            this.model.getPreviousPlayer().loseDice();
            this.model.getPreviousPlayer().setJustLostADice(true);
        }
        this.model.newRound();

        setPlayerLabel();
        this.lierButton.setDisable(true);
        this.playerLastBet.setVisible(false);
        this.lastBet.setVisible(false);
        this.choiceBoxValue.getItems().remove(Dice.Paco);
        this.choiceBoxValue.setValue(null);
        this.spinnerQuantity.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(1,model.totalNumberDices()));
        this.playerTurnLabel.setText( "Round du Joueur : " + playerTurnName());
        this.actualDice.setText(playerDices(this.model.getActualPlayer()));
    }

    @FXML public void bet(ActionEvent actionEvent) {
        disablePanel(this.vboxLierBet,true);
        disablePanel(this.vboxValidateBet,false);
        this.labelChoice.setText("Quelle mise ?");
    }

    @FXML public void validateBet(ActionEvent actionEvent) {
        Dice value = this.choiceBoxValue.getValue();
        int quantity = this.spinnerQuantity.getValue();

        if (this.model.betIsValid(value,quantity,0)){
            this.betIsValidLabel.setVisible(false);
            this.model.setBet(value,quantity);
            this.lastBet.setText(quantity + " " + value.toString());
            this.playerLastBet.setText(playerTurnName());
            if (this.model.isStart()) {
                this.choiceBoxValue.getItems().add(Dice.Paco);
                this.lierButton.setDisable(false);
            }
            this.model.nextTurn();
            this.lastBet.setVisible(true);
            this.playerLastBet.setVisible(true);
            disablePanel(this.vboxLierBet,false);
            disablePanel(this.vboxValidateBet,true);
        } else {
            this.betIsValidLabel.setVisible(true);
        }
        this.playerTurnLabel.setText( "Round du Joueur : " + playerTurnName());
        this.actualDice.setText(playerDices(this.model.getActualPlayer()));
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

    public void disablePanel(Pane p, boolean disable){
        p.setDisable(disable);
        p.setVisible(!disable);
    }

    public String playerTurnName(){
        int a = this.model.getTurn();
        return switch (a){
            case 0 -> this.player1;
            case 1 -> this.player2;
            case 2 -> this.player3;
            case 3 -> this.player4;
            default -> throw new IllegalStateException("Unexpected value: " + a);
        };
    }

    public void setPlayerLabel(){
        this.nplayer1.setText(this.player1 + " : " + this.model.getPlayers().get(0).getNumberDices());
        this.nplayer2.setText(this.player2 + " : " + this.model.getPlayers().get(1).getNumberDices());
        this.nplayer3.setText(this.player3 + " : " + this.model.getPlayers().get(2).getNumberDices());
        this.nplayer4.setText(this.player4 + " : " + this.model.getPlayers().get(3).getNumberDices());
    }

    public String playerDices(Player player){
        ArrayList<Dice> dices = player.getDices();
        int paco = Collections.frequency(dices, Dice.Paco);
        int deux = Collections.frequency(dices, Dice.Deux);
        int trois = Collections.frequency(dices, Dice.Trois);
        int quatre = Collections.frequency(dices, Dice.Quatre);
        int cinq = Collections.frequency(dices, Dice.Cinq);
        int six = Collections.frequency(dices, Dice.Six);

        return paco + " paco " + deux + " deux " + trois + " trois " + quatre + " quatre " + cinq + " cinq " + six + " six ";
    }

}
