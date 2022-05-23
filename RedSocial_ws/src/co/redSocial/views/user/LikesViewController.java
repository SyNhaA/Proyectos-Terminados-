package co.redSocial.views.user;

import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

import animatefx.animation.ZoomOutLeft;
import co.redSocial.controller.ModelFactoryController;
import co.redSocial.model.MeGusta;
import co.redSocial.model.Publicacion;
import co.redSocial.model.Usuario;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.stage.Stage;
import javafx.util.Duration;

public class LikesViewController implements Initializable {
    //--------------------------------------------------------------------------------------------------------------
    //                                                 ATRIBUTOS
    //--------------------------------------------------------------------------------------------------------------
    private ModelFactoryController Controller = ModelFactoryController.getInstance();
    private Stage stage;
    private Usuario usuario;
    private Publicacion publicacion;
    @FXML
    private ListView<MeGusta> listaLikes;
    @FXML
    private Label lblMensaje;

    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }

    public void cargarMeGustas() {
        ArrayList<MeGusta> listaArray = new ArrayList<MeGusta>();
        for (int i = 0; i < publicacion.getListaMeGusta().getSize(); i++) {
            lblMensaje.setVisible(false);
            listaArray.add(publicacion.getListaMeGusta().get(i));
        }
        ObservableList<MeGusta> listaO = FXCollections.observableArrayList(listaArray);
        listaLikes.setItems(listaO);
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
        cargarMeGustas();
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
