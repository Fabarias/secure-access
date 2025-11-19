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
import org.secureaccess.app.secureaccessfrontend.controllers.menuControllers.OpcionesDelMenuCiudadanoController;
import org.secureaccess.app.secureaccessfrontend.controllers.menuControllers.OpcionesDelMenuPoliciaController;
import org.secureaccess.app.secureaccessfrontend.util.Alerta;

import java.io.IOException;
import java.util.Optional;

public class LoginCiudadanoController {

    @FXML
    private TextField textoUsuario;
    @FXML
    private PasswordField claveCiudadano;

    private final AutenticacionDeServicio auth = new AutenticacionDeServicio();

    @FXML
    private void regresarDelLoginCiudadano(ActionEvent event) throws IOException {

        cambioDeEscena(event, "/ui/selection/eleccionView.fxml",
                 "SecureAccess - Eleccion", null);
    }

    @FXML
    private void accesoLoginCiudadano(ActionEvent event) throws IOException {

        String nombreUsuario = textoUsuario.getText();
        String contraUsuario = claveCiudadano.getText();

        if (nombreUsuario.isEmpty() || contraUsuario.isEmpty()) {
            Alerta.mostrar("Campos vacíos", "Por favor ingrese usuario y contraseña");
            return;
        }

        Optional<Usuario> resultado = auth.iniciarSesion(nombreUsuario, contraUsuario);

        if (resultado.isPresent()) {

            Usuario ciudadano = resultado.get();

            if (ciudadano.getRolId() == 3) {

                try {
                    cambioDeEscena(event, "/ui/menu/OpcionesDeMenuCiudadanoView.fxml",
                            "Menu Ciudadano", ciudadano);
                } catch (IOException e) {
                    e.printStackTrace();
                    Alerta.mostrar("Error", "No se pudo cargar el menu");
                }
            } else {
                Alerta.mostrar("Acceso Denegado", "Usuario o contraseña incorrectos");

            }
        } else {
            Alerta.mostrar("Error de Acceso", "Usuario o contraseña incorrectos");
        }

    }

    @FXML
    private void irACrearCiudadano(ActionEvent event) throws IOException {
        cambioDeEscena(event, "/ui/registers/registroCiudadanoView.fxml",
                "Registro de Ciudadano", null);
    }

    private void cambioDeEscena(ActionEvent event, String fxmlRuta,
                                String titulo, Usuario usuarioIniciado) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource(fxmlRuta));
        Parent root = loader.load();

        if (usuarioIniciado != null &&
                loader.getController() instanceof OpcionesDelMenuCiudadanoController) {
            OpcionesDelMenuCiudadanoController controlador = loader.getController();
            controlador.iniciarDatos(usuarioIniciado);
        }

        Scene escena = new Scene(root);
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setTitle(titulo);
        stage.setScene(escena);
        stage.show();
    }
}
