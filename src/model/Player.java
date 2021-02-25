package model;

import java.util.ArrayList;
import java.util.Random;

public class Player {
    private String name;
    private ArrayList<Dice> dices;
    private final static int MAXNUMBERDICES = 5; //maximum number of dices
    private int numberDices; //actual number of dices
    private boolean isAlive;
    private boolean justLostADice;
    private final int nbPlayer; // player's id

    public Player(int nbPlayer) {
        this.name = "";
        this.numberDices = MAXNUMBERDICES;
        this.dices = new ArrayList<>();
        for (int i = 0; i < MAXNUMBERDICES; i++) {
            int pick = new Random().nextInt(Dice.values().length);
            dices.add(Dice.values()[pick]);
        }
        this.nbPlayer = nbPlayer;
        this.isAlive = true;
        this.justLostADice = false;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public ArrayList<Dice> getDices() {
        return dices;
    }

    public boolean getJustLostADice() {
        return justLostADice;
    }

    public void setJustLostADice(boolean justLostADice) {
        this.justLostADice = justLostADice;
    }

    public int getNbPlayer() {
        return nbPlayer;
    }

    public boolean getIsAlive() {
        return isAlive;
    }

    public void setIsAlive(boolean isAlive) {
        this.isAlive = isAlive;
    }

    public int getNumberDices() {
        return numberDices;
    }

    /**
     * Remove a dice from the player
     */
    public void loseDice() {
        System.out.println("joueur " + this.nbPlayer + " a " + this.dices.size());
        this.dices.remove(0);
        this.numberDices--;
        if (this.dices.size() == 0) {
            setIsAlive(false);
        }
    }

    /**
     * Add a dice
     */
    public void gainDice() {
        if (this.dices.size() < MAXNUMBERDICES - 1) {
            this.dices.add(Dice.values()[new Random().nextInt(Dice.values().length)]);
            this.numberDices++;
        }
    }

    /**
     * Set new dices
     */
    public void setNewDices() {
        this.dices = new ArrayList<>();
        for (int i = 0; i < this.numberDices; i++) {
            int pick = new Random().nextInt(Dice.values().length);
            dices.add(Dice.values()[pick]);
        }
    }
}
