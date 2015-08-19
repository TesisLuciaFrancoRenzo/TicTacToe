/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ar.edu.unrc.gametictactoe.performanceandtraining.configurations;

/**
 *
 * @author Renzo Bianchini
 */
public class StatisticForCalc {
    private double drawRate;
    private double lossRate;

    private double maxTurn;
    private double meanTurn;
    private double minTurn;
    private double winRate;

    public double getDrawRate() {
        return drawRate;
    }

    public void setDrawRate(double drawRate) {
        this.drawRate = drawRate;
    }

    public double getLossRate() {
        return lossRate;
    }

    public void setLossRate(double lossRate) {
        this.lossRate = lossRate;
    }

    /**
     * @return the maxTurn
     */
    public double getMaxTurn() {
        return maxTurn;
    }

    /**
     * @param maxTurn the maxTurn to set
     */
    public void setMaxTurn(double maxTurn) {
        this.maxTurn = maxTurn;
    }

    /**
     * @return the meanTurn
     */
    public double getMeanTurn() {
        return meanTurn;
    }

    /**
     * @param meanTurn the meanTurn to set
     */
    public void setMeanTurn(double meanTurn) {
        this.meanTurn = meanTurn;
    }

    /**
     * @return the minTurn
     */
    public double getMinTurn() {
        return minTurn;
    }

    /**
     * @param minTurn the minTurn to set
     */
    public void setMinTurn(double minTurn) {
        this.minTurn = minTurn;
    }

    /**
     * @return the winRate
     */
    public double getWinRate() {
        return winRate;
    }

    /**
     * @param winRate the winRate to set
     */
    public void setWinRate(double winRate) {
        this.winRate = winRate;
    }

}
