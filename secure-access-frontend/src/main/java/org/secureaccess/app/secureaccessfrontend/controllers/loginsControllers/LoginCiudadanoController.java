package org.secureaccess.app.secureaccessfrontend.controllers.loginsControllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;

public class LoginCiudadanoController {

    @FXML
    private TextField textoUsuario;
    @FXML
    private PasswordField claveCiudadano;

    @FXML
    private void regresarDelLoginCiudadano(ActionEvent event) throws IOException {

        FXMLLoader fxmlSeleccion = new FXMLLoader(getClass().getResource("/ui/selection/eleccionView.fxml"));

        Parent seleccion = fxmlSeleccion.load();

        Scene escenaSeleccion = new Scene(seleccion);
        Stage ventanaActual = (Stage) ((Node) event.getSource()).getScene().getWindow();

        ventanaActual.setTitle("SecureAccess - Eleccion");
        ventanaActual.setScene(escenaSeleccion);
        ventanaActual.show();
    }

    @FXML
    private void accesoLoginCiudadano(ActionEvent event) throws IOException {

        String nombreUsuario = textoUsuario.getText();
        String contraUsuario = claveCiudadano.getText();

        if (nombreUsuario.equals("Faber") && contraUsuario.equals("Candy")) {

            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/ui/menu/listadoDelincuentes/listadoDelincuentesView.fxml"));
            Parent dashboardCiudadano = fxmlLoader.load();

            Scene escenaDashCiudadano = new Scene(dashboardCiudadano);
            Stage ventanaActual = (Stage) ((Node) event.getSource()).getScene().getWindow();

            ventanaActual.setTitle("Secure Access - Dashboard");
            ventanaActual.setScene(escenaDashCiudadano);
            ventanaActual.show();

        }

    }
}
