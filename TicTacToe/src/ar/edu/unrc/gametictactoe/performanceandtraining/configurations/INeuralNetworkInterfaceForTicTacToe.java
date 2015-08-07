/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ar.edu.unrc.gametictactoe.performanceandtraining.configurations;

import ar.edu.unrc.gametictactoe.Action;
import ar.edu.unrc.gametictactoe.GameTicTacToe;
import ar.edu.unrc.gametictactoe.PerceptronConfigurationTicTacToe;
import ar.edu.unrc.tdlearning.perceptron.interfaces.IAction;
import ar.edu.unrc.tdlearning.perceptron.interfaces.IPerceptronInterface;
import ar.edu.unrc.tdlearning.perceptron.interfaces.IsolatedComputation;
import ar.edu.unrc.tdlearning.perceptron.learning.TDLambdaLearning;
import java.io.File;
import java.util.List;

/**
 *
 * @author Renzo Bianchini
 * @param <NeuralNetworkClass>
 */
public abstract class INeuralNetworkInterfaceForTicTacToe<NeuralNetworkClass> implements Cloneable {

    private PerceptronConfigurationTicTacToe<NeuralNetworkClass> perceptronConfiguration;

    /**
     *
     * @param perceptronConfiguration
     */
    public INeuralNetworkInterfaceForTicTacToe(PerceptronConfigurationTicTacToe<NeuralNetworkClass> perceptronConfiguration) {
        this.perceptronConfiguration = perceptronConfiguration;
    }

    @Override
    public Object clone() throws CloneNotSupportedException {
        return super.clone(); //To change body of generated methods, choose Tools | Templates.
    }

    /**
     *
     * @param randomFile
     * @param trainedFile
     */
    public abstract void compareNeuralNetworks(File randomFile, File trainedFile);

    /**
     *
     * @return
     */
    public abstract String getLibName();

    /**
     * @return the perceptronConfiguration
     */
    public PerceptronConfigurationTicTacToe<NeuralNetworkClass> getPerceptronConfiguration() {
        return perceptronConfiguration;
    }

    /**
     * @param perceptronConfiguration the perceptronConfiguration to set
     */
    public void setPerceptronConfiguration(PerceptronConfigurationTicTacToe<NeuralNetworkClass> perceptronConfiguration) {
        this.perceptronConfiguration = perceptronConfiguration;
    }

    /**
     *
     * @return
     */
    public abstract IPerceptronInterface getPerceptronInterface();

    /**
     * @param perceptronFile       <p>
     * @param randomizedIfNotExist <p>
     * @throws Exception
     */
    public abstract void loadOrCreatePerceptron(File perceptronFile, boolean randomizedIfNotExist) throws Exception;

    /**
     *
     * @param game
     * @param learningMethod metodo usado para entrenar y evaluar, o null si se
     *                       utiliza una IA al azar
     * <p>
     * @return
     */
    public IsolatedComputation playATurn(GameTicTacToe<NeuralNetworkClass> game, TDLambdaLearning learningMethod) {
        return () -> {
            // evaluamos cada accion aplicada al estado inicial y elegimos la mejor
            // accion basada en las predicciones del problema
            List<IAction> possibleActions = game.listAllPossibleActions(game.getBoard());
            Action bestAction = (Action) learningMethod.computeBestPossibleAction(game, game.getBoard(), possibleActions, null).compute();
            game.processInput(bestAction);

            return null;
        };
    }

    /**
     *
     * @param perceptronFile <p>
     * @throws Exception
     */
    public abstract void savePerceptron(File perceptronFile) throws Exception;

}
