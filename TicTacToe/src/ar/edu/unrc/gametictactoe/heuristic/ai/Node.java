/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ar.edu.unrc.gametictactoe.heuristic.ai;

import java.util.ArrayList;

/**
 *
 * @author franco
 * @param <T>
 */
public class Node<T> {

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
