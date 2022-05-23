package co.redSocial.views.user;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import animatefx.animation.ZoomOutLeft;
import co.redSocial.RedSocialApplication;
import co.redSocial.controller.ModelFactoryController;
import co.redSocial.model.Comentario;
import co.redSocial.model.Publicacion;
import co.redSocial.model.Usuario;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import javafx.util.Duration;

public class CommentsViewController implements Initializable {
    //--------------------------------------------------------------------------------------------------------------
    //                                                 ATRIBUTOS
    //--------------------------------------------------------------------------------------------------------------
    private ModelFactoryController Controller = ModelFactoryController.getInstance();
    private Stage stage;
    private Usuario usuario;
    //	private Usuario usuarioDestino;
    private Publicacion publicacion;
    @FXML
    private TextArea txtComentario;
    @FXML
    private GridPane gridPaneComentarios;
    @FXML
    private Label lblMensaje;

    @Override
    public void initialize(URL arg0, ResourceBundle arg1) {
        txtComentario.setText(null);
    }

    @FXML
    public void agregarComentario() {
        if (txtComentario.getText() != null && txtComentario.getText() != "") {
            Comentario comentario = new Comentario(usuario, txtComentario.getText(), publicacion);
            publicacion.getListaComentarios().addEnd(comentario);
            Controller.guardarDatos();
            cargarComentarios();
            txtComentario.setText(null);
        } else {
            Controller.lanzarAlerta("Error", "Ingresa un mensaje", 1);
        }
    }

    public void cargarComentarios() {
        for (int i = 1; i < publicacion.getListaComentarios().getSize() + 1; i++) {
            lblMensaje.setVisible(false);
            Comentario aux = publicacion.getListaComentarios().get(i - 1);
            try {
                FXMLLoader loader = new FXMLLoader();
                loader.setLocation(RedSocialApplication.class.getResource("views/user/CommentPane.fxml"));
                Pane pane = loader.load();

                CommentPaneController paneComent = loader.getController();
                paneComent.setController(Controller);
                paneComent.cargarComentario(aux.getUsuario().getNombre(), aux.getContenido());
                if (!usuario.equals(aux.getUsuario())
                        && !Controller.getRed().verificarUsuariosAmigos(usuario, aux.getUsuario())) {
                    paneComent.cargarComentarioNoDisponible();
                }
                gridPaneComentarios.add(pane, 0, i);
                GridPane.setMargin(pane, new Insets(0, 0, 0, 0));
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    @FXML
    public void salir() {
        new ZoomOutLeft(stage.getScene().getRoot()).setSpeed(3).play();
        Timeline timeline = new Timeline(new KeyFrame(Duration.millis(400), ev -> {
            stage.close();
            Controller.openUserView(usuario, false, true);
        }));
        timeline.play();
    }

    public void inicializarDatos(ModelFactoryController controller, Stage stage, Publicacion publicacion, Usuario usuario) {
        this.Controller = controller;
        this.stage = stage;
        this.publicacion = publicacion;
        this.usuario = usuario;
        cargarComentarios();
    }


    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    public Publicacion getPublicacion() {
        return publicacion;
    }

    public void setPublicacion(Publicacion publicacion) {
        this.publicacion = publicacion;
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
