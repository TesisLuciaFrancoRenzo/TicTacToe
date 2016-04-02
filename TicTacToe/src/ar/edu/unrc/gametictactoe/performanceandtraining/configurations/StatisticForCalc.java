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

/**
 *
 * @author lucia bressan, franco pellegrini, renzo bianchini
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
