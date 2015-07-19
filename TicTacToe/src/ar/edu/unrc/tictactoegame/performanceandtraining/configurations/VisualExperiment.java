/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ar.edu.unrc.tictactoegame.performanceandtraining.configurations;

import ar.edu.unrc.tictactoegame.GameTicTacToe;
import ar.edu.unrc.tictactoegame.PerceptronConfigurationTicTacToe;
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
    private int tileToWin;

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
                this.tileToWin = learningExperiment.getTileToWin();
                this.experimentName = learningExperiment.getExperimentName();
                this.perceptronConfiguration = learningExperiment.getNeuralNetworkInterfaceForTicTacToe().getPerceptronConfiguration();
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
     * @return the tileToWin
     */
    protected int getTileToWin() {
        return tileToWin;
    }

    /**
     * @param tileToWin the tileToWin to set
     */
    protected void setTileToWin(int tileToWin) {
        this.tileToWin = tileToWin;
    }

    /**
     * Se deben inicializar:
     * <ul>
     * <li>private int delayPerMove;</li>
     * <li>private IPlayingExperiment neuralNetworkInterfaceFor2048;</li>
     * <li>private String perceptronName;</li>
     * </ul>
     * <p>
     * Las siguientes variables se inicializan automaticamente, pero pueden ser
     * modificadas:
     * <ul>
     * <li>private int tileToWin;</li>
     * <li>private String experimentName;</li>
     * <li>private PerceptronConfiguration2048 perceptronConfiguration;</li>
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
        GameTicTacToe<NeuralNetworkClass> game = new GameTicTacToe<>(perceptronConfiguration, true, delayPerMove);
        if ( perceptronConfiguration != null ) {
            //cargamos la red neuronal entrenada
            String dirPath = experimentPath
                    + this.learningExperiment.getNeuralNetworkInterfaceForTicTacToe().getLibName() + File.separator
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
            this.learningExperiment.getNeuralNetworkInterfaceForTicTacToe().loadOrCreatePerceptron(perceptronFile, true);
        }
        while ( !game.isTerminalState() && !forceStop ) {
            this.learningExperiment.getNeuralNetworkInterfaceForTicTacToe().playATurn(game, learningExperiment.getLearningAlgorithm()).compute();
        }
        if ( !forceStop ) {
            //sleep(5_000);
        }
        game.dispose();
        System.out.println("Finished");
    }
}
