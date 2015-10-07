/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ar.edu.unrc.gametictactoe;

/**
 *
 * @author Renzo Bianchini
 */
public enum Token {

    /**
     *
     */
    X(1),
    /**
     *
     */
    O(-1),
    /**
     *
     */
    EMPTY(0);

    private final int representation;

    Token(int representation) {
        this.representation = representation;
    }

    /**
     *
     * @return
     */
    public int getRepresentation() {
        return this.representation;
    }

}
