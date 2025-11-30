package org.secureaccess.app.secureaccessfrontend.controllers.users;

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

public class RegistroPoliciaController {

    @FXML
    private TextField nombrePolicia;
    @FXML
    private TextField primerApellidoPolicia;
    @FXML
    private TextField segundoApellidoPolicia;
    @FXML
    private TextField correoPolicia;
    @FXML
    private TextField usuarioPolicia;
    @FXML
    private PasswordField clavePolicia;

    private final AutenticacionDeServicio autenticacionDeServicio = new AutenticacionDeServicio();

    private Usuario administradorActual;

    public void setAdministradorActual(Usuario administrador) {
        this.administradorActual = administrador;
    }

    @FXML
    private void guardarPolicia(ActionEvent event) {

        String nombre = nombrePolicia.getText();
        String primerApellido = primerApellidoPolicia.getText();
        String segundoApellido = segundoApellidoPolicia.getText();
        String correo = correoPolicia.getText();
        String usuario = usuarioPolicia.getText();
        String clave = clavePolicia.getText();

        boolean camposListos = nombre.isEmpty() || primerApellido.isEmpty() || segundoApellido.isEmpty()
                || correo.isEmpty() || usuario.isEmpty() || clave.isEmpty();

        if (camposListos) {
            Alerta.mostrar("Campos Vacíos", "Por favor llene todos los campos");
            return;
        }

        Usuario nuevoPolicia = autenticacionDeServicio.registro(
                nombre,
                primerApellido,
                segundoApellido,
                correo,
                usuario,
                clave,
                2
        );

        if (nuevoPolicia != null) {
            Alerta.mostrar("Éxito", "Policia " + nuevoPolicia.getApellidosCompleto() + " registrado correctamente");
            limpiandoCampos();
        } else {
            Alerta.mostrar("Error", "No se proceso el registro. El usuario ya existe");
        }
    }

    @FXML
    private void regresarAlMenuAdmin(ActionEvent event) throws IOException {
        cambioDeEscena(event, "/ui/dashboard/menu-administrador.fxml",
                "Menu Administrador", this.administradorActual);
    }

    private void limpiandoCampos() {
        nombrePolicia.clear();
        primerApellidoPolicia.clear();
        segundoApellidoPolicia.clear();
        correoPolicia.clear();
        clavePolicia.clear();
    }

    private void cambioDeEscena(ActionEvent event, String fxmlRuta,
                                String titulo, Usuario usuarioIniciado) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource(fxmlRuta));
        Parent root = loader.load();

        boolean asegurandoMenu = usuarioIniciado != null &&
                loader.getController() instanceof MenuAdministradorController;

        if (asegurandoMenu) {
            MenuAdministradorController controller = loader.getController();
            controller.iniciarDatos(usuarioIniciado);
        }

        Scene escena = new Scene(root);
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setTitle(titulo);
        stage.setScene(escena);
        stage.show();
    }
}
