/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package tictactoegame;

import ar.edu.unrc.tdlearning.perceptron.interfaces.IActor;

/**
 *
 * @author Jox
 */
public class Player implements IActor {

    private String name;
    private int score;
    private boolean winner;

    public Player(String name, int score) {
        this.name = name;
        this.score = score;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public boolean isWinner() {
        return winner;
    }

    public void setWinner(boolean winner) {
        this.winner = winner;
    }
}
