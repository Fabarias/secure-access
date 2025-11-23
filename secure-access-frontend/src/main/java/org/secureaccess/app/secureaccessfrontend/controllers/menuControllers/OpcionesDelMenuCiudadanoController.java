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
import org.secureaccess.app.secureaccessfrontend.controllers.registersControllers.RegistroReporteController;

import java.io.IOException;

public class OpcionesDelMenuCiudadanoController {

    @FXML
    private Label labelNombreCiudadano;

    private Usuario ciudadanoActual;

    public void iniciarDatos(Usuario ciudadano) {
        this.ciudadanoActual = ciudadano;
        if (ciudadano != null) {
            labelNombreCiudadano.setText("Ciudadano " + ciudadano.getNombre() + " " + ciudadano.getApellido());
        }
    }

    @FXML
    private void irACrearReporte(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/ui/registers/registroReporteView.fxml"));
        Parent root = loader.load();

        RegistroReporteController controller = loader.getController();

        controller.setCiudadanoActual(this.ciudadanoActual);

        Scene scene = new Scene(root);
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setTitle("Crear Nuevo Reporte");
        stage.setScene(scene);
        stage.show();
    }

    @FXML
    private void irAVerHistorial(ActionEvent event) {

    }

    @FXML
    private void irAListadoDelincuentes(ActionEvent event) throws IOException {

        cambiarEscena(event, "/ui/menu/listadoDelincuentes/listadoDelincuentesView.fxml", "Listado de Buscados");
    }

    @FXML
    private void cerrarSesion(ActionEvent event) throws IOException {
        cambiarEscena(event, "/ui/selection/eleccionView.fxml", "SecureAccess - Inicio");
    }

    private void cambiarEscena(ActionEvent event, String fxmlPath, String titulo) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource(fxmlPath));
        Parent root = loader.load();

        Scene scene = new Scene(root);
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setTitle(titulo);
        stage.setScene(scene);
        stage.show();
    }
}
