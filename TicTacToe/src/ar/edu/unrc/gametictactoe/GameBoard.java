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

import static ar.edu.unrc.gametictactoe.Action.S0;
import static ar.edu.unrc.gametictactoe.Action.S1;
import static ar.edu.unrc.gametictactoe.Action.S2;
import static ar.edu.unrc.gametictactoe.Action.S3;
import static ar.edu.unrc.gametictactoe.Action.S4;
import static ar.edu.unrc.gametictactoe.Action.S5;
import static ar.edu.unrc.gametictactoe.Action.S6;
import static ar.edu.unrc.gametictactoe.Action.S7;
import static ar.edu.unrc.gametictactoe.Action.S8;
import ar.edu.unrc.tdlearning.perceptron.interfaces.IAction;
import ar.edu.unrc.tdlearning.perceptron.interfaces.IState;
import ar.edu.unrc.tdlearning.perceptron.interfaces.IStatePerceptron;
import java.util.ArrayList;

/**
 *
 * @author lucia bressan, franco pellegrini, renzo bianchini
 */
public class GameBoard implements IStatePerceptron {

    private static final int[][] winIndexes = {
        {0, 1, 2},
        {3, 4, 5},
        {6, 7, 8},
        {0, 3, 6},
        {1, 4, 7},
        {2, 5, 8},
        {0, 4, 8},
        {2, 4, 6}
    };

    /**
     *
     * @param action <p>
     * @return
     */
    public static int actionToSquareIndex(Action action) {
        switch ( action ) {
            case S0: {
                return 0;
            }
            case S1: {
                return 1;
            }
            case S2: {
                return 2;
            }
            case S3: {
                return 3;
            }
            case S4: {
                return 4;
            }
            case S5: {
                return 5;
            }
            case S6: {
                return 6;
            }
            case S7: {
                return 7;
            }
            case S8: {
                return 8;
            }
            default:
                throw new IllegalArgumentException();
        }
    }

    /**
     *
     * @param squareIndex <p>
     * @return
     */
    public static Action squareIndexToAction(int squareIndex) {
        switch ( squareIndex ) {
            case 0: {
                return S0;
            }
            case 1: {
                return S1;
            }
            case 2: {
                return S2;
            }
            case 3: {
                return S3;
            }
            case 4: {
                return S4;
            }
            case 5: {
                return S5;
            }
            case 6: {
                return S6;
            }
            case 7: {
                return S7;
            }
            case 8: {
                return S8;
            }
            default:
                throw new IllegalArgumentException();
        }
    }
    private int creationTurn;
    private Player currentPlayer;

    private final Player player1;
    private Action player1Action;
    private ArrayList<Integer> player1IndexList;
    private final Player player2;
    private Action player2Action;
    private ArrayList<Integer> player2IndexList;
    private ArrayList<Square> squares;
    private int turn;
    private Players winner;

    /**
     *
     * @param player1
     * @param player2
     * @param squares
     */
    public GameBoard(Player player1, Player player2, ArrayList<Square> squares) {
        this.squares = squares;
        player2IndexList = new ArrayList<>(5);
        player1IndexList = new ArrayList<>(5);
        this.player1 = player1;
        this.player2 = player2;
        this.currentPlayer = player1;
        this.turn = 1;
        this.player1Action = null;
        this.player2Action = null;
        this.winner = Players.NONE;
        this.creationTurn = 0;
    }

    /**
     *
     * @return
     */
    public long encrypt() {
        //TODO hacer assert y JUNIT de esto SI o SI
        long hash = creationTurn;
        for ( int i = 0; i < player1IndexList.size(); i++ ) {
            assert player1IndexList.get(i) + 1 < 10;
            hash += (player1IndexList.get(i) + 1) * Math.pow(10, i + 1);
        }
        for ( int i = 0; i < player2IndexList.size(); i++ ) {
            assert player2IndexList.get(i) + 1 < 10;
            hash += (player2IndexList.get(i) + 1) * Math.pow(10, player1IndexList.size() + i + 3);
        }
//        if ( hash >= 100 && (!Long.toString(hash).contains("00") || Long.toString(hash).contains("000")) ) {
//            System.out.println("");
//        }
        return hash;
    }

    @Override
    public IState getCopy() {
        GameBoard copy = new GameBoard(player1, player2, new ArrayList(9));
        for ( int i = 0; i < player2IndexList.size(); i++ ) {
            copy.player2IndexList.add(i, player2IndexList.get(i));
        }
        for ( int i = 0; i < player1IndexList.size(); i++ ) {
            copy.player1IndexList.add(i, player1IndexList.get(i));
        }
        for ( int i = 0; i < squares.size(); i++ ) {
            copy.squares.add(i, new Square(squares.get(i)));
        }
        copy.turn = this.turn;
        copy.currentPlayer = this.currentPlayer;
        copy.player1Action = this.player1Action;
        copy.player2Action = this.player2Action;
        copy.winner = this.winner;
        copy.creationTurn = this.creationTurn;
        return copy;
    }

    /**
     *
     * @return
     */
    public Player getCurrentPlayer() {
        return currentPlayer;
    }

    /**
     *
     * @return
     */
    public Player getEnemyPlayer() {
        assert currentPlayer != null;
        if ( currentPlayer.equals(player1) ) {
            return player2;
        } else {
            return player1;
        }
    }

    /**
     *
     * @return
     */
    public Player getPlayer1() {
        return player1;
    }

    /**
     *
     * @return
     */
    public Action getPlayer1Action() {
        return player1Action;
    }

    /**
     *
     * @param player1Action
     */
    public void setPlayer1Action(Action player1Action) {
        this.player1Action = player1Action;
    }

    /**
     * @return the player1IndexList
     */
    public ArrayList<Integer> getPlayer1IndexList() {
        return player1IndexList;
    }

    /**
     * @param player1IndexList the player1IndexList to set
     */
    public void setPlayer1IndexList(ArrayList<Integer> player1IndexList) {
        this.player1IndexList = player1IndexList;
    }

    /**
     *
     * @return
     */
    public Player getPlayer2() {
        return player2;
    }

    /**
     *
     * @return
     */
    public Action getPlayer2Action() {
        return player2Action;
    }

    /**
     *
     * @param player2Action
     */
    public void setPlayer2Action(Action player2Action) {
        this.player2Action = player2Action;
    }

    /**
     * @return the player2IndexList
     */
    public ArrayList<Integer> getPlayer2IndexList() {
        return player2IndexList;
    }

    /**
     * @param Player2IndexList the player2IndexList to set
     */
    public void setPlayer2IndexList(ArrayList<Integer> Player2IndexList) {
        this.player2IndexList = Player2IndexList;
    }

    /**
     * @return the squares
     */
    public ArrayList<Square> getSquares() {
        return squares;
    }

    /**
     * @param squares the squares to set
     */
    public void setSquares(ArrayList<Square> squares) {
        this.squares = squares;
    }

    @Override
    public double getStateReward(int outputNeuron) {//TODO Que se lo pregunte al configuration tictactoe
        return 0d;
    }

    /**
     *
     * @return
     */
    public int getTurn() {
        return turn;
    }

    /**
     *
     * @return
     */
    public int[][] getWinIndexes() {
        return winIndexes;
    }

    /**
     *
     * @return
     */
    public Players getWinner() {
        return winner;
    }

    /**
     *
     * @param winner
     */
    public void setWinner(Players winner) {
        this.winner = winner;
    }

    @Override
    public boolean isTerminalState() {
        return winner != Players.NONE;
    }

    /**
     *
     * @return
     */
    public ArrayList<IAction> listAllPossibleActions() {
        ArrayList<IAction> possibles = new ArrayList<>(squares.size());
        for ( int i = 0; i < squares.size(); i++ ) {
            if ( !squares.get(i).isClicked() ) {
                possibles.add(GameBoard.squareIndexToAction(i));
            }
        }
        return possibles;
    }

    /**
     *
     * @param actualSquareIndex
     */
    public void pickSquare(int actualSquareIndex) {
        Square actualSquare = squares.get(actualSquareIndex);
        assert !actualSquare.isClicked();
        actualSquare.setPaintType(currentPlayer.getToken());
        if ( currentPlayer.getToken() == Token.O ) {
            player2IndexList.add(0, actualSquareIndex);
        } else {
            player1IndexList.add(0, actualSquareIndex);
        }
        actualSquare.setClicked();
        computeWhoWin();
        nextTurn();
        creationTurn++;
    }

    /**
     *
     * @param action <p>
     * @return
     */
    public int pickSquare(Action action) {
        int actualSquareIndex = actionToSquareIndex(action);
        pickSquare(actualSquareIndex);
        return actualSquareIndex;
    }

    @Override
    public Double translateToPerceptronInput(int neuronIndex) {
        return ((Square) this.squares.get(neuronIndex)).getPaintType().getRepresentation() * 1d;
    }

    /**
     *
     * @return
     */
    private Players computeWhoWin() {
        ArrayList winList = new ArrayList();
        for ( int i = 0; i < getWinIndexes().length; i++ ) {
            winList.add(winIndexes[i][0]);
            winList.add(winIndexes[i][1]);
            winList.add(winIndexes[i][2]);
            if ( player2IndexList.containsAll(winList) ) {
                assert this.currentPlayer.equals(player2);
                winner = Players.PLAYER2;
                return Players.PLAYER2;
            } else if ( player1IndexList.containsAll(winList) ) {
                assert this.currentPlayer.equals(player1);
                winner = Players.PLAYER1;
                return Players.PLAYER1;
            }
            winList.clear();
        }
        if ( player2IndexList.size() + player1IndexList.size() == 9 ) {
            winner = Players.DRAW;
            return Players.DRAW;
        }
        return Players.NONE;
    }

    /**
     *
     */
    private void nextTurn() {
        //winner = computeWhoWin();
        if ( winner == Players.NONE ) {
            turn++;
            if ( currentPlayer.equals(player1) ) {
                this.currentPlayer = player2;
            } else {
                this.currentPlayer = player1;
            }
        }
    }

    void printLastActions(Player playerToTrain) {
        if ( playerToTrain.equals(player1) ) {
            System.out.print("* ");
            System.out.println(player1Action);
            if ( player2Action != null ) {
                System.out.println(player2Action);
            }
        }
        if ( playerToTrain.equals(player2) ) {
            if ( player2Action != null ) {
                System.out.print("* ");
                System.out.println(player2Action);
            }
            if ( player1Action != null ) {
                System.out.println(player1Action);
            }
        }
        player1Action = null;
        player2Action = null;
    }

    void reset() {
        this.player2IndexList.clear();
        this.player1IndexList.clear();
        this.squares.stream().forEach((s) -> {
            s.reset();
        });
        this.turn = 1;
        this.currentPlayer = player1;
        this.player1Action = null;
        this.player2Action = null;
        this.winner = Players.NONE;
    }

}
