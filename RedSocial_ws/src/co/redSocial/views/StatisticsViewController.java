package co.redSocial.views;

import java.util.ArrayList;

import com.jfoenix.controls.JFXDatePicker;
import animatefx.animation.ZoomOutDown;
import co.redSocial.controller.ModelFactoryController;
import co.redSocial.estructuras.ListaSimple;
import co.redSocial.model.Publicacion;
import co.redSocial.model.Usuario;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Label;
import javafx.scene.control.Tooltip;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import javafx.util.Duration;

public class StatisticsViewController {
    //--------------------------------------------------------------------------------------------------------------
    //                                                 ATRIBUTOS
    //--------------------------------------------------------------------------------------------------------------
    private ModelFactoryController Controller = ModelFactoryController.getInstance();
    private Stage stage;
    @FXML
    private Label lblUsuarios, lblPublicaciones, lblMensajes, lblComentarios, lblMegustas;
    @FXML
    private Label lblPublicacion1, lblPublicacion2, lblPublicacion3, lblPublicacion4, lblPublicacion5;
    @FXML
    private Label lblMGPublicacion1, lblMGPublicacion2, lblMGPublicacion3, lblMGPublicacion4, lblMGPublicacion5;
    @FXML
    private BarChart<String, Number> graficaDeBarras;
    @FXML
    private CategoryAxis xAxis;
    @FXML
    private NumberAxis yAxis;
    @FXML
    private JFXDatePicker dtPckrFecha;

    public void inicializarDatos(ModelFactoryController controller, Stage stage) {
        this.Controller = controller;
        this.stage = stage;
        llenarGrafica();
        llenarBar();
        llenarTop5();
        dtPckrFecha.requestFocus();
    }

    public void llenarBar() {
        lblUsuarios.setText(Controller.getRed().obtenerCantidadUsuarios() + "");
        lblPublicaciones.setText(Controller.getRed().obtenerCantidadPublicaciones() + "");
        lblMensajes.setText(Controller.getRed().obtenerCantidadMensajes() + "");
        lblComentarios.setText(Controller.getRed().obtenerCantidadComentarios() + "");
        lblMegustas.setText(Controller.getRed().obtenerCantidadMeGustas() + "");
    }

    public void llenarTop5() {
        llenarTop(lblPublicacion1, lblMGPublicacion1, 0);
        llenarTop(lblPublicacion2, lblMGPublicacion2, 1);
        llenarTop(lblPublicacion3, lblMGPublicacion3, 2);
        llenarTop(lblPublicacion4, lblMGPublicacion4, 3);
        llenarTop(lblPublicacion5, lblMGPublicacion5, 4);
    }

    public void llenarTop(Label lblPublicacion, Label lblMGPublicacion, int p) {
        ListaSimple<Publicacion> listaTop5 = Controller.getRed().obtenerTop5();
        try {
            lblPublicacion.setText(listaTop5.get(p).getNombreProducto());
            lblMGPublicacion.setText(listaTop5.get(p).obtenerCantidadMeGustas() + "");
            ponerAccion(lblPublicacion, listaTop5.get(p));
        } catch (RuntimeException e) {
            System.out.println("Top incompleto");
            lblPublicacion.setText("       ...");
            lblMGPublicacion.setText("-");
        }
    }

    public void ponerAccion(Label label, Publicacion publicacion) {
        final Tooltip tool = new Tooltip();
        tool.setText("Publicación de " + publicacion.getUsuarioDueno().getNombre());
        tool.setFont(new Font("Calibri", 15));
        label.setTooltip(tool);
    }

    public void llenarGrafica() {
        int rango = 5;
        ArrayList<String> listaArray = new ArrayList<String>();
        ListaSimple<Usuario> lista = Controller.getRed().obtenerListaUsuarios();
        for (int i = 0; i < lista.getSize(); i++) {
            listaArray.add(lista.get(i).getNombre().replace(" ", "\n"));
        }
        ObservableList<String> listaO = FXCollections.observableArrayList(listaArray);
        xAxis.setCategories(listaO);
        xAxis.setLabel("Usuarios");
        yAxis.setLabel("Publicaciones");
        yAxis.setUpperBound(rango);
        yAxis.setTickUnit(1);

        XYChart.Series<String, Number> series1 = new XYChart.Series<>();
        for (int i = 0; i < lista.getSize(); i++) {
            Usuario usuario = lista.get(i);
            series1.getData().add(new XYChart.Data<>(usuario.getNombre().replace(" ", "\n"), usuario.obtenerCantidadPublicaciones()));
        }
        graficaDeBarras.getData().add(series1);
    }

    @FXML
    public void salir() {
        new ZoomOutDown(stage.getScene().getRoot()).setSpeed(3).play();
        Timeline timeline = new Timeline(new KeyFrame(Duration.millis(400), ev -> {
            System.exit(0);
        }));
        timeline.play();
    }

    @FXML
    public void volver() {
        Controller.openPrincipalView(false);
    }

    @FXML
    public void minimizar() {
        stage.setIconified(true);
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
