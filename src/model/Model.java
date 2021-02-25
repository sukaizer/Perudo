package model;


import java.util.ArrayList;

public class Model {
    private final ArrayList<Player> players;
    private int turn;
    private Dice betValue; //The face
    private int betQuantity; //The number of betValue faces
    private boolean palifico; //If true then palifico round
    private boolean start; //If true then start of the round

    public Model() {
        this.turn = 0;
        this.start = true;
        this.palifico = false;
        this.players = new ArrayList<>(4);
        for (int i = 0; i < 4; i++) {
            players.add(new Player(i));
        }
    }

    public boolean isPalifico() {
        return palifico;
    }

    public boolean isStart() {
        return start;
    }

    public int getTurn() {
        return turn;
    }

    /**
     * get to the next turn, if the next player
     * already lost, skips his turn
     */
    public void nextTurn() {
        if (this.turn == 3) {
            this.turn = 1;
        } else {
            if (this.start && this.turn == firstTurn()) this.start = false;
            this.turn++;
        }
        if (!this.players.get(this.turn).getIsAlive()) {
            nextTurn();
        }
    }

    public int firstTurn() {
        for (int i = 0; i < 4; i++) {
            if (this.players.get(i).getIsAlive()) return i;
        }
        return 3;
    }

    public int previousTurn() {
        if (this.turn == 0) {
            return 3;
        } else {
            return this.turn - 1;
        }
    }

    public ArrayList<Player> getPlayers() {
        return players;
    }

    public Player getActualPlayer() {
        return players.get(this.turn);
    }

    public Player getPreviousPlayer() {
        return players.get(previousTurn());
    }

    /**
     * reset the parameters for the next round
     */
    public void newRound() {
        int a = 0;
        for (Player p : this.players) {
            if (p.getIsAlive()) p.setNewDices();
            if (p.getJustLostADice()) this.turn = p.getNbPlayer();
            if (p.getJustLostADice() && p.getNumberDices() == 1) a++;
            p.setJustLostADice(false);
        }
        this.start = true;
        this.palifico = a == 1;
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
     *
     * @param value     the value of the dice
     * @param quantity  quantity of dices
     * @param pacoValue value of the paco the player wants if DiceValue is a paco
     * @return true if the bet is valid
     */
    public boolean betIsValid(Dice value, int quantity, int pacoValue) {
        boolean quantityUp = quantity > this.betQuantity;
        if (this.start) {
            if (this.palifico) {
                return (quantity <= this.totalNumberDices() && quantity > 0);
            } else {
                return (!value.equals(Dice.Paco) && quantity <= this.totalNumberDices() && quantity > 0);
            }

        } else if (this.palifico) {
            if (this.betValue.equals(Dice.Paco)) {
                return (quantity > this.betQuantity && quantity <= this.totalNumberDices() && value.equals(Dice.Paco));
            } else {
                return (quantity > this.betQuantity && quantity <= this.totalNumberDices() && !value.equals(Dice.Paco));
            }
        } else {
            if (quantityUp) {
                if (this.betValue.equals(Dice.Paco)) {
                    return (quantity <= this.totalNumberDices() && value.equals(Dice.Paco)) || pacoSwitchConditionReverse(value, quantity);
                } else {
                    return quantity <= this.totalNumberDices() && value.equals(this.betValue);
                }
            } else {
                int total;
                int toCompare;
                if (this.betValue.equals(Dice.Paco)) {
                    return false;
                } else {
                    total = diceValueConverter(this.betValue, 0);
                    toCompare = diceValueConverter(value, 0);
                    return (toCompare > total && this.betQuantity == quantity || pacoSwitchCondition(value, quantity));

                }
            }
        }
    }

    public boolean pacoSwitchCondition(Dice value, int quantity) {
        return (quantity >= Math.round((float) (this.betQuantity / 2)) && value.equals(Dice.Paco));
    }

    public boolean pacoSwitchConditionReverse(Dice value, int quantity) {
        System.out.println("test2");
        return (quantity >= (2 * this.betQuantity) + 1 && !value.equals(Dice.Paco));
    }

    /**
     * Decides whether the bet is a lie or not
     *
     * @return true if the bet is a lie
     */
    public boolean lierValue() {
        //TODO
        int n = 0;
        for (Player p : this.players) {
            for (Dice d : p.getDices()) {
                if (d.equals(this.betValue)) n++;
            }
        }
        return n > this.betQuantity;
    }

    public void setBet(Dice value, int quantity) {
        this.betQuantity = quantity;
        this.betValue = value;
    }

    /**
     * converts the diceValue to enable comparison
     *
     * @param value     value of the dice
     * @param pacoValue the value we want for the paco
     * @return int, the value of the dice to compare
     */
    public int diceValueConverter(Dice value, int pacoValue) {
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
     *
     * @return int, the total number
     */
    public int totalNumberDices() {
        int numberDices = 0;
        for (Player p : this.players) {
            numberDices += p.getNumberDices();
        }
        return numberDices;
    }

}
