package co.redSocial.views;

import co.redSocial.controller.ModelFactoryController;
import co.redSocial.model.Usuario;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.stage.Stage;

public class UserPaneController {
    //--------------------------------------------------------------------------------------------------------------
    //                                                 ATRIBUTOS
    //--------------------------------------------------------------------------------------------------------------
    private ModelFactoryController Controller = ModelFactoryController.getInstance();
    private Stage stage;
    private Usuario usuario;
    @FXML
    private Label lblNombreUsuario;

    public void cargarUsuario(Usuario usuario) {
        this.usuario = usuario;
        lblNombreUsuario.setText(usuario.getNombre());
    }

    @FXML
    public void abrirViewUser() {
        Controller.openUserView(usuario, true, false);
    }

    public ModelFactoryController getController() {
        return Controller;
    }

    public void setController(ModelFactoryController controller) {
        Controller = controller;
    }

    public Stage getStage() {
        return stage;
    }

    public void setStage(Stage stage) {
        this.stage = stage;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

}
