/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ar.edu.unrc.gametictactoe;

import ar.edu.unrc.tdlearning.perceptron.interfaces.IState;
import ar.edu.unrc.tdlearning.perceptron.interfaces.IStatePerceptron;
import ar.edu.unrc.tdlearning.perceptron.interfaces.IsolatedComputation;
import java.util.ArrayList;

/**
 *
 * @author Renzo Bianchini
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
    private Player currentPlayer;

    private final PlayPanel playPanel;
    private Player player1;
    private ArrayList player1IndexList;
    private Player player2;
    private ArrayList player2IndexList;
    private ArrayList<Square> squares;
    private int turn;
    Action player1Action;
    Action player2Action;

    /**
     *
     * @param player1
     * @param player2
     * @param playPanel
     */
    public GameBoard(Player player1, Player player2, PlayPanel playPanel) {
        this.squares = new ArrayList(9);
        player2IndexList = new ArrayList(5);
        player1IndexList = new ArrayList(5);
        this.player1 = player1;
        this.player2 = player2;
        this.currentPlayer = player1;
        this.turn = 1;
        this.playPanel = playPanel;
        this.player1Action = null;
        this.player2Action = null;
    }

    @Override
    public IState getCopy() {
        GameBoard copy = new GameBoard(player1, player2, playPanel);
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
    public ArrayList getPlayer1IndexList() {
        return player1IndexList;
    }

    /**
     * @param player1IndexList the player1IndexList to set
     */
    public void setPlayer1IndexList(ArrayList player1IndexList) {
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
    public ArrayList getPlayer2IndexList() {
        return player2IndexList;
    }

    /**
     * @param Player2IndexList the player2IndexList to set
     */
    public void setPlayer2IndexList(ArrayList Player2IndexList) {
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
    public double getStateReward(int outputNeuron) {
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
    @Override
    public boolean isTerminalState() {
        return whoWin() != Players.NONE;
    }

    /**
     *
     * @param isThinking
     */
    public void nextTurn(boolean isThinking) {
        Players winner = whoWin();
        if ( winner != Players.NONE ) {
            if ( !isThinking ) {
                playPanel.endGame(this, winner);
            }
        } else {
            turn++;
            if ( currentPlayer.equals(player1) ) {
                this.currentPlayer = player2;
            } else {
                this.currentPlayer = player1;
            }
        }
    }

    @Override
    public IsolatedComputation<Double> translateToPerceptronInput(int neuronIndex) {
        return () -> {
            return ((Square) this.squares.get(neuronIndex)).getPaintType().getRepresentation() * 1d;
        };
    }

    /**
     *
     * @return
     */
    public Players whoWin() {
        ArrayList winList = new ArrayList();
        for ( int i = 0; i < getWinIndexes().length; i++ ) {
            winList.add(winIndexes[i][0]);
            winList.add(winIndexes[i][1]);
            winList.add(winIndexes[i][2]);
            if ( player2IndexList.containsAll(winList) ) {
                assert this.currentPlayer.equals(player2);
                return Players.PLAYER2;
            } else if ( player1IndexList.containsAll(winList) ) {
                assert this.currentPlayer.equals(player1);
                return Players.PLAYER1;
            }
            winList.clear();
        }
        if ( player2IndexList.size() + player1IndexList.size() == 9 ) {
            return Players.DRAW;
        }
        return Players.NONE;
    }

//    void printLastActions(Player playerToTrain) {
//        if ( playerToTrain.equals(player1) ) {
//            System.out.print("* ");
//        }
//        System.out.println(player1Action);
//
//        if ( player2Action != null ) {
//            if ( playerToTrain.equals(player2) ) {
//                System.out.print("* ");
//            }
//            System.out.println(player2Action);
//        }
//    }
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
    }

}
