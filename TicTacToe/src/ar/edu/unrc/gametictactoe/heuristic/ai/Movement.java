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
package ar.edu.unrc.gametictactoe.heuristic.ai;

import ar.edu.unrc.gametictactoe.Action;
import ar.edu.unrc.gametictactoe.GameBoard;

/**
 *
 * @author lucia bressan, franco pellegrini, renzo bianchini
 */
public class Movement {

    private Action actionToCreateBoard;
    private GameBoard board;
    private int drawCount;
    private int winPlayer1count;
    private int winPlayer2count;

    /**
     *
     * @param board
     * @param actionToCreateBoard
     */
    public Movement(GameBoard board, Action actionToCreateBoard) {
        this.board = board;
        this.actionToCreateBoard = actionToCreateBoard;
        winPlayer1count = 0;
        drawCount = 0;
        winPlayer2count = 0;
    }

    /**
     *
     */
    public void addDraw() {
        drawCount++;
    }

    /**
     *
     * @param value
     */
    public void addDraw(int value) {
        drawCount += value;
    }

    /**
     *
     */
    public void addPlayer1Win() {
        winPlayer1count++;
    }

    /**
     *
     * @param value
     */
    public void addPlayer1Win(int value) {
        winPlayer1count += value;
    }

    /**
     *
     */
    public void addPlayer2Win() {
        winPlayer2count++;
    }

    /**
     *
     * @param value
     */
    public void addPlayer2Win(int value) {
        winPlayer2count += value;
    }

    /**
     * @return the actionToCreateBoard
     */
    public Action getActionToCreateBoard() {
        return actionToCreateBoard;
    }

    /**
     * @param actionToCreateBoard the actionToCreateBoard to set
     */
    public void setActionToCreateBoard(Action actionToCreateBoard) {
        this.actionToCreateBoard = actionToCreateBoard;
    }

    /**
     * @return the board
     */
    public GameBoard getBoard() {
        return board;
    }

    /**
     * @param board the board to set
     */
    public void setBoard(GameBoard board) {
        this.board = board;
    }

    /**
     * @return the drawCount
     */
    public int getDrawCount() {
        return drawCount;
    }

    /**
     * @return the winPlayer1count
     */
    public int getWinPlayer1count() {
        return winPlayer1count;
    }

    /**
     * @return the winPlayer2count
     */
    public int getWinPlayer2count() {
        return winPlayer2count;
    }
}
