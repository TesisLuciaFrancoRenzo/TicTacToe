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
    private double winRatePlayer1;
    private double winRatePlayer2;

    /**
     *
     * @return
     */
    public double getDrawRate() {
        return drawRate;
    }

    /**
     *
     * @param drawRate
     */
    public void setDrawRate(double drawRate) {
        this.drawRate = drawRate;
    }

    /**
     *
     * @return
     */
    public double getWinRatePlayer1() {
        return winRatePlayer1;
    }

    /**
     *
     * @param winRatePlayer1
     */
    public void setWinRatePlayer1(double winRatePlayer1) {
        this.winRatePlayer1 = winRatePlayer1;
    }

    /**
     *
     * @return
     */
    public double getWinRatePlayer2() {
        return winRatePlayer2;
    }

    /**
     *
     * @param winRatePlayer2
     */
    public void setWinRatePlayer2(double winRatePlayer2) {
        this.winRatePlayer2 = winRatePlayer2;
    }

}
