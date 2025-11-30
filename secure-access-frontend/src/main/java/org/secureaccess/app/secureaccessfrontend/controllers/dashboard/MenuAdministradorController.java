package org.secureaccess.app.secureaccessfrontend.controllers.dashboard;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.stage.Stage;
import org.secureaccess.app.secureaccessbackend.modelos.Usuario;
import org.secureaccess.app.secureaccessfrontend.controllers.users.GestionDeUsuariosController;
import org.secureaccess.app.secureaccessfrontend.controllers.users.RegistroPoliciaController;

import java.io.IOException;

public class MenuAdministradorController {

    @FXML
    private Label labelNombreAdmin;

    private Usuario administradorActual;

    public void iniciarDatos(Usuario administrador) {

        this.administradorActual = administrador;

        if (administrador != null) {
            labelNombreAdmin.setText("Administrador " + administrador.getPrimerNombre() + " " + administrador.getApellidosCompleto());
        }
    }

    @FXML
    private void irACrearPolicia(ActionEvent event) throws IOException {
        cambioDeEscena(event, "/ui/users/registro-policia.fxml",
                "Registrar Nuevo Policía");
    }

    @FXML
    private void irAGestionUsuarios(ActionEvent event) throws IOException {
        cambioDeEscena(event, "/ui/users/gestion-usuarios.fxml", "Gestión de Usuarios");
    }

    @FXML
    public void irAVerInformacion(ActionEvent event) throws IOException {
        cambioDeEscena(event, "/ui/crimes/lista-admin.fxml",
                "Información del Sistema");
    }

    @FXML
    private void cerrarSesion(ActionEvent event) throws IOException {
        cambioDeEscena(event, "/ui/auth/seleccion-rol.fxml",
                "SecureAccess - Elección");
    }

    public void cambioDeEscena(ActionEvent event, String fxmlRuta, String titulo) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource(fxmlRuta));
        Parent root = fxmlLoader.load();

        Object controller = fxmlLoader.getController();

        if (controller instanceof RegistroPoliciaController) {
            ((RegistroPoliciaController) controller).setAdministradorActual(this.administradorActual);
        } else if (controller instanceof GestionDeUsuariosController) {
            ((GestionDeUsuariosController) controller).setAdministradorActual(this.administradorActual);
        }

        Scene scene = new Scene(root);
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setTitle(titulo);
        stage.setScene(scene);
        stage.show();
    }
}
