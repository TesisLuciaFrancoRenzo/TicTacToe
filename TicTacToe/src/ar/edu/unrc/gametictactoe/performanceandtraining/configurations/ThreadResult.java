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
package ar.edu.unrc.gametictactoe.performanceandtraining.configurations;

import ar.edu.unrc.gametictactoe.Players;

/**
 *
 * @author lucia bressan, franco pellegrini, renzo bianchini
 */
public class ThreadResult {

    private int draws;

    private int player1wins;
    private int player2wins;
    private int procesedGames;
//    private int maxTurn;
//    private int minTurn;
//    private double totalTurn;

    /**
     *
     */
    public ThreadResult() {
        player1wins = 0;
        player2wins = 0;
        draws = 0;
//        totalTurn = 0;
//        maxTurn = 0;
//        minTurn = 9;
    }

//    public double getTotalTurn() {
//        return totalTurn;
//    }
//
//    public void setTotalTurn(double totalTurn) {
//        this.totalTurn = totalTurn;
//    }
//
//    public void addLastTurn(int lastTurn) {
//        assert lastTurn != 0;
//        totalTurn += lastTurn;
//        if ( lastTurn > maxTurn ) {
//            maxTurn = lastTurn;
//        }
//        if ( lastTurn < minTurn ) {
//            minTurn = lastTurn;
//        }
//    }
    /**
     *
     */
    public void addProcesedGames() {
        procesedGames++;
    }

    /**
     *
     * @return
     */
    public int getDraws() {
        return draws;
    }

    /**
     *
     * @param draws
     */
    public void setDraws(int draws) {
        this.draws = draws;
    }

    /**
     *
     * @return
     */
    public int getPlayer1wins() {
        return player1wins;
    }

    /**
     *
     * @param player1wins
     */
    public void setPlayer1wins(int player1wins) {
        this.player1wins = player1wins;
    }

    /**
     *
     * @return
     */
    public int getPlayer2wins() {
        return player2wins;
    }

    /**
     *
     * @param player2wins
     */
    public void setPlayer2wins(int player2wins) {
        this.player2wins = player2wins;
    }

    /**
     */
//    public void addWinPlayer1() {
//        player1wins++;
//    }
//
//    /**
//     */
//    public void addWinPlayer2() {
//        player2wins++;
//    }
    /**
     * @return the procesedGames
     */
    public int getProcesedGames() {
        return procesedGames;
    }

    /**
     * @param procesedGames
     */
    public void setProcesedGames(int procesedGames) {
        this.procesedGames = procesedGames;
    }

    /**
     * @return the winGamesPlayer1
     */
    public double getWinRatePlayer1() {
        return (player1wins * 100d) / (procesedGames * 1d);
    }

    /**
     * @return the winGamesPlayer2
     */
    public double getWinRatePlayer2() {
        return (player2wins * 100d) / (procesedGames * 1d);
    }

    /**
     * @return the drawGames
     */
    public double getdrawRate() {
        return (draws * 100d) / (procesedGames * 1d);
    }

    /**
     */
    void addWinner(Players winner) {
        switch ( winner ) {
            case PLAYER1:
                player1wins++;
                break;
            case PLAYER2:
                player2wins++;
                break;
            case DRAW:
                draws++;
                break;
            default:
                throw new IllegalArgumentException("Unknown player: " + winner);
        }
    }
}
