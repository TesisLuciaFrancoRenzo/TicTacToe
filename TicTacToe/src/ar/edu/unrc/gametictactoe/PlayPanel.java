/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ar.edu.unrc.gametictactoe;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import static java.lang.Thread.sleep;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.BorderFactory;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.border.BevelBorder;

/**
 *
 * @author Gyarmati János
 */
public class PlayPanel extends JPanel {

    private GameBoard board;

    private final int delayPerMove;
    private InfoPanel infoPanel;
    private final Dimension panelSize;
    private final boolean repaint;

    /**
     *
     * @param size
     * @param repaint
     * @param delayPerMove
     * @param player1
     * @param player2
     */
    public PlayPanel(Dimension size, boolean repaint, int delayPerMove, Player player1, Player player2) {
        setBorder(BorderFactory.createBevelBorder(BevelBorder.RAISED, Color.lightGray, Color.black));
        setLayout(new GridLayout(3, 3));
        this.panelSize = size;
        this.repaint = repaint;
        this.delayPerMove = delayPerMove;
        this.board = new GameBoard(player1, player2, this.createBoardSquares());
    }

    /**
     *
     * @param board
     */
    public void endGame(GameBoard board) {
        Players winner = board.getWinner();
        switch ( winner ) {
            case NONE: {
                break;
            }
            case DRAW: {
                assert (board.getTurn() <= 9 && board.getTurn() >= 5);
                if ( repaint ) {
                    JOptionPane.showMessageDialog(this, " Empate");
                }
                break;
            }
            case PLAYER1: {
                assert (board.getTurn() <= 9 && board.getTurn() >= 5);
                winnerSetup(board.getPlayer1(), repaint);
                break;
            }
            case PLAYER2: {
                assert (board.getTurn() <= 9 && board.getTurn() >= 5);
                winnerSetup(board.getPlayer2(), repaint);
                break;
            }
            default:
                throw new IllegalStateException("El Ganador asociado al tablero = " + winner + " no es válido en este contexto");
        }
        //System.out.println(board.getPlayer1IndexList());
        //System.out.println(board.getPlayer2IndexList());
    }

    /**
     *
     * @return
     */
    public GameBoard getBoard() {
        return board;
    }

    /**
     *
     * @param gameBoard
     */
    public void setBoard(GameBoard gameBoard) {
        this.board = gameBoard;
    }

    /**
     *
     * @param infoPanel
     */
    public void setInfoPanel(InfoPanel infoPanel) {
        this.infoPanel = infoPanel;
    }

    /**
     *
     * @param board
     * @param action
     * @param show
     */
    public void mouseClickedOnSquare(GameBoard board, Action action, boolean show) {
        int actualSquareIndex = board.pickSquare(action);
        if ( show ) {
            Square actualSquare = board.getSquares().get(actualSquareIndex);
            show(actualSquare);
        }

    }

//    private void uploadPanelWithSquares(GameBoard board) {
//        Square square;
//        setSize(panelSize.width, panelSize.height);
//        for ( int i = 0; i < 9; i++ ) {
//            add(square = new Square(panelSize.width, panelSize.height));
//            board.getSquares().add(square);
//            square.addMouseListener(new MouseAdapter() {
//                @Override
//                public void mouseClicked(MouseEvent e) {
//                    super.mouseClicked(e);
//                    mouseClickedOnSquare(board, e);
//                }
//            });
//        }
//    }
    private ArrayList<Square> createBoardSquares() {
        Square square;
        setSize(panelSize.width, panelSize.height);
        ArrayList<Square> squares = new ArrayList(9);
        for ( int i = 0; i < 9; i++ ) {
            add(square = new Square(panelSize.width, panelSize.height));
            squares.add(square);
            square.addMouseListener(new MouseAdapter() {
                @Override
                public void mouseClicked(MouseEvent e) {
                    super.mouseClicked(e);
                    mouseClickedOnSquare(board, e);
                }
            });
        }
        return squares;
    }

    private void mouseClickedOnSquare(GameBoard board, MouseEvent e) {
        Square actualSquare = (Square) e.getSource();
        if ( !actualSquare.isClicked() ) {
            board.pickSquare(board.getSquares().indexOf(actualSquare));
            show(actualSquare);
            this.endGame(board);
        }
    }
    private void show(Square actualSquare) {
        if ( repaint ) {
            actualSquare.repaint();
            if ( this.delayPerMove > 0 ) {
                try {
                    sleep(this.delayPerMove);
                } catch ( InterruptedException ex ) {
                    Logger.getLogger(GameTicTacToe.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
    }

    private void winnerSetup(Player winner, boolean repaint) {
        winner.setScore(winner.getScore() + 1);
        winner.setWinner(true);
        if ( repaint ) {
            board.getSquares().stream().forEach((s) -> {
                s.setClicked();
            });
            String message;
            message = winner.getName();
            if ( winner.equals(board.getPlayer1()) ) {
                infoPanel.setP1score(board.getPlayer1().getScore());
            } else {
                infoPanel.setP2score(board.getPlayer2().getScore());
            }
            JOptionPane.showMessageDialog(this, message + " Ganó!");
        }
    }

    void mouseClickedOnSquare(Action action) {
        mouseClickedOnSquare(this.board, action, true);
    }

    void reset() {
        this.getBoard().reset();
    }

}
