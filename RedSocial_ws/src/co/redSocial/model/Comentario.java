package co.redSocial.model;

import java.io.Serializable;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.GregorianCalendar;

public class Comentario implements Serializable {

    private static final long serialVersionUID = 1L;
    private Usuario usuario;
    private Usuario usuarioDestino;
    private Publicacion publicacion;
    private String contenido;
    private String fecha;
    private String hora;

    public Comentario(Usuario usuario, String contenido, Publicacion publicacion) {
        super();
        this.usuarioDestino = publicacion.getUsuarioDueno();
        this.contenido = contenido;
        this.publicacion = publicacion;
        this.usuario = usuario;
        llenarFecha();
    }

    private void llenarFecha() {
        GregorianCalendar date = new GregorianCalendar();
        DateFormat horaFormat = new SimpleDateFormat("hh:mm:ss a");
        DateFormat fechaFormat = new SimpleDateFormat("dd-MMMM-yyyy");
        String horaActual = horaFormat.format(date.getTime());
        String fechaActual = fechaFormat.format(date.getTime());
//		System.out.println("Fecha: " + fechaActual + " hora: " + horaActual);
        this.fecha = fechaActual;
        this.hora = horaActual;
    }

    public Comentario() {
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    public Usuario getUsuarioDestino() {
        return usuarioDestino;
    }

    public void setUsuarioDestino(Usuario usuarioDestino) {
        this.usuarioDestino = usuarioDestino;
    }

    public String getContenido() {
        return contenido;
    }

    public void setContenido(String contenido) {
        this.contenido = contenido;
    }

    public Publicacion getPublicacion() {
        return publicacion;
    }

    public void setPublicacion(Publicacion publicacion) {
        this.publicacion = publicacion;
    }

    public String getFecha() {
        return fecha;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }

    public String getHora() {
        return hora;
    }

    public void setHora(String hora) {
        this.hora = hora;
    }

}
