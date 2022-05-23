package co.redSocial.views.user;

import co.redSocial.RedSocialApplication;
import co.redSocial.controller.ModelFactoryController;
import co.redSocial.model.MeGusta;
import co.redSocial.model.Publicacion;
import co.redSocial.model.Usuario;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;

public class PostPaneController {
    //--------------------------------------------------------------------------------------------------------------
    //                                                 ATRIBUTOS
    //--------------------------------------------------------------------------------------------------------------
    private ModelFactoryController Controller = ModelFactoryController.getInstance();
    private Stage stage;
    private boolean flagLike;
    private Publicacion publicacion;
    private Usuario usuario;
    @FXML
    private ImageView likeBtn;
    @FXML
    private Label lblFecha, lblProducto, lblCategoria, lblUsuario;
    @FXML
    private Label lblMeGustas, lblComentarios;
    @FXML
    private Button btnEliminar;

    @FXML
    public void meGusta() {
        if (flagLike) {
            if (publicacion.eliminarMegusta(usuario)) {
                Controller.guardarDatos();
                setFlagLike(false);
            } else Controller.lanzarAlerta("ï¿½ERROR!", "No se pudo eliminar su me gusta", 1);
        } else {
            MeGusta meGusta = new MeGusta(usuario, publicacion);
            publicacion.getListaMeGusta().addEnd(meGusta);
            Controller.guardarDatos();
            setFlagLike(true);
        }
        lblMeGustas.setText("Le gusta a " + publicacion.getListaMeGusta().getSize() + " personas");
    }

    @FXML
    public void eliminar() {
        usuario.eliminarPublicacion(publicacion);
        Controller.guardarDatos();
        Controller.openUserView(usuario, false, true);
        Controller.openNotification("Publicación eliminada", "Se ha eliminado una publicación de tu muro", 2);
    }

    @FXML
    public void comentario() {
        Controller.openCommentsView(publicacion, usuario);
    }

    @FXML
    public void verMegusta() {
        Controller.openLikesView(publicacion, usuario);
    }

    public void inicializarDatos(ModelFactoryController controller, Stage stage, Publicacion publicacion, Usuario usuario, boolean flagPropia) {
        this.Controller = controller;
        this.stage = stage;
        this.publicacion = publicacion;
        this.usuario = usuario;
        lblFecha.setText(publicacion.getFecha() + " a las " + publicacion.getHora());
        lblProducto.setText(publicacion.getNombreProducto());
        lblCategoria.setText(publicacion.getCategoriaProducto());
        lblMeGustas.setText("Le gusta a " + publicacion.getListaMeGusta().getSize() + " personas");
        lblComentarios.setText(publicacion.getListaComentarios().getSize() + " comentarios");
        lblUsuario.setText(publicacion.getUsuarioDueno().getNombre());
        if (publicacion.obtenerMeGustaUsuario(usuario) != null) setFlagLike(true);
        btnEliminar.setVisible(flagPropia);
    }

    public boolean isFlagLike() {
        return flagLike;
    }

    public void setFlagLike(boolean flagLike) {
        String img = "";

        if (flagLike) {
            img = "/images/p_heartred.png";
        } else {
            img = "/images/p_heartshape.png";
        }

        Image image = new Image(RedSocialApplication.class.getResourceAsStream(img));
        likeBtn.setImage(image);

        this.flagLike = flagLike;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
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

    public Publicacion getPublicacion() {
        return publicacion;
    }

    public void setPublicacion(Publicacion publicacion) {
        this.publicacion = publicacion;
    }


}
