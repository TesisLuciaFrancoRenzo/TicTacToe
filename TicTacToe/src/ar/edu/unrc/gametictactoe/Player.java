/*
 * Copyright (C) 2016  Lucia Bressan <lucyluz333@gmial.com>,
 *                     Franco Pellegrini <francogpellegrini@gmail.com>,
 *                     Renzo Bianchini <renzobianchini85@gmail.com
 *
 *   This program is free software: you can redistribute it and/or modify
 *   it under the terms of the GNU General Public License as published by
 *   the Free Software Foundation, either version 3 of the License, or
 *   (at your option) any later version.
 *
 *   This program is distributed in the hope that it will be useful,
 *   but WITHOUT ANY WARRANTY; without even the implied warranty of
 *   MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *   GNU General Public License for more details.
 *
 *   You should have received a copy of the GNU General Public License
 *   along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package ar.edu.unrc.gametictactoe;

import ar.edu.unrc.tdlearning.perceptron.interfaces.IActor;

/**
 *
 * @author Jox, lucia bressan, franco pellegrini, renzo bianchini
 */
public class Player implements IActor {

    private String name;
    private int score;
    private Token token;
    private final Players type;
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
