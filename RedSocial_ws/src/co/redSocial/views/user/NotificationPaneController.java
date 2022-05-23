package co.redSocial.views.user;

import co.redSocial.controller.ModelFactoryController;
import co.redSocial.model.Comentario;
import co.redSocial.model.MeGusta;
import co.redSocial.model.Mensaje;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.Pane;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class NotificationPaneController {
    //--------------------------------------------------------------------------------------------------------------
    //                                                 ATRIBUTOS
    //--------------------------------------------------------------------------------------------------------------
    private ModelFactoryController Controller = ModelFactoryController.getInstance();
    private Stage stage;
    private Comentario comentario;
    private MeGusta meGusta;
    private Mensaje mensaje;
    private Pane parent;
    @FXML
    private Label lblFecha;
    @FXML
    private Text lblNombre;
    @FXML
    private Text lblConector1;
    @FXML
    private Text lblContenido;
    @FXML
    private Text lblConector2;
    @FXML
    private Text lblProducto;

    public void cargarComentario(Comentario comentario) {
        this.comentario = comentario;
        lblFecha.setText(comentario.getFecha() + " a las " + comentario.getHora());
        lblNombre.setText(comentario.getUsuario().getNombre() + " ");
        lblConector1.setText("a comentado: ");
        lblContenido.setText("\"" + comentario.getContenido() + "\" ");
        lblConector2.setText("en tu publicaci�n del producto: ");
        lblProducto.setText(comentario.getPublicacion().getNombreProducto());
    }

    public void cargarMeGusta(MeGusta meGusta) {
        this.meGusta = meGusta;
        lblFecha.setText(meGusta.getFecha() + " a las " + meGusta.getHora());
        lblNombre.setText(meGusta.getUsuario().getNombre() + " ");
        lblConector1.setText("ha indicado que le gusta ");
        lblContenido.setText("");
        lblConector2.setText("tu publicaci�n del producto: ");
        lblProducto.setText(meGusta.getPublicacion().getNombreProducto());
    }

    public void cargarMensaje(Mensaje mensaje, Pane parent) {
        this.parent = parent;
        this.mensaje = mensaje;
        lblFecha.setText(mensaje.getFecha() + " a las " + mensaje.getHora());
        lblNombre.setText(mensaje.getUsuario().getNombre() + " ");
        lblConector1.setText("te ha escrito un mensaje: ");
        lblContenido.setText("\"" + mensaje.getContenido() + "\" ");
    }

    @FXML
    public void verMensaje() {
        Controller.openMessagesView(parent, mensaje.getUsuarioDestino(), mensaje.getUsuario(), true);
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

    public Comentario getComentario() {
        return comentario;
    }

    public void setComentario(Comentario comentario) {
        this.comentario = comentario;
    }

    public Mensaje getMensaje() {
        return mensaje;
    }

    public void setMensaje(Mensaje mensaje) {
        this.mensaje = mensaje;
    }

    public MeGusta getMeGusta() {
        return meGusta;
    }

    public void setMeGusta(MeGusta meGusta) {
        this.meGusta = meGusta;
    }
}
