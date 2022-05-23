package co.redSocial.estructuras;

import java.io.Serializable;

public class Link<T> implements Serializable {

    private static final long serialVersionUID = 1L;

    private NodoGrafo<T> targetNode;
    private double weight;

    public Link(NodoGrafo<T> targetNode, double weight) {
        super();
        this.targetNode = targetNode;
        this.weight = weight;
    }

    public NodoGrafo<T> getTargetNode() {
        return targetNode;
    }

    public void setTargetNode(NodoGrafo<T> targetNode) {
        this.targetNode = targetNode;
    }

    public double getWeight() {
        return weight;
    }

    public void setWeight(double weight) {
        this.weight = weight;
    }

    public Link() {
    }
}