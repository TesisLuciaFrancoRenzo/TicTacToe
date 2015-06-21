/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package tictactoegame;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import javax.swing.BorderFactory;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.border.BevelBorder;

/**
 *
 * @author Gyarmati János
 */
class PlayPanel extends JPanel {

    private ArrayList OIndexList = new ArrayList(5);
    private ArrayList XIndexList = new ArrayList(5);
    private int actualSquareIndex;
    private int clicks = 0;
    private InfoPanel infoPanel;
    private MouseAdapter mouseAdapter;
    private Dimension panelSize;
    private Player player1;
    private Player player2;
    private final boolean repaint;
    private Square square;
    private final ArrayList<Square> squares;
    int[][] winIndexes = {
        {0, 1, 2},
        {3, 4, 5},
        {6, 7, 8},
        {0, 3, 6},
        {1, 4, 7},
        {2, 5, 8},
        {0, 4, 8},
        {2, 4, 6}
    };

    public PlayPanel(Dimension size, boolean repaint) {
        this.squares = new ArrayList(9);
        setBorder(BorderFactory.createBevelBorder(BevelBorder.RAISED, Color.lightGray, Color.black));
        setLayout(new GridLayout(3, 3));
        this.panelSize = size;
        this.repaint = repaint;
    }

    public int getActualSquareIndex() {
        return actualSquareIndex;
    }

    public void setActualSquareIndex(int actSquareIndex) {
        this.actualSquareIndex = actSquareIndex;
    }

    public InfoPanel getInfoPanel() {
        return infoPanel;
    }

    public void setInfoPanel(InfoPanel infoPanel) {
        this.infoPanel = infoPanel;
    }

    public ArrayList getOIndexList() {
        return OIndexList;
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

    public int[][] getWinIndexes() {
        return winIndexes;
    }

    public ArrayList getXIndexList() {
        return XIndexList;
    }

    public void uploadPanelWithSquares() {
        setSize(panelSize.width, panelSize.height);
        for ( int i = 0; i < 9; i++ ) {
            add(square = new Square(panelSize.width, panelSize.height));
            squares.add(square);
            square.addMouseListener(mouseAdapter = new MouseAdapter() {
                @Override
                public void mouseClicked(MouseEvent e) {
                    super.mouseClicked(e);
                    mouseClickedOnSquare(e);
                }
            });
        }
    }

    /*@Override
    protected void paintComponent(Graphics g) {
    super.paintComponent(g);
    }*/
    private void drawOnActualSquare(int actualSquareIndex) {
        Square actualSquare = (Square) squares.get(actualSquareIndex);
        if ( clicks % 2 > 0 ) {
            actualSquare.setPaintType(1);
            XIndexList.add(0, actualSquareIndex);
        } else {
            actualSquare.setPaintType(2);
            OIndexList.add(0, actualSquareIndex);
        }
    }

    private void endGame(int winnerCode) {
        for ( Square s : squares ) {
            s.setClicked();
        }
        String winner = "";
        if ( winnerCode == 0 ) {
            winner = player1.getName();
            player1.setScore(player1.getScore() + 1);
            infoPanel.setP1score(player1.getScore());
        }
        if ( winnerCode == 1 ) {
            winner = player2.getName();
            player2.setScore(player2.getScore() + 1);
            infoPanel.setP2score(player2.getScore());

        }
        if ( winnerCode == 2 ) {
            winner = "Senki sem";
        }
        JOptionPane.showMessageDialog(this, winner + " nyert!");
    }

    private void mouseClickedOnSquare(MouseEvent e) {
        Square actualSquare = (Square) e.getSource();
        if ( !actualSquare.isClicked() ) {
            setActualSquareIndex(squares.indexOf(actualSquare));
            drawOnActualSquare(getActualSquareIndex());
            if ( repaint ) {
                actualSquare.repaint();
            }
            if ( !win() ) {
                actualSquare.setClicked();
                clicks++;
            }
        }
    }

    private boolean win() {
        ArrayList winList = new ArrayList();
        ArrayList oList = getOIndexList();
        ArrayList xList = getXIndexList();
        try {
            for ( int i = 0; i < getWinIndexes().length; i++ ) {
                winList.add(winIndexes[i][0]);
                winList.add(winIndexes[i][1]);
                winList.add(winIndexes[i][2]);
                if ( oList.containsAll(winList) ) {
                    endGame(0);
                    return true;
                } else if ( xList.containsAll(winList) ) {
                    endGame(1);
                    return true;
                } else if ( oList.size() == 5 && xList.size() == 4 ) {
                    endGame(2);
                    return true;
                }
                winList.clear();
            }
        } catch ( Exception e ) {
        }
        return false;
    }
}
