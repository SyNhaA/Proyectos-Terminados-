package co.redSocial.services;

import co.redSocial.model.RedSocial;
import javafx.stage.Stage;

public interface IModelFactoryService {

    /**
     * M�todo para abrir ventana moduladora
     *
     * @param titulo
     * @param contenido
     * @param opcion
     * @return
     */
    public void lanzarAlerta(String titulo, String contenido, int opcion);

    /**
     * M�todo para cargar la red con los datos almacenados
     */
    public void cargarDatos();

    /**
     * M�todo para guardar la red con los datos
     */
    public void guardarDatos();

    /**
     * M�todo para obtener la instancia de la red
     *
     * @return red
     */
    public RedSocial getRed();

    /**
     * M�todo para indicar la instancia del escenario
     *
     * @param stage
     */
    public void setStage(Stage stage);


}
