package org.secureaccess.app.secureaccessfrontend.controllers.users;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import org.secureaccess.app.secureaccessbackend.modelos.Usuario;
import org.secureaccess.app.secureaccessbackend.servicios.AutenticacionDeServicio;

import java.io.IOException;

public class RegistroCiudadanoController {

    @FXML
    private TextField nombreCiudadano;
    @FXML
    private TextField apellidoCiudadano;
    @FXML
    private TextField usuarioCiudadano;
    @FXML
    private PasswordField claveCiudadano;

    private final AutenticacionDeServicio auth = new AutenticacionDeServicio();

    @FXML
    private void registrarCiudadano(ActionEvent event) {

        String nombre = nombreCiudadano.getText();
        String apellido = apellidoCiudadano.getText();
        String nombreDeUsuario = usuarioCiudadano.getText();
        String claveUsuario = claveCiudadano.getText();

        boolean camposListos = nombre.isEmpty() || apellido.isEmpty()
                || nombreDeUsuario.isEmpty() || claveUsuario.isEmpty();

        if (camposListos) {
            mostrarAlerta(Alert.AlertType.WARNING, "Campos Vacíos",
                    "Por favor llene todos los campos");
            return;
        }

        Usuario nuevoCiudadano = auth.registro(nombre, apellido, nombreDeUsuario, claveUsuario, 3);

        if (nuevoCiudadano != null) {
            mostrarAlerta(Alert.AlertType.INFORMATION, "Éxito",
                    "Ciudadana " + apellido + " registrado correctamente.");
            limpiandoCampos();
        } else {
            mostrarAlerta(Alert.AlertType.ERROR, "Error",
                    "No se proceso el registro. El usuario ya existe.");
        }
    }

    @FXML
    private void regresarAlLoginCiudadano(ActionEvent event) throws IOException {
        cambioDeEscena(event, "/ui/auth/login-ciudadano.fxml", "Login Ciudadano");
    }

    private void mostrarAlerta(Alert.AlertType tipo, String titulo, String mensaje) {
        Alert alert = new Alert(tipo);
        alert.setHeaderText(titulo);
        alert.setContentText(mensaje);
        alert.showAndWait();
    }

    private void cambioDeEscena(ActionEvent event, String fxmlRuta,
                                String titulo) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource(fxmlRuta));
        Parent root = loader.load();

        Scene escena = new Scene(root);
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setTitle(titulo);
        stage.setScene(escena);
        stage.show();
    }

    private void limpiandoCampos() {
        nombreCiudadano.clear();
        apellidoCiudadano.clear();
        usuarioCiudadano.clear();
        claveCiudadano.clear();
    }
}
