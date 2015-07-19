/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ar.edu.unrc.tictactoegame;

import ar.edu.unrc.tdlearning.perceptron.interfaces.IActor;

/**
 *
 * @author Jox
 */
public class Player implements IActor {

    private String name;
    private int score;
    private int token;
    private boolean winner;

    /**
     *
     * @param name
     * @param score
     * @param token
     */
    public Player(String name, int score, int token) {
        this.name = name;
        this.score = score;
        this.token = token;
    }

    /**
     *
     * @return
     */
    public String getName() {
        return name;
    }

    /**
     *
     * @param name
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     *
     * @return
     */
    public int getScore() {
        return score;
    }

    /**
     *
     * @param score
     */
    public void setScore(int score) {
        this.score = score;
    }

    /**
     *
     * @return
     */
    public int getToken() {
        return token;
    }

    /**
     *
     * @param token
     */
    public void setToken(int token) {
        this.token = token;
    }

    /**
     *
     * @return
     */
    public boolean isWinner() {
        return winner;
    }

    /**
     *
     * @param winner
     */
    public void setWinner(boolean winner) {
        this.winner = winner;
    }
}
