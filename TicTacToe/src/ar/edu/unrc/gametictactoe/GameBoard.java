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

    private ArrayList player2IndexList;
    private ArrayList player1IndexList;
    private ArrayList<Square> squares;
    private final int[][] winIndexes = {
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
     */
    public GameBoard() {
        this.squares = new ArrayList(9);
        player2IndexList = new ArrayList(5);
        player1IndexList = new ArrayList(5);
    }

    @Override
    public IState getCopy() {
        GameBoard copy = new GameBoard();
        for ( int i = 0; i < player2IndexList.size(); i++ ) {
            copy.player2IndexList.add(i, player2IndexList.get(i));
        }
        for ( int i = 0; i < player1IndexList.size(); i++ ) {
            copy.player1IndexList.add(i, player1IndexList.get(i));
        }
        for ( int i = 0; i < squares.size(); i++ ) {
            copy.squares.add(i, new Square(squares.get(i)));
        }
        return copy;
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
    public int[][] getWinIndexes() {
        return winIndexes;
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

    @Override
    public boolean isTerminalState() {//TODO: calcularlo solo una vez cuando haya cambois en el tablero y actualizar una variable de estado
        ArrayList winList = new ArrayList();
        for ( int i = 0; i < getWinIndexes().length; i++ ) {
            winList.add(winIndexes[i][0]);
            winList.add(winIndexes[i][1]);
            winList.add(winIndexes[i][2]);
            if ( player2IndexList.containsAll(winList) ) {
                return true;
            } else if ( player1IndexList.containsAll(winList) ) {
                return true;
            }
            winList.clear();
        }
        return player2IndexList.size() + player1IndexList.size() == 9;
    }

    @Override
    public IsolatedComputation<Double> translateToPerceptronInput(int neuronIndex) {
        return () -> {
            return ((Square) this.squares.get(neuronIndex)).getPaintType().getRepresentation() * 1d;
        };
    }

    void reset() {
        this.player2IndexList.clear();
        this.player1IndexList.clear();
        this.squares.stream().forEach((s) -> {
            s.reset();
        });
    }

}
