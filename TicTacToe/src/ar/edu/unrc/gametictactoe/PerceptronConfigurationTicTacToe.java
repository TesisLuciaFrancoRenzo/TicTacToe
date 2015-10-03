/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ar.edu.unrc.gametictactoe;

import ar.edu.unrc.tdlearning.perceptron.interfaces.IActor;
import ar.edu.unrc.tdlearning.perceptron.interfaces.IsolatedComputation;
import java.util.List;
import org.encog.engine.network.activation.ActivationFunction;
import org.encog.util.arrayutil.NormalizedField;

/**
 *
 * @author Renzo Bianchini
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
     * @return
     */
    public abstract IsolatedComputation calculateNormalizedPerceptronInput(GameBoard board, List<Double> normalizedPerceptronInput);

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
    public abstract IsolatedComputation<Double> computeNumericRepresentationFor(Object[] output, IActor actor);

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
