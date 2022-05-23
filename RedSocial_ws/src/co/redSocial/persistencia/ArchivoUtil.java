package co.redSocial.persistencia;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

public class ArchivoUtil {

    //---------------------------------------------------------------------------------------------
    //                      Manejo archivo binario
    //---------------------------------------------------------------------------------------------
    public static Object cargarRecursoSerializado(String rutaArchivo) {
        Object aux = null;
        ObjectInputStream ois = null;
        try {
            ois = new ObjectInputStream(new FileInputStream(rutaArchivo));
            aux = ois.readObject();
        } catch (Exception e) {
        } finally {
            if (ois != null) {
                try {
                    ois.close();
                } catch (IOException e) {
                }
            }
        }
        return aux;
    }

    public static void salvarRecursoSerializado(Object objeto, String ruta) throws IOException {
        FileOutputStream fos = new FileOutputStream(ruta);

        ObjectOutputStream oos = new ObjectOutputStream(fos);

        oos.writeObject(objeto);

        fos.close();
        oos.close();
    }
}
