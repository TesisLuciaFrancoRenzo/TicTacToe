/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ar.edu.unrc.gametictactoe.performanceandtraining.configurations.visualPlay;

import ar.edu.unrc.gametictactoe.performanceandtraining.configurations.LearningExperiment;
import ar.edu.unrc.gametictactoe.performanceandtraining.configurations.VisualExperiment;
import ar.edu.unrc.gametictactoe.performanceandtraining.configurations.learning.random.Experiment_01;
import java.io.File;

/**
 *
 * @author renzo
 */
public class HumanVSBrain extends VisualExperiment {

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
        HumanVSBrain game = new HumanVSBrain(experiment);

        game.start(filePath, 500);
    }

    /**
     *
     * @param learningExperiment
     */
    public HumanVSBrain(LearningExperiment learningExperiment) {
        super(learningExperiment);
    }

    @Override
    protected void initializeVisual() throws Exception {
        setDelayPerMove(250);
        setPlayer1Human(true);
        setPlayer2Human(true);
    }

}
