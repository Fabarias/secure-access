package org.secureaccess.app.secureaccessfrontend.util;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.paint.Color;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import org.secureaccess.app.secureaccessfrontend.controllers.components.CustomAlertController;

import java.io.IOException;

public class Alerta {

    public static void mostrar(String titulo, String mensaje) {
        try {
            FXMLLoader loader = new FXMLLoader(Alerta.class.getResource("/ui/components/customAlert.fxml"));
            Parent root = loader.load();

            CustomAlertController controller = loader.getController();
            controller.setContenido(titulo, mensaje);

            Stage alertStage = new Stage();

            alertStage.initStyle(StageStyle.TRANSPARENT);

            alertStage.initModality(Modality.APPLICATION_MODAL);

            Scene scene = new Scene(root);
            scene.setFill(Color.TRANSPARENT);

            alertStage.setScene(scene);
            alertStage.showAndWait();

        } catch (IOException e) {
            e.printStackTrace();
            System.err.println("Error crítico al intentar mostrar la alerta personalizada.");
        }
    }
}
