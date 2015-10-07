/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ar.edu.unrc.gametictactoe.heuristic.ai;

import ar.edu.unrc.gametictactoe.Action;
import ar.edu.unrc.gametictactoe.GameBoard;

/**
 *
 * @author franc
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
