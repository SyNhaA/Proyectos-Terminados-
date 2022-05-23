package co.redSocial.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

import co.redSocial.estructuras.ArbolBinario;
import co.redSocial.estructuras.ListaSimple;
import co.redSocial.services.IUsuarioService;

public class Usuario implements IUsuarioService, Serializable {

    private static final long serialVersionUID = 1L;
    private String nombre;
    private ArbolBinario<Publicacion> arbolPublicaciones;
    private ListaSimple<Mensaje> mensajes;

    public Usuario(String nombre) {
        super();
        this.nombre = nombre;
        this.arbolPublicaciones = new ArbolBinario<Publicacion>();
        this.mensajes = new ListaSimple<Mensaje>();
    }

    @Override
    public int obtenerCantidadMensajes() {
        return mensajes.getSize();
    }

    @Override
    public void agregarPublicacion(Publicacion publicacion) {
        arbolPublicaciones.add(publicacion);
    }

    public void eliminarPublicacion(Publicacion publicacion) {
        arbolPublicaciones.eliminar(publicacion);
    }

    @Override
    public ListaSimple<Publicacion> obtenerPublicaciones() {
        ArrayList<Publicacion> listaPreOrden = new ArrayList<>();
        ListaSimple<Publicacion> listaPubicacionesOrdenadas = new ListaSimple<Publicacion>();
        arbolPublicaciones.preorden(listaPreOrden);
        Collections.sort(listaPreOrden, new ComparadorFechas().reversed());
        for (Publicacion p : listaPreOrden) {
            listaPubicacionesOrdenadas.addEnd(p);
        }
        return listaPubicacionesOrdenadas;
    }

    @Override
    public int obtenerCantidadPublicaciones() {
        ListaSimple<Publicacion> listaPubicaciones = obtenerPublicaciones();
        return listaPubicaciones.getSize();
    }

    @Override
    public ListaSimple<Comentario> obtenerComentarios() {
        ListaSimple<Comentario> listaComentarios = new ListaSimple<Comentario>();
        ArrayList<Comentario> listaAux = new ArrayList<>();
        ListaSimple<Publicacion> listaPublicaciones = obtenerPublicaciones();
        for (int i = 0; i < listaPublicaciones.getSize(); i++) {
            ListaSimple<Comentario> comentariosAux = listaPublicaciones.get(i).getListaComentarios();
            for (int j = 0; j < comentariosAux.getSize(); j++) {
                Comentario cAux = comentariosAux.get(j);
                if (!cAux.getUsuario().equals(this)) {
                    listaAux.add(cAux);
                }
            }
        }
        Collections.sort(listaAux, new Comparator<Comentario>() {
            @Override
            public int compare(Comentario comentario1, Comentario comentario2) {
                return (comentario1.getFecha() + " - " + comentario1.getHora())
                        .compareTo(comentario2.getFecha() + " - " + comentario2.getHora());
            }
        });
        for (Comentario comentario : listaAux) {
            listaComentarios.addEnd(comentario);
        }
        return listaComentarios;
    }

    @Override
    public int obtenerCantidadComentarios() {
        int cont = 0;
        ListaSimple<Publicacion> listaPublicaciones = obtenerPublicaciones();
        for (int i = 0; i < listaPublicaciones.getSize(); i++) {
            ListaSimple<Comentario> comentariosAux = listaPublicaciones.get(i).getListaComentarios();
            for (int j = 0; j < comentariosAux.getSize(); j++) {
                cont += 1;
            }
        }
        return cont;
    }

    @Override
    public ListaSimple<MeGusta> obtenerMeGustas() {
        ListaSimple<MeGusta> listaMeGustas = new ListaSimple<MeGusta>();
        ArrayList<MeGusta> listaAux = new ArrayList<>();
        ListaSimple<Publicacion> listaPublicaciones = obtenerPublicaciones();
        for (int i = 0; i < listaPublicaciones.getSize(); i++) {
            ListaSimple<MeGusta> meGustasAux = listaPublicaciones.get(i).getListaMeGusta();
            for (int j = 0; j < meGustasAux.getSize(); j++) {
                MeGusta mAux = meGustasAux.get(j);
                if (!mAux.getUsuario().equals(this)) {
                    listaAux.add(mAux);
                }
            }
        }
        Collections.sort(listaAux, new Comparator<MeGusta>() {
            @Override
            public int compare(MeGusta meGusta1, MeGusta meGusta2) {
                return (meGusta1.getFecha() + " - " + meGusta1.getHora())
                        .compareTo(meGusta2.getFecha() + " - " + meGusta2.getHora());
            }
        });
        for (MeGusta meGusta : listaAux) {
            listaMeGustas.addEnd(meGusta);
        }
        return listaMeGustas;
    }

    @Override
    public int obtenerCantidadMeGustas() {
        int cont = 0;
        ListaSimple<Publicacion> listaPublicaciones = obtenerPublicaciones();
        for (int i = 0; i < listaPublicaciones.getSize(); i++) {
            ListaSimple<MeGusta> meGustasAux = listaPublicaciones.get(i).getListaMeGusta();
            for (int j = 0; j < meGustasAux.getSize(); j++) {
                cont += 1;
            }
        }
        return cont;
    }

    public Usuario() {
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public ArbolBinario<Publicacion> getArbolPublicaciones() {
        return arbolPublicaciones;
    }

    public void setArbolPublicaciones(ArbolBinario<Publicacion> arbolPublicaciones) {
        this.arbolPublicaciones = arbolPublicaciones;
    }

    public ListaSimple<Mensaje> getMensajes() {
        return mensajes;
    }

    public void setMensajes(ListaSimple<Mensaje> mensajes) {
        this.mensajes = mensajes;
    }

    @Override
    public String toString() {
        return nombre;
    }

    @Override
    public int hashCode() {
        return nombre.length() * this.nombre.length();
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof Usuario) {
            Usuario v = (Usuario) obj;

            return this.nombre.equals(v.getNombre());
        } else {
            return false;
        }

    }
}
