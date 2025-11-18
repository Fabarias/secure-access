package org.secureaccess.app.secureaccessfrontend.controllers.loginsControllers;

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
import org.secureaccess.app.secureaccessfrontend.controllers.menuControllers.OpcionesDelMenuPoliciaController;
import java.io.IOException;
import java.util.Optional;

public class LoginPoliciaController {

    @FXML
    private TextField nombreUsuarioPolicia;
    @FXML
    private PasswordField clavePolicia;

    private final AutenticacionDeServicio auth = new AutenticacionDeServicio();

    @FXML
    private void regresarDelLoginPolicia(ActionEvent event) throws IOException {
        cambioDeEscena(event, "/ui/selection/eleccionView.fxml",
                "SecureAccess - Panel", null);
    }

    @FXML
    private void accesoLoginPolicia(ActionEvent event) throws IOException {

        String usuarioPolicia = nombreUsuarioPolicia.getText();
        String contraPolicia = clavePolicia.getText();

        if (usuarioPolicia.isEmpty() || contraPolicia.isEmpty()) {
            mostrarAlerta("Campos vacíos", "Por favor, ingrese usuario y contraseña");
            return;
        }

        Optional<Usuario> resultado = auth.iniciarSesion(usuarioPolicia, contraPolicia);

        if (resultado.isPresent()) {

            Usuario policia = resultado.get();

            if (policia.getRolId() == 2) {
                try {
                    cambioDeEscena(event, "/ui/menu/OpcionesDeMenuPoliciaView.fxml",
                            "Menú Policia", policia);
                } catch (IOException e) {
                    e.printStackTrace();
                    mostrarAlerta("Error", "No se pudo cargar el menú");
                }
            } else {
                mostrarAlerta("Acceso Denegado", "Este usuario no tienes credenciales de un Policia");
            }
        } else {
            mostrarAlerta("Error de Acceso", "Usuario o contraseña incorrectos");
        }
    }

    private void cambioDeEscena(ActionEvent event, String fxmlRuta,
                                String titulo, Usuario usuarioIniciado) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource(fxmlRuta));
        Parent root = loader.load();

        if (usuarioIniciado != null &&
                loader.getController() instanceof OpcionesDelMenuPoliciaController) {
            OpcionesDelMenuPoliciaController controlador = loader.getController();
            controlador.iniciarDatos(usuarioIniciado);
        }

        Scene escena = new Scene(root);
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setTitle(titulo);
        stage.setScene(escena);
        stage.show();
    }

    private void mostrarAlerta(String titulo, String mensaje) {

        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setHeaderText(titulo);
        alert.setContentText(mensaje);
        alert.showAndWait();

    }
}

