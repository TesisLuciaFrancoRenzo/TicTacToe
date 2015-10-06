/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
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
