/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ar.edu.unrc.gametictactoe.performanceandtraining.configurations;

import ar.edu.unrc.gametictactoe.GameTicTacToe;
import ar.edu.unrc.gametictactoe.PerceptronConfigurationTicTacToe;
import java.io.File;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Renzo Bianchini
 * @param <NeuralNetworkClass>
 */
public abstract class VisualExperiment<NeuralNetworkClass> {

    private int delayPerMove = 200;
    private String experimentName;
    private boolean forceStop;
    private PerceptronConfigurationTicTacToe<NeuralNetworkClass> perceptronConfiguration;
    private String perceptronName;
    private boolean player1Human;
    private boolean player2Human;
    /**
     *
     */
    protected LearningExperiment<NeuralNetworkClass> learningExperiment;

    /**
     *
     * @param learningExperiment
     */
    public VisualExperiment(LearningExperiment<NeuralNetworkClass> learningExperiment) {
        this.learningExperiment = learningExperiment;
    }

    /**
     *
     * @return
     */
    public String getPerceptronFileName() {
        return perceptronName;
    }

    /**
     *
     * @return
     */
    public boolean isPlayer1Human() {
        return player1Human;
    }

    /**
     *
     * @param player1Human
     */
    public void setPlayer1Human(boolean player1Human) {
        this.player1Human = player1Human;
    }

    /**
     *
     * @return
     */
    public boolean isPlayer2Human() {
        return player2Human;
    }

    /**
     *
     * @param player2Human
     */
    public void setPlayer2Human(boolean player2Human) {
        this.player2Human = player2Human;
    }

    /**
     *
     * @param experimentPath
     * @param delayPerMove
     */
    public void start(String experimentPath, int delayPerMove) {
        forceStop = false;
        File experimentPathFile = new File(experimentPath);
        if ( experimentPathFile.exists() && !experimentPathFile.isDirectory() ) {
            throw new IllegalArgumentException("experimentPath must be a directory");
        }
        if ( !experimentPathFile.exists() ) {
            experimentPathFile.mkdirs();
        }
        try {
            if ( learningExperiment != null ) {
                learningExperiment.initialize();
                this.experimentName = learningExperiment.getExperimentName();
                this.perceptronConfiguration = learningExperiment.getaINeuralNetwork().getPerceptronConfiguration();
            } else {
                this.perceptronConfiguration = null;
            }
            initializeVisual();
            run(experimentPath, delayPerMove);
        } catch ( Exception ex ) {
            Logger.getLogger(VisualExperiment.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     *
     */
    public void stop() {
        forceStop = true;
    }

    /**
     * @return the delayPerMove
     */
    protected int getDelayPerMove() {
        return delayPerMove;
    }

    /**
     * @param delayPerMove the delayPerMove to set
     */
    protected void setDelayPerMove(int delayPerMove) {
        this.delayPerMove = delayPerMove;
    }

    /**
     * @return the experimentName
     */
    protected String getExperimentName() {
        return experimentName;
    }

    /**
     * @param experimentName the experimentName to set
     */
    protected void setExperimentName(String experimentName) {
        this.experimentName = experimentName;
    }

    /**
     * @return the perceptronConfiguration
     */
    protected PerceptronConfigurationTicTacToe<NeuralNetworkClass> getPerceptronConfiguration() {
        return perceptronConfiguration;
    }

    /**
     * @param perceptronConfiguration the perceptronConfiguration to set
     */
    protected void setPerceptronConfiguration(PerceptronConfigurationTicTacToe<NeuralNetworkClass> perceptronConfiguration) {
        this.perceptronConfiguration = perceptronConfiguration;
    }

    /**
     * @return the perceptronName
     */
    protected String getPerceptronName() {
        return perceptronName;
    }

    /**
     * @param perceptronName the perceptronName to set
     */
    protected void setPerceptronName(String perceptronName) {
        this.perceptronName = perceptronName;
    }

    /**
     * Se deben inicializar:
     * <ul>
     * <li>private int delayPerMove;</li>
     * <li>private IPlayingExperiment neuralNetworkInterfaceFor2048;</li>
     * <li>private String perceptronName;</li>
     * <li>private boolean player1Human;</li>
     * <li>rivate boolean player2Human;</li>
     * </ul>
     * <p>
     * Las siguientes variables se inicializan automaticamente, pero pueden ser
     * modificadas:
     * <ul>
     * <li>private String experimentName;</li>
     * <li>private PerceptronConfigurationTicTacToe
     * perceptronConfiguration;</li>
     * </ul>
     * <p>
     * @throws Exception
     */
    protected abstract void initializeVisual() throws Exception;

    /**
     *
     * @param experimentPath
     * @param delayPerMove   <p>
     * @throws Exception +
     *                   this.learningExperiment.getNeuralNetworkInterfaceForTicTacToe().getLibName()
     *                   + File.separator + experimentName + File.separator;
     *                   File dirPathFile = new File(dirPath); if (
     *                   !dirPathFile.exists() ) {
     */
    protected void run(String experimentPath, int delayPerMove) throws Exception {
        System.out.println("Starting " + this.getPerceptronName() + " Visual");
        //TODO: arreglar el player
        GameTicTacToe<NeuralNetworkClass> game = new GameTicTacToe<>(perceptronConfiguration, learningExperiment.getLearningAlgorithm(), true, delayPerMove, null);
        if ( perceptronConfiguration != null ) {
            //cargamos la red neuronal entrenada
            String dirPath = experimentPath
                    + this.learningExperiment.getaINeuralNetwork().getLibName() + File.separator
                    + experimentName + File.separator;
            File dirPathFile = new File(dirPath);
            if ( !dirPathFile.exists() ) {
                dirPathFile.mkdirs();
            }
            String filePath = dirPath + perceptronName;
            File perceptronFile = new File(filePath + ".ser");
            if ( !perceptronFile.exists() ) {
                throw new IllegalArgumentException("perceptron file must exists");
            }
            this.learningExperiment.getaINeuralNetwork().loadOrCreatePerceptron(perceptronFile, true);
        }
        while ( !game.isTerminalState() && !forceStop ) {
            this.learningExperiment.getaINeuralNetwork().playATurn(game, learningExperiment.getLearningAlgorithm()).compute();
        }
        if ( !forceStop ) {
            //sleep(5_000);
        }
        game.dispose();
        System.out.println("Finished");
    }
}
