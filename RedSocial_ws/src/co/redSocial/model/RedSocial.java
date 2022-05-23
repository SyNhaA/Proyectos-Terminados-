package co.redSocial.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

import co.redSocial.estructuras.Grafo;
import co.redSocial.estructuras.ListaSimple;
import co.redSocial.services.IRedSocialService;

public class RedSocial implements IRedSocialService, Serializable {

    private static final long serialVersionUID = 1L;
    private String nombre;
    private Grafo<Usuario> red;

    public RedSocial(String nombre) {
        this.nombre = nombre;
        this.red = new Grafo<Usuario>();
    }

    @Override
    public void agregarUsuario(String nombre) {
        Usuario usuario = new Usuario(nombre);
        red.agregar(usuario);
        System.out.println("Agregado");
    }

    @Override
    public ListaSimple<Usuario> obtenerListaUsuarios() {
        ListaSimple<Usuario> lista = new ListaSimple<>();
        red.recorrerGrafo(lista);
        return lista;
    }

    @Override
    public boolean verificarUsuarioExistente(String nombre) {
        ListaSimple<Usuario> lista = obtenerListaUsuarios();
        for (int i = 0; i < lista.getSize(); i++) {
            if (lista.get(i).getNombre().equals(nombre)) {
                return true;
            }
        }
        return false;
    }

    @Override
    public void conectarUsuarios(Usuario usuario1, Usuario usuario2) {
        red.conectar(usuario1, usuario2, 0);
    }

    public boolean verificarUsuariosAmigos(Usuario usuario1, Usuario usuario2) {
        ListaSimple<Usuario> listaContactos = obtenerContactos(usuario1);
        for (int i = 0; i < listaContactos.getSize(); i++) {
            if (listaContactos.get(i).equals(usuario2)) {
                System.out.println(usuario1.getNombre() + " amix de " + usuario2.getNombre());
                return true;
            }
        }
        return false;
    }

    public void eliminarUsuario(Usuario usuario) {
        ListaSimple<Usuario> listaContactos = obtenerContactos(usuario);
        for (int i = 0; i < listaContactos.getSize(); i++) {
            red.desconectar(usuario, listaContactos.get(i));
            red.desconectar(listaContactos.get(i), usuario);
        }
        red.eliminar(usuario);
    }

    @Override
    public ListaSimple<Usuario> obtenerContactos(Usuario usuario) {
        ListaSimple<Usuario> listaContactos = new ListaSimple<Usuario>();
        listaContactos = red.getLinks(usuario);
        return listaContactos;
    }

    @Override
    public ListaSimple<Usuario> obtenerListaUsuariosSinAgregar(Usuario usuario) {
        ListaSimple<Usuario> listaContactos = obtenerContactos(usuario);
        ListaSimple<Usuario> lista = new ListaSimple<>();
        red.recorrerGrafo(lista);
        lista.delete(usuario);
        for (int i = 0; i < listaContactos.getSize(); i++) {
            lista.delete(listaContactos.get(i));
        }

        return lista;
    }

    @Override
    public ListaSimple<Usuario> obtenerSugerenciasContactos(Usuario usuario) {
        ListaSimple<Usuario> listaSugerencias = new ListaSimple<Usuario>();
        ListaSimple<Usuario> listaContactos = obtenerContactos(usuario);

        for (int i = 0; i < listaContactos.getSize(); i++) {
            Usuario usuario2 = listaContactos.get(i);
            ListaSimple<Usuario> contactosUsuario2 = obtenerContactos(usuario2);
            contactosUsuario2.delete(usuario);
            for (int j = 0; j < contactosUsuario2.getSize(); j++) {
                Usuario sug = contactosUsuario2.get(j);
                if (!listaContactos.contains(sug)) {
                    listaSugerencias.addEnd(contactosUsuario2.get(j));
                }
            }
        }
        return listaSugerencias;
    }

    @Override
    public void agregarPublicacion(String nombreProducto, String categoriaProducto, Usuario usuarioDueno) {
        Publicacion publicacion = new Publicacion(nombreProducto, categoriaProducto, usuarioDueno);
        usuarioDueno.agregarPublicacion(publicacion);
    }

    @Override
    public ListaSimple<Publicacion> obtenerPublicacionesContactos(Usuario usuario) {
        ArrayList<Publicacion> listaPublicacionesAux = new ArrayList<>();
        ListaSimple<Publicacion> publicacionesContactos = new ListaSimple<Publicacion>();
        ListaSimple<Usuario> listaContactos = obtenerContactos(usuario);
        for (int i = 0; i < listaContactos.getSize(); i++) {
            ListaSimple<Publicacion> publicacionesContactosAux = listaContactos.get(i).obtenerPublicaciones();
            for (int j = 0; j < publicacionesContactosAux.getSize(); j++) {
                listaPublicacionesAux.add(publicacionesContactosAux.get(j));
            }
        }

        Collections.sort(listaPublicacionesAux, new Comparator<Publicacion>() {
            @Override
            public int compare(Publicacion p1, Publicacion p2) {
                return (p1.getFecha() + " - " + p1.getHora())
                        .compareTo(p2.getFecha() + " - " + p2.getHora());
            }
        });
        Collections.reverse(listaPublicacionesAux);

        for (Publicacion p : listaPublicacionesAux) {
            publicacionesContactos.addEnd(p);
        }
        return publicacionesContactos;
    }

    @Override
    public ListaSimple<Mensaje> obtenerMensajesUsuarios(Usuario usuario1, Usuario usuario2) {
        ArrayList<Mensaje> listaMensajesAux = new ArrayList<>();
        ListaSimple<Mensaje> listaMensajes = new ListaSimple<Mensaje>();
        for (int i = 0; i < usuario1.getMensajes().getSize(); i++) {
            Mensaje aux = usuario1.getMensajes().get(i);
            if (aux.getUsuario().equals(usuario2)) {
                listaMensajesAux.add(aux);
            }
        }
        for (int i = 0; i < usuario2.getMensajes().getSize(); i++) {
            Mensaje aux = usuario2.getMensajes().get(i);
            if (aux.getUsuario().equals(usuario1)) {
                listaMensajesAux.add(aux);
            }
        }
        Collections.sort(listaMensajesAux, new Comparator<Mensaje>() {
            @Override
            public int compare(Mensaje c1, Mensaje c2) {
                return (c1.getFecha() + " - " + c1.getHora())
                        .compareTo(c2.getFecha() + " - " + c2.getHora());
            }
        });
        for (Mensaje mensaje : listaMensajesAux) {
            listaMensajes.addEnd(mensaje);
        }
        return listaMensajes;
    }

    @Override
    public int obtenerCantidadUsuarios() {
        return obtenerListaUsuarios().getSize();
    }

    @Override
    public int obtenerCantidadMensajes() {
        int cont = 0;
        ListaSimple<Usuario> listaUsuarios = obtenerListaUsuarios();
        for (int i = 0; i < listaUsuarios.getSize(); i++) {
            cont += listaUsuarios.get(i).obtenerCantidadMensajes();
        }
        return cont;
    }

    @Override
    public int obtenerCantidadPublicaciones() {
        int cont = 0;
        ListaSimple<Usuario> listaUsuarios = obtenerListaUsuarios();
        for (int i = 0; i < listaUsuarios.getSize(); i++) {
            cont += listaUsuarios.get(i).obtenerCantidadPublicaciones();
        }
        return cont;
    }

    @Override
    public int obtenerCantidadComentarios() {
        int cont = 0;
        ListaSimple<Usuario> listaUsuarios = obtenerListaUsuarios();
        for (int i = 0; i < listaUsuarios.getSize(); i++) {
            cont += listaUsuarios.get(i).obtenerCantidadComentarios();
        }
        return cont;
    }

    @Override
    public int obtenerCantidadMeGustas() {
        int cont = 0;
        ListaSimple<Usuario> listaUsuarios = obtenerListaUsuarios();
        for (int i = 0; i < listaUsuarios.getSize(); i++) {
            cont += listaUsuarios.get(i).obtenerCantidadMeGustas();
        }
        return cont;
    }

    public ListaSimple<Publicacion> obtenerTop5() {
        ArrayList<Publicacion> listaPublicacionesAux = new ArrayList<>();
        ListaSimple<Publicacion> listaTop5 = new ListaSimple<Publicacion>();
        ListaSimple<Publicacion> listaPublicaciones = obtenerPublicaciones();
        for (int i = 0; i < listaPublicaciones.getSize(); i++) {
            listaPublicacionesAux.add(listaPublicaciones.get(i));
        }
        Collections.sort(listaPublicacionesAux, new Comparator<Publicacion>() {
            @Override
            public int compare(Publicacion p1, Publicacion p2) {
                if (p1.obtenerCantidadMeGustas() > p2.obtenerCantidadMeGustas()) return -1;
                else {
                    if (p1.obtenerCantidadMeGustas() < p2.obtenerCantidadMeGustas()) return 1;
                }
                return 0;
            }
        });
        int tamanio = -1;
        if (listaPublicacionesAux.size() >= 5) {
            tamanio = 5;
        } else {
            tamanio = listaPublicacionesAux.size();
        }
        for (int i = 0; i < tamanio; i++) {
            listaTop5.addEnd(listaPublicacionesAux.get(i));
        }
        return listaTop5;

    }

    public ListaSimple<Publicacion> obtenerPublicaciones() {
        ListaSimple<Publicacion> listaPublicaciones = new ListaSimple<Publicacion>();
        ListaSimple<Usuario> listaUsuarios = obtenerListaUsuarios();
        for (int i = 0; i < listaUsuarios.getSize(); i++) {
            ListaSimple<Publicacion> listaAux = listaUsuarios.get(i).obtenerPublicaciones();
            for (int j = 0; j < listaAux.getSize(); j++) {
                listaPublicaciones.addEnd(listaAux.get(j));
            }
        }
        return listaPublicaciones;
    }

    public RedSocial() {
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public Grafo<Usuario> getRed() {
        return red;
    }

    public void setRed(Grafo<Usuario> red) {
        this.red = red;
    }


}
