/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ar.edu.unrc.gametictactoe;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import javax.swing.JComponent;

/**
 *
 * @author Gyarmati János
 */
public class Square extends JComponent {

    private boolean clicked;
    private int height;
    private int paintType;           //ez tárolja hogy x-et rajzol(0), vagy o-t (1)
    private int width;

    public Square(int width, int height) {
        this.width = width;
        this.height = height;
        this.clicked = false;
        this.paintType = 0;
    }

    public Square(Square oldSquare) {
        this.width = oldSquare.width;
        this.height = oldSquare.height;
        this.clicked = oldSquare.clicked;
        this.paintType = oldSquare.paintType;
    }

    public void reset() {
        this.clicked = false;
        this.paintType = 0;
    }

    public int getPaintType() {
        return paintType;
    }

    public void setPaintType(int paintType) {
        this.paintType = paintType;
    }

    public boolean isClicked() {
        return clicked;
    }

    public void paintO(Graphics gr) {
        Graphics2D g = (Graphics2D) gr;
        g.setColor(Color.red);
        g.setStroke(new BasicStroke(6));
        g.drawOval(3, 3, getWidth() - 3, getHeight() - 3);
        g.dispose();
    }

    public void paintX(Graphics gr) {
        Graphics2D g = (Graphics2D) gr;
        g.setColor(Color.blue);
        g.setStroke(new BasicStroke(6));
        g.drawLine(0, 0, getWidth(), getHeight());
        g.drawLine(0, getHeight(), getWidth(), 0);
        g.dispose();
    }

    public void setClicked() {
        clicked = true;
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.drawRect(0, 0, width, height);
        switch ( getPaintType() ) {
            case 0:
                break;
            case 1:
                paintX(g);
                break;
            case -1:
                paintO(g);
                break;
        }
    }
}
