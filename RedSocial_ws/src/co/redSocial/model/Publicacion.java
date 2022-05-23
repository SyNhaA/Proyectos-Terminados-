package co.redSocial.model;

import java.io.Serializable;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.GregorianCalendar;

import co.redSocial.estructuras.ListaSimple;

public class Publicacion implements Comparable<Publicacion>, Serializable {

    private static final long serialVersionUID = 1L;
    private String nombreProducto;
    private String categoriaProducto;
    private String fecha;
    private String hora;
    private Usuario usuarioDueno;
    private ListaSimple<Comentario> listaComentarios;
    private ListaSimple<MeGusta> listaMeGusta;

    public Publicacion(String nombreProducto, String categoriaProducto, Usuario usuarioDueno) {
        this.nombreProducto = nombreProducto;
        this.categoriaProducto = categoriaProducto;
        this.usuarioDueno = usuarioDueno;
        this.listaComentarios = new ListaSimple<Comentario>();
        this.listaMeGusta = new ListaSimple<MeGusta>();
        llenarFecha();
    }

    private void llenarFecha() {
        GregorianCalendar date = new GregorianCalendar();
        DateFormat horaFormat = new SimpleDateFormat("hh:mm:ss a");
        DateFormat fechaFormat = new SimpleDateFormat("dd-MMMM-yyyy");
        String horaActual = horaFormat.format(date.getTime());
        String fechaActual = fechaFormat.format(date.getTime());
        //System.out.println("Fecha: " + fechaActual + " hora: " + horaActual);
        this.fecha = fechaActual;
        this.hora = horaActual;
    }

    public MeGusta obtenerMeGustaUsuario(Usuario usuario) {
        for (int i = 0; i < this.getListaMeGusta().getSize(); i++) {
            MeGusta likeAux = this.getListaMeGusta().get(i);
            if (likeAux.getUsuario().equals(usuario)) {
                return likeAux;
            }
        }
        return null;
    }

    public boolean eliminarMegusta(Usuario usuario) {
        MeGusta like = obtenerMeGustaUsuario(usuario);
        if (like != null) {
            this.getListaMeGusta().delete(like);
            return true;
        }
        return false;
    }

    public int obtenerCantidadMeGustas() {
        return this.getListaMeGusta().getSize();
    }

    public String getNombreProducto() {
        return nombreProducto;
    }

    public void setNombreProducto(String nombreProducto) {
        this.nombreProducto = nombreProducto;
    }

    public String getCategoriaProducto() {
        return categoriaProducto;
    }

    public void setCategoriaProducto(String categoriaProducto) {
        this.categoriaProducto = categoriaProducto;
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

    public Usuario getUsuarioDueno() {
        return usuarioDueno;
    }

    public void setUsuarioDueno(Usuario usuarioDueno) {
        this.usuarioDueno = usuarioDueno;
    }

    public ListaSimple<Comentario> getListaComentarios() {
        return listaComentarios;
    }

    public void setListaComentarios(ListaSimple<Comentario> listaComentarios) {
        this.listaComentarios = listaComentarios;
    }

    public ListaSimple<MeGusta> getListaMeGusta() {
        return listaMeGusta;
    }

    public void setListaMeGusta(ListaSimple<MeGusta> listaMeGusta) {
        this.listaMeGusta = listaMeGusta;
    }

    public Publicacion() {
    }

    @Override
    public int compareTo(Publicacion arg0) {
        return this.getNombreProducto().compareToIgnoreCase(arg0.getNombreProducto());
    }

}
