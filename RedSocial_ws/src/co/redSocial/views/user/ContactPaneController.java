package co.redSocial.views.user;

import java.io.IOException;

import co.redSocial.controller.ModelFactoryController;
import co.redSocial.model.Usuario;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.Pane;

public class ContactPaneController {
    //--------------------------------------------------------------------------------------------------------------
    //                                                 ATRIBUTOS
    //--------------------------------------------------------------------------------------------------------------
    private ModelFactoryController Controller = ModelFactoryController.getInstance();
    private Usuario usuario;
    private Usuario contacto;
    @FXML
    private Label lblNombreContacto;
    @FXML
    private Button boton;
    @FXML
    private Pane parent;

    @FXML
    public void accionBoton() throws IOException {
        if (boton.getText().equals("Enviar mensaje")) {
            Controller.openMessagesView(parent, usuario, contacto, true);
        } else {
            Controller.getRed().conectarUsuarios(usuario, contacto);
            Controller.guardarDatos();
            Controller.openContactsView(parent, usuario, false);
        }
    }

    public void cargarNoContacto(Usuario usr, Usuario cnto) {
        boton.setText("Agregar");
        this.usuario = usr;
        this.contacto = cnto;
        lblNombreContacto.setText(contacto.getNombre());
    }

    public void inicializarDatosContacto(ModelFactoryController controller, Pane parent, Usuario usr, Usuario cnto) {
        this.Controller = controller;
        this.parent = parent;
        boton.setText("Enviar mensaje");
        this.usuario = usr;
        this.contacto = cnto;
        lblNombreContacto.setText(contacto.getNombre());
    }

    public void inicializarDatosNoContacto(ModelFactoryController controller, Pane parent, Usuario usr, Usuario cnto) {
        this.Controller = controller;
        this.parent = parent;
        boton.setText("Agregar");
        this.usuario = usr;
        this.contacto = cnto;
        lblNombreContacto.setText(contacto.getNombre());
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    public Usuario getContacto() {
        return contacto;
    }

    public void setContacto(Usuario contacto) {
        this.contacto = contacto;
    }

    public ModelFactoryController getController() {
        return Controller;
    }

    public void setController(ModelFactoryController controller) {
        Controller = controller;
    }

    public Pane getParent() {
        return parent;
    }

    public void setParent(Pane parent) {
        this.parent = parent;
    }

}
