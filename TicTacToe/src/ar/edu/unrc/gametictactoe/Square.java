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
package ar.edu.unrc.gametictactoe;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.io.Serializable;
import javax.swing.JComponent;

/**
 *
 * @author Gyarmati János, lucia bressan, franco pellegrini, renzo bianchini
 */
public class Square extends JComponent implements Serializable {

    private boolean clicked;
    private final int height;
    private Token paintType;
    private final int width;

    /**
     *
     * @param width
     * @param height
     */
    public Square(int width, int height) {
        this.width = width;
        this.height = height;
        this.clicked = false;
        this.paintType = Token.EMPTY;
    }

    /**
     *
     * @param oldSquare
     */
    public Square(Square oldSquare) {
        this.width = oldSquare.width;
        this.height = oldSquare.height;
        this.clicked = oldSquare.clicked;
        this.paintType = oldSquare.paintType;
    }

    /**
     *
     * @return
     */
    public Token getPaintType() {
        return paintType;
    }

    /**
     *
     * @param paintType
     */
    public void setPaintType(Token paintType) {
        this.paintType = paintType;
    }

    /**
     *
     * @return
     */
    public boolean isClicked() {
        return clicked;
    }

    /**
     *
     * @param gr
     */
    public void paintO(Graphics gr) {
        Graphics2D g = (Graphics2D) gr;
        g.setColor(Color.red);
        g.setStroke(new BasicStroke(6));
        g.drawOval(3, 3, getWidth() - 3, getHeight() - 3);
        g.dispose();
    }

    /**
     *
     * @param gr
     */
    public void paintX(Graphics gr) {
        Graphics2D g = (Graphics2D) gr;
        g.setColor(Color.blue);
        g.setStroke(new BasicStroke(6));
        g.drawLine(0, 0, getWidth(), getHeight());
        g.drawLine(0, getHeight(), getWidth(), 0);
        g.dispose();
    }

    /**
     *
     */
    public void reset() {
        this.clicked = false;
        this.paintType = Token.EMPTY;
    }

    /**
     *
     */
    public void setClicked() {
        clicked = true;
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.drawRect(0, 0, width, height);
        switch ( getPaintType() ) {
            case EMPTY:
                break;
            case X:
                paintX(g);
                break;
            case O:
                paintO(g);
                break;
        }
    }
}
