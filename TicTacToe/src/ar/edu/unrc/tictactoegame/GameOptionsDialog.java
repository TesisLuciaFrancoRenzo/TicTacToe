/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ar.edu.unrc.tictactoegame;

import java.awt.Container;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

/**
 *
 * @author Gyarmati János
 */
public class GameOptionsDialog extends JDialog {

    private JButton btCancel;
    private JButton btOk;
    private String p1name;
    private String p2name;
    private JTextField tfP1;
    private JTextField tfP2;

    public GameOptionsDialog(JFrame owner) {
        super(owner, "Options", true);
        p1name = "Player 1";
        p2name = "Player 2";
        Container cp = getContentPane();
        cp.setLayout(new GridLayout(3, 1));
        setSize(getParent().getWidth() / 2, getParent().getHeight() / 2);
        setResizable(false);
        setLocation(getParent().getX() + getWidth() / 2, getParent().getY() + getHeight() / 2);

        JPanel pn1 = new JPanel();
        pn1.add(new JLabel("Player 1 name: "));
        pn1.add(tfP1 = new JTextField(10));
        tfP1.setName("tfP1");
        cp.add(pn1);

        JPanel pn2 = new JPanel();
        pn2.add(new JLabel("Player 2 name: "));
        pn2.add(tfP2 = new JTextField(10));
        tfP2.setName("tfP2");
        cp.add(pn2);

        JPanel pn3 = new JPanel();
        pn3.add(btOk = new JButton("Ok"));
        pn3.add(btCancel = new JButton("Cancel"));
        btOk.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if ( isValid(tfP1.getText()) && isValid(tfP2.getText()) ) {
                    setP1name(tfP1.getText());
                    setP2name(tfP2.getText());
                }
                dispose();
            }
        });

        btCancel.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
            }
        });
        cp.add(pn3);
    }

    public String getP1name() {
        return p1name;
    }

    public void setP1name(String p1name) {
        this.p1name = p1name;
    }

    public String getP2name() {
        return p2name;
    }

    public void setP2name(String p2name) {
        this.p2name = p2name;
    }

    private boolean isValid(String name) {
        if ( name.trim().length() > 0 ) {
            return true;
        }
        return false;
    }
}
