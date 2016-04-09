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

import ar.edu.unrc.tdlearning.perceptron.interfaces.IActor;
import java.util.List;
import org.encog.engine.network.activation.ActivationFunction;
import org.encog.util.arrayutil.NormalizedField;

/**
 *
 * @author lucia bressan, franco pellegrini, renzo bianchini
 * @param <NeuralNetworkClass>
 */
public abstract class PerceptronConfigurationTicTacToe<NeuralNetworkClass> implements Cloneable, IConfigurationTicTacToe {

    /**
     *
     */
    public ActivationFunction[] activationFunctionForEncog;

    /**
     *
     */
    public double activationFunctionMax;

    /**
     *
     */
    public double activationFunctionMin;
    /**
     *
     */
    public int[] neuronQuantityInLayer;

    /**
     *
     */
    public NormalizedField normInput;

    /**
     *
     */
    public NormalizedField normOutput;

    //fin de la configuracion de la red neuronal
    private NeuralNetworkClass neuralNetwork;

    /**
     *
     * @param board
     * @param normalizedPerceptronInput <p>
     */
    public abstract void calculateNormalizedPerceptronInput(GameBoard board, List<Double> normalizedPerceptronInput);

    @Override
    public Object clone() throws CloneNotSupportedException {
        return super.clone(); //To change body of generated methods, choose Tools | Templates.
    }

    /**
     *
     * @param actor
     * @param output <p>
     * @return
     */
    public abstract Double computeNumericRepresentationFor(Object[] output, IActor actor);

    /**
     * @return the neuralNetwork
     */
    public NeuralNetworkClass getNeuralNetwork() {
        return neuralNetwork;
    }

    /**
     * @param neuralNetwork the neuralNetwork to set
     */
    public void setNeuralNetwork(NeuralNetworkClass neuralNetwork) {
        this.neuralNetwork = neuralNetwork;
    }

}
