package co.redSocial;

import co.redSocial.controller.ModelFactoryController;
import javafx.application.Application;
import javafx.stage.Stage;

public class RedSocialApplication extends Application {

    ModelFactoryController modelFactoryController;

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage stage) {
        modelFactoryController = ModelFactoryController.getInstance();
        modelFactoryController.setStage(stage);
        modelFactoryController.cargarRedNueva();
//		modelFactoryController.cargarDatos();
        modelFactoryController.openPrincipalView(true);

    }

}
