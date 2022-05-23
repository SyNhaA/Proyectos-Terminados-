package co.redSocial.estructuras;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;

public class Grafo<T> implements Serializable {

    private static final long serialVersionUID = 1L;
    private HashMap<T, NodoGrafo<T>> graph;
    private int size;
    private NodoGrafo<T> inicial;

    public Grafo() {
        graph = new HashMap<T, NodoGrafo<T>>();
        size = 0;
        inicial = null;
    }

    //NUEVO
    public ListaSimple<T> getLinks(T value) {
        ListaSimple<T> listLinks = new ListaSimple<T>();
        NodoGrafo<T> nodo = Buscar(value);

        for (int i = 0; i < nodo.getLinksSize(); i++) {
            if (nodo.getLinks().get(i) != null) {
                listLinks.addEnd(nodo.getLinks().get(i).getTargetNode().getValue());
            }
        }
        return listLinks;
    }

    public void agregar(T elemento) {//
        if (!graph.containsKey(elemento)) {
            NodoGrafo<T> nodo = new NodoGrafo<T>(elemento);
            graph.put(elemento, nodo);
            size++;
        } else {
//			throw new ErrorExisteNodo("Ya existe nodo con ese nombre");
        }
    }

    public void eliminar(T elemento) {//
        if (graph.containsKey(elemento)) {
            graph.remove(elemento);
            size--;
        }
    }

    public NodoGrafo<T> Buscar(T elemento) {//
        NodoGrafo<T> nodo = null;
        if (graph.containsKey(elemento)) {
            nodo = graph.get(elemento);
        }
        return nodo;
    }

    public void conectar(T elementoOrigen, T elementoDestino, double weight) {
        NodoGrafo<T> nodoOrigen = null;
        NodoGrafo<T> nodoDestino = null;
        if (graph.containsKey(elementoOrigen)) {
            nodoOrigen = graph.get(elementoOrigen);
            if (graph.containsKey(elementoDestino)) {          //elementoOrigen
                nodoDestino = graph.get(elementoDestino);
            } else {
//				throw new ErrorNodoNoExiste("Nodo destino no existe");
            }

            if (!nodoOrigen.isConnected(0)) {
                nodoOrigen.connect(nodoDestino, nodoOrigen.getLinksSize() - 1, weight);
                if (!nodoDestino.isConnected(0)) {
                    nodoDestino.connect(nodoOrigen, nodoDestino.getLinksSize() - 1, weight);
                } else {
                    nodoDestino.connect(nodoOrigen, nodoDestino.getLinksSize(), weight);
                }

            } else {
                nodoOrigen.connect(nodoDestino, nodoOrigen.getLinksSize(), weight);
                if (!nodoDestino.isConnected(0)) {
                    nodoDestino.connect(nodoOrigen, nodoDestino.getLinksSize() - 1, weight);
                } else {
                    nodoDestino.connect(nodoOrigen, nodoDestino.getLinksSize(), weight);
                }
            }

        } else {
//			throw new ErrorNodoNoExiste("Nodo origen no existe");
        }
    }

    public void desconectar(T elementoOrigen, T elementodestino) {
//		System.out.println("Aqui");
        NodoGrafo<T> nodoOrigen;
        NodoGrafo<T> nodoDestino = null;
        // int indice;
        if (graph.containsKey(elementoOrigen)) {
            nodoOrigen = graph.get(elementoOrigen);
            if (graph.containsKey(elementodestino)) {
                nodoDestino = graph.get(elementodestino);
            }

            for (int i = 0; i < nodoOrigen.getLinksSize(); i++) {
                if (nodoOrigen.getLinks().get(i) != null) {          // != null
                    if (nodoOrigen.getLinks().get(i).getTargetNode().equals(nodoDestino)) {
//						System.out.println(nodoDestino.getValue());
                        nodoOrigen.disconnect(i);
                    }
                }
            }
            // nodoOrigen.desconectar(indice);
        } else {
//			throw new ErrorNodoNoExiste("Nodo origen no existe");
        }
    }

    public void desconectar2(T elementoOrigen, T elementodestino) {
        System.out.println("Aqui");
        NodoGrafo<T> nodoOrigen;
        NodoGrafo<T> nodoDestino = null;
        // int indice;
        if (graph.containsKey(elementoOrigen)) {
            nodoOrigen = graph.get(elementoOrigen);
//			System.out.println(nodoOrigen.getValue());
            if (graph.containsKey(elementodestino)) {
                nodoDestino = graph.get(elementodestino);
            }
            System.out.println("NodoO enla: " + nodoOrigen.getLinksSize());
            for (int i = 0; i < nodoOrigen.getLinksSize(); i++) {
                if (nodoOrigen.getLinks().get(i) != null) {
                    System.out.println("i: " + 1);
                    System.out.println("Conexion: " + nodoOrigen.getLinks().get(i).getTargetNode().getValue());
                    if (nodoOrigen.getLinks().get(i).getTargetNode().equals(nodoDestino)) {
                        System.out.println(nodoDestino.getValue());
                        nodoOrigen.disconnect(i);
                    }
                }
            }

            for (int i = 0; i < nodoDestino.getLinksSize(); i++) {
                if (nodoDestino.getLinks().get(i).getTargetNode().equals(nodoOrigen)) {
                    System.out.println(nodoDestino.getValue());
                    nodoDestino.disconnect(i);
                }
            }

            // nodoOrigen.desconectar(indice);
        } else {
//			throw new ErrorNodoNoExiste("Nodo origen no existe");
        }
    }

    public boolean estaConectado(T elemento, int indice) {
        boolean respuesta = false;
        NodoGrafo<T> nodoOrigen;
        if (graph.containsKey(elemento)) {
            nodoOrigen = graph.get(elemento);
            respuesta = nodoOrigen.isConnected(indice);
        } else {
//			throw new ErrorNodoNoExiste("Nodo origen no existe");
        }
        return respuesta;
    }

    public NodoGrafo<T> seguirEnlace(T elemento, int indice) {
        NodoGrafo<T> nodoOrigen = null;
        NodoGrafo<T> nodoEnlace = null;
        if (graph.containsKey(elemento)) {
            nodoOrigen = graph.get(elemento);
            nodoEnlace = nodoOrigen.seguirEnlace(indice);
        } else {
//			throw new ErrorNodoNoExiste("Nodo origen no existe");
        }
        return nodoEnlace;
    }

    public void setInicial(T elemento) {
        NodoGrafo<T> nodo = null;
        if (graph.containsKey(elemento)) {
            nodo = graph.get(elemento);
            inicial = nodo;
        } else {
//			throw new ErrorNodoNoExiste("Nodo origen no existe");
        }
    }

    public void setDato(T elemento) {
        NodoGrafo<T> nodo = null;
        if (graph.containsKey(elemento)) {
            nodo = graph.get(elemento);
            nodo.setValue(elemento);
        } else {
//			throw new ErrorNodoNoExiste("Nodo origen no existe");
        }
    }

    public T getDato(T elemento) {
        T dato = null;
        NodoGrafo<T> nodo;
        if (graph.containsKey(elemento)) {
            nodo = graph.get(elemento);
            dato = nodo.getValue();
        } else {
//			throw new ErrorNodoNoExiste("Nodo origen no existe");
        }
        return dato;
    }

    public int getSize() {
        return size;
    }

    public int getSizeNodo(T elemento) {
        int dato = -1;
        NodoGrafo<T> nodo;
        if (graph.containsKey(elemento)) {
            nodo = graph.get(elemento);
            dato = nodo.getLinksSize();
        } else {
//			throw new ErrorNodoNoExiste("Nodo origen no existe");
        }
        return dato;
    }

    @Override
    public String toString() {
        return "Grafo: [ \n\tsize=" + size + "\n\t inicial=" + inicial + "\n\t grafo=\n\t\t" + graph + "\n]";
    }

    public void recorrerGrafo(ListaSimple<T> lista) {
        recorrerGrafo(inicial, lista);
    }

    private void recorrerGrafo(NodoGrafo<T> nodo, ListaSimple<T> lista) {
        for (HashMap.Entry<T, NodoGrafo<T>> entry : graph.entrySet()) {
            lista.addEnd(entry.getKey());
        }
    }

    public void profundidad(ArrayList<T> lista) {
        // resetFlags();
        profundidad(inicial, lista);
    }

    private void profundidad(NodoGrafo<T> nodo, ArrayList<T> lista) {
        // System.out.println("Profundidad");
        int i;
        T elemento;
        if (nodo != null && !nodo.isFlag()) {
            nodo.setFlag(true);
            elemento = nodo.getValue();
            lista.add(elemento);
            // System.out.println("Agregado");
            System.out.println(nodo.getValue().toString());
            for (i = 0; i < nodo.getLinksSize(); i++) {
                if (nodo.isConnected(i)) {
                    System.out.println("Enlaces de: " + nodo.getValue().toString());
                    profundidad(nodo.seguirEnlace(i), lista);
                }
            }
        }
        return;
    }
}