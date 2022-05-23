package co.redSocial.services;

import co.redSocial.estructuras.ListaSimple;
import co.redSocial.model.Comentario;
import co.redSocial.model.MeGusta;
import co.redSocial.model.Publicacion;

public interface IUsuarioService {
    /**
     * M�todo que obtiene la cantidad de mensajes del usuario
     *
     * @return catidad de mensajes
     */
    public int obtenerCantidadMensajes();

    /**
     * M�todo que agrega una publicacion al �rbol de publicaciones del usuario
     *
     * @param publicacion
     */
    public void agregarPublicacion(Publicacion publicacion);

    /**
     * M�todo que obtiene la lista de publicaciones del usuario
     *
     * @return ListaSimple<Publicacion> listaPublicaciones
     */
    public ListaSimple<Publicacion> obtenerPublicaciones();

    /**
     * M�todo que obtiene la cantidad de publicaciones hechas por el usuario
     *
     * @return catidad de publicaciones
     */
    public int obtenerCantidadPublicaciones();

    /**
     * M�todo que obtiene la lista de los comentarios recibidos del usuario
     *
     * @return ListaSimple<Comentario> listaComentarios
     */
    public ListaSimple<Comentario> obtenerComentarios();

    /**
     * M�todo que obtiene la cantidad de comentarios hechos en las publicaciones del usuario
     *
     * @return catidad de comentarios
     */
    public int obtenerCantidadComentarios();

    /**
     * M�todo que obtiene la lista de los me gustas recibidos del usuario
     *
     * @return ListaSimple<MeGusta> listaMeGustas
     */
    public ListaSimple<MeGusta> obtenerMeGustas();

    /**
     * M�todo que obtiene la cantidad de me gustas en las publicaciones del usuario
     *
     * @return catidad de me gustas
     */
    public int obtenerCantidadMeGustas();

}
