/**
 * TicTacToe játék 1.0 For more information, please visit http://www.xyz.hu Thx
 * for downloading
 */
package ar.edu.unrc.tictactoegame;

import ar.edu.unrc.tdlearning.perceptron.interfaces.IAction;
import ar.edu.unrc.tdlearning.perceptron.interfaces.IActor;
import ar.edu.unrc.tdlearning.perceptron.interfaces.IProblem;
import ar.edu.unrc.tdlearning.perceptron.interfaces.IState;
import ar.edu.unrc.tdlearning.perceptron.interfaces.IStatePerceptron;
import ar.edu.unrc.tdlearning.perceptron.interfaces.IsolatedComputation;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.SwingUtilities;
import org.encog.ml.data.MLData;
import org.encog.ml.data.basic.BasicMLData;
import org.encog.neural.networks.BasicNetwork;

/**
 *
 * @author Gyarmati János
 * @param <NeuralNetworkClass>
 */
public class GameTicTacToe<NeuralNetworkClass> extends JFrame implements IProblem {

    private static String[] arguments;

    public static void main(String[] args) {
        arguments = args;
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            @SuppressWarnings( "ResultOfObjectAllocationIgnored" )
            public void run() {
                new GameTicTacToe(null, true, 0);
            }
        });
    }
    private String about;
    private GameBoard board;


    private Container contentPanel;
    private int frameHeight;
    private int frameWidth;
    private String howToPlay;
    private InfoPanel infoPanel;
    private JMenu mFile;
    private JMenu mHelp;
    private JMenuBar mbMainMenu;
    private JMenuItem miAbout;
    private JMenuItem miExit;
    private JMenuItem miHowto;
    private JMenuItem miNewGame;
    private JMenuItem miOptions;
    private final PerceptronConfigurationTicTacToe<NeuralNetworkClass> perceptronConfiguration;
    private PlayPanel playPanel;
    private Player player1;
    private Player player2;
    private Dimension screenSize;

    public GameTicTacToe(PerceptronConfigurationTicTacToe<NeuralNetworkClass> perceptronConfiguration, boolean show, int delayPerMove) {
        initComponents();
        createMenu();
        setTitle("TicTacToe");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setBounds((screenSize.width - frameWidth) / 2, (screenSize.height - frameHeight) / 2, frameWidth, frameHeight);
        contentPanel.setLayout(new GridLayout(1, 2));
        contentPanel.add(playPanel = new PlayPanel(getSize(), show, delayPerMove));
        contentPanel.add(infoPanel);
        board = new GameBoard();
        playPanel.uploadPanelWithSquares(board);
        playPanel.setPlayer1(player1);
        playPanel.setPlayer2(player2);
        playPanel.setInfoPanel(infoPanel);
        setVisible(show);
        this.perceptronConfiguration = perceptronConfiguration;

    }

    @Override
    public IState computeAfterState(IState turnInitialState, IAction action) {
        GameBoard afterState = (GameBoard) turnInitialState.getCopy();
        playPanel.mouseClickedOnSquare(afterState, (Action) action, playPanel.getClicks());
        return afterState;
    }

    @Override
    public IState computeNextTurnStateFromAfterstate(IState afterstate) {
        GameBoard best = null;
        double bestEvaluation = 999999999;
        for ( int b : board.getAllPossibleMovements() ) {
            GameBoard finalBoard = (GameBoard) afterstate.getCopy();
            playPanel.mouseClickedOnSquare(finalBoard, PlayPanel.squareIndexToAction(b), playPanel.getClicks());
            Object[] output = evaluateBoardWithPerceptron(finalBoard).compute();
            double output0 = (double) output[0];
            //el jugador 2 es el 0, luego los valores mas cerca del 0 es lo que va a elegir
            if ( output0 < 0 ) {
                output0 = output0 * -1; //para no tener que distinguir entre negativos y positivos
            }
            if ( output0 < bestEvaluation ) {
                bestEvaluation = output0;
                best = finalBoard;
            }
        }
        return best;
    }

    @Override
    public IsolatedComputation<Double> computeNumericRepresentationFor(Object[] output, IActor actor) {
//        if ( this.perceptronConfiguration != null ) {
//            return this.perceptronConfiguration.computeNumericRepresentationFor(this, output);
//        } else {
        return () -> {
            assert output.length == 1;
            return (Double) output[0];
        };
        //}
    }

    @Override
    public double denormalizeValueFromPerceptronOutput(Object value) {
        return (Double) value;
    }

    @Override
    public IsolatedComputation<Object[]> evaluateBoardWithPerceptron(IState state) {
        return () -> {
            //dependiendo de que tipo de red neuronal utilizamos, evaluamos las entradas y calculamos una salida
            if ( perceptronConfiguration != null && perceptronConfiguration.getNeuralNetwork() != null ) {
                if ( perceptronConfiguration.getNeuralNetwork() instanceof BasicNetwork ) { //es sobre la libreria encog
                    double[] inputs = new double[perceptronConfiguration.neuronQuantityInLayer[0]];
                    for ( int i = 0; i < perceptronConfiguration.neuronQuantityInLayer[0]; i++ ) {
                        inputs[i] = ((IStatePerceptron) state).translateToPerceptronInput(i).compute();
                    } //todo reeemplazar esot po algo ams elegante
                    MLData inputData = new BasicMLData(inputs);
                    MLData output = ((BasicNetwork) perceptronConfiguration.getNeuralNetwork()).compute(inputData);
                    Double[] out = new Double[output.getData().length];
                    for ( int i = 0; i < output.size(); i++ ) {
                        out[i] = output.getData()[i];
                    }
                    return out;
                }
            }
            throw new UnsupportedOperationException("only Encog and NTupleSystem is implemented");
        };
    }

    @Override
    public IActor getActorToTrain() {
        return player1; //TODO: Implementar ramdom para que juegue tanto con1 como con 2 y recordar modificar initialize
        //Si se hace esa modificación, hay que tener en cuenta modificar como busca los jugadores, axtualment elos busca por los clicks no le da bola al player
    }

    public GameBoard getBoard() {
        return board;
    }

    @Override
    public void setCurrentState(IState nextTurnState) {
        board = (GameBoard) nextTurnState;
        playPanel.nextTurn();
    }

    @Override
    public double getFinalReward(int outputNeuron) {
        if ( playPanel.getWinner() == null ) {
            return 0;
        } else {
            return playPanel.getWinner().getToken();
        }
    }

    public int getLastTurn() {
        return this.playPanel.getClicks();
    }

    public String getWinner() {
        return (this.playPanel.getWinner() != null) ? this.playPanel.getWinner().getName() : "Empate";
    }

    @Override
    public IState initialize(IActor actor) {
        return new GameBoard();
    }

    public boolean isTerminalState() {
        return this.board.isTerminalState();
    }

    @Override
    public ArrayList<IAction> listAllPossibleActions(IState turnInitialState) {
        GameBoard initialBoard = (GameBoard) turnInitialState;
        ArrayList<IAction> possibles = new ArrayList<>(9);
        for ( int i = 0; i < initialBoard.getSquares().size(); i++ ) {
            if ( !initialBoard.getSquares().get(i).isClicked() ) {
                possibles.add(PlayPanel.squareIndexToAction(i));
            }
        }
        return possibles;
    }

    public void newGameMenuItemActionPerformed(ActionEvent e) {
        dispose();
        String args[] = {player1.getName(), player2.getName(),
            Integer.toString(player1.getScore()), Integer.toString(player2.getScore())};
        main(args);
    }

    @Override
    public double normalizeValueToPerceptronOutput(Object value) {
        return (Double) value;
    }

    public void processInput(Action action) {
        playPanel.mouseClickedOnSquare(board, action, playPanel.getClicks());
    }

    private void aboutMenuItemActionPerformed(ActionEvent e) {
        JOptionPane.showMessageDialog(this, about, "About", JOptionPane.INFORMATION_MESSAGE);
    }

    private void createMenu() {
        setJMenuBar(mbMainMenu);
        mbMainMenu.add(mFile);
        mbMainMenu.add(mHelp);
        mFile.add(miNewGame);
        mFile.add(miOptions);
        mFile.add(miExit);
        mHelp.add(miHowto);
        mHelp.add(miAbout);

        mFile.setText("File");
        mHelp.setText("Help");
        miNewGame.setText("New Game");
        miOptions.setText("Options");
        miExit.setText("Exit");
        miHowto.setText("How To Play");
        miAbout.setText("About");

        miNewGame.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                newGameMenuItemActionPerformed(e);
            }
        });
        miOptions.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                optionsMenuItemActionPerformed(e);
            }
        });
        miExit.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                exitMenuItemActionPerformed(e);
            }
        });
        miHowto.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                howToPlayMenuItemActionPerformed(e);
            }
        });
        miAbout.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                aboutMenuItemActionPerformed(e);
            }
        });
    }

    private void exitMenuItemActionPerformed(ActionEvent e) {
        System.exit(0);
    }

    private void howToPlayMenuItemActionPerformed(ActionEvent e) {
        JOptionPane.showMessageDialog(this, howToPlay, "How To Play", JOptionPane.INFORMATION_MESSAGE);
    }

    private void initComponents() {
        contentPanel = getContentPane();
        mbMainMenu = new JMenuBar();
        mFile = new JMenu();
        mHelp = new JMenu();
        miNewGame = new JMenuItem();
        miOptions = new JMenuItem();
        miExit = new JMenuItem();
        miAbout = new JMenuItem();
        miHowto = new JMenuItem();

        howToPlay = "The X player usually goes first[citation needed]."
                + "\nThe player who succeeds in placing three respective marks in a "
                + "\nhorizontal, vertical, or diagonal row wins the game."
                + "\n\nIn the options menu, You can set the player names."
                + "\n\nWikipedia link: http://en.wikipedia.org/wiki/Tic-tac-toe";
        about = "TicTacToe game 1.0"
                + "\nCreated by Janos Gyarmati"
                + "\nAll rights reserved";
        screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        frameWidth = (screenSize.width) / 2;
        frameHeight = (screenSize.height) / 2;

        if ( arguments == null || arguments.length < 1 ) {
            player1 = new Player("Player 1", 0, 1);
            player2 = new Player("Player 2", 0, 0);
        } else {
            player1 = new Player(arguments[0], Integer.parseInt(arguments[2]), 1);
            player2 = new Player(arguments[1], Integer.parseInt(arguments[3]), 0);
        }
        infoPanel = new InfoPanel(player1, player2);
    }

    private void optionsMenuItemActionPerformed(ActionEvent e) {
        GameOptionsDialog god = new GameOptionsDialog(this);
        god.setVisible(true);
        player1.setName(god.getP1name());
        infoPanel.setP1name(player1.getName());
        player2.setName(god.getP2name());
        infoPanel.setP2name(player2.getName());
    }

}
