package co.redSocial.persistencia;

import java.io.IOException;

import co.redSocial.model.RedSocial;

public class Persistencia {

    public static final String RUTA_ARCHIVO_RED_DATA = "archivo/RedSocial.dat";
    //public static final String RUTA_ARCHIVO_red_DATA = "RedSocial/src/resources/red.dat";

    //---------------------------------------------------------------------------------------------
    //                      Manejo archivo binario
    //---------------------------------------------------------------------------------------------
    public static RedSocial cargarRedSocialBinario() {
        RedSocial red = (RedSocial) ArchivoUtil.cargarRecursoSerializado(RUTA_ARCHIVO_RED_DATA);
        return red;
    }

    public static void guardarRedSocialBinario(RedSocial red) {
        try {
            ArchivoUtil.salvarRecursoSerializado(red, RUTA_ARCHIVO_RED_DATA);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

