package co.redSocial.estructuras;

import java.io.Serializable;

public class Nodo<T> implements Serializable {

    //------------------------------------------------------------------------
    // ATRIBUTOS
    //--------------------------------------------------------------------------
    private static final long serialVersionUID = 1L;
    private T value;
    private Nodo<T> next;


    public Nodo(T valor) {
        super();
        this.value = valor;
    }

    public Nodo() {

    }

    public T getValue() {
        return value;
    }

    public void setValue(T value) {
        this.value = value;
    }

    public Nodo<T> getNext() {
        return next;
    }

    public void setNext(Nodo<T> next) {
        this.next = next;
    }


}
