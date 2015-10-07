/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ar.edu.unrc.gametictactoe.heuristic.ai;

import ar.edu.unrc.gametictactoe.Action;
import ar.edu.unrc.gametictactoe.GameBoard;
import ar.edu.unrc.gametictactoe.GameTicTacToe;
import ar.edu.unrc.gametictactoe.Player;
import ar.edu.unrc.gametictactoe.Players;
import static ar.edu.unrc.gametictactoe.Players.PLAYER1;
import static ar.edu.unrc.gametictactoe.Players.PLAYER2;
import ar.edu.unrc.gametictactoe.Square;
import ar.edu.unrc.gametictactoe.Token;
import ar.edu.unrc.tdlearning.perceptron.learning.TDLambdaLearning;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 *
 * @author franco
 */
public class DesicionTree {

//    public static DesicionTree loadFromFile(File file) {
//        System.out.print("Inicializando IA heurística...");
//        try {
//            // Read from disk using FileInputStream
//            FileInputStream f_in = new FileInputStream(file);
//            // Read object using ObjectInputStream
//            ObjectInputStream obj_in = new ObjectInputStream(f_in);
//            // Read an object
//            DesicionTree obj = (DesicionTree) obj_in.readObject();
//            System.out.println(" cargada exitosamente del archivo: \"" + file + "\".");
//            return obj;
//        } catch ( IOException | ClassNotFoundException exception ) {
//            System.out.print(" no se puede cargar archivo, creando nueva instancia de la IA...");
//            DesicionTree aiTree = new DesicionTree();
//            try {
//                aiTree.saveToFile(file);
//                System.out.println(" Exito.");
//                System.out.println("IA Guardada exitosamente del archivo: \"" + file + "\".");
//            } catch ( Exception ex ) {
//                System.out.println(" Fracaso, no se pudo guardar IA en el archivo: \"" + file + "\".");
//                Logger.getLogger(DesicionTree.class.getName()).log(Level.SEVERE, null, ex);
//            }
//            return aiTree;
//        }
//    }
    /**
     *
     * @param args
     */
    public static void main(String[] args) {
        System.out.print("Creando nueva instancia de la IA...");
        DesicionTree tree = new DesicionTree();
        System.out.println("Listo. Cantidad de tableros válidos computados = " + tree.size() + ".");

        GameBoard board = new GameBoard(new Player("Player 1", 0, Token.X, Players.PLAYER1), new Player("Player 2", 0, Token.O, Players.PLAYER2), createBoardSquares());

        Action action = tree.computeBestAction(board);
        board.pickSquare(action);

        action = tree.computeBestAction(board);
        board.pickSquare(action);

        action = tree.computeBestAction(board);
        board.pickSquare(action);

        action = tree.computeBestAction(board);
        board.pickSquare(action);
    }

    private static ArrayList<Square> createBoardSquares() {
        ArrayList<Square> squares = new ArrayList(9);
        for ( int i = 0; i < 9; i++ ) {
            squares.add(new Square(1, 1));
        }
        return squares;
    }

    private final Node<Movement> firstNode;
    private final Map<Long, Node<Movement>> hashMap;

    /**
     */
    public DesicionTree() {
        hashMap = new HashMap<>();
        GameBoard emptyBoard = new GameBoard(new Player("Player 1", 0, Token.X, Players.PLAYER1), new Player("Player 2", 0, Token.O, Players.PLAYER2), createBoardSquares());
        firstNode = new Node<>();
        firstNode.setValue(new Movement(emptyBoard, null));
        recursiveConstruction(firstNode);
    }

    /**
     *
     * @param game <p>
     * @return
     */
    public Action computeBestAction(GameTicTacToe game) {
        return computeBestAction((GameBoard) game.getBoard());
    }

    /**
     *
     * @param board <p>
     * @return
     */
    public Action computeBestAction(GameBoard board) {
        Node<Movement> curentNode = this.getNode(board);
        assert curentNode != null;
        Action bestAction = null;
        if ( !curentNode.getLeaveChilds().isEmpty() ) {
            int randomMove = 0;
            if ( curentNode.getLeaveChilds().size() > 1 ) {
                randomMove = TDLambdaLearning.randomBetween(0, curentNode.getLeaveChilds().size() - 1);
            }
            bestAction = curentNode.getLeaveChilds().get(randomMove).getValue().getActionToCreateBoard();
        } else {
            if ( curentNode.getValue().getBoard().getCurrentPlayer().getType() == PLAYER1 ) {
                List<Node<Movement>> bestActions = curentNode.getBranchChilds().stream()
                        .filter(n -> n.getValue().getWinPlayer1count() > 0)
                        .sorted((n1, n2) -> Integer.compare(n1.getValue().getWinPlayer1count(), n2.getValue().getWinPlayer1count())).collect(Collectors.toList());
                if ( !bestActions.isEmpty() ) {
                    bestAction = bestActions.get(bestActions.size() - 1).getValue().getActionToCreateBoard();
                } else {
                    bestActions = curentNode.getBranchChilds().stream()
                            .filter(n -> n.getValue().getDrawCount() > 0)
                            .sorted((n1, n2) -> Integer.compare(n1.getValue().getDrawCount(), n2.getValue().getDrawCount())).collect(Collectors.toList());
                    if ( !bestActions.isEmpty() ) {
                        bestAction = bestActions.get(bestActions.size() - 1).getValue().getActionToCreateBoard();
                    } else {
                        bestActions = curentNode.getBranchChilds().stream()
                                .filter(n -> n.getValue().getWinPlayer2count() > 0)
                                .sorted((n1, n2) -> Integer.compare(n1.getValue().getWinPlayer2count(), n2.getValue().getWinPlayer2count())).collect(Collectors.toList());
                        bestAction = bestActions.get(0).getValue().getActionToCreateBoard();
                    }
                }
            } else if ( curentNode.getValue().getBoard().getCurrentPlayer().getType() == PLAYER2 ) {
                List<Node<Movement>> bestActions = curentNode.getBranchChilds().stream()
                        .filter(n -> n.getValue().getWinPlayer2count() > 0)
                        .sorted((n1, n2) -> Integer.compare(n1.getValue().getWinPlayer2count(), n2.getValue().getWinPlayer2count())).collect(Collectors.toList());
                if ( !bestActions.isEmpty() ) {
                    bestAction = bestActions.get(bestActions.size() - 1).getValue().getActionToCreateBoard();
                } else {
                    bestActions = curentNode.getBranchChilds().stream()
                            .filter(n -> n.getValue().getDrawCount() > 0)
                            .sorted((n1, n2) -> Integer.compare(n1.getValue().getDrawCount(), n2.getValue().getDrawCount())).collect(Collectors.toList());
                    if ( !bestActions.isEmpty() ) {
                        bestAction = bestActions.get(bestActions.size() - 1).getValue().getActionToCreateBoard();
                    } else {
                        bestActions = curentNode.getBranchChilds().stream()
                                .filter(n -> n.getValue().getWinPlayer1count() > 0)
                                .sorted((n1, n2) -> Integer.compare(n1.getValue().getWinPlayer1count(), n2.getValue().getWinPlayer1count())).collect(Collectors.toList());
                        bestAction = bestActions.get(0).getValue().getActionToCreateBoard();
                    }
                }
            } else {
                throw new IllegalStateException("No se reconoce el jugador actual");
            }
        }
        assert bestAction != null;
        return bestAction;
    }

    /**
     * @return the firstNode
     */
    public Node<Movement> getFirstNode() {
        return firstNode;
    }

    /**
     *
     * @param board <p>
     * @return
     */
    public Node<Movement> getNode(GameBoard board) {
        return hashMap.get(board.encrypt());
    }

    /**
     *
     * @return
     */
    public long size() {
        return hashMap.size();
    }

    private void recursiveConstruction(Node<Movement> node) {
//        System.out.println(node.getValue().encrypt());
        GameBoard currentBoard = node.getValue().getBoard();
        long encript = currentBoard.encrypt();
//        if ( hashMap.containsKey(encript) ) {
//            GameBoard board = hashMap.get(encript).getValue();
//            GameBoard thisboard = node.getValue();
//            System.out.println("");
//        }
        assert !hashMap.containsKey(encript); //TODO mudar a JUnit
        hashMap.put(encript, node);
        if ( !currentBoard.isTerminalState() ) {
            currentBoard.listAllPossibleActions().stream().forEach((a) -> {
                GameBoard newBoard = (GameBoard) currentBoard.getCopy();
                newBoard.pickSquare((Action) a);
                Node<Movement> newNode = new Node<>();
                newNode.setValue(new Movement(newBoard, (Action) a));
                if ( newBoard.isTerminalState() ) {
                    node.addLeaveChild(newNode);
                } else {
                    node.addBranchChild(newNode);
                }
                recursiveConstruction(newNode);
            });

            //computamos costos del nodo actual
            node.getLeaveChilds().stream().forEach((branch) -> {
                node.getValue().addPlayer1Win(branch.getValue().getWinPlayer1count());
                node.getValue().addPlayer2Win(branch.getValue().getWinPlayer2count());
                node.getValue().addDraw(branch.getValue().getDrawCount());
            });
            node.getBranchChilds().stream().forEach((branch) -> {
                node.getValue().addPlayer1Win(branch.getValue().getWinPlayer1count());
                node.getValue().addPlayer2Win(branch.getValue().getWinPlayer2count());
                node.getValue().addDraw(branch.getValue().getDrawCount());
            });

        } else {
            //computamos costos del nodo hoja actual
            switch ( currentBoard.getWinner() ) {
                case PLAYER1: {
                    node.getValue().addPlayer1Win();
                    break;
                }
                case PLAYER2: {
                    node.getValue().addPlayer2Win();
                    break;
                }
                case DRAW: {
                    node.getValue().addDraw();
                    break;
                }
                default:
                    throw new IllegalStateException("El tablero final no esta en un estado valido");
            }
        }
    }

//    private void saveToFile(File file) throws Exception {
//        file.delete();
//        FileOutputStream fout = new FileOutputStream(file);
//        try ( ObjectOutputStream oos = new ObjectOutputStream(fout) ) {
//            oos.writeObject(this);
//        }
//    }
}
