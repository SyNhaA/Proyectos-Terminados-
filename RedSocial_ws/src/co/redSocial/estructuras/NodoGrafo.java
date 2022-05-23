package co.redSocial.estructuras;

import java.io.Serializable;
import java.util.ArrayList;

public class NodoGrafo<T> implements Serializable {

    private static final long serialVersionUID = 1L;
    private ArrayList<Link<T>> links;
    private String name;
    private boolean flag;
    private T value;

    public NodoGrafo(T elemento) {
        this.value = elemento;
        this.flag = false;
        links = new ArrayList<Link<T>>();
        links.add(null);
    }

    public void connect(NodoGrafo<T> destino, int indice, double peso) {
        Link<T> newLink = new Link<T>(destino, peso);
        if (indice >= links.size()) {
            int n = indice - links.size();
            for (int i = 0; i < n; i++) {
                links.add(null);
            }
            links.add(newLink);
        } else {
            links.set(indice, newLink);
        }
    }

    public void disconnect(int index) {
        links.set(index, null);
    }

    public boolean isConnected(int index) {
        return links.get(index) != null;
    }

    public boolean isConnected(NodoGrafo<T> node) {
        for (int i = 0; i < links.size(); i++) {
            if (links.get(i).getTargetNode().equals(node)) {
                return true;
            }
        }
        return false;
    }

    public NodoGrafo<T> seguirEnlace(int indice) {
        return links.get(indice).getTargetNode();
    }

    public int getLinksSize() {
        return links.size();
    }

    public T getValue() {
        return value;
    }

    public void setValue(T value) {
        this.value = value;
    }

    public ArrayList<Link<T>> getLinks() {
        return links;
    }

    public void setLinks(ArrayList<Link<T>> links) {
        this.links = links;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean isFlag() {
        return flag;
    }

    public void setFlag(boolean flag) {
        this.flag = flag;
    }

    public NodoGrafo() {
    }

}