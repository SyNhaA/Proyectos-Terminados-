package co.redSocial.views.user;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import org.controlsfx.control.textfield.TextFields;

import animatefx.animation.ZoomOutDown;
import co.redSocial.RedSocialApplication;
import co.redSocial.controller.ModelFactoryController;
import co.redSocial.estructuras.ListaSimple;
import co.redSocial.exceptions.CampoVacioException;
import co.redSocial.model.Publicacion;
import co.redSocial.model.Usuario;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.util.Duration;

public class UserViewController implements Initializable {
    //--------------------------------------------------------------------------------------------------------------
    //                                                 ATRIBUTOS
    //--------------------------------------------------------------------------------------------------------------
    private ModelFactoryController Controller = ModelFactoryController.getInstance();
    private String borderSelect = "-fx-border-width: 0px 0px 0px 3px; -fx-border-color: white;";
    private String borderUnselect = "-fx-border-width: 0px 0px 0px 3px; -fx-border-color: transparent;";
    private Stage stage;
    private Usuario usuario;
    @FXML
    private Pane panePublicaciones;
    @FXML
    private Label lblLink, lblNombreUsr;
    @FXML
    private VBox contentP, contentC;
    @FXML
    private Label lblMensajePOwn, lblMensajePOth;
    @FXML
    private Label lblInfoProducto, lblInfoCategoria;
    @FXML
    private GridPane gridPanePostsPropios, gridPanePostsNoPropios;
    @FXML
    private TextField txtNombreProducto, txtCategoriaProducto;
    @FXML
    private HBox btnPublicaciones, btnContactos, btnNotificaciones, btnMensajes;


    @Override
    public void initialize(URL arg0, ResourceBundle arg1) {
        inicializarCampos();
        cambiarTextField();
    }

    public void inicializarCampos() {
        txtNombreProducto.setText(null);
        txtCategoriaProducto.setText(null);
    }

    @FXML
    public void agregarPublicacion() throws InterruptedException {
        if (verificarCampos()) {
            String nombreP = txtNombreProducto.getText();
            String categoriaP = txtCategoriaProducto.getText();
            inicializarCampos();
            Controller.getRed().agregarPublicacion(nombreP, categoriaP, usuario);
            Controller.guardarDatos();
            cargarPublicacionesPropias();
            Controller.openNotification("Nueva publicación", "Se ha agregado una publicación a tu muro", 0);
        }
    }

    public boolean verificarCampos() {
        boolean flag = true;
        lblInfoProducto.setVisible(false);
        lblInfoCategoria.setVisible(false);
        if (txtCategoriaProducto.getText() == null || txtCategoriaProducto.getText().trim().length() == 0) {
            flag = false;
            lblInfoCategoria.setVisible(true);
        }
        if (txtNombreProducto.getText() == null || txtNombreProducto.getText().trim().length() == 0) {
            flag = false;
            lblInfoProducto.setVisible(true);
        }
        try{
        	if(!flag){
            	throw new CampoVacioException("publicar un producto");
            }
        }catch(CampoVacioException e){
        	System.out.println(e);
        }
        return flag;
    }


    public void cargarPublicacionesAmigos() {
        ListaSimple<Publicacion> listaPublicaciones = Controller.getRed().obtenerPublicacionesContactos(usuario);
        for (int i = 1; i < listaPublicaciones.getSize() + 1; i++) {
            lblMensajePOth.setVisible(false);
            try {
                FXMLLoader loader = new FXMLLoader();
                loader.setLocation(RedSocialApplication.class.getResource("views/user/PostPane.fxml"));
                Pane pane = loader.load();

                PostPaneController paneContact = loader.getController();
                paneContact.inicializarDatos(Controller, stage, listaPublicaciones.get(i - 1), usuario, false);

                gridPanePostsNoPropios.add(pane, 0, i);
                GridPane.setMargin(pane, new Insets(5, 0, 5, 0));
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public void cargarPublicacionesPropias() {
        ListaSimple<Publicacion> listaPublicaciones = usuario.obtenerPublicaciones();
        for (int i = 1; i < listaPublicaciones.getSize() + 1; i++) {
            try {
                lblMensajePOwn.setVisible(false);
                FXMLLoader loader = new FXMLLoader();
                loader.setLocation(RedSocialApplication.class.getResource("views/user/PostPane.fxml"));
                Pane pane = loader.load();

                PostPaneController paneContact = loader.getController();
                paneContact.inicializarDatos(Controller, stage, listaPublicaciones.get(i - 1), usuario, true);

                gridPanePostsPropios.add(pane, 0, i);
                GridPane.setMargin(pane, new Insets(5, 0, 5, 0));
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    @FXML
    public void eliminarCuenta() {
        Controller.getRed().eliminarUsuario(usuario);
        Controller.guardarDatos();
        Controller.openPrincipalView(false);
    }

    @FXML
    public void viewContacts() throws IOException {
        Controller.openContactsView(panePublicaciones, usuario, true);
    }

    @FXML
    public void viewMessages() throws IOException {
        Controller.openMessagesView(panePublicaciones, usuario, null, true);
    }

    @FXML
    public void viewNotifications() {
        Controller.openNotificationsView(panePublicaciones, usuario, true);
    }

    @FXML
    public void viewPublications() {
        Controller.openUserView(usuario, false, false);
    }

    @FXML
    public void volver() {
        Controller.openPrincipalView(false);
    }

    @FXML
    public void salir() throws InterruptedException {
        new ZoomOutDown(stage.getScene().getRoot()).setSpeed(3).play();
        Timeline timeline = new Timeline(new KeyFrame(Duration.millis(400), ev -> {
            System.exit(0);
        }));
        timeline.play();
    }

    @FXML
    public void minimizar() {
        stage.setIconified(true);
    }

    public void selectContacts() {
        btnContactos.setStyle(borderSelect);
        btnMensajes.setStyle(borderUnselect);
        btnNotificaciones.setStyle(borderUnselect);
        btnPublicaciones.setStyle(borderUnselect);
    }

    public void selectMessages() {
        btnMensajes.setStyle(borderSelect);
        btnNotificaciones.setStyle(borderUnselect);
        btnPublicaciones.setStyle(borderUnselect);
        btnContactos.setStyle(borderUnselect);
    }

    public void selectNotifications() {
        btnNotificaciones.setStyle(borderSelect);
        btnMensajes.setStyle(borderUnselect);
        btnContactos.setStyle(borderUnselect);
        btnPublicaciones.setStyle(borderUnselect);
    }

    public void selectPublications() {
        btnPublicaciones.setStyle(borderSelect);
        btnMensajes.setStyle(borderUnselect);
        btnContactos.setStyle(borderUnselect);
        btnNotificaciones.setStyle(borderUnselect);
    }

    public void cambiarTextField() {
        TextField newTxt1 = TextFields.createClearableTextField();
        newTxt1.setPromptText(txtNombreProducto.getPromptText());
        newTxt1.setPrefSize(txtNombreProducto.getPrefWidth(), txtNombreProducto.getPrefHeight());
        contentP.getChildren().remove(txtNombreProducto);
        contentP.getChildren().add(newTxt1);
        txtNombreProducto = newTxt1;

        TextField newTxt2 = TextFields.createClearableTextField();
        newTxt2.setPromptText(txtCategoriaProducto.getPromptText());
        newTxt2.setPrefSize(txtCategoriaProducto.getPrefWidth(), txtCategoriaProducto.getPrefHeight());
        contentC.getChildren().remove(txtCategoriaProducto);
        contentC.getChildren().add(newTxt2);
        txtCategoriaProducto = newTxt2;
    }

    public void inicializarDatos(ModelFactoryController controller, Stage stage, Usuario usr) {
        this.Controller = controller;
        this.stage = stage;
        this.usuario = usr;
        lblNombreUsr.setText(usuario.getNombre());
        String nombre = usuario.getNombre().replace(" ", "").toLowerCase();
        lblLink.setText("https://market-system/network/user/account-" + nombre + ".com");
        cargarPublicacionesPropias();
        cargarPublicacionesAmigos();
        selectPublications();
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

    public Pane getPanePublicaciones() {
        return panePublicaciones;
    }

    public void setPanePublicaciones(Pane panePublicaciones) {
        this.panePublicaciones = panePublicaciones;
    }

}
