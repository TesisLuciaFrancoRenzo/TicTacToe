/*
 * Copyright (C) 2016  Lucia Bressan <lucyluz333@gmial.com>,
 *                     Franco Pellegrini <francogpellegrini@gmail.com>,
 *                     Renzo Bianchini <renzobianchini85@gmail.com
 *
 *   This program is free software: you can redistribute it and/or modify
 *   it under the terms of the GNU General Public License as published by
 *   the Free Software Foundation, either version 3 of the License, or
 *   (at your option) any later version.
 *
 *   This program is distributed in the hope that it will be useful,
 *   but WITHOUT ANY WARRANTY; without even the implied warranty of
 *   MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *   GNU General Public License for more details.
 *
 *   You should have received a copy of the GNU General Public License
 *   along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package ar.edu.unrc.gametictactoe.heuristic.ai;

import ar.edu.unrc.gametictactoe.Action;
import ar.edu.unrc.gametictactoe.GameBoard;
import ar.edu.unrc.gametictactoe.Player;
import ar.edu.unrc.gametictactoe.Players;
import static ar.edu.unrc.gametictactoe.Players.PLAYER1;
import static ar.edu.unrc.gametictactoe.Players.PLAYER2;
import ar.edu.unrc.gametictactoe.Square;
import ar.edu.unrc.gametictactoe.Token;
import ar.edu.unrc.tdlearning.perceptron.learning.TDLambdaLearning;
import ar.edu.unrc.utils.StringAndFiles;
import static ar.edu.unrc.utils.StringAndFiles.UTF_8;
import ar.edu.unrc.utils.StringIterator;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;

/**
 *
 * @author lucia bressan, franco pellegrini, renzo bianchini
 */
public class DesicionTree {

    /**
     *
     * @param args
     */
    public static void main(String[] args) {
        DesicionTree tree = new DesicionTree();
        tree.construct(new File("../../Perceptrones ENTRENADOS TicTacToe/heuristics.bin"));

        GameBoard board = new GameBoard(new Player("Player 1", 0, Token.X, Players.PLAYER1), new Player("Player 2", 0, Token.O, Players.PLAYER2), createBoardSquares());

        List<Action> action = tree.solutionsFor(board);
        int randomMove = TDLambdaLearning.randomBetween(0, action.size() - 1);
        board.pickSquare(action.get(randomMove));
        System.out.println("1º movimiento => " + action.get(randomMove));

        action = tree.solutionsFor(board);
        randomMove = TDLambdaLearning.randomBetween(0, action.size() - 1);
        board.pickSquare(action.get(randomMove));
        System.out.println("2º movimiento => " + action.get(randomMove));

        action = tree.solutionsFor(board);
        randomMove = TDLambdaLearning.randomBetween(0, action.size() - 1);
        board.pickSquare(action.get(randomMove));
        System.out.println("3º movimiento => " + action.get(randomMove));

        action = tree.solutionsFor(board);
        randomMove = TDLambdaLearning.randomBetween(0, action.size() - 1);
        board.pickSquare(action.get(randomMove));
        System.out.println("4º movimiento => " + action.get(randomMove));

        System.out.println("Fin de simulacion");
    }

    private static ArrayList<Square> createBoardSquares() {
        ArrayList<Square> squares = new ArrayList(9);
        for ( int i = 0; i < 9; i++ ) {
            squares.add(new Square(1, 1));
        }
        return squares;
    }

    private Node<Movement> firstNode;
    private final Map<Long, Node<Movement>> hashMap;
    private final Map<Long, List<Action>> hashMapIA;
    private boolean iaLoaded;

    /**
     */
    public DesicionTree() {
        hashMap = new HashMap<>();
        hashMapIA = new HashMap<>();
        firstNode = null;
        iaLoaded = false;
    }

    /**
     *
     * @param file
     */
    public void construct(File file) {
        iaLoaded = true;
        System.out.print("Inicializando IA heurística...");
        try {
            //intentamos cargar la IA desde algun archivo
            loadHashMapIA(StringAndFiles.fileToString(file, UTF_8));
            System.out.println(" cargada exitosamente del archivo: \"" + file + "\".");
        } catch ( IOException exception ) {
            System.out.print(" no se puede cargar archivo, creando nueva instancia de la IA...");
            GameBoard emptyBoard = new GameBoard(new Player("Player 1", 0, Token.X, Players.PLAYER1), new Player("Player 2", 0, Token.O, Players.PLAYER2), createBoardSquares());
            firstNode = new Node<>();
            firstNode.setValue(new Movement(emptyBoard, null));
            recursiveConstruction(firstNode);
            hashMap.entrySet().stream()
                    .filter(boardNode -> !boardNode.getValue().getValue().getBoard().isTerminalState())
                    .forEach(boardNode -> {
                        hashMapIA.put(boardNode.getKey(), computeBestActions(boardNode.getValue().getValue().getBoard()));
                    });
            try {
                file.delete();
                StringAndFiles.stringToFile(file, hashMapIAToString(), UTF_8);
                System.out.println(" Exito.");
                System.out.println("IA Guardada exitosamente del archivo: \"" + file + "\".");
            } catch ( IOException ex ) {
                System.out.println(" Fracaso, no se pudo guardar IA en el archivo: \"" + file + "\".");
                Logger.getLogger(DesicionTree.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    /**
     * @return the firstNode
     */
    public Node<Movement> getFirstNode() {
        if ( !iaLoaded ) {
            throw new IllegalStateException("se debe ejecutar el metodo construct antes de usar este objeto");
        }
        return firstNode;
    }

    /**
     *
     * @param board <p>
     * @return
     */
    public Node<Movement> getNode(GameBoard board) {
        if ( !iaLoaded ) {
            throw new IllegalStateException("se debe ejecutar el metodo construct antes de usar este objeto");
        }
        return hashMap.get(board.encrypt());
    }

    /**
     *
     * @param board
     *
     * @return
     */
    public List<Action> solutionsFor(GameBoard board) {
        if ( !iaLoaded ) {
            throw new IllegalStateException("se debe ejecutar el metodo construct antes de usar este objeto");
        }
        return hashMapIA.get(board.encrypt());
    }

    /**
     *
     * @param board <p>
     * @return
     */
    private List<Action> computeBestActions(GameBoard board) {
        List<Action> actions = new ArrayList<>(4);
        Node<Movement> curentNode = this.getNode(board);
        assert curentNode != null;
        if ( !curentNode.getLeaveChilds().isEmpty() ) {
            curentNode.getLeaveChilds().stream().forEach((node) -> {
                actions.add(node.getValue().getActionToCreateBoard());
            });
        } else if ( curentNode.getValue().getBoard().getCurrentPlayer().getType() == PLAYER1 ) {
            List<Node<Movement>> bestActions = curentNode.getBranchChilds().stream()
                    .filter(n -> n.getValue().getWinPlayer1count() > 0)
                    .sorted((n1, n2) -> Integer.compare(n1.getValue().getWinPlayer1count(), n2.getValue().getWinPlayer1count()))
                    .collect(Collectors.toList());
            if ( !bestActions.isEmpty() ) { //Si hay una buena jugada para player 1
                int bestNumber = bestActions.get(bestActions.size() - 1).getValue().getWinPlayer1count();
                bestActions.stream()
                        .filter(n -> n.getValue().getWinPlayer1count() == bestNumber)
                        .forEach((node) -> {
                            actions.add(node.getValue().getActionToCreateBoard());
                        });
            } else {
                bestActions = curentNode.getBranchChilds().stream()
                        .filter(n -> n.getValue().getDrawCount() > 0)
                        .sorted((n1, n2) -> Integer.compare(n1.getValue().getDrawCount(), n2.getValue().getDrawCount()))
                        .collect(Collectors.toList());
                if ( !bestActions.isEmpty() ) { //Si hay una buena jugada para empatar
                    int bestNumber = bestActions.get(bestActions.size() - 1).getValue().getDrawCount();
                    bestActions.stream()
                            .filter(n -> n.getValue().getDrawCount() == bestNumber)
                            .forEach((node) -> {
                                actions.add(node.getValue().getActionToCreateBoard());
                            });
                } else { //Si hay una buena jugada para dificultarle el trabajo a player 2
                    bestActions = curentNode.getBranchChilds().stream()
                            .filter(n -> n.getValue().getWinPlayer2count() > 0)
                            .sorted((n1, n2) -> Integer.compare(n1.getValue().getWinPlayer2count(), n2.getValue().getWinPlayer2count()))
                            .collect(Collectors.toList());

                    int worstNumber = bestActions.get(0).getValue().getWinPlayer2count();
                    bestActions.stream()
                            .filter(n -> n.getValue().getWinPlayer2count() == worstNumber)
                            .forEach((node) -> {
                                actions.add(node.getValue().getActionToCreateBoard());
                            });
                }
            }
        } else if ( curentNode.getValue().getBoard().getCurrentPlayer().getType() == PLAYER2 ) {
            List<Node<Movement>> bestActions = curentNode.getBranchChilds().stream()
                    .filter(n -> n.getValue().getWinPlayer2count() > 0)
                    .sorted((n1, n2) -> Integer.compare(n1.getValue().getWinPlayer2count(), n2.getValue().getWinPlayer2count()))
                    .collect(Collectors.toList());
            if ( !bestActions.isEmpty() ) { //Si hay una buena jugada para player 2
                int bestNumber = bestActions.get(bestActions.size() - 1).getValue().getWinPlayer2count();
                bestActions.stream()
                        .filter(n -> n.getValue().getWinPlayer2count() == bestNumber)
                        .forEach((node) -> {
                            actions.add(node.getValue().getActionToCreateBoard());
                        });
            } else {
                bestActions = curentNode.getBranchChilds().stream()
                        .filter(n -> n.getValue().getDrawCount() > 0)
                        .sorted((n1, n2) -> Integer.compare(n1.getValue().getDrawCount(), n2.getValue().getDrawCount()))
                        .collect(Collectors.toList());
                if ( !bestActions.isEmpty() ) { //Si hay una buena jugada para empatar
                    int bestNumber = bestActions.get(bestActions.size() - 1).getValue().getDrawCount();
                    bestActions.stream()
                            .filter(n -> n.getValue().getDrawCount() == bestNumber)
                            .forEach((node) -> {
                                actions.add(node.getValue().getActionToCreateBoard());
                            });
                } else { //Si hay una buena jugada para dificultarle el trabajo a player 1
                    bestActions = curentNode.getBranchChilds().stream()
                            .filter(n -> n.getValue().getWinPlayer1count() > 0)
                            .sorted((n1, n2) -> Integer.compare(n1.getValue().getWinPlayer1count(), n2.getValue().getWinPlayer1count()))
                            .collect(Collectors.toList());
                    if ( bestActions.isEmpty() ) {
                        System.err.println("cac");
                    }
                    int worstNumber = bestActions.get(0).getValue().getWinPlayer1count();
                    bestActions.stream()
                            .filter(n -> n.getValue().getWinPlayer1count() == worstNumber)
                            .forEach((node) -> {
                                actions.add(node.getValue().getActionToCreateBoard());
                            });
                }
            }
        } else {
            throw new IllegalStateException("No se reconoce el jugador actual");
        }
        assert !actions.isEmpty();
        return actions;
    }

    private String hashMapIAToString() {
        StringBuilder output = new StringBuilder();
        hashMapIA.entrySet().stream().forEach((entry) -> {
            output.append(entry.getKey()).append("=");
            entry.getValue().stream().forEach((a) -> {
                output.append(GameBoard.actionToSquareIndex(a)).append(" ");
            });
            output.append("\n");
        });
        return output.toString();
    }

    private void loadHashMapIA(String string) {
        StringIterator iterador = new StringIterator(string, null, "\n");
        String renglon;
        while ( (renglon = iterador.readLine()) != null ) {
            int index = renglon.indexOf('=');
            if ( index < 0 ) {
                throw new IllegalStateException("El archivo tiene un formato invalido");
            }
            List<Action> actions = new ArrayList<>(4);
            String actionsStrings = renglon.substring(index + 1, renglon.length()).trim();
            StringIterator iterador2 = new StringIterator(actionsStrings, null, " ");
            String renglon2;
            while ( (renglon2 = iterador2.readLine()) != null ) {
                actions.add(GameBoard.squareIndexToAction(Integer.parseInt(renglon2.trim())));
            }
            hashMapIA.put(Long.parseLong(renglon.substring(0, index).trim()), actions);
        }
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

}
