package co.redSocial.views.user;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

import co.redSocial.RedSocialApplication;
import co.redSocial.controller.ModelFactoryController;
import co.redSocial.estructuras.ListaSimple;
import co.redSocial.model.Mensaje;
import co.redSocial.model.Usuario;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

public class MessagesViewController implements Initializable {
    //--------------------------------------------------------------------------------------------------------------
    //                                                 ATRIBUTOS
    //--------------------------------------------------------------------------------------------------------------
    private ModelFactoryController Controller = ModelFactoryController.getInstance();
    private Stage stage;
    private Usuario usuario;
    private Usuario usuarioDestino;
    @FXML
    private ListView<Usuario> listacontactos;
    @FXML
    private Label lblMensajeC, lblMensajeM, lblInfoMensaje, lblMensajeUsr;
    @FXML
    private Label lblNombreUsr;
    @FXML
    private TextArea txtMensaje;
    @FXML
    private GridPane gridPaneMensajes;
    @FXML
    private TextField txtBuscarContacto;
    @FXML
    private HBox bxInfoUsr;

    @Override
    public void initialize(URL arg0, ResourceBundle arg1) {
        cambiarVisibilidadInfoUser(false);
        txtMensaje.setEditable(false);
        txtMensaje.setText(null);

    }

    public void cambiarVisibilidadInfoUser(boolean flag) {
        bxInfoUsr.setVisible(flag);
        lblMensajeUsr.setVisible(!flag);
    }

    @FXML
    public void enviarMensaje() {
        if (validarMensaje()) {
            Mensaje mensaje = new Mensaje(usuario, usuarioDestino, txtMensaje.getText());
            usuarioDestino.getMensajes().addEnd(mensaje);
            txtMensaje.setText(null);
            Controller.guardarDatos();
            cargarMensajes();
        }
    }

    private boolean validarMensaje() {
        lblInfoMensaje.setVisible(false);
        boolean flag = true;
        if (usuarioDestino == null) {
            flag = false;
            lblInfoMensaje.setVisible(true);
            lblInfoMensaje.setText("*Debes seleccionar un contacto");
        } else {
            if (txtMensaje.getText() == null || txtMensaje.getText() == "") {
                flag = false;
                lblInfoMensaje.setVisible(true);
                lblInfoMensaje.setText("*Debes ingresar un mensaje");
            }
        }
        return flag;
    }

    public void cargarMensajes() {
        lblNombreUsr.setText(usuarioDestino.getNombre());
        ListaSimple<Mensaje> listaMensajes = Controller.getRed().obtenerMensajesUsuarios(usuario, usuarioDestino);
        cambiarVisibilidadInfoUser(true);
        gridPaneMensajes.getChildren().clear();
        if (listaMensajes.getSize() > 0) lblMensajeM.setVisible(false);
        else lblMensajeM.setVisible(true);
        String ruta = "", rutaPropia = "views/user/MessageOwnerPane.fxml", rutaOtro = "views/user/MessageSendPane.fxml";
        for (int i = 1; i <= listaMensajes.getSize(); i++) {
            try {
                Mensaje aux = listaMensajes.get(i - 1);
                if (aux.getUsuario().equals(usuario)) {
                    ruta = rutaPropia;
                } else {
                    ruta = rutaOtro;
                }
                FXMLLoader loader = new FXMLLoader();
                loader.setLocation(RedSocialApplication.class.getResource(ruta));
                Pane pane = loader.load();

                MessagePaneController paneContact = loader.getController();
                paneContact.setController(Controller);

                paneContact.cargarMensaje(aux.getContenido());

                gridPaneMensajes.add(pane, 0, i);
                GridPane.setMargin(pane, new Insets(0, 0, 0, 0));
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public void cargarcontactos() {
        ArrayList<Usuario> listaArray = new ArrayList<Usuario>();
        ListaSimple<Usuario> listaContactos = Controller.getRed().obtenerContactos(usuario);
        if (listaContactos.getSize() > 0) {
            lblMensajeC.setVisible(false);
        }
        for (int i = 0; i < listaContactos.getSize(); i++) {
            listaArray.add(listaContactos.get(i));
        }
        ObservableList<Usuario> listaO = FXCollections.observableArrayList(listaArray);
        FilteredList<Usuario> filteredData = new FilteredList<>(listaO, p -> true);
        listacontactos.setItems(filteredData);

        listacontactos.getSelectionModel().selectedItemProperty().addListener((options, oldValue, newValue) -> {
                    Usuario contacto = listacontactos.getSelectionModel().getSelectedItem();
                    if (contacto != null) {
                        setUsuarioDestino(contacto);
                        txtMensaje.setEditable(true);
                    }
                }
        );

        txtBuscarContacto.textProperty().addListener((prop, old, text) -> {
            filteredData.setPredicate(contact -> {
                if (text == null || text.isEmpty()) return true;
                String nombre = contact.getNombre() + "";
                String name = nombre.toLowerCase();
                return name.contains(text.toLowerCase());
            });
        });
    }

    public void inicializarDatos(ModelFactoryController controller, Stage stage, Usuario usuario) {
        this.Controller = controller;
        this.stage = stage;
        this.usuario = usuario;
        cargarcontactos();
        Controller.getVentanaUsuario().selectMessages();
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

    public Usuario getUsuarioDestino() {
        return usuarioDestino;
    }

    public void setUsuarioDestino(Usuario usuarioDestino) {
        this.usuarioDestino = usuarioDestino;
        listacontactos.getSelectionModel().select(usuarioDestino);
        cargarMensajes();
    }

}
