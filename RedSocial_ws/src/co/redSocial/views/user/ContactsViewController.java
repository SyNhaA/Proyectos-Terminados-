package co.redSocial.views.user;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import co.redSocial.RedSocialApplication;
import co.redSocial.controller.ModelFactoryController;
import co.redSocial.estructuras.ListaSimple;
import co.redSocial.model.Usuario;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

public class ContactsViewController implements Initializable {
    //--------------------------------------------------------------------------------------------------------------
    //                                                 ATRIBUTOS
    //--------------------------------------------------------------------------------------------------------------
    private ModelFactoryController Controller = ModelFactoryController.getInstance();
    private Stage stage;
    private Usuario usuario;
    @FXML
    private Pane parent;
    @FXML
    private Button btnNoSugeridos, btnSugeridos;
    @FXML
    private Label lblMensajeContactos, lblMensajeNoContactos;
    @FXML
    private GridPane gridPaneContactos, gridPaneNoContactos;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
    }

    public void cargarContactos() {
        ListaSimple<Usuario> listaSimple = Controller.getRed().obtenerContactos(usuario);
        if (listaSimple.getSize() > 0) lblMensajeContactos.setVisible(false);
        for (int i = 1; i < listaSimple.getSize() + 1; i++) {
            try {
                FXMLLoader loader = new FXMLLoader();
                loader.setLocation(RedSocialApplication.class.getResource("views/user/ContactPane.fxml"));
                Pane pane = loader.load();
                ContactPaneController paneContact = loader.getController();

                Usuario usrAux = listaSimple.get(i - 1);
                paneContact.inicializarDatosContacto(Controller, parent, usuario, usrAux);

                gridPaneContactos.add(pane, 0, i);
                GridPane.setMargin(pane, new Insets(5, 10, 5, 10));
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    @FXML
    public void cargarNoContactos() {
        btnNoSugeridos.setStyle("-fx-background-color: rgb(22, 146, 134)");
        btnSugeridos.setStyle("-fx-text-fill: white;");

        ListaSimple<Usuario> listaUsuarios = Controller.getRed().obtenerListaUsuariosSinAgregar(usuario);
        if (listaUsuarios.getSize() > 0) lblMensajeNoContactos.setVisible(false);
        else {
            lblMensajeNoContactos.setVisible(true);
            lblMensajeNoContactos.setText("No hay usuarios disponibles");
        }
        llenarNoContactos(listaUsuarios);
    }

    @FXML
    public void cargarNoContactosSugeridos() {
        btnSugeridos.setStyle("-fx-background-color: rgb(22, 146, 134)");
        btnNoSugeridos.setStyle("-fx-text-fill: white;");
        ListaSimple<Usuario> listaSimple = Controller.getRed().obtenerSugerenciasContactos(usuario);
        if (listaSimple.getSize() > 0) lblMensajeNoContactos.setVisible(false);
        else {
            lblMensajeNoContactos.setVisible(true);
            lblMensajeNoContactos.setText("No hay sugerencias para ti");
        }
        llenarNoContactos(listaSimple);
    }

    private void llenarNoContactos(ListaSimple<Usuario> listaUsuarios) {
        gridPaneNoContactos.getChildren().clear();
        for (int i = 1; i < listaUsuarios.getSize() + 1; i++) {
            try {
                FXMLLoader loader = new FXMLLoader();
                loader.setLocation(RedSocialApplication.class.getResource("views/user/ContactPane.fxml"));
                Pane pane = loader.load();

                ContactPaneController paneContact = loader.getController();
                Usuario usrAux = listaUsuarios.get(i - 1);
                paneContact.inicializarDatosNoContacto(Controller, parent, usuario, usrAux);

                gridPaneNoContactos.add(pane, 0, i);
                GridPane.setMargin(pane, new Insets(5, 10, 5, 10));
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public void inicializarDatos(ModelFactoryController controller, Stage stage, Pane parent, Usuario usuario) {
        this.Controller = controller;
        this.stage = stage;
        this.parent = parent;
        this.usuario = usuario;
        cargarContactos();
        cargarNoContactos();
        Controller.getVentanaUsuario().selectContacts();
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

    public Pane getParent() {
        return parent;
    }

    public void setParent(Pane parent) {
        this.parent = parent;
    }
}
