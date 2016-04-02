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
package ar.edu.unrc.gametictactoe.performanceandtraining.configurations.perceptrons;

import ar.edu.unrc.gametictactoe.GameBoard;
import ar.edu.unrc.gametictactoe.PerceptronConfigurationTicTacToe;
import ar.edu.unrc.gametictactoe.Player;
import ar.edu.unrc.gametictactoe.Players;
import ar.edu.unrc.tdlearning.perceptron.interfaces.IActor;
import ar.edu.unrc.tdlearning.perceptron.interfaces.IsolatedComputation;
import java.util.List;
import org.encog.engine.network.activation.ActivationFunction;
import org.encog.engine.network.activation.ActivationTANH;

/**
 *
 * @author lucia bressan, franco pellegrini, renzo bianchini
 * @param <NeuralNetworkClass>
 */
public class ConfigurationTicTacToe<NeuralNetworkClass> extends PerceptronConfigurationTicTacToe<NeuralNetworkClass> {

    /**
     *
     */
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
    public IsolatedComputation<Double> computeNumericRepresentationFor(Object[] output, IActor actor) {
        return () -> {
            assert output.length == 1;
            return (Double) output[0] * ((Player) actor).getToken().getRepresentation();
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

    @Override
    public double getFinalReward(GameBoard board, int outputNeuron) {
        Players winner = board.getWinner();
        switch ( winner ) {
            case DRAW:
                return 0;
            case PLAYER1:
                return board.getPlayer1().getToken().getRepresentation();
            case PLAYER2:
                //System.out.println(winner);
                return board.getPlayer2().getToken().getRepresentation();
            default:
                //System.err.println("");
                throw new IllegalStateException("El estado deberia ser un estado final, y el resultado fue: " + winner);
        }
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
