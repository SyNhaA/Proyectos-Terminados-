package co.redSocial.views;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import org.controlsfx.control.textfield.TextFields;
import com.jfoenix.controls.JFXToggleButton;
import animatefx.animation.Pulse;
import animatefx.animation.SlideInRight;
import animatefx.animation.ZoomOutDown;
import co.redSocial.RedSocialApplication;
import co.redSocial.controller.ModelFactoryController;
import co.redSocial.estructuras.ListaSimple;
import co.redSocial.exceptions.CampoVacioException;
import co.redSocial.exceptions.UsuarioExistenteException;
import co.redSocial.model.Usuario;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.util.Duration;

public class PrincipalViewController implements Initializable {
    //--------------------------------------------------------------------------------------------------------------
    //                                                 ATRIBUTOS
    //--------------------------------------------------------------------------------------------------------------
    private ModelFactoryController Controller = ModelFactoryController.getInstance();
    private Stage stage;
    private boolean flagMsg;
    @FXML
    private Label lblInfoUsr;
    @FXML
    private GridPane gridPaneUsuarios;
    @FXML
    private TextField txtNombre;
    @FXML
    private Label lblMsgBievenida;
    @FXML
    private ImageView imgLogo;
    @FXML
    private JFXToggleButton tggBtnPersistencia;
    @FXML
    private VBox content;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        txtNombre.setText(null);
        cambiarTextField();
        tggBtnPersistencia.selectedProperty().addListener((options, oldValue, newValue) -> {
            if (tggBtnPersistencia.isSelected()) {
                Controller.cargarDatos();
                if (Controller.isRedEmpty()) {
                    if (flagMsg) crearNotificacion(1);
                } else {
                    if (flagMsg) crearNotificacion(0);
                    cargarUsuarios();
                }
            } else {
                Controller.cargarRedNueva();
                Controller.openPrincipalView(true);
            }
            Controller.setFlagPersistencia(tggBtnPersistencia.isSelected());
        });
    }

    public void cambiarTextField() {
        TextField newTxt = TextFields.createClearableTextField();
        newTxt.setPromptText(txtNombre.getPromptText());
        newTxt.setPrefSize(txtNombre.getPrefWidth(), txtNombre.getPrefHeight());
        content.getChildren().remove(txtNombre);
        content.getChildren().add(newTxt);
        txtNombre = newTxt;
    }


    public void crearNotificacion(int opcion) {
        String texto = "Se ha cargado la información guardada previamente";
        if (opcion == 1) texto = "No se encontró información guardada previamente";
        Controller.openNotification("Información cargada", texto, opcion);
    }

    public void animacion() {
        new SlideInRight(lblMsgBievenida).play();
        Timeline timeline = new Timeline(new KeyFrame(Duration.millis(900), ev -> {
            new Pulse(imgLogo).setCycleCount(2).play();

        }));
        timeline.play();
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
                GridPane.setHalignment(pane, HPos.CENTER);
                GridPane.setMargin(pane, new Insets(3));
            } catch (IOException e) {
                e.printStackTrace();
            }

        }
    }

    @FXML
    public void abrirViewEstadisticas() {
        Controller.openStatisticsView();
    }

    @FXML
    public void crearUsuario() {
        if (validarNombre()) {
            Controller.getRed().agregarUsuario(txtNombre.getText().trim());
            Controller.guardarDatos();
            cargarUsuarios();
            txtNombre.setText(null);
        }
    }

    private boolean validarNombre() {
        lblInfoUsr.setVisible(false);
        boolean flag = true;
        try{
        	if(txtNombre.getText() == null || txtNombre.getText().trim().length() == 0) {
                flag = false;
                lblInfoUsr.setVisible(true);
                lblInfoUsr.setText("*Debes llenar el campo con tu nombre");
                throw new CampoVacioException("registrarse");
            }else {
            	if (Controller.getRed().verificarUsuarioExistente(txtNombre.getText().trim())) {
                    flag = false;
                    lblInfoUsr.setVisible(true);
                    lblInfoUsr.setText("*Nombre de usuario ya registrado");
                    throw new UsuarioExistenteException(txtNombre.getText().trim());
                }
            }
        }catch(CampoVacioException | UsuarioExistenteException e){
        	System.out.println(e);
        }
        return flag;
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

    public void inicializaDatos(ModelFactoryController controller, Stage stage, boolean flag) {
        this.Controller = controller;
        this.stage = stage;
        this.flagMsg = flag;
        cargarUsuarios();
        if (Controller.isFlagPersistencia()) {
            tggBtnPersistencia.setSelected(true);
        }
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
