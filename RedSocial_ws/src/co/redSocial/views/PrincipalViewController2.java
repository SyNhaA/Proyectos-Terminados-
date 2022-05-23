package co.redSocial.views;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.SortedSet;
import java.util.TreeSet;

import javafx.scene.control.MenuItem;
import animatefx.animation.ZoomOutDown;
import co.redSocial.RedSocialApplication;
import co.redSocial.controller.ModelFactoryController;
import co.redSocial.estructuras.ListaSimple;
import co.redSocial.model.Usuario;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import javafx.util.Duration;
import resources.AutoCompleteTextField;

public class PrincipalViewController2 implements Initializable {
    //--------------------------------------------------------------------------------------------------------------
    //                                                 ATRIBUTOS
    //--------------------------------------------------------------------------------------------------------------
    private ModelFactoryController Controller = ModelFactoryController.getInstance();
    private Stage stage;
    @FXML
    private GridPane gridPaneUsuarios;
    @FXML
    private TextField txtNombre;
    @FXML
    TextField text;
    @FXML
    HBox h;
    @FXML
    private AnchorPane pane;


    @SuppressWarnings("unchecked")
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        txtNombre.setText(null);

        SortedSet<Usuario> entries = new TreeSet<>((Usuario o1, Usuario o2) -> o1.getNombre().compareTo(o2.getNombre()));

        entries.add(new Usuario("Michelle"));
        entries.add(new Usuario("Camila"));

        text = new AutoCompleteTextField<Usuario>(entries);

        ((AutoCompleteTextField<Usuario>) text).getEntryMenu().setOnAction(e ->
        {
            ((MenuItem) e.getTarget()).addEventHandler(Event.ANY, event ->
            {
                if (((AutoCompleteTextField<Usuario>) text).getLastSelectedObject() != null) {
                    text.setText(((AutoCompleteTextField<Usuario>) text).getLastSelectedObject().toString());
                    System.out.println(((AutoCompleteTextField<Usuario>) text).getLastSelectedObject().getNombre());
                }
            });
        });

        text.setPrefWidth(241);
        text.setPrefHeight(33);
        text.setPromptText("Ingrese el nombre de Usuario");
        h.getChildren().add(text);
//		pane.getChildren().add(text);
//		text.setLayoutX(141);
//		text.setLayoutY(25);
    }

    public void cargarUsuarios() {
        ListaSimple<Usuario> listaUsuarios = Controller.getRed().obtenerListaUsuarios();
        for (int i = 1; i < listaUsuarios.getSize() + 1; i++) {
            try {
                FXMLLoader loader = new FXMLLoader();
                loader.setLocation(RedSocialApplication.class.getResource("views/UserPane.fxml"));
                Pane pane = loader.load();

                UserPaneController paneUser = loader.getController();
                paneUser.setController(Controller);
                paneUser.cargarUsuario(listaUsuarios.get(i - 1));

                gridPaneUsuarios.add(pane, 0, i);

                GridPane.setMargin(pane, new Insets(3, 35, 3, 35));
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    @FXML
    public void abrirViewEstadisticas() {

    }

    @FXML
    public void crearUsuario() {
        if (txtNombre.getText() != null && txtNombre.getText() != "") {
            Controller.getRed().agregarUsuario(txtNombre.getText());
            Controller.guardarDatos();
            cargarUsuarios();
            txtNombre.setText(null);
        } else {
            Controller.lanzarAlerta("", "Debes ingresar tu nombre", 1);
        }
    }

    @FXML
    public void minimizar() {
        stage.setIconified(true);
    }

    @FXML
    public void salir() {
        new ZoomOutDown(stage.getScene().getRoot()).setSpeed(3).play();
        Timeline timeline = new Timeline(new KeyFrame(Duration.millis(500), ev -> {
            System.exit(0);
        }));
        timeline.play();
    }

    public void inicializaDatos(ModelFactoryController controller, Stage stage) {
        this.Controller = controller;
        this.stage = stage;
        cargarUsuarios();
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
