package org.secureaccess.app.secureaccessfrontend.controllers.eleccionControllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class terceraEleccionController {

    @FXML
    private void regresarAEleccion(ActionEvent event) throws IOException {

        FXMLLoader fxmlSeleccion = new FXMLLoader(getClass().getResource("/ui/selection/eleccionView.fxml"));

        Parent seleccion = fxmlSeleccion.load();

        Scene escenaSeleccion = new Scene(seleccion);
        Stage ventanaActual = (Stage) ((Node) event.getSource()).getScene().getWindow();

        ventanaActual.setTitle("SecureAccess - Elección");
        ventanaActual.setScene(escenaSeleccion);
        ventanaActual.show();
    }

    @FXML
    private void accesoLoginAdministrador(ActionEvent event) throws IOException {

        FXMLLoader fxmlSeleccion = new FXMLLoader(getClass().getResource("/ui/logins/loginAdministradorView.fxml"));

        Parent seleccion = fxmlSeleccion.load();

        Scene escenaSeleccion = new Scene(seleccion);
        Stage ventanaActual = (Stage) ((Node) event.getSource()).getScene().getWindow();

        ventanaActual.setTitle("SecureAccess - Elección");
        ventanaActual.setScene(escenaSeleccion);
        ventanaActual.show();
    }
}
