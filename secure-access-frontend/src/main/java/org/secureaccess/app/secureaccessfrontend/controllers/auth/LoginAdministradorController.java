package org.secureaccess.app.secureaccessfrontend.controllers.auth;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import org.secureaccess.app.secureaccessbackend.modelos.Usuario;
import org.secureaccess.app.secureaccessbackend.servicios.AutenticacionDeServicio;
import org.secureaccess.app.secureaccessfrontend.controllers.dashboard.MenuAdministradorController;
import org.secureaccess.app.secureaccessfrontend.util.Alerta;

import java.io.IOException;
import java.util.Optional;

public class LoginAdministradorController {

    @FXML
    TextField correoUsuarioAdministrador;

    @FXML
    PasswordField claveUsuarioAdministrador;

    private final AutenticacionDeServicio auth = new AutenticacionDeServicio();

    @FXML
    public void regresarDelLoginAdministrador(ActionEvent event) throws IOException {

        cambioDeEscena(event, "/ui/auth/seleccion-admin.fxml", "SecureAccess - Elección", null);
    }

    @FXML
    public void ingresarUsuarioAdministrador(ActionEvent event) {
        String usuario = correoUsuarioAdministrador.getText();
        String clave = claveUsuarioAdministrador.getText();

        if (usuario.isEmpty() || clave.isEmpty()) {
            Alerta.mostrar("Campos vacíos", "Por favor, ingrese usuario y contraseña");
            return;
        }

        Optional<Usuario> resultado = auth.iniciarSesion(usuario, clave);

        if (resultado.isPresent()) {

            Usuario admin = resultado.get();

            if (admin.getRolId() == 1) {
                try {
                    cambioDeEscena(event, "/ui/auth/verificacion-admin.fxml", "Menú Administrador", admin);
                } catch (IOException e) {
                    e.printStackTrace();
                    Alerta.mostrar("Error", "No se pudo cargar el menú");
                }
            } else {
                Alerta.mostrar("Acceso Denegado", "Este usuario no tienes permisos de Administrador");
            }
        } else {
            Alerta.mostrar("Error de Acceso", "Usuario o contraseña incorrectos");
        }

    }

    private void cambioDeEscena(ActionEvent event, String fxmlRuta,
                                String titulo, Usuario usuarioIniciado) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource(fxmlRuta));
        Parent root = loader.load();

        if (usuarioIniciado != null &&
                loader.getController() instanceof MenuAdministradorController) {
            MenuAdministradorController controlador = loader.getController();
            controlador.iniciarDatos(usuarioIniciado);
        }

        Scene escena = new Scene(root);
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setTitle(titulo);
        stage.setScene(escena);
        stage.show();
    }
}
