/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ar.edu.unrc.gametictactoe.performanceandtraining.configurations;

import ar.edu.unrc.gametictactoe.Players;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author lucia bressan, franco pellegrini, renzo bianchini pellegrini
 */
public class ThreadResult {

    private int draws;

    private double maxScore;

    private double maxTurn;
    private double minScore;
    private double minTurn;
    private int player1wins;
    private int player2wins;

    private int procesedGames;
    private final List<Integer> tileStatistics;
    private double totalScore;
    private int winGames = 0;
    double totalTurn;

    /**
     *
     */
    public ThreadResult() {
        winGames = 0;
        tileStatistics = new ArrayList<>(18);
        for ( int i = 0; i <= 17; i++ ) {
            tileStatistics.add(0);
        }
        totalScore = 0;
        totalTurn = 0;
        maxScore = 0;
        minScore = Integer.MAX_VALUE;
        maxTurn = 0;
        minTurn = Integer.MAX_VALUE;
    }

    public void addLastTurn(int lastTurn) {
        assert lastTurn != 0;
        totalTurn += lastTurn;
        if ( lastTurn > maxTurn ) {
            maxTurn = lastTurn;
        }
        if ( lastTurn < minTurn ) {
            minTurn = lastTurn;
        }
    }

    /**
     *
     */
    public void addProcesedGames() {
        procesedGames++;
    }

    /**
     *
     * @param score
     */
    public void addScore(double score) {
        totalScore += score;
        if ( score > getMaxScore() ) {
            maxScore = score;
        }
        if ( score < getMinScore() ) {
            minScore = score;
        }
    }

    /**
     */
    public void addWin() {
        winGames++;
    }

    /**
     * @return the maxScore
     */
    public double getMaxScore() {
        return maxScore;
    }

    /**
     * @return the maxTurn
     */
    public double getMaxTurn() {
        if ( winGames > 0 ) {
            return maxTurn;
        } else {
            return 0;
        }
    }

    /**
     * @return the maxScore
     */
    public double getMeanScore() {
        return totalScore / (procesedGames * 1d);
    }

    /**
     * @return the maxScore
     */
    public double getMeanTurn() {
        if ( winGames > 0 ) {
            return totalTurn / (winGames * 1d);
        } else {
            return 0;
        }
    }

    /**
     * @return the minScore
     */
    public double getMinScore() {
        return minScore;
    }

    /**
     * @return the minTurn
     */
    public double getMinTurn() {
        if ( winGames > 0 ) {
            return minTurn;
        } else {
            return 0;
        }
    }

    /**
     * @return the procesedGames
     */
    public int getProcesedGames() {
        return procesedGames;
    }

    /**
     *
     * @param value
     */
    public void setProcesedGames(int value) {
        procesedGames = value;
    }

    /**
     *
     * @param tileCode <p>
     * @return
     */
    public Integer getStatisticForTile(int tileCode) {
        return tileStatistics.get(tileCode);
    }

    /**
     * @return the winGames
     */
    public double getWinRate() {
        return (winGames * 100d) / (procesedGames * 1d);
    }

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
