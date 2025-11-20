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

import java.io.IOException;

public class OpcionesDelMenuPoliciaController {

    @FXML
    private Label labelNombrePolicia;

    // Variable para guardar la sesión del policía actual
    private Usuario policiaActual;

    /**
     * Recibe los datos del usuario al iniciar sesión o regresar.
     */
    public void iniciarDatos(Usuario policia) {
        this.policiaActual = policia;
        if (policia != null) {
            labelNombrePolicia.setText("Oficial: " + policia.getNombre() + " " + policia.getApellido());
        }
    }

    /**
     * Acción del botón "Observar listado de delincuentes"
     */
    @FXML
    private void irAListadoDelincuentes(ActionEvent event) throws IOException {
        cambiarEscena(event, "/ui/menu/listasDelincuentes/listadoPoliciaDelincuentes.fxml", "Listado de Buscados - Policía");
    }

    @FXML
    private void irAReportes(ActionEvent event) {
        // TODO: Implementar navegación a Reportes cuando tengas la vista
        System.out.println("Navegando a Reportes...");
    }

    @FXML
    private void cerrarSesion(ActionEvent event) throws IOException {
        cambiarEscena(event, "/ui/selection/eleccionView.fxml", "SecureAccess - Inicio");
    }

    /**
     * Método auxiliar para cambiar de escena de forma limpia.
     */
    private void cambiarEscena(ActionEvent event, String fxmlPath, String titulo) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource(fxmlPath));
        Parent root = loader.load();

         if (loader.getController() instanceof ListadoPoliciaDelincuentesController) {
             ((ListadoPoliciaDelincuentesController) loader.getController()).setUsuarioActual(this.policiaActual);
         }

        Scene scene = new Scene(root);
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setTitle(titulo);
        stage.setScene(scene);
        stage.show();
    }
}
