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

import java.io.Serializable;
import java.util.ArrayList;

/**
 *
 * @author lucia bressan, franco pellegrini, renzo bianchini
 * @param <T>
 */
public class Node<T> implements Serializable {

    private final ArrayList<Node<T>> branchChilds;
    private final ArrayList<Node<T>> leaveChilds;

    private T value;

    /**
     *
     */
    public Node() {
        value = null;
        leaveChilds = new ArrayList<>();
        branchChilds = new ArrayList<>();
    }

    /**
     *
     * @param value
     */
    public Node(T value) {
        this.value = value;
        leaveChilds = new ArrayList<>();
        branchChilds = new ArrayList<>();
    }

    /**
     *
     * @param node
     */
    public void addBranchChild(Node<T> node) {
        branchChilds.add(node);
    }

    /**
     *
     * @param node
     */
    public void addLeaveChild(Node<T> node) {
        leaveChilds.add(node);
    }

    /**
     * @return the branch childs
     */
    public ArrayList<Node<T>> getBranchChilds() {
        return branchChilds;
    }

    /**
     * @return the leave childs
     */
    public ArrayList<Node<T>> getLeaveChilds() {
        return leaveChilds;
    }

    /**
     * @return the value
     */
    public T getValue() {
        return value;
    }

    /**
     * @param value the value to set
     */
    public void setValue(T value) {
        this.value = value;
    }

    /**
     *
     * @return
     */
    public boolean haveLeaves() {
        return !leaveChilds.isEmpty();
    }

    /**
     *
     * @return
     */
    public boolean isLeave() {
        return leaveChilds.isEmpty() && branchChilds.isEmpty();
    }
}
