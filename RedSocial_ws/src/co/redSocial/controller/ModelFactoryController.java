package co.redSocial.controller;

import java.io.IOException;
import java.io.Serializable;

import org.controlsfx.control.Notifications;
import animatefx.animation.BounceInDown;
import animatefx.animation.ZoomIn;
import co.redSocial.RedSocialApplication;
import co.redSocial.model.Publicacion;
import co.redSocial.model.RedSocial;
import co.redSocial.model.Usuario;
import co.redSocial.persistencia.Persistencia;
import co.redSocial.services.IModelFactoryService;
import co.redSocial.views.PrincipalViewController;
import co.redSocial.views.StatisticsViewController;
import co.redSocial.views.resource.AlertViewController;
import co.redSocial.views.user.CommentsViewController;
import co.redSocial.views.user.ContactsViewController;
import co.redSocial.views.user.LikesViewController;
import co.redSocial.views.user.MessagesViewController;
import co.redSocial.views.user.NotificationsViewController;
import co.redSocial.views.user.UserViewController;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.util.Duration;

/**
 * Autores
 * <p>
 * Michelle Alejandra Gonz�lez Hern�ndez
 * Tatiana Cubillos Montes
 * Sebasti�n Delgado Cardenas
 */
public class ModelFactoryController implements IModelFactoryService, Serializable {

    private static final long serialVersionUID = 1L;
    private static Stage stage;
    private static Stage stage2;
    private static Stage stage3;
    private Image imagenError;
    private Image imagenInfo;
    private double xOffset = 0;
    private double yOffset = 0;
    private RedSocial red;
    private boolean flagPersistencia;

    // Clase estatica oculta. Tan solo se instanciara el singleton una vez
    private static class SingletonHolder {
        private final static ModelFactoryController eINSTANCE = new ModelFactoryController();
    }

    //M�todo para obtener la instancia de nuestra clase
    public static ModelFactoryController getInstance() {
        return SingletonHolder.eINSTANCE;
    }

    //--------------------------------------------------------------------------------------------------------------------------
    // ABRIR VENTANAS
    //--------------------------------------------------------------------------------------------------------------------------

    /**
     * Ventanana principal
     *
     * @param flag TODO
     */
    public void openPrincipalView(boolean flag) {
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(RedSocialApplication.class.getResource("views/PrincipalView.fxml"));
            Parent root = loader.load();
            Scene scene = new Scene(root);
            scene.setFill(Color.TRANSPARENT);
            stage.setScene(scene);

            movilizarStage(root);
            new ZoomIn(root).setSpeed(2).play();
            PrincipalViewController ventana = loader.getController();
            ventana = loader.getController();
            ventana.inicializaDatos(this, stage, flag);
            stage.show();
            ventana.animacion();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Ventanana estad�sticas
     */
    public void openStatisticsView() {
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(RedSocialApplication.class.getResource("views/StatisticsView.fxml"));
            Parent root = loader.load();
            Scene scene = new Scene(root);
            scene.setFill(Color.TRANSPARENT);
            stage.setScene(scene);
            movilizarStage(root);
            new ZoomIn(root).setSpeed(2).play();
            StatisticsViewController ventana = loader.getController();
            ventana = loader.getController();
            ventana.inicializarDatos(this, stage);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private UserViewController ventanaUsuario;

    /**
     * Ventanana usuario
     *
     * @param flag  TODO
     * @param flagC TODO
     */
    public void openUserView(Usuario usuario, boolean flag, boolean flagC) {
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(RedSocialApplication.class.getResource("views/user/UserView.fxml"));
            Parent root = loader.load();
            Scene scene = new Scene(root);
            scene.setFill(Color.TRANSPARENT);
            stage.setScene(scene);
            movilizarStage(root);
            ventanaUsuario = loader.getController();
            ventanaUsuario = loader.getController();
            ventanaUsuario.inicializarDatos(this, stage, usuario);
            if (flag) new ZoomIn(root).setSpeed(2).play();
            else if (!flagC) new BounceInDown(ventanaUsuario.getPanePublicaciones()).setSpeed(3).play();
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Ventana mensajes
     *
     * @param contacto
     * @param flag
     */
    public void openMessagesView(Pane parent, Usuario usuario, Usuario contacto, boolean flag) {
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(RedSocialApplication.class.getResource("views/user/MessagesView.fxml"));
            Parent panel = loader.load();
            MessagesViewController ventana = loader.getController();
            ventana.inicializarDatos(this, stage, usuario);
            if (contacto != null) ventana.setUsuarioDestino(contacto);
            if (flag) new BounceInDown(panel).setSpeed(3).play();
            parent.getChildren().removeAll();
            parent.getChildren().setAll(panel);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Ventana contactos
     *
     * @param flag
     */
    public void openContactsView(Pane parent, Usuario usuario, boolean flag) {
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(RedSocialApplication.class.getResource("views/user/ContactsView.fxml"));
            Parent panel = loader.load();
            ContactsViewController ventana = loader.getController();
            ventana.inicializarDatos(this, stage, parent, usuario);
            if (flag) new BounceInDown(panel).setSpeed(3).play();
            parent.getChildren().removeAll();
            parent.getChildren().setAll(panel);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Ventana notificaciones
     *
     * @param usuario
     * @param flag
     */
    public void openNotificationsView(Pane parent, Usuario usuario, boolean flag) {
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(RedSocialApplication.class.getResource("views/user/NotificationsView.fxml"));
            Parent panel = loader.load();
            NotificationsViewController ventana = loader.getController();
            ventana.inicializarDatos(this, stage, parent, usuario);
            if (flag) new BounceInDown(panel).setSpeed(3).play();
            parent.getChildren().removeAll();
            parent.getChildren().setAll(panel);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Ventana comentarios
     *
     * @param publicacion
     * @param usuario
     */
    public void openCommentsView(Publicacion publicacion, Usuario usuario) {
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(RedSocialApplication.class.getResource("views/user/CommentsView.fxml"));
            Parent root = loader.load();
            Scene scene = new Scene(root);
            scene.setFill(Color.TRANSPARENT);
            stage2.setScene(scene);
            new ZoomIn(root).setSpeed(3).play();
            CommentsViewController ventana = loader.getController();
            ventana = loader.getController();
            ventana.inicializarDatos(this, stage2, publicacion, usuario);
            stage2.showAndWait();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Ventana me gustas
     *
     * @param publicacion
     * @param usuario
     */
    public void openLikesView(Publicacion publicacion, Usuario usuario) {
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(RedSocialApplication.class.getResource("views/user/LikesView.fxml"));
            Parent root = loader.load();
            Scene scene = new Scene(root);
            scene.setFill(Color.TRANSPARENT);
            stage2.setScene(scene);
            new ZoomIn(root).setSpeed(3).play();
            LikesViewController ventana = loader.getController();
            ventana = loader.getController();
            ventana.inicializarDatos(this, stage2, publicacion, usuario);
            stage2.showAndWait();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void lanzarAlerta(String titulo, String contenido, int opcion) {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(RedSocialApplication.class.getResource("views/resource/AlertView.fxml"));
        try {
            Parent root = loader.load();
            Scene scene = new Scene(root);
            scene.setFill(Color.TRANSPARENT);
            stage3.setScene(scene);
            stage3.show();
            AlertViewController ventana = loader.getController();
            ventana = loader.getController();
            ventana.setStage(stage3);
            if (opcion == 1) ventana.cargarContenido(titulo, contenido, imagenError);
            else ventana.cargarContenido(titulo, contenido, imagenInfo);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void openNotification(String title, String content, int option) {
        String css = this.getClass().getResource("../../../resources/style_sheet.css").toExternalForm();
        stage.getScene().getStylesheets().add(0, css);
        String img = "/images/icon_happy.png";
        if (option == 1) img = "/images/icon_sad.png";
        else {
            if (option == 2) img = "/images/icon_withoutmouth.png";
        }
        Notifications not = Notifications.create()
                .title(title)
                .text(content)
                .graphic(new ImageView(img))
                .hideAfter(Duration.seconds(3))
                .position(Pos.TOP_CENTER);
        not.owner(stage).show();
    }

    public void movilizarStage(Parent root) {
        root.setOnMousePressed(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                xOffset = event.getSceneX();
                yOffset = event.getSceneY();
            }
        });
        root.setOnMouseDragged(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                stage.setX(event.getScreenX() - xOffset);
                stage.setY(event.getScreenY() - yOffset);
            }

        });
    }

    //-------------------------------------------------------------------------------------------------------------------------
    // OTROS M�TODOS
    //-------------------------------------------------------------------------------------------------------------------------

    public Stage getStage() {
        return stage;
    }

    @Override
    public void setStage(Stage stage) {
        ModelFactoryController.stage = stage;
        stage.initStyle(StageStyle.TRANSPARENT);
        Image ico = new Image("images/icon.png");
        stage.getIcons().add(ico);
        stage2 = new Stage();
        stage2.initStyle(StageStyle.TRANSPARENT);
        stage2.initOwner(stage);
        stage2.initModality(Modality.APPLICATION_MODAL);
        stage3 = new Stage();
        stage3.initStyle(StageStyle.TRANSPARENT);
        stage3.initModality(Modality.APPLICATION_MODAL);
        imagenError = new Image(RedSocialApplication.class.getResourceAsStream("/images/icon_error.png"));
        imagenInfo = new Image(RedSocialApplication.class.getResourceAsStream("/images/icon_info.png"));
    }

    /**
     * Carga el objeto con sus datos de un archivo binario
     */
    @Override
    public void cargarDatos() {
        red = Persistencia.cargarRedSocialBinario();
        if (red == null) {
            cargarRedNueva();
        }
    }

    /**
     * Guarda el objeto con sus datos en un archivo binario
     */
    @Override
    public void guardarDatos() {
        if (isFlagPersistencia()) {
            Persistencia.guardarRedSocialBinario(red);
        }
    }

    public void cargarRedNueva() {
        setFlagPersistencia(false);
        red = new RedSocial("Red");
    }

    public boolean isRedEmpty() {
        return !(red.obtenerCantidadUsuarios() > 0);
    }

    @Override
    public RedSocial getRed() {
        return red;
    }

    public UserViewController getVentanaUsuario() {
        return ventanaUsuario;
    }

    public void setVentanaUsuario(UserViewController ventanaUsuario) {
        this.ventanaUsuario = ventanaUsuario;
    }

    public boolean isFlagPersistencia() {
        return flagPersistencia;
    }

    public void setFlagPersistencia(boolean flagPersistencia) {
        this.flagPersistencia = flagPersistencia;
    }

    public void setRed(RedSocial red) {
        this.red = red;
    }

    public ModelFactoryController() {
    }
}
