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

public class LoginAdministradorController {

    @FXML
    TextField nombreUsuarioAdministrador;

    @FXML
    PasswordField claveUsuarioAdministrador;

    @FXML
    public void regresarDelLoginAdministrador(ActionEvent event) throws IOException {

        FXMLLoader fxmlSeleccion = new FXMLLoader(getClass().getResource("/ui/selection/terceraEleccionView.fxml"));

        Parent seleccion = fxmlSeleccion.load();

        Scene escenaSeleccion = new Scene(seleccion);
        Stage ventanaActual = (Stage) ((Node) event.getSource()).getScene().getWindow();

        ventanaActual.setTitle("SecureAccess - Eleccion");
        ventanaActual.setScene(escenaSeleccion);
        ventanaActual.show();
    }

    @FXML
    public void ingresarUsuarioAdministrador(ActionEvent event) {


    }


}
