/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ar.edu.unrc.gametictactoe.performanceandtraining.configurations.perceptrons;

import ar.edu.unrc.gametictactoe.GameBoard;
import ar.edu.unrc.gametictactoe.GameTicTacToe;
import ar.edu.unrc.gametictactoe.PerceptronConfigurationTicTacToe;
import ar.edu.unrc.tdlearning.perceptron.interfaces.IsolatedComputation;
import java.util.List;

/**
 *
 * @author Renzo Bianchini
 */
public class ConfigurationTicTacToe<NeuralNetworkClass> extends PerceptronConfigurationTicTacToe<NeuralNetworkClass> {

    @Override
    public IsolatedComputation calculateNormalizedPerceptronInput(GameBoard board, List<Double> normalizedPerceptronInput) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public IsolatedComputation<Double> computeNumericRepresentationFor(GameTicTacToe game, Object[] output) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public double denormalizeValueFromPerceptronOutput(Object value) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public double getBoardReward(GameBoard board, int outputNeuron) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public double getFinalReward(GameTicTacToe game, int outputNeuron) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public double normalizeValueToPerceptronOutput(Object value) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

}
