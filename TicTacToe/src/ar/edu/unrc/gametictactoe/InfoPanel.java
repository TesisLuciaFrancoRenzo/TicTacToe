/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ar.edu.unrc.gametictactoe;

import java.awt.Font;
import java.awt.Graphics;
import java.awt.GridLayout;
import javax.swing.JLabel;
import javax.swing.JPanel;

/**
 *
 * @author Gyarmati János
 */
class InfoPanel extends JPanel {
    private final Font font = new Font("Arial", Font.BOLD, 30);

    private JLabel lbP1name;
    private JLabel lbP1score;
    private JLabel lbP2name;
    private JLabel lbP2score;
    private String p1name;
    private int p1score;
    private String p2name;
    private int p2score;

    public InfoPanel(Player player1, Player player2) {
        p1name = player1.getName();
        p2name = player2.getName();
        p1score = player1.getScore();
        p2score = player2.getScore();
        
        setLayout(new GridLayout(2, 1));
        JPanel pn1 = new JPanel(new GridLayout(2, 1));
        pn1.add(lbP1name = new JLabel(p1name + " Score:", JLabel.CENTER));
        lbP1name.setFont(font);
        pn1.add(lbP1score = new JLabel(Integer.toString(p1score), JLabel.CENTER));
        lbP1score.setFont(font);
        add(pn1);
        
        JPanel pn2 = new JPanel(new GridLayout(2, 1));
        pn2.add(lbP2name = new JLabel(p2name + " Score:", JLabel.CENTER));
        lbP2name.setFont(font);
        pn2.add(lbP2score = new JLabel(Integer.toString(p2score), JLabel.CENTER));
        lbP2score.setFont(font);
        add(pn2);
    }

    public String getP1name() {
        return p1name;
    }

    public void setP1name(String p1name) {
        this.p1name = p1name;
        lbP1name.setText(this.p1name + " Score:");
    }

    public int getP1score() {
        return p1score;
    }

    public void setP1score(int p1score) {
        this.p1score = p1score;
        lbP1score.setText(Integer.toString(p1score));
    }

    public String getP2name() {
        return p2name;
    }

    public void setP2name(String p2name) {
        this.p2name = p2name;
        lbP2name.setText(this.p2name + " Score:");
    }

    public int getP2score() {
        return p2score;
    }

    public void setP2score(int p2score) {
        this.p2score = p2score;
        lbP2score.setText(Integer.toString(p2score));
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
    }
}
