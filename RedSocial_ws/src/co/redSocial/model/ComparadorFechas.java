package co.redSocial.model;

import java.util.Comparator;

public class ComparadorFechas implements Comparator<Publicacion> {

    public int compare(Publicacion publicacion1, Publicacion publicacion2) {
        return (publicacion1.getFecha() + " - " + publicacion1.getHora())
                .compareTo(publicacion2.getFecha() + " - " + publicacion2.getHora());

    }

}

