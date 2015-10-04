/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ar.edu.unrc.gametictactoe;

/**
 *
 * @author Renzo Bianchini
 */
public interface IConfigurationTicTacToe {

    /**
     *
     * @param value <p>
     * @return
     */
    public abstract double denormalizeValueFromPerceptronOutput(Object value);

    /**
     *
     * @param board        <p>
     * @param outputNeuron <p>
     * @return
     */
    public abstract double getBoardReward(GameBoard board, int outputNeuron);

    /**
     *
     * @param board
     * @param outputNeuron <p>
     * @return
     */
    public abstract double getFinalReward(GameBoard board, int outputNeuron);

    /**
     *
     * @param value <p>
     * @return
     */
    public abstract double normalizeValueToPerceptronOutput(Object value);

}
