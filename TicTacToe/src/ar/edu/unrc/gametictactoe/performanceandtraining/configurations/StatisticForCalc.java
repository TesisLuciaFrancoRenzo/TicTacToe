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

    public double getDrawRate() {
        return drawRate;
    }

    public void setDrawRate(double drawRate) {
        this.drawRate = drawRate;
    }

    public double getWinRatePlayer1() {
        return winRatePlayer1;
    }

    public void setWinRatePlayer1(double winRatePlayer1) {
        this.winRatePlayer1 = winRatePlayer1;
    }

    public double getWinRatePlayer2() {
        return winRatePlayer2;
    }

    public void setWinRatePlayer2(double winRatePlayer2) {
        this.winRatePlayer2 = winRatePlayer2;
    }

}
