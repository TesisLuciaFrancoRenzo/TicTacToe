/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ar.edu.unrc.gametictactoe;

import ar.edu.unrc.tdlearning.perceptron.interfaces.IActor;

/**
 *
 * @author Jox
 */
public class Player implements IActor {

    private String name;
    private int score;
    private Token token;
    private boolean winner;

    /**
     *
     * @param name
     * @param score
     * @param token
     */
    public Player(String name, int score, Token token) {
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
    public Token getToken() {
        return token;
    }

    /**
     *
     * @param token
     */
    public void setToken(Token token) {
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

    @Override
    public String toString() {
        return name + ", score=" + score + ", ficha=" + token;
    }

}
