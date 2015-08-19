/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ar.edu.unrc.gametictactoe.performanceandtraining.configurations.libraries;

import ar.edu.unrc.gametictactoe.Action;
import ar.edu.unrc.gametictactoe.GameTicTacToe;
import ar.edu.unrc.gametictactoe.PerceptronConfigurationTicTacToe;
import ar.edu.unrc.gametictactoe.performanceandtraining.configurations.INeuralNetworkInterfaceForTicTacToe;
import ar.edu.unrc.tdlearning.perceptron.interfaces.IPerceptronInterface;
import ar.edu.unrc.tdlearning.perceptron.interfaces.IsolatedComputation;
import ar.edu.unrc.tdlearning.perceptron.learning.TDLambdaLearning;
import java.io.File;
import java.util.ArrayList;

/**
 *
 * @author lucia bressan, franco pellegrini, renzo bianchini
 */
public class RandomExperimentInterface extends INeuralNetworkInterfaceForTicTacToe {

    /**
     *
     * @param perceptronConfiguration
     */
    public RandomExperimentInterface(PerceptronConfigurationTicTacToe perceptronConfiguration) {
        super(perceptronConfiguration);
    }

    /**
     *
     * @param randomFile
     * @param trainedFile
     */
    @Override
    public void compareNeuralNetworks(File randomFile, File trainedFile) {
    }

    @Override
    public String getLibName() {
        return "Random";
    }

    @Override
    public IPerceptronInterface getPerceptronInterface() {
        return null;
    }

    @Override
    public void loadOrCreatePerceptron(File perceptronFile, boolean randomizedIfNotExist) throws Exception {
    }

    @Override
    public IsolatedComputation playATurn(GameTicTacToe game, TDLambdaLearning learningMethod) {
        return () -> {
            ArrayList<Action> possibleActions = game.listAllPossibleActions(game.getBoard());
            int randomMove = TDLambdaLearning.randomBetween(0, possibleActions.size() - 1);
            game.processInput(possibleActions.get(randomMove));
            return null;
        };
    }

    @Override
    public void savePerceptron(File perceptronFile) throws Exception {
    }

}
