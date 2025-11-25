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
import org.secureaccess.app.secureaccessfrontend.controllers.crimes.ListaDelincuentesCiudadanoController;
import org.secureaccess.app.secureaccessfrontend.controllers.reports.HistorialReportesController;
import org.secureaccess.app.secureaccessfrontend.controllers.reports.CrearReporteController;

import java.io.IOException;

public class MenuCiudadanoController {

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
        cambiarEscena(event, "/ui/reports/crear-reporte.fxml", "Crear Nuevo Reporte");
    }

    @FXML
    private void irAVerHistorial(ActionEvent event) throws IOException {
        cambiarEscena(event, "/ui/reports/historial-reportes.fxml", "Historial de Reportes");
    }

    @FXML
    private void irAListadoDelincuentes(ActionEvent event) throws IOException {
        cambiarEscena(event, "/ui/crimes/lista-ciudadano.fxml", "Listado de Buscados");
    }

    @FXML
    private void cerrarSesion(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/ui/auth/seleccion-rol.fxml"));
        Parent root = loader.load();
        Scene scene = new Scene(root);

        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setTitle("SecureAccess - Elección");
        stage.setScene(scene);
        stage.show();
    }

    private void cambiarEscena(ActionEvent event, String fxmlPath, String titulo) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource(fxmlPath));
        Parent root = loader.load();

        Object controller = loader.getController();

        if (controller instanceof CrearReporteController) {
            ((CrearReporteController) controller).setCiudadanoActual(ciudadanoActual);
        } else if (controller instanceof ListaDelincuentesCiudadanoController) {
            ((ListaDelincuentesCiudadanoController) controller).setCiudadanoActual(ciudadanoActual);
        } else if (controller instanceof HistorialReportesController) {
            ((HistorialReportesController) controller).setCiudadanoActual(ciudadanoActual);
        }

        Scene scene = new Scene(root);
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setTitle(titulo);
        stage.setScene(scene);
        stage.show();
    }
}
