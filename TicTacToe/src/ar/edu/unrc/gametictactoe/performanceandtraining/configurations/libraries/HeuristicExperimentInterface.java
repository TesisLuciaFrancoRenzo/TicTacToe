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
package ar.edu.unrc.gametictactoe.performanceandtraining.configurations.libraries;

import ar.edu.unrc.gametictactoe.Action;
import ar.edu.unrc.gametictactoe.GameBoard;
import ar.edu.unrc.gametictactoe.GameTicTacToe;
import ar.edu.unrc.gametictactoe.PerceptronConfigurationTicTacToe;
import ar.edu.unrc.gametictactoe.heuristic.ai.DesicionTree;
import ar.edu.unrc.gametictactoe.performanceandtraining.configurations.INeuralNetworkInterfaceForTicTacToe;
import ar.edu.unrc.tdlearning.perceptron.interfaces.IPerceptronInterface;
import ar.edu.unrc.tdlearning.perceptron.interfaces.IsolatedComputation;
import ar.edu.unrc.tdlearning.perceptron.learning.TDLambdaLearning;
import java.io.File;
import java.util.List;

/**
 *
 * @author lucia bressan, franco pellegrini, renzo bianchini
 */
public class HeuristicExperimentInterface extends INeuralNetworkInterfaceForTicTacToe {

    /**
     *
     */
    public static final String HEURISTIC = "Heuristic";
    private final DesicionTree ai;

    /**
     *
     * @param perceptronConfiguration
     */
    public HeuristicExperimentInterface(PerceptronConfigurationTicTacToe perceptronConfiguration) {
        super(perceptronConfiguration);
        ai = new DesicionTree();
        ai.construct(new File("../../Perceptrones ENTRENADOS TicTacToe/heuristics.bin"));
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
        return HEURISTIC;
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
            List<Action> action = ai.solutionsFor((GameBoard) game.getBoard());
            int randomMove = TDLambdaLearning.randomBetween(0, action.size() - 1);
            game.processInput(action.get(randomMove));
            return null;
        };
    }

    @Override
    public void savePerceptron(File perceptronFile) throws Exception {
    }

}
