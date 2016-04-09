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
package ar.edu.unrc.gametictactoe.performanceandtraining.configurations;

import ar.edu.unrc.gametictactoe.Action;
import ar.edu.unrc.gametictactoe.GameTicTacToe;
import ar.edu.unrc.gametictactoe.PerceptronConfigurationTicTacToe;
import ar.edu.unrc.tdlearning.perceptron.interfaces.IAction;
import ar.edu.unrc.tdlearning.perceptron.interfaces.IPerceptronInterface;
import ar.edu.unrc.tdlearning.perceptron.learning.TDLambdaLearning;
import java.io.File;
import java.util.List;

/**
 *
 * @author lucia bressan, franco pellegrini, renzo bianchini
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
     */
    public void playATurn(GameTicTacToe<NeuralNetworkClass> game, TDLambdaLearning learningMethod) {
        // evaluamos cada accion aplicada al estado inicial y elegimos la mejor
        // accion basada en las predicciones del problema
        List<IAction> possibleActions = game.listAllPossibleActions(game.getBoard());
        Action bestAction = (Action) learningMethod.computeBestPossibleAction(game, game.getBoard(), possibleActions, game.getCurrentActor());
        game.processInput(bestAction);
    }

    /**
     *
     * @param perceptronFile <p>
     * @throws Exception
     */
    public abstract void savePerceptron(File perceptronFile) throws Exception;

}
