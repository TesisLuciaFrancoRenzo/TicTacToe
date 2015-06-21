/**
 * TicTacToe játék 1.0 For more information, please visit http://www.xyz.hu Thx
 * for downloading
 */
package tictactoegame;

import ar.edu.unrc.tdlearning.perceptron.interfaces.IAction;
import ar.edu.unrc.tdlearning.perceptron.interfaces.IActor;
import ar.edu.unrc.tdlearning.perceptron.interfaces.IProblem;
import ar.edu.unrc.tdlearning.perceptron.interfaces.IState;
import ar.edu.unrc.tdlearning.perceptron.interfaces.IsolatedComputation;
import ar.edu.unrc.tdlearning.perceptron.learning.StateProbability;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.SwingUtilities;

/**
 *
 * @author Gyarmati János
 */
public class TicTacToeGame extends JFrame implements IProblem {

    private static String[] arguments;

    public static void main(String[] args) {
        try {
            arguments = args;
        } catch ( Exception e ) {
        }
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            @SuppressWarnings( "ResultOfObjectAllocationIgnored" )
            public void run() {
                new TicTacToeGame(true);
            }
        });
    }
    private String about;
    private Board board;

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
    private PlayPanel playPanel;
    private Player player1;
    private Player player2;
    private Dimension screenSize;

    private final boolean show;

    private TicTacToeGame(boolean show) {
        initComponents();
        createMenu();
        setTitle("TicTacToe");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setBounds((screenSize.width - frameWidth) / 2, (screenSize.height - frameHeight) / 2, frameWidth, frameHeight);
        contentPanel.setLayout(new GridLayout(1, 2));
        contentPanel.add(playPanel = new PlayPanel(getSize(), show));
        contentPanel.add(infoPanel);
        board = new Board();
        playPanel.uploadPanelWithSquares(board);
        playPanel.setPlayer1(player1);
        playPanel.setPlayer2(player2);
        playPanel.setInfoPanel(infoPanel);
        this.show = show;
        setVisible(show);

    }

    @Override
    public IState computeAfterState(IState turnInitialState, IAction action) {
        Board afterState = (Board) turnInitialState.getCopy();
        playPanel.mouseClickedOnSquare(afterState, (Action) action);
        return afterState;
    }

    @Override
    public IState computeNextTurnStateFromAfterstate(IState afterstate) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public IsolatedComputation<Double> computeNumericRepresentationFor(Object[] output, IActor actor) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public double denormalizeValueFromPerceptronOutput(Object value) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public IsolatedComputation<Object[]> evaluateBoardWithPerceptron(IState state) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public IActor getActorToTrain() {
        return player1; //TODO: Implementar ramdom para que juegue tanto con1 como con 2 y recordar modificar initialize
    }

    @Override
    public void setCurrentState(IState nextTurnState) {
        board = (Board) nextTurnState;
        playPanel.nextTurn();
    }

    @Override
    public double getFinalReward(int outputNeuron) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public IState initialize(IActor actor) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public ArrayList<IAction> listAllPossibleActions(IState turnInitialState) {
        Board initialBoard = (Board) turnInitialState;
        ArrayList<IAction> possibles = new ArrayList<>(9);
        for ( int i = 0; i < initialBoard.getSquares().size(); i++ ) {
            if ( !initialBoard.getSquares().get(i).isClicked() ) {
                possibles.add(PlayPanel.squareIndexToAction(i));
            }
        }
        return possibles;
    }

    @Override
    public List<StateProbability> listAllPossibleNextTurnStateFromAfterstate(IState afterState) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    public void newGameMenuItemActionPerformed(ActionEvent e) {
        dispose();
        String args[] = {player1.getName(), player2.getName(),
            Integer.toString(player1.getScore()), Integer.toString(player2.getScore())};
        main(args);
    }

    @Override
    public double normalizeValueToPerceptronOutput(Object value) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
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
        //playPanel = new PlayPanel();

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

        if ( arguments.length < 1 ) {
            player1 = new Player("Player 1", 0);
            player2 = new Player("Player 2", 0);
        } else {
            player1 = new Player(arguments[0], Integer.parseInt(arguments[2]));
            player2 = new Player(arguments[1], Integer.parseInt(arguments[3]));
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
