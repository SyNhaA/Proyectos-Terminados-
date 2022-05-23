package co.redSocial.views.resource;

import java.net.URL;
import java.util.ResourceBundle;

import co.redSocial.controller.ModelFactoryController;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class AlertViewController implements Initializable {
    //--------------------------------------------------------------------------------------------------------------
    //                                                 ATRIBUTOS
    //--------------------------------------------------------------------------------------------------------------
    private ModelFactoryController Controller = ModelFactoryController.getInstance();
    private Stage stage;
    @FXML
    private Label lblTitle;
    @FXML
    private Text lblContents;
    @FXML
    private ImageView image;

    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }

    public void cargarContenido2(String titulo, String contenido, Image img) {
        Thread hilo_2 = new Thread(() -> {
            Platform.runLater(new Runnable() {
                @Override
                public void run() {
                    System.out.println("Se ve");
                    image.setImage(img);
                    lblTitle.setText(titulo);
                    lblContents.setText(contenido);
                }
            });
        });
        hilo_2.start();
    }

    public void cargarContenido(String titulo, String contenido, Image img) {
        System.out.println("Se ve");
        image.setImage(img);
        lblTitle.setText(titulo);
        lblContents.setText(contenido);
    }

    public void verSpinner() {
        Thread hilo_2 = new Thread(() -> {
            Platform.runLater(new Runnable() {
                @Override
                public void run() {
                    System.out.println("Se ve");
                }
            });
        });
        hilo_2.start();
    }

    @FXML
    public void salir() {
        stage.close();
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
