package co.redSocial.services;

import co.redSocial.model.Usuario;
import co.redSocial.estructuras.ListaSimple;
import co.redSocial.model.Mensaje;
import co.redSocial.model.Publicacion;

public interface IRedSocialService {

    /**
     * M�todo para agregar un usuario a la red
     *
     * @param nombre
     */
    public void agregarUsuario(String nombre);

    /**
     * M�todo para obtener los usuario en la red
     *
     * @return ListaSimple<Usuario> listaUsuarios
     */
    public ListaSimple<Usuario> obtenerListaUsuarios();

    /**
     * M�todo que verifica si existe un usuario con el nombre dado
     *
     * @param nombre
     * @return true or false
     */
    public boolean verificarUsuarioExistente(String nombre);

    /**
     * M�todo para conectar dos usuarios en la red
     *
     * @param usuario1
     * @param usuario2
     */
    public void conectarUsuarios(Usuario usuario1, Usuario usuario2);

    /**
     * M�todo para obtener los contactos de usuario en la red
     *
     * @param usuario
     * @return ListaSimple<Usuario> listaUsuarios
     */
    public ListaSimple<Usuario> obtenerContactos(Usuario usuario);


    /**
     * M�todo para obtener los usuario en la red que no ha agregado el usuario
     *
     * @param usuario
     * @return
     */
    public ListaSimple<Usuario> obtenerListaUsuariosSinAgregar(Usuario usuario);

    /**
     * M�todo para obtener sugerencias de contactos de usuario en la red
     *
     * @param usuario
     * @return ListaSimple<Usuario> listaUsuarios
     */
    public ListaSimple<Usuario> obtenerSugerenciasContactos(Usuario usuario);

    /**
     * M�todo para agregar al usuario una publicaci�n
     *
     * @param nombreProducto
     * @param categoriaProducto
     * @param usuarioDueno
     */
    public void agregarPublicacion(String nombreProducto, String categoriaProducto, Usuario usuarioDueno);

    /**
     * M�todo para obtener las publicaciones de los contactos de usuario en la red
     *
     * @param usuario
     * @return ListaSimple<Publicacion> listaPublicaciones
     */
    public ListaSimple<Publicacion> obtenerPublicacionesContactos(Usuario usuario);

    /**
     * M�todo para obtener los mensajes entre dos usuarios
     *
     * @param usuario1
     * @param usuario2
     * @return ListaEnlazadaSimple<Mensaje> listaMensajes
     */
    public ListaSimple<Mensaje> obtenerMensajesUsuarios(Usuario usuario1, Usuario usuario2);

    /**
     * M�todo que obtiene la cantidad de usuarios en la red
     *
     * @return catidad de usuarios
     */
    public int obtenerCantidadUsuarios();

    /**
     * M�todo que obtiene la cantidad de mensajes del usuario
     *
     * @return catidad de publicaciones
     */
    public int obtenerCantidadMensajes();

    /**
     * M�todo que obtiene la cantidad de publicaciones hechas por los usuarios
     *
     * @return catidad de publicaciones
     */
    public int obtenerCantidadPublicaciones();

    /**
     * M�todo que obtiene la cantidad de comentarios hechos en las publicaciones de los usuarios
     *
     * @return catidad de comentarios
     */
    public int obtenerCantidadComentarios();

    /**
     * M�todo que obtiene la cantidad de me gustas en las publicaciones de los usuarios
     *
     * @return catidad de me gustas
     */
    public int obtenerCantidadMeGustas();

}
