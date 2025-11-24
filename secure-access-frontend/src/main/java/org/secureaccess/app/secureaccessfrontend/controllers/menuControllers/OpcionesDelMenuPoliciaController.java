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
import org.secureaccess.app.secureaccessfrontend.controllers.menuControllers.listadoDelincuentesControllers.ListadoPoliciaDelincuentesController;
import org.secureaccess.app.secureaccessfrontend.controllers.menuControllers.listadoDelincuentesControllers.ListadoReportesPoliciaController;

import java.io.IOException;

public class OpcionesDelMenuPoliciaController {

    @FXML
    private Label labelNombrePolicia;
    private Usuario policiaActual;

    public void iniciarDatos(Usuario policia) {
        this.policiaActual = policia;
        if (policia != null) {
            labelNombrePolicia.setText("Oficial: " + policia.getNombre() + " " + policia.getApellido());
        }
    }

    @FXML
    private void cerrarSesion(ActionEvent event) throws IOException {

        FXMLLoader loader = new FXMLLoader(getClass().getResource("/ui/selection/eleccionView.fxml"));
        Parent root = loader.load();

        Scene scene = new Scene(root);
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setTitle("SecureAccess - Inicio");
        stage.setScene(scene);
        stage.show();
    }

    @FXML
    private void irAListadoDelincuentes(ActionEvent event) throws IOException {
        cambiarEscena(event, "/ui/menu/listasDelincuentes/listadoPoliciaDelincuentes.fxml", "Listado de Buscados - Policía");
    }

    @FXML
    private void irAReportes(ActionEvent event) throws IOException {
        cambiarEscena(event, "/ui/menu/listasDelincuentes/listadoReportesPoliciaView.fxml", "Reportes Ciudadanos");
    }

    private void cambiarEscena(ActionEvent event,
                               String fxmlPath,
                               String titulo) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource(fxmlPath));
        Parent root = loader.load();

         if (loader.getController() instanceof ListadoPoliciaDelincuentesController) {
             ((ListadoPoliciaDelincuentesController) loader.getController()).setUsuarioActual(this.policiaActual);
         } else if (loader.getController() instanceof ListadoReportesPoliciaController) {
             ((ListadoReportesPoliciaController) loader.getController()).setPoliciaActual(this.policiaActual);
         }

        Scene scene = new Scene(root);
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setTitle(titulo);
        stage.setScene(scene);
        stage.show();
    }
}
