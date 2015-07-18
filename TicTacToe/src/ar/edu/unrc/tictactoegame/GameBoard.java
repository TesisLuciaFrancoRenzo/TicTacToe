/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ar.edu.unrc.tictactoegame;

import ar.edu.unrc.tdlearning.perceptron.interfaces.IState;
import ar.edu.unrc.tdlearning.perceptron.interfaces.IStatePerceptron;
import ar.edu.unrc.tdlearning.perceptron.interfaces.IsolatedComputation;
import java.util.ArrayList;

/**
 *
 * @author Renzo Bianchini
 */
public class GameBoard implements IStatePerceptron {

    private ArrayList OIndexList;
    private ArrayList XIndexList;
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

    public ArrayList<Integer> getAllPossibleMovements() {
        ArrayList libres = (ArrayList) this.squares.clone();
        libres.removeAll(OIndexList);
        libres.removeAll(XIndexList);
        return libres;
    }

    public int[][] getWinIndexes() {
        return winIndexes;
    }

    public GameBoard() {
        this.squares = new ArrayList(9);
        OIndexList = new ArrayList(5);
        XIndexList = new ArrayList(5);
    }

    @Override
    public IState getCopy() {
        GameBoard copy = new GameBoard();
        for ( int i = 0; i < OIndexList.size(); i++ ) {
            copy.OIndexList.add(i, OIndexList.get(i));
        }
        for ( int i = 0; i < XIndexList.size(); i++ ) {
            copy.XIndexList.add(i, XIndexList.get(i));
        }
        for ( int i = 0; i < squares.size(); i++ ) {
            copy.squares.add(i, new Square(squares.get(i)));
        }
        return copy;
    }

    /**
     * @return the OIndexList
     */
    public ArrayList getOIndexList() {
        return OIndexList;
    }

    /**
     * @param OIndexList the OIndexList to set
     */
    public void setOIndexList(ArrayList OIndexList) {
        this.OIndexList = OIndexList;
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

    /**
     * @return the XIndexList
     */
    public ArrayList getXIndexList() {
        return XIndexList;
    }

    /**
     * @param XIndexList the XIndexList to set
     */
    public void setXIndexList(ArrayList XIndexList) {
        this.XIndexList = XIndexList;
    }

    @Override
    public boolean isTerminalState() {
        ArrayList winList = new ArrayList();
        for ( int i = 0; i < getWinIndexes().length; i++ ) {
            winList.add(winIndexes[i][0]);
            winList.add(winIndexes[i][1]);
            winList.add(winIndexes[i][2]);
            if ( OIndexList.containsAll(winList) ) {
                return true;
            } else if ( XIndexList.containsAll(winList) ) {
                return true;
            }
            winList.clear();
        }
        return OIndexList.size() == 5 && XIndexList.size() == 4;
    }

    @Override
    public IsolatedComputation<Double> translateToPerceptronInput(int neuronIndex) {
        return () -> {
            return ((Square) this.squares.get(neuronIndex)).getPaintType() * 1d;
        };
    }

    @Override
    public double getStateReward(int outputNeuron) {
        return 0d;
    }

}
