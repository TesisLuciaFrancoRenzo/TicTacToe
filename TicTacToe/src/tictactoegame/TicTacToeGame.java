/**
 * TicTacToe játék 1.0 For more information, please visit http://www.xyz.hu Thx
 * for downloading
 */
package tictactoegame;

import java.awt.Container;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
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
public class TicTacToeGame extends JFrame {
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
        playPanel.uploadPanelWithSquares();
        playPanel.setPlayer1(player1);
        playPanel.setPlayer2(player2);
        playPanel.setInfoPanel(infoPanel);
        this.show = show;
        setVisible(show);

    }

    public void newGameMenuItemActionPerformed(ActionEvent e) {
        dispose();
        String args[] = {player1.getName(), player2.getName(),
            Integer.toString(player1.getScore()), Integer.toString(player2.getScore())};
        main(args);
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
