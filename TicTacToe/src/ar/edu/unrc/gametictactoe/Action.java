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
