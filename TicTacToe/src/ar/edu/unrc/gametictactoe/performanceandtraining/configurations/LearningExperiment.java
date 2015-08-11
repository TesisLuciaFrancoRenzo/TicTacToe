/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ar.edu.unrc.gametictactoe.performanceandtraining.configurations;

import ar.edu.unrc.gametictactoe.GameTicTacToe;
import ar.edu.unrc.gametictactoe.Players;
import ar.edu.unrc.tdlearning.perceptron.interfaces.IPerceptronInterface;
import ar.edu.unrc.tdlearning.perceptron.learning.EExplorationRateAlgorithms;
import ar.edu.unrc.tdlearning.perceptron.learning.ELearningRateAdaptation;
import ar.edu.unrc.tdlearning.perceptron.learning.TDLambdaLearning;
import ar.edu.unrc.tdlearning.perceptron.ntuple.NTupleSystem;
import ar.edu.unrc.utils.StringAndFiles;
import ar.edu.unrc.utils.StringIterator;
import java.io.File;
import java.io.PrintStream;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Renzo Bianchini
 * @param <NeuralNetworkClass>
 */
public abstract class LearningExperiment<NeuralNetworkClass> {

    /**
     *
     */
    public static final String _CONFIG = "_config";

    /**
     *
     */
    public static final String _RANDOM = "_random";

    /**
     *
     */
    public static final String _TRAINED = "_trained";
    private double[] alpha;
    private int annealingT;
    private int backupNumber;
    private long elapsedTime = 0;
    private String experimentName;
    private EExplorationRateAlgorithms explorationRate;
    private double explorationRateFinalValue;
    private int explorationRateFinishDecrementing;
    private double explorationRateInitialValue;
    private int explorationRateStartDecrementing;
    private int gamesToPlay;
    private int gamesToPlayPerThreadForStatistics;
    private double gamma;
    private boolean initializePerceptronRandomized = false;
    private double lambda;
    private int lastSavedGamePlayedNumber;
    private TDLambdaLearning learningAlgorithm;
    private ELearningRateAdaptation learningRateAdaptation;
    private boolean logsActivated = false;
    private INeuralNetworkInterfaceForTicTacToe<NeuralNetworkClass> neuralNetworkInterfaceForTicTacToe;
    private String perceptronName;
    private boolean resetEligibilitiTraces = false;
    private boolean runStatisticForRandom = false;
    private boolean runStatisticsForBackups = false;
    private int saveBackupEvery = 0;
    private int saveEvery = 0;
    private int simulationsForStatistics;
    private boolean statisticsOnly = false;
    //private int tileToWin;

    /**
     *
     */
    protected StatisticExperiment statisticExperiment;

    /**
     *
     * @param logsActivated
     */
    public void createLogs(boolean logsActivated) {
        this.logsActivated = logsActivated;
    }

    /**
     *
     * @param experimentPath <p>
     * @return
     */
    public String createPathToDir(String experimentPath) {
        String dirPath = experimentPath
                + neuralNetworkInterfaceForTicTacToe.getLibName() + File.separator
                + experimentName + File.separator;
        return dirPath;
    }

    /**
     * @return the alpha
     */
    public double[] getAlpha() {
        return alpha;
    }

    /**
     * @param alpha the alpha to set
     */
    public void setAlpha(double[] alpha) {
        this.alpha = alpha;
    }

    /**
     * @return the experimentName
     */
    public String getExperimentName() {
        return experimentName;
    }

    /**
     * @param experimentName the experimentName to set
     */
    public void setExperimentName(String experimentName) {
        this.experimentName = experimentName;
    }

    /**
     *
     * @param value
     */
    public void setExplorationRateToFixed(double value) {
        if ( value < 0 || value > 1 ) {
            throw new IllegalArgumentException("value debe estar en el intervalo [0,1]");
        }
        this.explorationRate = EExplorationRateAlgorithms.fixed;
        this.explorationRateInitialValue = value;
    }

    /**
     * @return the gamesToPlayPerThreadForStatistics
     */
    public int getGamesToPlayPerThreadForStatistics() {
        return gamesToPlayPerThreadForStatistics;
    }

    /**
     * @param gamesToPlayPerThreadForStatistics the
     *                                          gamesToPlayPerThreadForStatistics
     *                                          to set
     */
    public void setGamesToPlayPerThreadForStatistics(int gamesToPlayPerThreadForStatistics) {
        this.gamesToPlayPerThreadForStatistics = gamesToPlayPerThreadForStatistics;
    }

    /**
     * @return the gamma
     */
    public double getGamma() {
        return gamma;
    }

    /**
     * @param gamma the gamma to set
     */
    public void setGamma(double gamma) {
        this.gamma = gamma;
    }

    /**
     *
     * @param randomized
     */
    public void setInitializePerceptronRandomized(boolean randomized) {
        initializePerceptronRandomized = randomized;
    }

    /**
     * @return the lambda
     */
    public double getLambda() {
        return lambda;
    }

    /**
     * @param lambda the lambda to set
     */
    public void setLambda(double lambda) {
        this.lambda = lambda;
    }

    /**
     * @return the learningRateAdaptation
     */
    public ELearningRateAdaptation getLearningRateAdaptation() {
        return learningRateAdaptation;
    }

    /**
     * @param learningRateAdaptation the learningRateAdaptation to set
     */
    public void setLearningRateAdaptation(ELearningRateAdaptation learningRateAdaptation) {
        this.learningRateAdaptation = learningRateAdaptation;
    }

    /**
     *
     * @param annealingT
     */
    public void setLearningRateAdaptationToAnnealing(int annealingT) {
        this.learningRateAdaptation = ELearningRateAdaptation.annealing;
        this.annealingT = annealingT;
    }

    /**
     * @return the perceptronName
     */
    public String getPerceptronName() {
        return perceptronName;
    }

    /**
     * @param perceptronName the perceptronName to set
     */
    public void setPerceptronName(String perceptronName) {
        this.perceptronName = perceptronName;
    }

    /**
     * @return the saveBackupEvery
     */
    public int getSaveBackupEvery() {
        return saveBackupEvery;
    }

    /**
     * @param saveBackupEvery the saveBackupEvery to set
     */
    public void setSaveBackupEvery(int saveBackupEvery) {
        this.saveBackupEvery = saveBackupEvery;
    }

    /**
     * @return the saveEvery
     */
    public int getSaveEvery() {
        return saveEvery;
    }

    /**
     * @param saveEvery the saveEvery to set
     */
    public void setSaveEvery(int saveEvery) {
        this.saveEvery = saveEvery;
    }

    /**
     * you must initialize:
     * <ul>
     * <li>private double alpha;</li>
     * <li>private double lambda;</li>
     * <li>private int gamesToPlay;</li>
     * <li>private int saveBackupEvery;</li>
     * <li>private String experimentName;</li>
     * <li>private String perceptronName;</li>
     * <li>private PerceptronConfiguration2048 perceptronConfiguration; </li>
     * <li>private INeuralNetworkInterfaceFor2048
     * neuralNetworkInterfaceFor2048;</li>
     * <li>private TDTrainerMethod trainerMethod;</li>
     * <li>private int gamesToPlayPerThreadForStatistics;</li>
     * <li>private int simulationsForStatistics;</li>
     * <li>private boolean statisticsOnly;</li>
     * <li>private TDLambdaLearning learningAlgorithm</li>
     * <p>
     * </ul>
     * <p>
     * @throws Exception
     */
    public abstract void initialize() throws Exception;

    /**
     *
     * @param perceptronInterface <p>
     * @return
     */
    public abstract TDLambdaLearning instanceOfTdLearninrgImplementation(IPerceptronInterface perceptronInterface);

    /**
     *
     * @param nTupleSystem <p>
     * @return
     */
    public abstract TDLambdaLearning instanceOfTdLearninrgImplementation(NTupleSystem nTupleSystem);

    /**
     * @return the resetEligibilitiTraces
     */
    public boolean isResetEligibilitiTraces() {
        return resetEligibilitiTraces;
    }

    /**
     * @param resetEligibilitiTraces the resetEligibilitiTraces to set
     */
    public void setResetEligibilitiTraces(boolean resetEligibilitiTraces) {
        this.resetEligibilitiTraces = resetEligibilitiTraces;
    }

    /**
     * @return the runStatisticForRandom
     */
    public boolean isRunStatisticForRandom() {
        return runStatisticForRandom;
    }

    /**
     * @param runStatisticForRandom the runStatisticForRandom to set
     */
    public void setRunStatisticForRandom(boolean runStatisticForRandom) {
        this.runStatisticForRandom = runStatisticForRandom;
    }

    /**
     * @return the runStatisticsForBackups
     */
    public boolean isRunStatisticsForBackups() {
        return runStatisticsForBackups;
    }

    /**
     * @param runStatisticsForBackups the runStatisticsForBackups to set
     */
    public void setRunStatisticsForBackups(boolean runStatisticsForBackups) {
        this.runStatisticsForBackups = runStatisticsForBackups;
    }

    /**
     * @return the statisticsOnly
     */
    public boolean isStatisticsOnly() {
        return statisticsOnly;
    }

    /**
     * @param statisticsOnly the statisticsOnly to set
     */
    public void setStatisticsOnly(boolean statisticsOnly) {
        this.statisticsOnly = statisticsOnly;
    }

    /**
     *
     * @param initialValue
     * @param startDecrementing
     * @param finalValue
     * @param finishDecrementing
     */
    public void setExplorationRate(double initialValue, int startDecrementing, double finalValue, int finishDecrementing) {
        if ( initialValue < 0 || initialValue > 1 ) {
            throw new IllegalArgumentException("initialValue debe estar en el intervalo [0,1]");
        }
        if ( finalValue < 0 || finalValue > 1 ) {
            throw new IllegalArgumentException("finalValue debe estar en el intervalo [0,1]");
        }
        this.explorationRate = EExplorationRateAlgorithms.linear;
        this.explorationRateInitialValue = initialValue;
        this.explorationRateStartDecrementing = startDecrementing;
        this.explorationRateFinalValue = finalValue;
        this.explorationRateFinishDecrementing = finishDecrementing;
    }

    /**
     *
     */
    public void setLearningRateAdaptationToFixed() {
        this.learningRateAdaptation = ELearningRateAdaptation.fixed;
    }

    /**
     *
     * @param experimentPath
     * @param delayPerMove
     */
    public void start(String experimentPath, int delayPerMove) {
        File experimentPathFile = new File(experimentPath);
        if ( experimentPathFile.exists() && !experimentPathFile.isDirectory() ) {
            throw new IllegalArgumentException("experimentPath must be a directory");
        }
        if ( !experimentPathFile.exists() ) {
            experimentPathFile.mkdirs();
        }
        try {
            initialize();
            runExperiment(experimentPath, delayPerMove);
        } catch ( Exception ex ) {
            Logger.getLogger(LearningExperiment.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     *
     * @param game
     * @param printStream
     * @param randomPerceptronFile
     * @param perceptronFile
     * @param configFile
     * @param filePath
     * @param dateFormater
     * @param now
     * @param zeroNumbers          <p>
     * @throws Exception
     */
    public void training(GameTicTacToe<NeuralNetworkClass> game, final PrintStream printStream, File randomPerceptronFile, File perceptronFile, File configFile, String filePath, SimpleDateFormat dateFormater, Date now, int zeroNumbers) throws Exception {
        File perceptronFileBackup;
        switch ( this.learningRateAdaptation ) {
            case fixed: {
                learningAlgorithm.setLearningRateAdaptationToFixed();
                break;
            }
            case annealing: {
                learningAlgorithm.setLearningRateAdaptationToAnnealing(annealingT);
                break;
            }
        }
        switch ( this.explorationRate ) {
            case fixed: {
                learningAlgorithm.setExplorationRateToFixed(this.explorationRateInitialValue);
                break;
            }
            case linear: {
                learningAlgorithm.setExplorationRate(
                        this.explorationRateInitialValue,
                        this.explorationRateStartDecrementing,
                        this.explorationRateFinalValue,
                        this.explorationRateFinishDecrementing
                );
                break;
            }
        }

        for ( int i = lastSavedGamePlayedNumber + 1; i <= gamesToPlay; i++ ) {
            long start = System.nanoTime();
            learningAlgorithm.solveAndTrainOnce(game, i);
            elapsedTime += System.nanoTime() - start;

            //TODO contar empates y cuantas veces ganan los jugadores
            int percent = (int) (((i * 1d) / (gamesToPlay * 1d)) * 100d);
            System.out.println("Juego número " + i + " (" + percent + "%)    ganador = " + game.getWinner()
                    + "      turno alcanzado = " + game.getLastTurn()
                    + "      current alpha = " + Arrays.toString(learningAlgorithm.getCurrentAlpha())
                    + "      jugador entrenado = " + game.getActorToTrain().toString());
            if ( printStream != null ) {
                printStream.println(game.getWinner() + "\t" + game.getLastTurn());
//                printStream.println(game.getScore() + "\t" + game.getMaxNumber());
            }
            boolean writeConfig = false;
            if ( i % saveEvery == 0 || i % saveBackupEvery == 0 ) {
                neuralNetworkInterfaceForTicTacToe.savePerceptron(perceptronFile);
                System.out.println("============ Perceptron Exportado Exitosamente (SAVE) ============");
                writeConfig = true;
            }
            if ( i % saveBackupEvery == 0 ) {
                backupNumber++;
                perceptronFileBackup = new File(filePath + _TRAINED + "_" + dateFormater.format(now) + "_BackupN-" + String.format("%0" + zeroNumbers + "d", backupNumber)
                        + ".ser");
                neuralNetworkInterfaceForTicTacToe.savePerceptron(perceptronFileBackup);
                System.out.println("============ Perceptron Exportado Exitosamente (BACKUP " + backupNumber + ") ============");
                writeConfig = true;
            }
            if ( writeConfig ) {
                StringAndFiles.stringToFile(configFile, Integer.toString(i) + "\n" + Integer.toString(backupNumber) + "\n" + Long.toString(elapsedTime), StringAndFiles.UTF_8);
            }
            game.switchPlayerToTrain();
        }
    }

    /**
     * @return the annealingT
     */
    protected int getAnnealingT() {
        return annealingT;
    }

    /**
     * @param annealingT the annealingT to set
     */
    protected void setAnnealingT(int annealingT) {
        this.annealingT = annealingT;
    }

    /**
     * @return the gamesToPlay
     */
    protected int getGamesToPlay() {
        return gamesToPlay;
    }

    /**
     * @param gamesToPlay the gamesToPlay to set
     */
    public void setGamesToPlay(int gamesToPlay) {
        this.gamesToPlay = gamesToPlay;
    }

    /**
     * @return the learningAlgorithm
     */
    protected TDLambdaLearning getLearningAlgorithm() {
        return learningAlgorithm;
    }

    /**
     * @param learningAlgorithm the learningAlgorithm to set
     */
    protected void setLearningAlgorithm(TDLambdaLearning learningAlgorithm) {
        this.learningAlgorithm = learningAlgorithm;
    }

    /**
     * @return the neuralNetworkInterfaceFor2048
     */
    protected INeuralNetworkInterfaceForTicTacToe<NeuralNetworkClass> getNeuralNetworkInterfaceForTicTacToe() {
        return neuralNetworkInterfaceForTicTacToe;
    }

    /**
     * @param neuralNetworkInterfaceForTicTacToe
     */
    protected void setNeuralNetworkInterfaceForTicTacToe(INeuralNetworkInterfaceForTicTacToe<NeuralNetworkClass> neuralNetworkInterfaceForTicTacToe) {
        this.neuralNetworkInterfaceForTicTacToe = neuralNetworkInterfaceForTicTacToe;
    }

    /**
     * @return the simulationsForStatistics
     */
    protected int getSimulationsForStatistics() {
        return simulationsForStatistics;
    }

    /**
     * @param simulationsForStatistics the simulationsForStatistics to set
     */
    public void setSimulationsForStatistics(int simulationsForStatistics) {
        this.simulationsForStatistics = simulationsForStatistics;
    }

    /**
     * @return the statisticExperiment
     */
    protected StatisticExperiment getStatisticExperiment() {
        return statisticExperiment;
    }

    /**
     *
     * @param experimentPath
     * @param delayPerMove   <p>
     * @throws Exception
     */
    @SuppressWarnings( "static-access" )
    protected void runExperiment(String experimentPath, int delayPerMove) throws Exception {
        if ( saveEvery == 0 ) {
            throw new IllegalArgumentException("se debe configurar cada cuanto guardar el perceptron mediante la variable saveEvery");
        }
        if ( saveBackupEvery == 0 ) {
            throw new IllegalArgumentException("se debe configurar cada cuanto guardar backups del perceptron mediante la variable saveBackupEvery");
        }
        SimpleDateFormat dateFormater = new SimpleDateFormat("dd-MM-yyyy_HH'h'mm'm'ss's'");
        Date now = new Date();

        System.out.println("Starting " + this.getPerceptronName() + " Trainer");

        String dirPath = createPathToDir(experimentPath);
        File dirPathFile = new File(dirPath);
        if ( !dirPathFile.exists() ) {
            dirPathFile.mkdirs();
        }
        String filePath = dirPath + perceptronName;
        File perceptronFile = new File(filePath + _TRAINED + ".ser");
        File configFile = new File(filePath + _CONFIG + ".txt");

        backupNumber = 0;
        lastSavedGamePlayedNumber = 0;
        elapsedTime = 0;
        if ( configFile.exists() ) {
            String configs = StringAndFiles.fileToString(configFile, StringAndFiles.UTF_8);
            StringIterator iterator = new StringIterator(configs, null, "\n");
            String line = iterator.readLine();
            if ( line == null ) {
                throw new IllegalArgumentException("el archivo de configuracion no tiene un formato válido");
            }
            this.lastSavedGamePlayedNumber = Integer.parseInt(line);
            line = iterator.readLine();
            if ( line == null ) {
                throw new IllegalArgumentException("el archivo de configuracion no tiene un formato válido");
            }
            this.backupNumber = Integer.parseInt(line);
            line = iterator.readLine();
            if ( line == null ) {
                throw new IllegalArgumentException("el archivo de configuracion no tiene un formato válido");
            }
            this.elapsedTime = Long.parseLong(line);
        }

        int zeroNumbers = 1;
        if ( !this.statisticsOnly ) {
            zeroNumbers = Integer.toString(this.gamesToPlay / this.saveBackupEvery).length();
        }

        File randomPerceptronFile = new File(filePath + _RANDOM + ".ser");

        boolean backupRandomPerceptron = false;
        if ( !perceptronFile.exists() ) {//ver por que existe el archivo
            if ( randomPerceptronFile.exists() ) {
                randomPerceptronFile.delete();
            }
            backupRandomPerceptron = true;
        }

        // Si hay un perceptron ya entrenado, lo buscamos en el archivo.
        // En caso contrario creamos un perceptron vacio, inicializado al azar
        neuralNetworkInterfaceForTicTacToe.loadOrCreatePerceptron(perceptronFile, this.initializePerceptronRandomized);

        //creamos una interfaz de comunicacion entre la red neuronal de encog y el algoritmo de entrenamiento
        if ( backupRandomPerceptron ) {
            //guardamos el perceptron inicial para ahcer estadisticas
            neuralNetworkInterfaceForTicTacToe.savePerceptron(randomPerceptronFile);
        }

        if ( this.getNeuralNetworkInterfaceForTicTacToe().getPerceptronInterface() != null ) {
            this.setLearningAlgorithm(instanceOfTdLearninrgImplementation(this.getNeuralNetworkInterfaceForTicTacToe().getPerceptronInterface()));
        }

        if ( learningAlgorithm == null && !this.statisticsOnly ) {
            throw new IllegalArgumentException("learningAlgorithm no puede ser null");
        }

        //creamos el juego
        boolean show = (delayPerMove > 0);
        GameTicTacToe<NeuralNetworkClass> game = new GameTicTacToe<>(neuralNetworkInterfaceForTicTacToe.getPerceptronConfiguration(),
                learningAlgorithm, show, delayPerMove, Players.PLAYER1);

        statisticExperiment = new StatisticExperiment(this) {
            @Override
            protected void initializeStatistics() throws Exception {
                this.setGamesToPlayPerThread(gamesToPlayPerThreadForStatistics);
                this.setSimulations(simulationsForStatistics);
                this.setLearningMethod(learningAlgorithm);
            }
        };
        statisticExperiment.setDateForFileName(now);

        System.out.println("Training...");

        //creamos un archivo de logs para acumular estadisticas
        File logFile = new File(filePath + "_" + dateFormater.format(now) + "_LOG" + ".txt");

        if ( !this.statisticsOnly ) {
            //comenzamos a entrenar y guardar estadisticas en el archivo de log
            if ( logsActivated ) {
                try ( PrintStream printStream = new PrintStream(logFile, "UTF-8") ) {
                    training(game, printStream, randomPerceptronFile, perceptronFile, configFile, filePath, dateFormater, now, zeroNumbers);
                }
            } else {
                training(game, null, randomPerceptronFile, perceptronFile, configFile, filePath, dateFormater, now, zeroNumbers);
            }

            //guardamos los progresos en un archivo
            neuralNetworkInterfaceForTicTacToe.savePerceptron(perceptronFile);
            //  neuralNetworkInterfaceForTicTacToe.compareNeuralNetworks(randomPerceptronFile, perceptronFile);
        }
        //cerramos el juego
        game.dispose();

        System.out.println("Training Finished.");

        if ( this.simulationsForStatistics > 0 && this.gamesToPlayPerThreadForStatistics > 0 ) {
            statisticExperiment = new StatisticExperiment(this) {

                @Override
                protected void initializeStatistics() throws Exception {
                    this.setGamesToPlayPerThread(gamesToPlayPerThreadForStatistics);
                    this.saveBackupEvery(saveBackupEvery);
                    this.setSimulations(simulationsForStatistics);
                    this.setLearningMethod(learningAlgorithm);
                    this.setDateForFileName(now);
                    this.setSimpleDateFormat(dateFormater);
                }
            };
            statisticExperiment.setFileName(this.getExperimentName());
            statisticExperiment.start(experimentPath, delayPerMove);
        }
    }
}
