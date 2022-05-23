package co.redSocial.estructuras;

import java.io.Serializable;
import java.util.Iterator;

public class ListaSimple<T> implements Iterable<T>, Serializable {

    //------------------------------------------------------------------------
    // ATRIBUTOS
    //--------------------------------------------------------------------------
    private static final long serialVersionUID = 1L;
    private Nodo<T> first;
    private Nodo<T> last;
    private int size;

    public ListaSimple() {
        first = null;
        last = null;
        size = 0;
    }

    //NUEVO
    public boolean contains(T value) {
        boolean flag = false;
        Nodo<T> aux = first;
        while (aux != null) {
            if (aux.getValue() == value) {
                return true;
            }
            aux = aux.getNext();
        }
        return flag;
    }

    //Agregar al inicio de la lista
    public void addFirst(T value) {
        Nodo<T> nuevoNodo = new Nodo<T>(value);
        if (isEmpty()) {
            first = nuevoNodo;
            last = nuevoNodo;
        } else {
            nuevoNodo.setNext(first);
            first = nuevoNodo;
        }
        size++;
    }

    //Agregar al final de la lista
    public void addEnd(T value) {
        Nodo<T> nodo = new Nodo<T>(value);
        if (isEmpty()) {
            first = last = nodo;
        } else {
            last.setNext(nodo);
            last = nodo;
        }
        size++;
    }

    //Obtener Nodo el valor de un Nodo
    public T get(int index) {
        Nodo<T> nodoTemporal = null;
        int contador = 0;

        if (validIndex(index)) {
            nodoTemporal = first;
            while (contador < index) {
                nodoTemporal = nodoTemporal.getNext();
                contador++;
            }
        }
        if (nodoTemporal != null)
            return nodoTemporal.getValue();
        else
            return null;
    }

    //Verificar si indice es valido
    private boolean validIndex(int index) {
        if (index >= 0 && index < size) {
            return true;
        }
        throw new RuntimeException("�ndice no v�lido");
    }

    //Verificar si la lista esta vacia
    public boolean isEmpty() {
        return (first == null) ? true : false;
    }


    /**
     * Imprime en consola la lista enlazada
     */
    public void print() {
        Nodo<T> aux = first;
        while (aux != null) {
            System.out.print(aux.getValue() + "\t");
            aux = aux.getNext();
        }
        System.out.println();
    }

    //Eliminar dado el valor de un nodo
    public T delete(T dato) {
        Nodo<T> nodo = first;
        Nodo<T> previo = null;
        Nodo<T> siguiente = null;
        boolean encontrado = false;

        //buscar el nodo previo
        while (nodo != null) {
            if (nodo.getValue() == dato) {
                encontrado = true;
                break;
            }
            previo = nodo;
            nodo = nodo.getNext();
        }

        if (encontrado) {
            siguiente = nodo.getNext();
            if (previo == null) {
                first = siguiente;
            } else {
                previo.setNext(siguiente);
            }

            if (siguiente == null) {
//				last = previo;
            } else {
                nodo.setNext(null);
            }

            nodo = null;
            size--;
            return dato;
        }
        throw new RuntimeException("El elemento no existe");
    }


    //Elimina el primer nodo de la lista
    public T deleteFirst() {
        if (!isEmpty()) {
            Nodo<T> n = first;
            T valor = n.getValue();
            first = n.getNext();

            if (first == null) {
//				last = null;
            }

            size--;
            return valor;
        }
        throw new RuntimeException("Lista vac�a");
    }


    private Nodo<T> getNode(int indice) {
        if (indice >= 0 && indice < size) {
            Nodo<T> nodo = first;
            for (int i = 0; i < indice; i++) {
                nodo = nodo.getNext();
            }
            return nodo;
        }
        return null;
    }


    /**
     * Cambia el valor de un nodo dada su posici�n en la lista
     *
     * @param indice posici�n donde se va a cambiar el dato
     * @param nuevo  nuevo valor por el que se actualizar� el nodo
     */
    public void modifyNode(int indice, T nuevo) {
        if (validIndex(indice)) {
            Nodo<T> nodo = getNode(indice);
            nodo.setValue(nuevo);
        }
    }


    /**
     * Retorna la primera posici�n donde est� guardado el dato
     *
     * @param value valor a buscar dentro de la lista
     * @return primera posici�n del dato
     */
    public int IndexOf(T value) {
        int i = 0;
        for (Nodo<T> aux = first; aux != null; aux = aux.getNext()) {
            if (aux.getValue().equals(value)) {
                return i;
            }
            i++;
        }
        return -1;
    }

    public Nodo<T> getfirst() {
        return first;
    }

    public void setfirst(Nodo<T> first) {
        this.first = first;
    }

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }

    public int contarValorRepetido(T valor) {
        int cont = 0;
        Nodo<T> aux = first;
        for (int i = 0; i < this.getSize(); i++) {
            T valorNodo = aux.getValue();
            if (valor == valorNodo) {
                cont += 1;
            }
            aux = aux.getNext();
        }
        return cont;
    }

    public Nodo<T> obtenerUltimoNodo() {
        Nodo<T> aux = first;
        if (!isEmpty()) {
            while (aux.getNext() != null) {
                aux = aux.getNext();
            }
        }
        return aux;
    }

    @Override
    public String toString() {

        String salida = "Estado de la lista: \n";
        Nodo<T> temporal = first;
        for (int i = 0; i < this.getSize(); i++) {
            salida += temporal.getValue() + " ";
            temporal = temporal.getNext();
        }
        if (size > 0) {
            salida += "\n";
            salida += "Primero: " + first.getValue() + "\n";

            salida += "Longitud: " + size + "\n";
        } else {
            salida = "Lista NULL";
        }
        return salida;
    }

    @Override
    public Iterator<T> iterator() {
        return new IteradorListaSimple(first);
    }

    public class IteradorListaSimple implements Iterator<T> {
        private Nodo<T> nodo;
        private int posicion;

        public IteradorListaSimple(Nodo<T> nodo) {
            super();
            this.nodo = nodo;
            this.posicion = 0;
        }

        @Override
        public boolean hasNext() {
            return nodo != null;
        }

        @Override
        public T next() {
            T valor = nodo.getValue();
            nodo = nodo.getNext();
            posicion++;
            return valor;
        }
    }

}
