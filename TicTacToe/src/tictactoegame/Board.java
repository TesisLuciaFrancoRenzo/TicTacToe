/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tictactoegame;

import ar.edu.unrc.tdlearning.perceptron.interfaces.IState;
import ar.edu.unrc.tdlearning.perceptron.interfaces.IStatePerceptron;
import ar.edu.unrc.tdlearning.perceptron.interfaces.IsolatedComputation;
import java.util.ArrayList;

/**
 *
 * @author Renzo Bianchini
 */
public class Board implements IStatePerceptron {

    private ArrayList OIndexList;
    private ArrayList XIndexList;
    private ArrayList<Square> squares;

    public Board() {
        this.squares = new ArrayList(9);
        OIndexList = new ArrayList(5);
        XIndexList = new ArrayList(5);
    }

    @Override
    public IState getCopy() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
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

    @Override
    public double getStateReward(int outputNeuron) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
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
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public IsolatedComputation<Double> translateToPerceptronInput(int neuronIndex) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

}
