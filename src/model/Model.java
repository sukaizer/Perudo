package model;


import java.util.ArrayList;

public class Model {
    private final ArrayList<Player> players;
    private int turn;
    private Dice betValue; //The face
    private int betQuantity; //The number of betValue faces
    private boolean palifico; //If true then palifico round
    private boolean start; //If true then start of the round
    private boolean paco;

    public Model(){
        this.turn = 0;
        this.start = true;
        this.palifico = false;
        this.paco = false;
        this.players = new ArrayList<>(4);
        for (int i = 0; i < 4; i++) {
            players.add(new Player(this,i));
        }
    }

    public boolean isPalifico() {
        return palifico;
    }

    public boolean isStart() {
        return start;
    }

    public boolean isPaco() {
        return paco;
    }

    public int getTurn() {
        return turn;
    }

    /**
     * get to the next turn, if the next player
     * already lost, skips his turn
     */
    public void nextTurn(){
        if(this.turn == 3){
            this.turn = 1;
        }else{
            this.turn++;
            if (this.start && this.turn == 1) this.start = false;
        }
        if(!this.players.get(this.turn).getIsAlive()){
            nextTurn();
        }
    }

    public ArrayList<Player> getPlayers() {
        return players;
    }

    /**
     * reset the parameters for the next round
     */
    public void newRound(){
        int a = 0;
        for (Player p: this.players) {
            if(p.getIsAlive()) p.setNewDices();
            if(p.getJustLostADice()) this.turn = p.getNbPlayer();
            if(p.getJustLostADice() && p.getNumberDices() == 1) a++;
            p.setJustLostADice(false);
        }
        this.start = true;
        this.palifico = a == 1;
        this.paco = false;
    }

    public void setPaco(boolean paco){
        this.paco = paco;
    }

    public Dice getBetValue() {
        return betValue;
    }

    public void setBetValue(Dice betValue) {
        this.betValue = betValue;
    }

    public int getBetQuantity() {
        return betQuantity;
    }

    public void setBetQuantity(int betQuantity) {
        this.betQuantity = betQuantity;
    }


    /**
     * Checks if the given bet is valid
     * @param value the value of the dice
     * @param quantity quantity of dices
     * @param pacoValue value of the paco the player wants if DiceValue is a paco
     * @return true if the bet is valid
     */
    public boolean betIsValid(Dice value, int quantity, int pacoValue){
        boolean quantityUp = quantity > this.betQuantity;
        if (this.start) {
            if (this.palifico) {
                return (quantity <= this.totalNumberDices() && quantity > 0);
            } else {
                return (!value.equals(Dice.Paco) && quantity <= this.totalNumberDices() && quantity > 0);
            }

        }else if (this.palifico){
            if (this.paco){
                return (quantity > this.betQuantity && quantity <= this.totalNumberDices() && value.equals(Dice.Paco));
            }else{
                return (quantity > this.betQuantity && quantity <= this.totalNumberDices() && !value.equals(Dice.Paco));
            }
        }else{
            if (quantityUp) {
                if (this.paco){
                    return (quantity <= this.totalNumberDices() && value.equals(Dice.Paco)) || pacoSwitchConditionReverse(value,quantity);
                }else{
                    return quantity <= this.totalNumberDices() && value.equals(this.betValue);
                }
            } else {
                int total;
                int toCompare;
                if (this.paco){
                    total = diceValueConverter(this.betValue, pacoValue);
                    toCompare = diceValueConverter(value, pacoValue);
                    return (toCompare > total && this.betQuantity == quantity);

                }else{
                    total = diceValueConverter(this.betValue, 0);
                    toCompare = diceValueConverter(value, 0);
                    return (toCompare > total && this.betQuantity == quantity || pacoSwitchCondition(value,quantity));

                }
            }
        }
    }

    public boolean pacoSwitchCondition(Dice value, int quantity){
        return (quantity > Math.round((float)(this.betQuantity/2)) && value.equals(Dice.Paco));
    }

    public boolean pacoSwitchConditionReverse(Dice value, int quantity){
        System.out.println("test");
        return (quantity >= (2*this.betQuantity)+1 && !value.equals(Dice.Paco));
    }

    /**
     * Decides whether the bet is a lie or not
     * @param value value of the bet
     * @param quantity quantity of dices
     * @return true if the bet is a lie
     */
    public boolean lierValue(Dice value, int quantity){
        int n = 0;
        for (Player p : this.players) {
            for (Dice d : p.getDices()) {
                if (d.equals(value)) n++;
            }
        }
        return n > quantity;
    }

    public void setBet(Dice value, int quantity){
        this.betQuantity = quantity;
        this.betValue = value;
    }

    /**
     * converts the diceValue to enable comparison
     * @param value value of the dice
     * @param pacoValue the value we want for the paco
     * @return int, the value of the dice to compare
     */
    public int diceValueConverter(Dice value, int pacoValue){
        return switch (value) {
            case Deux -> 2;
            case Trois -> 3;
            case Quatre -> 4;
            case Cinq -> 5;
            case Six -> 6;
            case Paco -> pacoValue;
        };
    }

    /**
     * returns the total number of dices
     * @return int, the total number
     */
    public int totalNumberDices(){
        int numberDices = 0;
        for (Player p : this.players) {
            numberDices += p.getNumberDices();
        }
        return numberDices;
    }

}
