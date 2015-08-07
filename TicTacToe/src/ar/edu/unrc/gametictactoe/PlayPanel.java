/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ar.edu.unrc.gametictactoe;

import static ar.edu.unrc.gametictactoe.Action.S0;
import static ar.edu.unrc.gametictactoe.Action.S1;
import static ar.edu.unrc.gametictactoe.Action.S2;
import static ar.edu.unrc.gametictactoe.Action.S3;
import static ar.edu.unrc.gametictactoe.Action.S4;
import static ar.edu.unrc.gametictactoe.Action.S5;
import static ar.edu.unrc.gametictactoe.Action.S6;
import static ar.edu.unrc.gametictactoe.Action.S7;
import static ar.edu.unrc.gametictactoe.Action.S8;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import static java.lang.Thread.sleep;
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

    public static int actionToSquareIndex(Action action) {
        switch ( action ) {
            case S0: {
                return 0;
            }
            case S1: {
                return 1;
            }
            case S2: {
                return 2;
            }
            case S3: {
                return 3;
            }
            case S4: {
                return 4;
            }
            case S5: {
                return 5;
            }
            case S6: {
                return 6;
            }
            case S7: {
                return 7;
            }
            case S8: {
                return 8;
            }
            default:
                throw new IllegalArgumentException();
        }
    }

    public static Action squareIndexToAction(int squareIndex) {
        switch ( squareIndex ) {
            case 0: {
                return S0;
            }
            case 1: {
                return S1;
            }
            case 2: {
                return S2;
            }
            case 3: {
                return S3;
            }
            case 4: {
                return S4;
            }
            case 5: {
                return S5;
            }
            case 6: {
                return S6;
            }
            case 7: {
                return S7;
            }
            case 8: {
                return S8;
            }
            default:
                throw new IllegalArgumentException();
        }
    }

    private final int delayPerMove;
    private InfoPanel infoPanel;
    private final Dimension panelSize;
    private final boolean repaint;
    private GameBoard board;

    public PlayPanel(Dimension size, boolean repaint, int delayPerMove, Player player1, Player player2) {
        setBorder(BorderFactory.createBevelBorder(BevelBorder.RAISED, Color.lightGray, Color.black));
        setLayout(new GridLayout(3, 3));
        this.panelSize = size;
        this.repaint = repaint;
        this.delayPerMove = delayPerMove;
        this.board = new GameBoard(player1, player2, this);
        this.uploadPanelWithSquares(board);
    }

    public void setInfoPanel(InfoPanel infoPanel) {
        this.infoPanel = infoPanel;
    }

    public void mouseClickedOnSquare(GameBoard board, Action action, boolean show) {
        int actualSquareIndex = actionToSquareIndex(action);
        pickSquare(board, actualSquareIndex);
        if ( show ) {
            Square actualSquare = board.getSquares().get(actualSquareIndex);
            show(actualSquare);
        }

    }

    private void uploadPanelWithSquares(GameBoard board) {
        Square square;
        setSize(panelSize.width, panelSize.height);
        for ( int i = 0; i < 9; i++ ) {
            add(square = new Square(panelSize.width, panelSize.height));
            board.getSquares().add(square);
            square.addMouseListener(new MouseAdapter() {
                @Override
                public void mouseClicked(MouseEvent e) {
                    super.mouseClicked(e);
                    mouseClickedOnSquare(board, e);
                }
            });
        }
    }

    /**
     *
     * @return
     */
    public GameBoard getBoard() {
        return board;
    }

    public void setBoard(GameBoard gameBoard) {
        this.board = gameBoard;
    }

    /**
     *
     * @param board
     * @param actualSquareIndex
     */
    public void pickSquare(GameBoard board, int actualSquareIndex) {
        Square actualSquare = board.getSquares().get(actualSquareIndex);
        assert !actualSquare.isClicked();
        actualSquare.setPaintType(board.getCurrentPlayer().getToken());
        if ( board.getCurrentPlayer().getToken() == Token.O ) {
            board.getPlayer2IndexList().add(0, actualSquareIndex);
        } else {
            board.getPlayer1IndexList().add(0, actualSquareIndex);
        }
        actualSquare.setClicked();

    }

    public void endGame(GameBoard board, Players winner) {
//        if ( !(board.getTurn() <= 9 && board.getTurn() >= 5) ) {
//            System.out.println("mal");
//        }
        assert (board.getTurn() <= 9 && board.getTurn() >= 5);
        switch ( winner ) {
            case DRAW: {
                if ( repaint ) {
                    JOptionPane.showMessageDialog(this, " Empate");
                }
                break;
            }
            case PLAYER1: {
                winnerSetup(board.getPlayer1(), repaint);
                break;
            }
            case PLAYER2: {
                winnerSetup(board.getPlayer2(), repaint);
                break;
            }
            default:
                throw new IllegalArgumentException("El parametro winner = " + winner + " no es válido en este contexto");
        }
        System.out.println(board.getPlayer1IndexList());
        System.out.println(board.getPlayer2IndexList());
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

    private void mouseClickedOnSquare(GameBoard board, MouseEvent e) {
        Square actualSquare = (Square) e.getSource();
        if ( !actualSquare.isClicked() ) {
            pickSquare(board, board.getSquares().indexOf(actualSquare));
            show(actualSquare);
            board.nextTurn(false);
        }
    }

    void mouseClickedOnSquare(Action action) {
        mouseClickedOnSquare(this.board, action, true);
    }

    void reset() {
        this.getBoard().reset();
    }

}
