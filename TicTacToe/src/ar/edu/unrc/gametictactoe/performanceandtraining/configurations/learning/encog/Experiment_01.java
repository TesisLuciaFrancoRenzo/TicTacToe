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
package ar.edu.unrc.gametictactoe.performanceandtraining.configurations.learning.encog;

import ar.edu.unrc.gametictactoe.Players;
import ar.edu.unrc.gametictactoe.performanceandtraining.configurations.LearningExperiment;
import ar.edu.unrc.gametictactoe.performanceandtraining.configurations.libraries.EncogExperimentInterface;
import ar.edu.unrc.tdlearning.perceptron.interfaces.IPerceptronInterface;
import ar.edu.unrc.tdlearning.perceptron.learning.TDLambdaLearning;
import ar.edu.unrc.tdlearning.perceptron.learning.TDLambdaLearningAfterstate;
import ar.edu.unrc.tdlearning.perceptron.ntuple.NTupleSystem;
import java.io.File;
import org.encog.neural.networks.BasicNetwork;

/**
 * @author lucia bressan, franco pellegrini, renzo bianchini
 */
public class Experiment_01 extends LearningExperiment<BasicNetwork> { //TODO Revisar si se usa el parametro en bonetes o no

    /**
     *
     * @param args <p>
     * @throws Exception
     */
    public static void main(String[] args) throws Exception {
        String filePath;
        if ( args.length == 0 ) {
            filePath
                    = ".." + File.separator + ".." + File.separator
                    + "Perceptrones ENTRENADOS TicTacToe" + File.separator;
        } else {
            filePath = args[0];
        }
        LearningExperiment experiment = new Experiment_01();

        boolean statistics = true;
        //boolean statistics = false;
        //experiment.setAlpha();
        experiment.setLearningRateAdaptationToFixed();
        experiment.setLambda(0.7);
        experiment.setGamma(1);
        experiment.setExplorationRateToFixed(0.1);
        experiment.setResetEligibilitiTraces(false);
//        experiment.setResetEligibilitiTraces(true);
//        experiment.setGamesToPlay(10_000_000);
//        experiment.setSaveEvery(2_000_000);//para continuar el entrenamiento
//        experiment.setSaveBackupEvery(100_000);//para estadisticas
        experiment.setGamesToPlay(1_000);
        experiment.setSaveEvery(2_000_000);//para continuar el entrenamiento
        experiment.setSaveBackupEvery(100);//para estadisticas
        experiment.setInitializePerceptronRandomized(true);
//        experiment.setInitializePerceptronRandomized(false);

        experiment.createLogs(false);
        //para calcualar estadisticas
        if ( statistics ) {
            experiment.setStatisticsOnly(true);
            experiment.setRunStatisticForRandom(true);
            experiment.setRunStatisticsForBackups(true);
//            experiment.setGamesToPlayPerThreadForStatistics(10);
//            experiment.setSimulationsForStatistics(1);
            experiment.setGamesToPlayPerThreadForStatistics(1_000);
            experiment.setSimulationsForStatistics(8);
        } else {
            experiment.setStatisticsOnly(false);
            experiment.setRunStatisticForRandom(false);
            experiment.setRunStatisticsForBackups(false);
            experiment.setGamesToPlayPerThreadForStatistics(0);
            experiment.setSimulationsForStatistics(0);
        }

        experiment.start(filePath, EncogExperimentInterface.ENCOG, 0);
    }

    @Override
    public void initialize() throws Exception {
        if ( this.getExperimentName() == null ) {
            this.setExperimentName("Experiment_01");
        }
        this.setPerceptronName(this.getExperimentName());
        this.setPlayer1Random(false);
        this.setPlayer2Random(true);
        this.setPlayerForStatistics(Players.PLAYER2);
    }

    /**
     *
     * @param perceptronInterface <p>
     * @return
     */
    @Override
    public TDLambdaLearning instanceOfTdLearninrgImplementation(IPerceptronInterface perceptronInterface) {
        return new TDLambdaLearningAfterstate(perceptronInterface, getAlpha(), getLambda(), getGamma(), isResetEligibilitiTraces());
    }

    @Override
    public TDLambdaLearning instanceOfTdLearninrgImplementation(NTupleSystem nTupleSystem) {
        return null;
    }

}
