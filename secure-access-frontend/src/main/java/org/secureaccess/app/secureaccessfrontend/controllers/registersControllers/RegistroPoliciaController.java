package org.secureaccess.app.secureaccessfrontend.controllers.registersControllers;

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
import org.secureaccess.app.secureaccessfrontend.controllers.menuControllers.OpcionesDelMenuAdministradorController;

import java.io.IOException;

public class RegistroPoliciaController {

    @FXML
    private TextField nombrePolicia;
    @FXML
    private TextField apellidoPolicia;
    @FXML
    private TextField usuarioPolicia;
    @FXML
    private PasswordField clavePolicia;

    private final AutenticacionDeServicio autenticacionDeServicio = new AutenticacionDeServicio();

    @FXML
    private void guardarPolicia (ActionEvent event) {

        String nombre = nombrePolicia.getText();
        String apellido = apellidoPolicia.getText();
        String usuario = usuarioPolicia.getText();
        String clave = clavePolicia.getText();

        boolean camposListos = nombre.isEmpty() || apellido.isEmpty()
                || usuario.isEmpty() || clave.isEmpty();

        if (camposListos) {
            mostrarAlerta(Alert.AlertType.WARNING, "Campos Vacíos",
                    "Por favor llene todos los campos");
            return;
        }

        Usuario nuevoPolicia = autenticacionDeServicio.registro(nombre, apellido, usuario, clave, 2);

        if (nuevoPolicia != null) {
            mostrarAlerta(Alert.AlertType.INFORMATION, "Éxito",
                    "Policia " + apellido + " registrado correctamente.");
            limpiandoCampos();
        } else {
            mostrarAlerta(Alert.AlertType.ERROR, "Error",
                    "No se proceso el registro. El usuario ya existe.");
        }
    }

    @FXML
    private void regresarAlMenuAdmin(ActionEvent event) throws IOException {
        cambioDeEscena(event, "/ui/menu/OpcionesDeMenuAdministradorView.fxml",
                "Menu Administrador", null);
    }


    private void limpiandoCampos() {
        nombrePolicia.clear();
        apellidoPolicia.clear();
        usuarioPolicia.clear();
        clavePolicia.clear();
    }

    private void mostrarAlerta(Alert.AlertType tipo, String titulo, String mensaje) {
        Alert alert = new Alert(tipo);
        alert.setHeaderText(titulo);
        alert.setContentText(mensaje);
        alert.showAndWait();
    }

    private void cambioDeEscena(ActionEvent event, String fxmlRuta,
                                String titulo, Usuario usuarioIniciado) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource(fxmlRuta));
        Parent root = loader.load();

        Scene escena = new Scene(root);
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setTitle(titulo);
        stage.setScene(escena);
        stage.show();
    }
}
