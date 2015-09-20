/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
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

    public int getDraws() {
        return draws;
    }

    public void setDraws(int draws) {
        this.draws = draws;
    }

    public int getPlayer1wins() {
        return player1wins;
    }

    public void setPlayer1wins(int player1wins) {
        this.player1wins = player1wins;
    }

    public int getPlayer2wins() {
        return player2wins;
    }

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
     *
     * @param value
     */
//    public void setProcesedGames(int value) {
//        procesedGames = value;
//    }
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
