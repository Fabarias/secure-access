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
import org.secureaccess.app.secureaccessfrontend.controllers.crimes.ListaDelincuentesPoliciaController;
import org.secureaccess.app.secureaccessfrontend.controllers.reports.RevisionReportesController;

import java.io.IOException;

public class MenuPoliciaController {

    @FXML
    private Label labelNombrePolicia;
    private Usuario policiaActual;

    public void iniciarDatos(Usuario policia) {
        this.policiaActual = policia;
        if (policia != null) {
            labelNombrePolicia.setText("Oficial: " + policia.getPrimerNombre() + " " + policia.getApellidosCompleto());
        }
    }

    @FXML
    private void cerrarSesion(ActionEvent event) throws IOException {

        FXMLLoader loader = new FXMLLoader(getClass().getResource("/ui/auth/seleccion-rol.fxml"));
        Parent root = loader.load();

        Scene scene = new Scene(root);
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setTitle("SecureAccess - Inicio");
        stage.setScene(scene);
        stage.show();
    }

    @FXML
    private void irAListadoDelincuentes(ActionEvent event) throws IOException {
        cambiarEscena(event, "/ui/crimes/lista-policia.fxml", "Listado de Buscados - Policía");
    }

    @FXML
    private void irAReportes(ActionEvent event) throws IOException {
        cambiarEscena(event, "/ui/reports/revision-reportes.fxml", "Reportes Ciudadanos");
    }

    private void cambiarEscena(ActionEvent event,
                               String fxmlPath,
                               String titulo) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource(fxmlPath));
        Parent root = loader.load();

         if (loader.getController() instanceof ListaDelincuentesPoliciaController) {
             ((ListaDelincuentesPoliciaController) loader.getController()).setUsuarioActual(this.policiaActual);
         } else if (loader.getController() instanceof RevisionReportesController) {
             ((RevisionReportesController) loader.getController()).setPoliciaActual(this.policiaActual);
         }

        Scene scene = new Scene(root);
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setTitle(titulo);
        stage.setScene(scene);
        stage.show();
    }
}
