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
    private Players type;
    private boolean winner;

    /**
     *
     * @param name
     * @param score
     * @param token
     * @param type
     */
    public Player(String name, int score, Token token, Players type) {
        this.name = name;
        this.score = score;
        this.token = token;
        this.type = type;
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
    public Players getType() {
        return type;
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
