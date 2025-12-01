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
import org.secureaccess.app.secureaccessbackend.email.GeneradorCodigos;
import org.secureaccess.app.secureaccessbackend.email.ServicioEmail;
import org.secureaccess.app.secureaccessbackend.modelos.Usuario;
import org.secureaccess.app.secureaccessbackend.servicios.AutenticacionDeServicio;
import org.secureaccess.app.secureaccessfrontend.controllers.dashboard.MenuAdministradorController;
import org.secureaccess.app.secureaccessfrontend.util.Alerta;
import org.secureaccess.app.secureaccessfrontend.util.GestorServicios;

import java.io.IOException;
import java.util.Optional;

public class LoginAdministradorController {

    @FXML
    TextField nombreUsuarioAdministrador;
    @FXML
    PasswordField claveUsuarioAdministrador;

    private final AutenticacionDeServicio auth = new AutenticacionDeServicio();

    @FXML
    public void regresarDelLoginAdministrador(ActionEvent event) throws IOException {

        cambioDeEscena(event, "/ui/auth/seleccion-admin.fxml", "SecureAccess - Elección", null);
    }

    @FXML
    public void ingresarUsuarioAdministrador(ActionEvent event) {
        String usuario = nombreUsuarioAdministrador.getText();
        String clave = claveUsuarioAdministrador.getText();

        if (usuario.isEmpty() || clave.isEmpty()) {
            Alerta.mostrar("Campos vacíos", "Por favor, ingrese usuario y contraseña");
            return;
        }

        Optional<Usuario> resultado = auth.iniciarSesion(usuario, clave);

        if (resultado.isPresent()) {

            Usuario admin = resultado.get();

            if (admin.getRolId() == 1) {

                if (admin.getCorreo() == null || admin.getCorreo().isEmpty()) {
                    Alerta.mostrar("Error", "El administrador no tiene el correo  configurado");
                    return;
                }

                boolean enviado = GestorServicios.getServicioEmail()
                        .enviarCodigoVerificacion(admin.getCorreo(), admin.getPrimerNombre());

                if (enviado) {

                    try {
                        FXMLLoader loader = new FXMLLoader(getClass().getResource("/ui/auth/verificacion-admin.fxml"));
                        Parent root = loader.load();

                        VerificacionAdminController controller = loader.getController();
                        controller.setAdminPendiente(admin);

                        Scene escena = new Scene(root);
                        Stage stage = (Stage) (((Node) event.getSource())).getScene().getWindow();
                        stage.setTitle("Verificación Requerida");
                        stage.setScene(escena);
                        stage.show();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                } else {
                    Alerta.mostrar("Error de conexión", "No se pudo enviar el correo de verificación");
                }

            }
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
