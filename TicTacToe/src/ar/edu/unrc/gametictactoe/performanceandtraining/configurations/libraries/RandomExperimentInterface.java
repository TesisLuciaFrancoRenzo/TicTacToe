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
import ar.edu.unrc.gametictactoe.GameTicTacToe;
import ar.edu.unrc.gametictactoe.PerceptronConfigurationTicTacToe;
import ar.edu.unrc.gametictactoe.performanceandtraining.configurations.INeuralNetworkInterfaceForTicTacToe;
import ar.edu.unrc.tdlearning.perceptron.interfaces.IPerceptronInterface;
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
     */
    public static final String RANDOM = "Random";

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
    public void playATurn(GameTicTacToe game, TDLambdaLearning learningMethod) {
        ArrayList<Action> possibleActions = game.listAllPossibleActions(game.getBoard());
        int randomMove = TDLambdaLearning.randomBetween(0, possibleActions.size() - 1);
        game.processInput(possibleActions.get(randomMove));
    }

    @Override
    public void savePerceptron(File perceptronFile) throws Exception {
    }

}
