package co.redSocial.views.user;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import co.redSocial.RedSocialApplication;
import co.redSocial.controller.ModelFactoryController;
import co.redSocial.estructuras.ListaSimple;
import co.redSocial.model.Comentario;
import co.redSocial.model.MeGusta;
import co.redSocial.model.Mensaje;
import co.redSocial.model.Usuario;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

public class NotificationsViewController implements Initializable {
    //--------------------------------------------------------------------------------------------------------------
    //                                                 ATRIBUTOS
    //--------------------------------------------------------------------------------------------------------------
    private ModelFactoryController Controller = ModelFactoryController.getInstance();
    private Stage stage;
    private Usuario usuario;
    private Pane parent;
    @FXML
    private Label lblMensajeC, lblMensajeM, lblMensajeL;
    @FXML
    private GridPane gridPaneMensajes, gridPaneComentarios, gridPaneMeGustas;

    @Override
    public void initialize(URL arg0, ResourceBundle arg1) {

    }

    public void cargarLikes() throws IOException {
        ListaSimple<MeGusta> listaMeGustas = usuario.obtenerMeGustas();
        for (int i = 1; i <= listaMeGustas.getSize(); i++) {
            lblMensajeL.setVisible(false);

            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(RedSocialApplication.class.getResource("views/user/NotificationPaneLikeComment.fxml"));
            Pane pane = loader.load();

            NotificationPaneController paneNotification = loader.getController();
            paneNotification.setController(Controller);
            paneNotification.cargarMeGusta(listaMeGustas.get(i - 1));

            gridPaneMeGustas.add(pane, 0, i);
            GridPane.setMargin(pane, new Insets(2, 0, 2, 0));

        }
    }

    public void cargarComentarios() throws IOException {
        ListaSimple<Comentario> listaComentarios = usuario.obtenerComentarios();
        for (int i = 1; i <= listaComentarios.getSize(); i++) {
            lblMensajeC.setVisible(false);

            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(RedSocialApplication.class.getResource("views/user/NotificationPaneLikeComment.fxml"));
            Pane pane = loader.load();

            NotificationPaneController paneNotification = loader.getController();
            paneNotification.setController(Controller);
            paneNotification.cargarComentario(listaComentarios.get(i - 1));

            gridPaneComentarios.add(pane, 0, i);
            GridPane.setMargin(pane, new Insets(2, 0, 2, 0));
        }
    }

    public void cargarMensajes() throws IOException {
        ListaSimple<Mensaje> listaMensajes = usuario.getMensajes();
        for (int i = 1; i <= listaMensajes.getSize(); i++) {
            lblMensajeM.setVisible(false);

            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(RedSocialApplication.class.getResource("views/user/NotificationPaneMessage.fxml"));
            Pane pane = loader.load();

            NotificationPaneController paneNotification = loader.getController();
            paneNotification.setController(Controller);
            paneNotification.cargarMensaje(listaMensajes.get(i - 1), parent);

            gridPaneMensajes.add(pane, 0, i);
            GridPane.setMargin(pane, new Insets(2, 0, 2, 0));
        }
    }

    public void inicializarDatos(ModelFactoryController controller, Stage stage, Pane parent, Usuario usr) throws IOException {
        this.Controller = controller;
        this.stage = stage;
        this.parent = parent;
        this.usuario = usr;
        cargarLikes();
        cargarComentarios();
        cargarMensajes();
        Controller.getVentanaUsuario().selectNotifications();
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usr) {
        this.usuario = usr;
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
