/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ar.edu.unrc.gametictactoe.euristic.ai;

import ar.edu.unrc.gametictactoe.Action;
import ar.edu.unrc.gametictactoe.GameBoard;
import ar.edu.unrc.gametictactoe.Player;
import ar.edu.unrc.gametictactoe.Players;
import ar.edu.unrc.gametictactoe.Square;
import ar.edu.unrc.gametictactoe.Token;
import ar.edu.unrc.tdlearning.perceptron.interfaces.IAction;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 *
 * @author franco
 */
public class DesicionTree {

    /**
     *
     * @param args
     */
    public static void main(String[] args) {
        Player player1 = new Player("Player 1", 0, Token.X, Players.PLAYER1);
        Player player2 = new Player("Player 2", 0, Token.O, Players.PLAYER2);
        DesicionTree tree = new DesicionTree(player1, player2);
        System.out.println(tree.size());
    }

    private final Node<GameBoard> firstNode;
    private final Map<Long, Node<GameBoard>> hashMap;

    /**
     *
     * @param player1
     * @param player2
     */
    public DesicionTree(Player player1, Player player2) {
        hashMap = new HashMap<>();
        GameBoard emptyBoard = new GameBoard(player1, player2, createBoardSquares());
        firstNode = new Node<>();
        firstNode.setValue(emptyBoard);
        recursiveConstruction(firstNode);
    }

    public Node getNode(GameBoard board) {
        return hashMap.get(board.encrypt());
    }

    /**
     * @return the firstNode
     */
    public Node<GameBoard> getFirstNode() {
        return firstNode;
    }

    public long size() {
        return hashMap.size();
    }

    private ArrayList<Square> createBoardSquares() {
        ArrayList<Square> squares = new ArrayList(9);
        for ( int i = 0; i < 9; i++ ) {
            squares.add(new Square(1, 1));
        }
        return squares;
    }

    private void recursiveConstruction(Node<GameBoard> node) {
        System.out.println(node.getValue().encrypt());
        ArrayList<IAction> possibleActions;
        long encript = node.getValue().encrypt();
//        if ( hashMap.containsKey(encript) ) {
//            GameBoard board = hashMap.get(encript).getValue();
//            GameBoard thisboard = node.getValue();
//            System.out.println("");
//        }
        assert !hashMap.containsKey(encript);
        hashMap.put(node.getValue().encrypt(), node);
        if ( !node.getValue().isTerminalState() ) {
            possibleActions = node.getValue().listAllPossibleActions();
            possibleActions.stream().forEach((a) -> {
                GameBoard newBoard = (GameBoard) node.getValue().getCopy();
                newBoard.pickSquare((Action) a);
                Node<GameBoard> newNode = new Node<>();
                newNode.setValue(newBoard);
                node.addChild(newNode);
                recursiveConstruction(newNode);
            });
        }
    }

}
