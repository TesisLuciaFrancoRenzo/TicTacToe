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
        this.uploadPanelWithSquares(board);//TODO ver que hace
    }

//    public int getClicks() {
//        return clicks;
//    }
    public InfoPanel getInfoPanel() {
        return infoPanel;
    }

    public void setInfoPanel(InfoPanel infoPanel) {
        this.infoPanel = infoPanel;
    }

    public void mouseClickedOnSquare(GameBoard board, Action action) {
        int actualSquareIndex = actionToSquareIndex(action);
        drawOnActualSquare(board, actualSquareIndex);
        if ( repaint ) {
            board.getSquares().get(actualSquareIndex).repaint();
            if ( this.delayPerMove > 0 ) {
                try {
                    sleep(this.delayPerMove); //FIXME NO DEBERIA ESTAR CUANDO PIENSA, SOLO AL JUGAR
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
//    public boolean somePlayerWins(GameBoard board) {
//        ArrayList winList = new ArrayList();
//        ArrayList player2List = board.getPlayer2IndexList();
//        ArrayList player1List = board.getPlayer1IndexList();
//        for ( int[] winIndexe : board.getWinIndexes() ) {
//            winList.add(winIndexe[0]);
//            winList.add(winIndexe[1]);
//            winList.add(winIndexe[2]);
//            if ( player2List.containsAll(winList) ) {//TODO Refactorizar para usar el player y el token del player
//                endGame(board);
//                return true;
//            } else if ( player1List.containsAll(winList) ) {
//                endGame(board);
//                return true;
//            }
//            winList.clear();
//        }
//        if ( player2List.size() + player1List.size() == 9 ) {
//            endGame(board);
//            return true;
//        }
//        return false;
//    }
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
    private void drawOnActualSquare(GameBoard board, int actualSquareIndex) {
        Square actualSquare = (Square) board.getSquares().get(actualSquareIndex);
        actualSquare.setPaintType(board.getCurrentPlayer().getToken());
        if ( board.getCurrentPlayer().getToken() == Token.O ) {
            board.getPlayer2IndexList().add(0, actualSquareIndex);
        } else {
            board.getPlayer1IndexList().add(0, actualSquareIndex);
        }
    }

    public void endGame(GameBoard board) {
//        if ( !(board.getTurn() <= 9 && board.getTurn() >= 5) ) {
//            System.out.println("mal");
//        }
        assert (board.getTurn() <= 9 && board.getTurn() >= 5);
        Player winner = board.getCurrentPlayer();
        if ( winner == null ) {
            if ( repaint ) {
                JOptionPane.showMessageDialog(this, " Empate");
            }
        } else {
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
    }

    //TODO: separar grafica de logica
    private void mouseClickedOnSquare(GameBoard board, MouseEvent e) {
        Square actualSquare = (Square) e.getSource();
        if ( !actualSquare.isClicked() ) {
            drawOnActualSquare(board, board.getSquares().indexOf(actualSquare));
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
            actualSquare.setClicked();
            board.nextTurn();

        }
    }

//    private boolean win(GameBoard board, int turn) {
//        ArrayList indexList;
//        if ( turn % 2 > 0 ) {
//            indexList = board.getPlayer1IndexList();
//        } else {
//            indexList = board.getPlayer2IndexList();
//        }
//        ArrayList winList = new ArrayList();
//        try {
//            for ( int[] winIndexe : board.getWinIndexes() ) {
//                winList.add(winIndexe[0]);
//                winList.add(winIndexe[1]);
//                winList.add(winIndexe[2]);
//                if ( indexList.containsAll(winList) ) {
//                    return true;
//                }
//            }
//        } catch ( Exception e ) {
//        }
//        return false;
//    }
    void mouseClickedOnSquare(Action action) {
        mouseClickedOnSquare(this.board, action);
    }

    void reset() {
        this.getBoard().reset();
    }

}
