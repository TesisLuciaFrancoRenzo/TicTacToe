/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ar.edu.unrc.gametictactoe.euristic.ai;

import java.util.ArrayList;

/**
 *
 * @author franco
 * @param <T>
 */
public class Node<T> {

    private final ArrayList<Node<T>> childs;

    private T value;

    /**
     *
     */
    public Node() {
        value = null;
        childs = new ArrayList<>();
    }

    /**
     *
     * @param value
     */
    public Node(T value) {
        this.value = value;
        childs = new ArrayList<>();
    }

    /**
     *
     * @param node
     */
    public void addChild(Node<T> node) {
        childs.add(node);
    }

    /**
     * @return the childs
     */
    public ArrayList<Node<T>> getChilds() {
        return childs;
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
    public boolean isLeave() {
        return childs.isEmpty();
    }
}
