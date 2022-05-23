package co.redSocial.model;

import java.io.Serializable;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Comparator;
import java.util.GregorianCalendar;

public class Mensaje implements Comparator<Mensaje>, Serializable {

    private static final long serialVersionUID = 1L;
    private Usuario usuario;
    private Usuario usuarioDestino;
    private String contenido;
    private String fecha;
    private String hora;

    public Mensaje(Usuario usuario, Usuario usuarioDestino, String contenido) {
        super();
        this.usuario = usuario;
        this.contenido = contenido;
        this.usuarioDestino = usuarioDestino;
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

    public Mensaje() {
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

    @Override
    public int compare(Mensaje o1, Mensaje o2) {
        return (o1.getFecha() + " - " + o1.getHora())
                .compareTo(o2.getFecha() + " - " + o2.getHora());
    }

    public class ComparadorFechas implements Comparator<Mensaje> {

        public int compare(Mensaje publicacion1, Mensaje publicacion2) {
            return (publicacion1.getFecha() + " - " + publicacion1.getHora())
                    .compareTo(publicacion2.getFecha() + " - " + publicacion2.getHora());

        }
    }
}
