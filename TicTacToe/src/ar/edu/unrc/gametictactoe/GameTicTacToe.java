/**
 * TicTacToe játék 1.0 For more information, please visit http://www.xyz.hu Thx
 * for downloading
 */
package ar.edu.unrc.gametictactoe;

import static ar.edu.unrc.gametictactoe.PlayPanel.actionToSquareIndex;
import ar.edu.unrc.tdlearning.perceptron.interfaces.IAction;
import ar.edu.unrc.tdlearning.perceptron.interfaces.IActor;
import ar.edu.unrc.tdlearning.perceptron.interfaces.IProblem;
import ar.edu.unrc.tdlearning.perceptron.interfaces.IState;
import ar.edu.unrc.tdlearning.perceptron.interfaces.IStatePerceptron;
import ar.edu.unrc.tdlearning.perceptron.interfaces.IsolatedComputation;
import ar.edu.unrc.tdlearning.perceptron.learning.TDLambdaLearning;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
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

    /**
     *
     * @param args
     */
    @SuppressWarnings( "ResultOfObjectAllocationIgnored" )
    public static void main(String[] args) {
        arguments = args;
        SwingUtilities.invokeLater(() -> {
            new GameTicTacToe(null, null, true, 0, null);
        });
    }
    private String about;

    private Container contentPanel;
    private int frameHeight;
    private int frameWidth;
    private String howToPlay;
    private InfoPanel infoPanel;
    private final TDLambdaLearning learningAlgorithm;
    private JMenu mFile;
    private JMenu mHelp;
    private JMenuBar mbMainMenu;
    private JMenuItem miAbout;
    private JMenuItem miExit;
    private JMenuItem miHowto;
    private JMenuItem miNewGame;
    private JMenuItem miOptions;
    private final PerceptronConfigurationTicTacToe<NeuralNetworkClass> perceptronConfiguration;
    private final PlayPanel playPanel;
    private Player player1;
    private Player player2;
    private Dimension screenSize;
    private final boolean show;
    private Player playerToTrain;

    /**
     *
     * @param perceptronConfiguration
     * @param show
     * @param delayPerMove
     * @param learningAlgorithm
     * @param playerToTrain
     */
    public GameTicTacToe(PerceptronConfigurationTicTacToe<NeuralNetworkClass> perceptronConfiguration, TDLambdaLearning learningAlgorithm, boolean show, int delayPerMove, Players playerToTrain) {
        initComponents();
        createMenu();
        setTitle("TicTacToe");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setBounds((screenSize.width - frameWidth) / 2, (screenSize.height - frameHeight) / 2, frameWidth, frameHeight);
        contentPanel.setLayout(new GridLayout(1, 2));
        contentPanel.add(playPanel = new PlayPanel(getSize(), show, delayPerMove, player1, player2));
        contentPanel.add(infoPanel);
        newGameMenuItemActionPerformed(null);
        playPanel.setInfoPanel(infoPanel);
        setVisible(show);
        this.perceptronConfiguration = perceptronConfiguration;
        this.show = show;
        this.learningAlgorithm = learningAlgorithm;
        if ( playerToTrain == Players.PLAYER1 ) {
            this.playerToTrain = player1;
        } else if ( playerToTrain == Players.PLAYER2 ) {
            this.playerToTrain = player2;
        } else {
            this.playerToTrain = null;
        }
    }

    @Override
    public IState computeAfterState(IState turnInitialState, IAction action) {
        assert action != null;
        GameBoard afterState = (GameBoard) turnInitialState.getCopy();
        int actualSquareIndex = actionToSquareIndex((Action) action);
        playPanel.pickSquare(afterState, actualSquareIndex);
        if ( afterState.getCurrentPlayer().equals(player1) ) {
            afterState.setPlayer1Action((Action) action);
        } else {
            afterState.setPlayer2Action((Action) action);
        }
        afterState.nextTurn(true);
        return afterState;
    }

    @Override
    public IState computeNextTurnStateFromAfterstate(IState afterState) {
        if ( afterState.isTerminalState() ) {
            if ( ((GameBoard) afterState).getCurrentPlayer().equals(player1) ) {
                ((GameBoard) afterState).setPlayer2Action(null);
            }
            return afterState;
        } else {
            ArrayList<IAction> possibleEnemyActions = this.listAllPossibleActions(afterState);
            assert !possibleEnemyActions.isEmpty();
            IAction bestEnemyAction = this.learningAlgorithm.computeBestPossibleAction(this, afterState, possibleEnemyActions, ((GameBoard) afterState).getEnemyPlayer()).compute();
            assert bestEnemyAction != null;
            GameBoard finalBoard = (GameBoard) afterState.getCopy();
            int actualSquareIndex = actionToSquareIndex((Action) bestEnemyAction);
            playPanel.pickSquare(finalBoard, actualSquareIndex);
            if ( finalBoard.getCurrentPlayer().equals(player1) ) {
                finalBoard.setPlayer1Action((Action) bestEnemyAction);
            } else {
                finalBoard.setPlayer2Action((Action) bestEnemyAction);
            }
            finalBoard.nextTurn(true);
            return finalBoard;
        }
    }

    @Override
    public IsolatedComputation<Double> computeNumericRepresentationFor(Object[] output, IActor actor) {
//        if ( this.perceptronConfiguration != null ) {
//            return this.perceptronConfiguration.computeNumericRepresentationFor(this, output);
//        } else {
        return () -> {
            assert output.length == 1;
            return (Double) output[0] * ((Player) actor).getToken().getRepresentation();
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
                    } //TODO reeemplazar esto por algo mas elegante
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
        return playerToTrain; //TODO: Implementar ramdom para que juegue tanto con1 como con 2 y recordar modificar initialize
        //Si se hace esa modificación, hay que tener en cuenta modificar como busca los jugadores, actualmente
        //los busca por los clicks no le da bola al player. Modificar tambien la funcion getFinalReward
    }

    public IState getBoard() {
        return playPanel.getBoard();
    }

    @Override
    public void setCurrentState(IState nextTurnState) {
        GameBoard board = ((GameBoard) nextTurnState);
        playPanel.setBoard(board);
        board.printLastActions(playerToTrain);
        Players winner = board.whoWin();
        if ( winner != Players.NONE ) {
            playPanel.endGame(board, winner);
        }
        assert board.getTurn() <= 9;
    }

    @Override
    public double getFinalReward(int outputNeuron) {
        if ( playPanel.getBoard().getCurrentPlayer() == null ) {
            return 0;
        } else {
            return playPanel.getBoard().getCurrentPlayer().getToken().getRepresentation();
            //TODO: revisar, Si gana el player 2 le va a dar un reward negativo (-1) y no se si eso esta bien o tiene que darle 0
        }
    }

    /**
     *
     * @return
     */
    public int getLastTurn() {
        return this.playPanel.getBoard().getTurn();
    }

    /**
     *
     * @return
     */
    public String getWinner() {
        return (this.playPanel.getBoard().getCurrentPlayer() != null) ? this.playPanel.getBoard().getCurrentPlayer().getName() : "Empate";
    }

    @Override//FIXME: que imprima el turno 1 y arreglar que el player 1 tiene los turnos corridos (imprimnir al revez si entrena con el 2)
    public IState initialize(IActor actor) {
        newGameMenuItemActionPerformed(null);
        GameBoard board = (GameBoard) this.playPanel.getBoard();
        if ( ((Player) actor).equals(this.player2) ) {
            ArrayList<IAction> possibleEnemyActions = this.listAllPossibleActions(board);
            IAction bestEnemyAction = this.learningAlgorithm.computeBestPossibleAction(this, board, possibleEnemyActions, ((GameBoard) board).getCurrentPlayer()).compute();
            playPanel.setBoard((GameBoard) computeAfterState(board, bestEnemyAction));
            playPanel.getBoard().printLastActions(playerToTrain);
            return playPanel.getBoard();
        }
        return board.getCopy();
    }

    /**
     *
     * @return
     */
    public boolean isTerminalState() {
        return this.playPanel.getBoard().isTerminalState();
    }

    @Override
    public ArrayList<IAction> listAllPossibleActions(IState turnInitialState) {
        GameBoard initialBoard = (GameBoard) turnInitialState;
        ArrayList<IAction> possibles = new ArrayList<>(initialBoard.getSquares().size());
        for ( int i = 0; i < initialBoard.getSquares().size(); i++ ) {
            if ( !initialBoard.getSquares().get(i).isClicked() ) {
                possibles.add(PlayPanel.squareIndexToAction(i));
            }
        }
//        System.out.println(possibles.toString());
        return possibles;
    }

    /**
     *
     * @param e
     */
    private void newGameMenuItemActionPerformed(ActionEvent e) {
        playPanel.reset();
        if ( this.show ) {
            this.repaint();
        }
        //        dispose();
        //        String args[] = {player1.getName(), player2.getName(),
        //            Integer.toString(player1.getScore()), Integer.toString(player2.getScore())};
        //        main(args);
        {

        }
    }

    @Override
    public double normalizeValueToPerceptronOutput(Object value) {
        return (Double) value;
    }

    /**
     *
     * @param action
     */
    public void processInput(Action action) {
        playPanel.mouseClickedOnSquare(action);
        playPanel.getBoard().nextTurn(false);
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

        miNewGame.addActionListener((ActionEvent e) -> {
            newGameMenuItemActionPerformed(e);
        });
        miOptions.addActionListener((ActionEvent e) -> {
            optionsMenuItemActionPerformed(e);
        });
        miExit.addActionListener((ActionEvent e) -> {
            exitMenuItemActionPerformed(e);
        });
        miHowto.addActionListener((ActionEvent e) -> {
            howToPlayMenuItemActionPerformed(e);
        });
        miAbout.addActionListener((ActionEvent e) -> {
            aboutMenuItemActionPerformed(e);
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
            player1 = new Player("Player 1", 0, Token.X);
            player2 = new Player("Player 2", 0, Token.O);
        } else {
            player1 = new Player(arguments[0], Integer.parseInt(arguments[2]), Token.X);
            player2 = new Player(arguments[1], Integer.parseInt(arguments[3]), Token.O);
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

//    @Override
//    public void dispose() {
//        this.dispose();
//    }
}
