package org.secureaccess.app.secureaccessfrontend.controllers.menuControllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.stage.Stage;
import org.secureaccess.app.secureaccessbackend.modelos.Usuario;
import org.secureaccess.app.secureaccessfrontend.controllers.profilesControllers.GestionDeUsuariosController;

import java.io.IOException;

public class OpcionesDelMenuAdministradorController {

    @FXML
    private Label labelNombreAdmin;

    private Usuario administradorActual;

    @FXML
    private void irACrearPolicia(ActionEvent event) throws IOException {
        cambioDeEscena(event, "/ui/registers/registroPoliciaView.fxml",
                "Registrar Nuevo Policía");
    }

    @FXML
    private void irAGestionUsuarios(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/ui/menu/profiles/gestionDeUsuariosView.fxml"));
        Parent root = loader.load();

        GestionDeUsuariosController controller = loader.getController();
        controller.setAdministradorActual(this.administradorActual);

        Scene scene = new Scene(root);
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setTitle("Gestión de Usuarios");
        stage.setScene(scene);
        stage.show();
    }

    @FXML
    public void irAVerInformacion(ActionEvent event) throws IOException {
        cambioDeEscena(event, "/ui/menu/listasDelincuentes/listadoAdminDelincuentesView.fxml",
                "Información del Sistema");
    }

    public void iniciarDatos(Usuario administrador) {

        this.administradorActual = administrador;

        if (administrador != null) {
            labelNombreAdmin.setText("Administrador " + administrador.getNombre() + " " + administrador.getApellido());
        }
    }

    public void cambioDeEscena(ActionEvent event, String fxmlRuta, String titulo) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource(fxmlRuta));

        Parent root = fxmlLoader.load();

        Scene scene = new Scene(root);
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setTitle(titulo);
        stage.setScene(scene);
        stage.show();
    }
}
