/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ar.edu.unrc.gametictactoe.performanceandtraining.configurations;

import ar.edu.unrc.gametictactoe.GameTicTacToe;
import ar.edu.unrc.gametictactoe.PerceptronConfigurationTicTacToe;
import ar.edu.unrc.gametictactoe.Players;
import ar.edu.unrc.tdlearning.perceptron.interfaces.IPerceptronInterface;
import ar.edu.unrc.tdlearning.perceptron.learning.TDLambdaLearning;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintStream;
import static java.lang.Math.round;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.IntStream;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;

/**
 *
 * @author Renzo Bianchini
 * @param <NeuralNetworkClass>
 */
public abstract class StatisticExperiment<NeuralNetworkClass> {

    private Date dateForFileName;
    private SimpleDateFormat dateFormater;
    private double drawRate;

    private String experimentName;
    private String fileName;

    private int gamesToPlay;
    private TDLambdaLearning learningMethod;
    private int saveBackupEvery;
    private int simulations;
    private double winRatePlayer1;
    private double winRatePlayer2;

    /**
     *
     */
    protected LearningExperiment<NeuralNetworkClass> learningExperiment;

    /**
     *
     * @param learningExperiment
     */
    public StatisticExperiment(LearningExperiment<NeuralNetworkClass> learningExperiment) {
        this.learningExperiment = learningExperiment;
    }

    /**
     *
     * @param filePath
     * @param backupFiles
     * @param resultsPerFile
     * @param resultsRandom
     * @param randomPerceptronFile <p>
     * @throws IOException
     * @throws InvalidFormatException
     */
    public void exportToExcel(String filePath, List<File> backupFiles, Map<File, StatisticForCalc> resultsPerFile, Map<File, StatisticForCalc> resultsRandom, File randomPerceptronFile) throws IOException, InvalidFormatException {
        InputStream inputXLSX = this.getClass().getResourceAsStream("/ar/edu/unrc/gametictactoe/resources/EstadisticasTicTacToe.xlsx");
        Workbook wb = WorkbookFactory.create(inputXLSX);

        try ( FileOutputStream outputXLSX = new FileOutputStream(filePath + "_" + dateFormater.format(dateForFileName) + "_STATISTICS" + ".xlsx") ) {
            //============= imptimimos en la hoja de % Of Games Won ===================
            Sheet sheet = wb.getSheetAt(0);
            //Estilo par los titulos de las tablas
            int rowStartTitle = 0;
            int colStartTitle = 2;
            int rowStart = 1;
            int colStart = 3;
            Row rowPlayer1;
            Row rowPlayer2;
            Row rowDraw;
            // Luego creamos el objeto que se encargará de aplicar el estilo a la celda
            Font fontCellTitle = wb.createFont();
            fontCellTitle.setFontHeightInPoints((short) 10);
            fontCellTitle.setFontName("Arial");
            fontCellTitle.setBoldweight(Font.BOLDWEIGHT_BOLD);
            CellStyle CellStyleTitle = wb.createCellStyle();
            CellStyleTitle.setWrapText(true);
            CellStyleTitle.setAlignment(CellStyle.ALIGN_CENTER);
            CellStyleTitle.setVerticalAlignment(CellStyle.VERTICAL_TOP);
            CellStyleTitle.setFont(fontCellTitle);

            // Establecemos el tipo de sombreado de nuestra celda
            CellStyleTitle.setFillBackgroundColor(IndexedColors.LIGHT_CORNFLOWER_BLUE.getIndex());
            CellStyleTitle.setFillPattern(CellStyle.SOLID_FOREGROUND);
            loadTitle(rowStartTitle, colStartTitle, sheet, backupFiles.size(), CellStyleTitle);
            //estilo titulo finalizado

            //Estilo de celdas con los valores de las estadisticas
            CellStyle cellStyle = wb.createCellStyle();
            cellStyle.setWrapText(true);
            /* We are now ready to set borders for this style */
            /* Draw a thin left border */
            cellStyle.setBorderLeft(CellStyle.BORDER_THIN);
            /* Add medium right border */
            cellStyle.setBorderRight(CellStyle.BORDER_THIN);
            /* Add dashed top border */
            cellStyle.setBorderTop(CellStyle.BORDER_THIN);
            /* Add dotted bottom border */
            cellStyle.setBorderBottom(CellStyle.BORDER_THIN);
            //estilo celdas finalizado
            //loadTitle(rowStartTitle, colStartTitle, sheet, backupFiles.size(), CellStyleTitle);
            rowPlayer1 = sheet.getRow(rowStart);
            rowPlayer2 = sheet.getRow(rowStart + 1);
            rowDraw = sheet.getRow(rowStart + 2);
            for ( int file = 0; file < backupFiles.size(); file++ ) {
                Cell cellPlayer1 = rowPlayer1.createCell(file + colStart, Cell.CELL_TYPE_NUMERIC);
                Cell cellPlayer2 = rowPlayer2.createCell(file + colStart, Cell.CELL_TYPE_NUMERIC);
                Cell cellDraw = rowDraw.createCell(file + colStart, Cell.CELL_TYPE_NUMERIC);
                cellPlayer1.setCellStyle(cellStyle);
                cellPlayer2.setCellStyle(cellStyle);
                cellDraw.setCellStyle(cellStyle);
                Double cellValuePlayer1 = resultsPerFile.get(backupFiles.get(file)).getWinRatePlayer1();
                Double cellValuePlayer2 = resultsPerFile.get(backupFiles.get(file)).getWinRatePlayer2();
                Double cellValueDraw = resultsPerFile.get(backupFiles.get(file)).getDrawRate();
                assert cellValuePlayer1 <= 100 && cellValuePlayer1 >= 0;
                assert cellValuePlayer2 <= 100 && cellValuePlayer2 >= 0;
                assert cellValueDraw <= 100 && cellValueDraw >= 0;
                //assert cellValueDraw + cellValuePlayer1 + cellValuePlayer2 == 100;
                cellDraw.setCellValue(cellValueDraw);
                cellPlayer1.setCellValue(cellValuePlayer1);
                cellPlayer2.setCellValue(cellValuePlayer2);
            }
            if ( !resultsRandom.isEmpty() ) {
                int file = 0;//hay que ir a buscar el randomfile
                Cell cellDraw = rowDraw.createCell(file + colStart - 1, Cell.CELL_TYPE_NUMERIC);
                Cell cellPlayer1 = rowPlayer1.createCell(file + colStart - 1, Cell.CELL_TYPE_NUMERIC);
                Cell cellPlayer2 = rowPlayer2.createCell(file + colStart - 1, Cell.CELL_TYPE_NUMERIC);
                cellDraw.setCellStyle(cellStyle);
                cellPlayer1.setCellStyle(cellStyle);
                cellPlayer2.setCellStyle(cellStyle);
//                StatisticForCalc get = resultsRandom.get(randomPerceptronFile);
//                Double cellValuePlayer1 = get.getWinRatePlayer1();
                Double cellValuePlayer1 = resultsRandom.get(randomPerceptronFile).getWinRatePlayer1();
                Double cellValuePlayer2 = resultsRandom.get(randomPerceptronFile).getWinRatePlayer2();
                Double cellValueDraw = resultsRandom.get(randomPerceptronFile).getDrawRate();
                //assert cellValueDraw + cellValuePlayer1 + cellValuePlayer2 == 100;

                cellPlayer1.setCellValue(cellValuePlayer1);
                cellPlayer2.setCellValue(cellValuePlayer2);
                cellDraw.setCellValue(cellValueDraw);
            }
            wb.write(outputXLSX);
        }
    }

    /**
     * @param gamesToPlay the gamesToPlay to set
     */
    public void setGamesToPlayPerThread(int gamesToPlay) {
        this.gamesToPlay = gamesToPlay;
    }

    /**
     * @return the tileStatistics
     */
    public StatisticForCalc getStatistics() {
        StatisticForCalc statistic = new StatisticForCalc();
        statistic.setWinRatePlayer1(winRatePlayer1);
        statistic.setDrawRate(drawRate);
        statistic.setWinRatePlayer2(winRatePlayer2);
        return statistic;
    }

    /**
     *
     * @param rowStartTitle
     * @param colStartTitle
     * @param sheet
     * @param backupFilesSize
     * @param CellStyleTitle
     */
    public void loadTitle(int rowStartTitle, int colStartTitle, Sheet sheet, int backupFilesSize, CellStyle CellStyleTitle) {
        int total_juegos = saveBackupEvery;
        Row row1 = sheet.getRow(rowStartTitle);
        for ( int file = 1; file <= backupFilesSize; file++ ) {
            Cell cell = row1.createCell(file + colStartTitle, Cell.CELL_TYPE_NUMERIC);
            cell.setCellStyle(CellStyleTitle);
            Integer value = total_juegos * file;
            String valueStr = value.toString();
            String cellV = valueStr;
            if ( valueStr.length() > 3 ) {
                cellV = valueStr.substring(0, valueStr.length() - 3) + "K";
            }
            cell.setCellValue(cellV);
        }
    }

    /**
     *
     * @param fileToProcess
     * @param delayPerMove  <p>
     * @throws Exception
     */
    public void processFile(String fileToProcess, int delayPerMove) throws Exception {
        if ( learningExperiment.getPlayerForStatistics() == null || (learningExperiment.getPlayerForStatistics() != Players.PLAYER1 && learningExperiment.getPlayerForStatistics() != Players.PLAYER2) ) {
            throw new IllegalStateException("La variable playerForStatistics debe ser Players.PLAYER1 o Players.PLAYER2");
        }

        //preparamos los destinos de las simulaciones para posterior sumatoria final
        List<ThreadResult> results = new ArrayList(simulations);
        List<GameTicTacToe<NeuralNetworkClass>> games = new ArrayList(simulations);
        List<INeuralNetworkInterfaceForTicTacToe<NeuralNetworkClass>> neuralNetworkInterfaces = new ArrayList(simulations);
        List<TDLambdaLearning> tdLambdaLearning = new ArrayList(simulations);

        for ( int i = 0; i < simulations; i++ ) {
            PerceptronConfigurationTicTacToe<NeuralNetworkClass> tempPerceptronConfiguration = null;
            INeuralNetworkInterfaceForTicTacToe<NeuralNetworkClass> neuralNetworkInterfaceClone = null;
            IPerceptronInterface tempPerceptronInterface = null;
            if ( !this.learningExperiment.isPlayer1Random() || !this.learningExperiment.isPlayer2Random() ) {
                tempPerceptronConfiguration = null;
                neuralNetworkInterfaceClone = (INeuralNetworkInterfaceForTicTacToe<NeuralNetworkClass>) learningExperiment.getaINeuralNetwork().clone();

                tempPerceptronInterface = null;

                if ( learningExperiment.getaINeuralNetwork().getPerceptronConfiguration() != null ) {
                    tempPerceptronConfiguration = (PerceptronConfigurationTicTacToe<NeuralNetworkClass>) learningExperiment.getaINeuralNetwork().getPerceptronConfiguration().clone();
                    neuralNetworkInterfaceClone.setPerceptronConfiguration(tempPerceptronConfiguration);
                    tempPerceptronInterface = neuralNetworkInterfaceClone.getPerceptronInterface(); //TODO revisar esto
                }

                if ( tempPerceptronConfiguration != null ) {
                    //cargamos la red neuronal entrenada
                    File perceptronFile = new File(fileToProcess + ".ser");
                    if ( !perceptronFile.exists() ) {
                        throw new IllegalArgumentException("perceptron file must exists: " + perceptronFile.getCanonicalPath());
                    }
                    neuralNetworkInterfaceClone.loadOrCreatePerceptron(perceptronFile, true);
                }
            }
            boolean show = (delayPerMove > 0);

            GameTicTacToe<NeuralNetworkClass> game = new GameTicTacToe<>(tempPerceptronConfiguration, learningExperiment.getLearningAlgorithm(), show, delayPerMove, null);//learningExperiment.getPlayerForStatistics());

            neuralNetworkInterfaces.add(neuralNetworkInterfaceClone);
            if ( tempPerceptronConfiguration != null ) {
                tdLambdaLearning.add(learningExperiment.instanceOfTdLearninrgImplementation(tempPerceptronInterface)); //TODO revisar esto
            }
            games.add(game);
            results.add(new ThreadResult());
        }

        IntStream
                .range(0, simulations)
                .parallel()
                .forEach(i -> {
                    // Si hay un perceptron ya entrenado, lo buscamos en el archivo.
                    // En caso contrario creamos un perceptron vacio, inicializado al azar
                    for ( results.get(i).setProcesedGames(i); results.get(i).getProcesedGames() < gamesToPlay; results.get(i).addProcesedGames() ) {
                        games.get(i).newGameMenuItemActionPerformed(); //reset
                        while ( !games.get(i).isTerminalState() ) {
                            switch ( games.get(i).getCurrentlPlayer() ) {
                                case PLAYER1: {
                                    if ( this.learningExperiment.isPlayer1Random() ) {
                                        this.learningExperiment.getaIRandom().playATurn(games.get(i), tdLambdaLearning.get(i)).compute();
                                    } else {
                                        neuralNetworkInterfaces.get(i).playATurn(games.get(i), tdLambdaLearning.get(i)).compute();
                                    }
                                    break;
                                }
                                case PLAYER2: {
                                    if ( this.learningExperiment.isPlayer2Random() ) {
                                        this.learningExperiment.getaIRandom().playATurn(games.get(i), tdLambdaLearning.get(i)).compute();
                                    } else {
                                        neuralNetworkInterfaces.get(i).playATurn(games.get(i), tdLambdaLearning.get(i)).compute();
                                    }
                                    break;
                                }
                                default:
                                    throw new IllegalStateException("No se reconoce el jugador actual");
                            }

                        }
                        //calculamos estadisticas
                        results.get(i).addWinner(games.get(i).getWinner());

                    }
                    games.get(i).dispose();
                });

        winRatePlayer1 = 0;
        winRatePlayer2 = 0;
        drawRate = 0;

        results.stream().forEach((result) -> {
            winRatePlayer1 += result.getWinRatePlayer1();
            winRatePlayer2 += result.getWinRatePlayer2();
            drawRate += result.getdrawRate();
        });

        winRatePlayer1 = winRatePlayer1 / (simulations * 1d);
        assert winRatePlayer1 <= 100;
        winRatePlayer2 = winRatePlayer2 / (simulations * 1d);
        drawRate = drawRate / (simulations * 1d);

        if ( !results.isEmpty() ) {
            //creamos un archivo de logs para acumular estadisticas
            Date now;
            if ( dateForFileName != null ) {
                now = dateForFileName;
            } else {
                now = new Date();
            }
            File logFile = new File(fileToProcess + "_" + dateFormater.format(now) + "_STATISTICS" + ".txt");

            try ( PrintStream printStream = new PrintStream(logFile, "UTF-8") ) {
                printStream.print("Gano: " + round(winRatePlayer1) + "% - Total de partidas: " + gamesToPlay + " (promedios obtenidos con " + simulations + " simulaciones)");
                printStream.print("Gano: " + round(winRatePlayer2) + "% - Total de partidas: " + gamesToPlay + " (promedios obtenidos con " + simulations + " simulaciones)");
                printStream.print("Gano: " + round(drawRate) + "% - Total de partidas: " + gamesToPlay + " (promedios obtenidos con " + simulations + " simulaciones)");
                printStream.println("\nValores alcanzados:");

                printStream.println("\nPuntajes alcanzados (valor/veces):");

                printStream.println("Porcentaje de Juegos Ganados Jugador 1:" + winRatePlayer1);
                printStream.println("Porcentaje de Juegos Ganados Jugador 2:" + winRatePlayer2);
                printStream.println("Porcentaje de Juegos Empatadas:" + drawRate);
            }
            System.out.println("Finished.");
        }
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
            learningMethod = null;
            if ( learningExperiment != null ) {
                this.experimentName = learningExperiment.getExperimentName();
            }
            initializeStatistics();
            run(experimentPath, delayPerMove);
        } catch ( Exception ex ) {
            Logger.getLogger(StatisticExperiment.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * @return the dateForFileName
     */
    protected Date getDateForFileName() {
        return dateForFileName;
    }

    /**
     * @param dateForFileName the dateForFileName to set
     */
    protected void setDateForFileName(Date dateForFileName) {
        this.dateForFileName = dateForFileName;
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
     * @return the fileName
     */
    protected String getFileName() {
        return fileName;
    }

    /**
     * @param fileName the fileName to set
     */
    protected void setFileName(String fileName) {
        this.fileName = fileName;
    }

    /**
     * @return the gamesToPlay
     */
    protected int getGamesToPlay() {
        return gamesToPlay;
    }

    /**
     * @return the learningMethod
     */
    protected TDLambdaLearning getLearningMethod() {
        return learningMethod;
    }

    /**
     * @param learningMethod the learningMethod to set
     */
    protected void setLearningMethod(TDLambdaLearning learningMethod) {
        this.learningMethod = learningMethod;
    }

    /**
     *
     * @param dateFormater
     */
    protected void setSimpleDateFormat(SimpleDateFormat dateFormater) {
        this.dateFormater = dateFormater;
    }

    /**
     * @return the simulations
     */
    protected int getSimulations() {
        return simulations;
    }

    /**
     * @param threads the simulations to set
     */
    public void setSimulations(int threads) {
        this.simulations = threads;
    }

    /**
     * Se deben inicializar:
     * <ul>
     * <li>private int delayPerMove;</li>
     * <li>private IPlayingExperiment playingExperiment;</li>
     * <li>private String fileName;</li>
     * </ul>
     * <p>
     * Las siguientes variables se inicializan automaticamente, pero pueden ser
     * modificadas:
     * <ul>
     * <li>private int tileToWin;</li>
     * <li>private String experimentName;</li>
     * <li>private PerceptronConfiguration2048 perceptronConfiguration;</li>
     * <li>private TDLambdaLearning learningMethod;</li>
     * </ul>
     * <p>
     * @throws Exception
     */
    protected abstract void initializeStatistics() throws Exception;

    /**
     *
     * @param experimentPath
     * @param delayPerMove   <p>
     * @throws Exception
     */
    protected void run(String experimentPath, int delayPerMove) throws Exception {
        String dirPath = experimentPath
                + this.learningExperiment.getaINeuralNetwork().getLibName() + File.separator
                + experimentName + File.separator;
        File dirPathFile = new File(dirPath);
        if ( !dirPathFile.exists() ) {
            dirPathFile.mkdirs();
        }
        String filePath = dirPath + fileName;
        File randomPerceptronFile = new File(dirPath + this.getExperimentName() + LearningExperiment._RANDOM + ".ser");

        //hacemos estadisticas del perceptron random, si es necesario
        Map<File, StatisticForCalc> resultsRandom = new HashMap<>();
        System.out.print("Starting " + this.getExperimentName() + LearningExperiment._RANDOM + " Statistics... ");
        processFile(dirPath + this.getExperimentName() + LearningExperiment._RANDOM, delayPerMove);
        resultsRandom.put(randomPerceptronFile, getStatistics());

        //calculamos las estadisticas de los backup si es necesario
        File[] allFiles = (new File(dirPath)).listFiles();
        Arrays.sort(allFiles, (Object o1, Object o2) -> {
            if ( ((File) o1).lastModified() > ((File) o2).lastModified() ) {
                return +1;
            } else if ( ((File) o1).lastModified() < ((File) o2).lastModified() ) {
                return -1;
            } else {
                return 0;
            }
        });
        List<File> backupFiles = new ArrayList<>();
        Map<File, StatisticForCalc> resultsPerFile = new HashMap<>();
        for ( File f : allFiles ) {
            if ( f.getName().matches(".*\\_BackupN\\-.*\\.ser") ) {
                System.out.print("Starting " + f.getName() + " Statistics... ");
                processFile(dirPath + f.getName().replaceAll("\\.ser$", ""), delayPerMove);
                resultsPerFile.put(f, getStatistics());
                backupFiles.add(f);
            }
        }
        backupFiles.sort((Object o1, Object o2) -> {
            if ( ((File) o1).lastModified() > ((File) o2).lastModified() ) {
                return +1;
            } else if ( ((File) o1).lastModified() < ((File) o2).lastModified() ) {
                return -1;
            } else {
                return 0;
            }
        });

        exportToExcel(filePath, backupFiles, resultsPerFile, resultsRandom, randomPerceptronFile);

    }

    /**
     *
     * @param saveBackupEvery
     */
    protected void saveBackupEvery(int saveBackupEvery) {
        this.saveBackupEvery = saveBackupEvery;
    }

}
