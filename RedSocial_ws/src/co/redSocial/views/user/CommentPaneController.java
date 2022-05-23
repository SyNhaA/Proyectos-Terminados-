package co.redSocial.views.user;

import co.redSocial.controller.ModelFactoryController;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.effect.GaussianBlur;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class CommentPaneController {
    //--------------------------------------------------------------------------------------------------------------
    //                                                 ATRIBUTOS
    //--------------------------------------------------------------------------------------------------------------
    private ModelFactoryController Controller = ModelFactoryController.getInstance();
    private Stage stage;
    @FXML
    private Label lblNombre;
    @FXML
    private Text lblComentario;
    @FXML
    private AnchorPane paneComentario, paneDeshabilitado;

    public void cargarComentario(String nombre, String comentario) {
        lblNombre.setText(nombre);
        lblComentario.setText(comentario);
    }

    public void cargarComentarioNoDisponible() {
        GaussianBlur efecto = new GaussianBlur(5);
        paneComentario.setEffect(efecto);
        paneDeshabilitado.setVisible(true);
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


}
