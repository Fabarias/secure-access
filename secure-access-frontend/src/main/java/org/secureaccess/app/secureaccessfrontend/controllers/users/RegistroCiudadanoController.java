package org.secureaccess.app.secureaccessfrontend.controllers.users;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import org.secureaccess.app.secureaccessbackend.modelos.Usuario;
import org.secureaccess.app.secureaccessbackend.servicios.AutenticacionDeServicio;
import org.secureaccess.app.secureaccessfrontend.util.Alerta;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class RegistroCiudadanoController implements Initializable {

    @FXML
    private TextField nombreCiudadano;
    @FXML
    private TextField primerApellidoCiudadano;
    @FXML
    private TextField segundoApellidoCiudadano;
    @FXML
    private TextField usuarioCiudadano;
    @FXML
    private TextField usuarioCorreo;
    @FXML
    private PasswordField claveCiudadano;

    private final AutenticacionDeServicio auth = new AutenticacionDeServicio();

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        usuarioCiudadano.setEditable(false);
        usuarioCiudadano.setFocusTraversable(false);

        nombreCiudadano.focusedProperty().addListener((obs,
                                                       oldVal,
                                                       newVal) -> {
            if (!newVal) intentarGenerarUsuario();
        });

        primerApellidoCiudadano.focusedProperty().addListener((obs,
                oldVal,
                newVal) -> {
            if (!newVal) intentarGenerarUsuario();
        });
    }

    @FXML
    private void registrarCiudadano(ActionEvent event) {

        String nombre = nombreCiudadano.getText();
        String apellido = primerApellidoCiudadano.getText();
        String segundoApellido = segundoApellidoCiudadano.getText();
        String correo = usuarioCorreo.getText();
        String nombreDeUsuario = usuarioCiudadano.getText();
        String claveUsuario = claveCiudadano.getText();

        boolean camposListos = nombre.isEmpty() || apellido.isEmpty() || segundoApellido.isEmpty()
                || correo.isEmpty() || claveUsuario.isEmpty();

        if (camposListos) {
            Alerta.mostrar("Campos Vacíos",
                    "Por favor llene todos los campos");
            return;
        }

        Usuario nuevoCiudadano = auth.registro(nombre,
                apellido,
                segundoApellido,
                correo,
                nombreDeUsuario,
                claveUsuario,
                3);

        if (nuevoCiudadano != null) {
            Alerta.mostrar("Éxito",
                    "Ciudadano " + nuevoCiudadano.getApellidosCompleto() + " registrado correctamente.");
            limpiandoCampos();
        } else {
            Alerta.mostrar("Error",
                    "No se proceso el registro. El usuario ya existe.");
        }
    }

    @FXML
    private void regresarAlLoginCiudadano(ActionEvent event) throws IOException {
        cambioDeEscena(event, "/ui/auth/login-ciudadano.fxml", "Login Ciudadano");
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
        primerApellidoCiudadano.clear();
        segundoApellidoCiudadano.clear();
        usuarioCorreo.clear();
        usuarioCiudadano.clear();
        claveCiudadano.clear();
    }

    private void intentarGenerarUsuario() {
        String nombre = nombreCiudadano.getText();
        String apellido = primerApellidoCiudadano.getText();

        if (!nombre.isEmpty() && !apellido.isEmpty()) {
            String usuarioGenerado = auth.generarNombreUsuarioUnico(nombre, apellido);
            usuarioCiudadano.setText(usuarioGenerado);
        }
    }
}
