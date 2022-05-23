package co.redSocial.views.user;

import co.redSocial.controller.ModelFactoryController;
import javafx.fxml.FXML;
import javafx.scene.text.Text;

public class MessagePaneController {
    //--------------------------------------------------------------------------------------------------------------
    //                                                 ATRIBUTOS
    //--------------------------------------------------------------------------------------------------------------
    private ModelFactoryController Controller = ModelFactoryController.getInstance();
    @FXML
    private Text lblMensaje;

    public void cargarMensaje(String nombre) {
        lblMensaje.setText(nombre);
    }

    public ModelFactoryController getController() {
        return Controller;
    }

    public void setController(ModelFactoryController controller) {
        Controller = controller;
    }
}
