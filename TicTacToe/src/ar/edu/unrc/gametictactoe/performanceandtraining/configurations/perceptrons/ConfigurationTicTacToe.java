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
import org.encog.engine.network.activation.ActivationFunction;
import org.encog.engine.network.activation.ActivationTANH;

/**
 *
 * @author Renzo Bianchini
 * @param <NeuralNetworkClass>
 */
public class ConfigurationTicTacToe<NeuralNetworkClass> extends PerceptronConfigurationTicTacToe<NeuralNetworkClass> {

    public ConfigurationTicTacToe() {

        this.neuronQuantityInLayer = new int[3];
        neuronQuantityInLayer[0] = 9;
        neuronQuantityInLayer[1] = 9;
        neuronQuantityInLayer[2] = 1;

        this.activationFunctionForEncog = new ActivationFunction[2];

        activationFunctionForEncog[0] = new ActivationTANH();
        activationFunctionForEncog[1] = new ActivationTANH(); //cambiar por la sigmoidea

        activationFunctionMax = 1;
        activationFunctionMin = -1;

    }

    @Override
    public IsolatedComputation calculateNormalizedPerceptronInput(GameBoard board, List<Double> normalizedPerceptronInput) {
        return () -> {
            for ( int i = 0; i < 9; i++ ) {
                normalizedPerceptronInput.set(i, (double) board.getSquares().get(i).getPaintType().getRepresentation());
            }
            return null;
        };
//throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public IsolatedComputation<Double> computeNumericRepresentationFor(GameTicTacToe game, Object[] output) {
        return () -> {
            assert output.length == 1;
            return (Double) output[0];
        };
    }

    @Override
    public double denormalizeValueFromPerceptronOutput(Object value) {
        return (Double) value;
    }

    @Override
    public double getBoardReward(GameBoard board, int outputNeuron) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

//    @Override
//    public double getFinalReward(GameTicTacToe game, int outputNeuron) {
//        return game.getFinalReward(outputNeuron);
//    }
    @Override
    public double normalizeValueToPerceptronOutput(Object value) {
        return (Double) value;
    }

}
