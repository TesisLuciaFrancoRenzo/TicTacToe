/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ar.edu.unrc.tictactoegame;

import static ar.edu.unrc.tictactoegame.Action.S0;
import static ar.edu.unrc.tictactoegame.Action.S1;
import static ar.edu.unrc.tictactoegame.Action.S2;
import static ar.edu.unrc.tictactoegame.Action.S3;
import static ar.edu.unrc.tictactoegame.Action.S4;
import static ar.edu.unrc.tictactoegame.Action.S5;
import static ar.edu.unrc.tictactoegame.Action.S6;
import static ar.edu.unrc.tictactoegame.Action.S7;
import static ar.edu.unrc.tictactoegame.Action.S8;
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
class PlayPanel extends JPanel {

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

    private int clicks = 1;
    private final int delayPerMove;
    private InfoPanel infoPanel;
    private final Dimension panelSize;
    private Player player1;
    private Player player2;
    private final boolean repaint;
    private Player winner;

    public PlayPanel(Dimension size, boolean repaint, int delayPerMove) {
        setBorder(BorderFactory.createBevelBorder(BevelBorder.RAISED, Color.lightGray, Color.black));
        setLayout(new GridLayout(3, 3));
        this.panelSize = size;
        this.repaint = repaint;
        this.winner = null;
        this.delayPerMove = delayPerMove;
    }

    public int getClicks() {
        return clicks;
    }

    public InfoPanel getInfoPanel() {
        return infoPanel;
    }

    public void setInfoPanel(InfoPanel infoPanel) {
        this.infoPanel = infoPanel;
    }

    public Player getPlayer1() {
        return player1;
    }

    public void setPlayer1(Player player1) {
        this.player1 = player1;
    }

    public Player getPlayer2() {
        return player2;
    }

    public void setPlayer2(Player player2) {
        this.player2 = player2;
    }

    public Player getWinner() {
        return winner;
    }

    public void setWinner(Player winner) {
        this.winner = winner;
    }

    public void mouseClickedOnSquare(GameBoard board, Action action, int turn) {
        int actualSquareIndex = actionToSquareIndex(action);
        drawOnActualSquare(board, actualSquareIndex, turn);
        if ( repaint ) {
            board.getSquares().get(actualSquareIndex).repaint();
            if ( this.delayPerMove > 0 ) {
                try {
                    sleep(this.delayPerMove);
                } catch ( InterruptedException ex ) {
                    Logger.getLogger(GameTicTacToe.class.getName()).log(Level.SEVERE, null, ex);

                }

            }
        }
        board.getSquares().get(actualSquareIndex).setClicked();

    }

//    /**
//     * Calcula los posibles movimientos del contrincante. Si existen movimientos
//     * ganadores por parte del contrincante, hay una posibilidad del 75% de que
//     * ejecute alguno los movimientos ganadores
//     * <p>
//     * @param afterState <p>
//     * @return
//     */
//    List<StateProbability> listAllPossibleNextTurnStateFromAfterstate(GameBoard afterState) {
//        ArrayList<StateProbability> possiblesNextTurnState = new ArrayList<>();
//        ArrayList indexNotClickedSquares = new ArrayList();
//        ArrayList<Board> winnerBoards = new ArrayList();
//        ArrayList<Board> nonWinnerBoards = new ArrayList();
//        //Asigno las probabilidades de elegir un movimiento ganador sobre uno no ganador
//        double probabilityToWinnerBoard = 0.75;
//        double probabilityToNonWinnerBoard = 0.25;
//        //Calculo la lista de lugares disponibles
//        for ( int i = 0; i < afterState.getSquares().size(); i++ ) {
//            if ( !afterState.getSquares().get(i).isClicked() ) {
//                indexNotClickedSquares.add(i);
//            }
//        }
//        //Calculo los posibles movimientos, discriminandolos por movimiento ganador o no ganador (desde el punto de vista del contrincante)
//        indexNotClickedSquares.stream().map((indexNotClickedSquare) -> (Integer) indexNotClickedSquare).map((pos) -> {
//            GameBoard possible = (GameBoard) afterState.getCopy();
//            mouseClickedOnSquare(possible, squareIndexToAction((Integer) pos), clicks + 1);
//            return possible;
//        }).forEach((possible) -> {
//            if ( win((GameBoard) possible, clicks + 1) ) {
//                winnerBoards.add((GameBoard) possible);
//            } else {
//                nonWinnerBoards.add((GameBoard) possible);
//            }
//        });
//        //Recalculo las probabilidades (todos los tableros ganadores tienen la misma probabilidad de ser elegidos,
//        // y todos los tableros no ganadores tienen la misma probabilidad de ser elegidos.
//        if ( winnerBoards.isEmpty() ) {
//            probabilityToNonWinnerBoard = 1;
//        } else {
//            probabilityToWinnerBoard = probabilityToWinnerBoard / winnerBoards.size();
//        }
//        probabilityToNonWinnerBoard = probabilityToNonWinnerBoard / nonWinnerBoards.size();
//        //Cargo la lista de tableros futuros posibles con su probabilidad de ocurrencia
//        for ( GameBoard winnerBoard : winnerBoards ) {
//            possiblesNextTurnState.add(new StateProbability(winnerBoard, probabilityToWinnerBoard));
//        }
//        for ( GameBoard nonWinnerBoard : nonWinnerBoards ) {
//            possiblesNextTurnState.add(new StateProbability(nonWinnerBoard, probabilityToNonWinnerBoard));
//        }
//
//        return possiblesNextTurnState;
//    }
    public void nextTurn() {
        clicks++;
    }

    public boolean somePlayerWins(GameBoard board) {
        ArrayList winList = new ArrayList();
        ArrayList oList = board.getOIndexList();
        ArrayList xList = board.getXIndexList();
        for ( int[] winIndexe : board.getWinIndexes() ) {
            winList.add(winIndexe[0]);
            winList.add(winIndexe[1]);
            winList.add(winIndexe[2]);
            if ( oList.containsAll(winList) ) {
                endGame(board, 0);
                return true;
            } else if ( xList.containsAll(winList) ) {
                endGame(board, 1);
                return true;
            }
            winList.clear();
        }
        if ( oList.size() == 5 && xList.size() == 4 ) {
            endGame(board, 2);
            return true;
        }
        return false;
    }

    public void uploadPanelWithSquares(GameBoard board) {
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

    /*@Override
    protected void paintComponent(Graphics g) {
    super.paintComponent(g);
    }*/
    /**
     *
     * @param board
     * @param actualSquareIndex
     * @param turn              para calcular los siguientes states posibles
     *                          necesito saber a que jugador le toca. si uso la
     *                          variable global, la tengo que modificar y rompo
     *                          el juego.
     */
    private void drawOnActualSquare(GameBoard board, int actualSquareIndex, int turn) {
        Square actualSquare = (Square) board.getSquares().get(actualSquareIndex);
        if ( turn % 2 > 0 ) {
            actualSquare.setPaintType(player2.getToken());
            board.getOIndexList().add(0, actualSquareIndex);
        } else {
            actualSquare.setPaintType(player1.getToken());
            board.getXIndexList().add(0, actualSquareIndex);
        }
    }

    private void endGame(GameBoard board, int winnerCode) {
        board.getSquares().stream().forEach((s) -> {
            s.setClicked();
        });
        String winner = "";
        if ( winnerCode == 0 ) {
            this.winner = this.player1;
            winner = player1.getName();
            player1.setScore(player1.getScore() + 1);
            infoPanel.setP1score(player1.getScore());
            player1.setWinner(true);
        }
        if ( winnerCode == 1 ) {
            this.winner = this.player2;
            winner = player2.getName();
            player2.setScore(player2.getScore() + 1);
            infoPanel.setP2score(player2.getScore());
            player2.setWinner(true);

        }
        if ( winnerCode == 2 ) {
            winner = "Empate";
        }
        JOptionPane.showMessageDialog(this, winner + " Ganó!");
    }

    private void mouseClickedOnSquare(GameBoard board, MouseEvent e) {
        Square actualSquare = (Square) e.getSource();
        if ( !actualSquare.isClicked() ) {
            drawOnActualSquare(board, board.getSquares().indexOf(actualSquare), clicks);
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
            if ( !somePlayerWins(board) ) {
                actualSquare.setClicked();
                nextTurn();
            }
        }
    }
    private boolean win(GameBoard board, int turn) {
        ArrayList indexList;
        if ( turn % 2 > 0 ) {
            indexList = board.getXIndexList();
        } else {
            indexList = board.getOIndexList();
        }
        ArrayList winList = new ArrayList();
        try {
            for ( int[] winIndexe : board.getWinIndexes() ) {
                winList.add(winIndexe[0]);
                winList.add(winIndexe[1]);
                winList.add(winIndexe[2]);
                if ( indexList.containsAll(winList) ) {
                    return true;
                }
            }
        } catch ( Exception e ) {
        }
        return false;
    }

}
