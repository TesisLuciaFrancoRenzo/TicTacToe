/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ar.edu.unrc.gametictactoe.performanceandtraining.configurations.libraries;

import ar.edu.unrc.gametictactoe.GameTicTacToe;
import ar.edu.unrc.gametictactoe.PerceptronConfigurationTicTacToe;
import ar.edu.unrc.gametictactoe.heuristic.ai.DesicionTree;
import ar.edu.unrc.gametictactoe.performanceandtraining.configurations.INeuralNetworkInterfaceForTicTacToe;
import ar.edu.unrc.tdlearning.perceptron.interfaces.IPerceptronInterface;
import ar.edu.unrc.tdlearning.perceptron.interfaces.IsolatedComputation;
import ar.edu.unrc.tdlearning.perceptron.learning.TDLambdaLearning;
import java.io.File;

/**
 *
 * @author lucia bressan, franco pellegrini, renzo bianchini
 */
public class HeuristicExperimentInterface extends INeuralNetworkInterfaceForTicTacToe {

    /**
     *
     */
    public static final String RANDOM = "Heuristic";
    private final DesicionTree ai;
    private boolean initialized;

    /**
     *
     * @param perceptronConfiguration
     */
    public HeuristicExperimentInterface(PerceptronConfigurationTicTacToe perceptronConfiguration) {
        super(perceptronConfiguration);
        System.out.print("Inicializando IA heurística...");
        ai = new DesicionTree();
        System.out.println(" LISTO.");
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
        return RANDOM;
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
        if ( game.getBoard().isTerminalState() ) {
            throw new IllegalStateException("No se puede realizar movimientos sobre un tablero en estado terminal (juego finalizado)");
        }
        return () -> {
            game.processInput(ai.computeBestAction(game));
            return null;
        };
    }

    @Override
    public void savePerceptron(File perceptronFile) throws Exception {
    }

}
