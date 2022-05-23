package co.redSocial.estructuras;

import java.io.Serializable;

/**
 * Clase que representa un Nodo del �rbol binario
 *
 * @param <T>
 */
public class NodoArbol<T extends Comparable<T>> implements Serializable {

    private static final long serialVersionUID = 1L;
    private NodoArbol<T> izquierdo, derecho;
    private NodoArbol<T> padre;
    private T elemento;

    /**
     * Constructor de la clase
     *
     * @param elemento Dato del nodo
     */
    public NodoArbol(T elemento) {
        this.elemento = elemento;
    }

    public NodoArbol(T elemento, NodoArbol<T> padre) {
        this.elemento = elemento;
        this.padre = padre;
    }

    /**
     * Agrega un nuevo elemento en el �rbol
     *
     * @param elemento Nuevo dato
     * @return true si lo pudo guardar
     */
    public boolean agregar(T nuevo) {
        if (nuevo.compareTo(elemento) < 0) {
            if (izquierdo == null) {
                izquierdo = new NodoArbol<>(nuevo, this);
                return true;
            } else {
                return izquierdo.agregar(nuevo);
            }
        } else {
            if (nuevo.compareTo(elemento) > 0) {
                if (derecho == null) {
                    derecho = new NodoArbol<>(nuevo, this);
                    return true;
                } else {
                    return derecho.agregar(nuevo);
                }
            }
        }

        return false;
    }

    /**
     * Determina si un Nodo es una Hoja
     *
     * @return true si es Hoja
     */
    public boolean esHoja() {
        return izquierdo == null && derecho == null;
    }

    /**
     * @return
     */
    public boolean tieneUnHijo() {
        return (izquierdo != null && derecho == null) || (derecho != null && izquierdo == null);
    }

    /**
     * @return the izq
     */
    public NodoArbol<T> getIzquierdo() {
        return izquierdo;
    }

    /**
     * @param izq the izq to set
     */
    public void setIzquierdo(NodoArbol<T> izq) {
        this.izquierdo = izq;
    }

    /**
     * @return the der
     */
    public NodoArbol<T> getDerecho() {
        return derecho;
    }

    /**
     * @param der the der to set
     */
    public void setDerecho(NodoArbol<T> der) {
        this.derecho = der;
    }

    /**
     * @return the elemento
     */
    public T getElemento() {
        return elemento;
    }

    /**
     * @param elemento the elemento to set
     */
    public void setElemento(T elemento) {
        this.elemento = elemento;
    }

    /**
     * @return the padre
     */
    public NodoArbol<T> getPadre() {
        return padre;
    }

    /**
     * @param padre the padre to set
     */
    public void setPadre(NodoArbol<T> padre) {
        this.padre = padre;
    }

}
