/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ar.edu.unrc.gametictactoe;

import ar.edu.unrc.tdlearning.perceptron.interfaces.IAction;

/**
 *
 * @author lucia bressan, franco pellegrini, renzo bianchini
 */
public enum Action implements IAction {

    /**
     * Primer Cuadrado Arriba a la izquierda
     */
    S0,
    /**
     * Segundo Cuadrado Arriba al medio
     */
    S1,
    /**
     * Tercer Cuadrado Arriba a la derecha
     */
    S2,
    /**
     * Cuarto Cuadrado Medio a la izquierda
     */
    S3,
    /**
     * Quinto Cuadrado Medio al medio
     */
    S4,
    /**
     * Sexto Cuadrado Medio a la derecha
     */
    S5,
    /**
     * Septimo Cuadrado Abajo a la izquierda
     */
    S6,
    /**
     * Octavo Cuadrado Abajo al medio
     */
    S7,
    /**
     * Noveno Cuadrado Abajo a la derecha
     */
    S8,
    /**
     * Restablecer
     */
    reset
}
