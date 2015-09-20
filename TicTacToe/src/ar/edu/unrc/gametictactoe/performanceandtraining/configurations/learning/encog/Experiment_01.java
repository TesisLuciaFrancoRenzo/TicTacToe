/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ar.edu.unrc.gametictactoe.performanceandtraining.configurations.learning.encog;

import ar.edu.unrc.gametictactoe.PerceptronConfigurationTicTacToe;
import ar.edu.unrc.gametictactoe.performanceandtraining.configurations.LearningExperiment;
import ar.edu.unrc.gametictactoe.performanceandtraining.configurations.libraries.EncogExperimentInterface;
import ar.edu.unrc.gametictactoe.performanceandtraining.configurations.perceptrons.ConfigurationTicTacToe;
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
                    = ".." + File.separator
                    + "Perceptrones ENTRENADOS" + File.separator;
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
        experiment.setResetEligibilitiTraces(true);
        experiment.setGamesToPlay(2_000);//(2_000_000);
        experiment.setSaveEvery(1_000);//(25_000);
        experiment.setSaveBackupEvery(1_000);//(25_000);
        experiment.setInitializePerceptronRandomized(true);

        experiment.createLogs(false);
        //para calcualar estadisticas
        if ( statistics ) {
            experiment.setStatisticsOnly(true);
            experiment.setRunStatisticForRandom(true);
            experiment.setRunStatisticsForBackups(true);
            experiment.setGamesToPlayPerThreadForStatistics(1_000);
            experiment.setSimulationsForStatistics(8);
        } else {
            experiment.setStatisticsOnly(false);
            experiment.setRunStatisticForRandom(false);
            experiment.setRunStatisticsForBackups(false);
            experiment.setGamesToPlayPerThreadForStatistics(0);
            experiment.setSimulationsForStatistics(0);
        }

        experiment.start(filePath, 0);
    }

    @Override
    public void initialize() throws Exception {
        if ( this.getExperimentName() == null ) {
            this.setExperimentName("Experiment_01");
        }
        this.setPerceptronName(this.getExperimentName());
        PerceptronConfigurationTicTacToe<BasicNetwork> config = new ConfigurationTicTacToe<>();
        this.setNeuralNetworkInterfaceForTicTacToe(new EncogExperimentInterface(config));
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
