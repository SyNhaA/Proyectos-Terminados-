package co.redSocial.model;

import java.io.Serializable;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.GregorianCalendar;

public class MeGusta implements Serializable {

    private static final long serialVersionUID = 1L;
    private Usuario usuario;
    private Usuario usuarioDestino;
    private Publicacion publicacion;
    private String fecha;
    private String hora;

    public MeGusta(Usuario usuario, Publicacion publicacion) {
        super();
        this.usuario = usuario;
        this.publicacion = publicacion;
        this.usuarioDestino = publicacion.getUsuarioDueno();
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


    @Override
    public String toString() {
        return usuario.getNombre();
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

    public MeGusta() {
    }
}
