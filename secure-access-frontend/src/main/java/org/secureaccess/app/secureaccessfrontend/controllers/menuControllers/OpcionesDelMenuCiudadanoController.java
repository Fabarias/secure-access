package org.secureaccess.app.secureaccessfrontend.controllers.menuControllers;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import org.secureaccess.app.secureaccessbackend.modelos.Usuario;

public class OpcionesDelMenuCiudadanoController {

    @FXML
    private Label labelNombreCiudadano;







    public void iniciarDatos(Usuario ciudadano) {
        if (ciudadano != null) {
            labelNombreCiudadano.setText("Ciudadano " + ciudadano.getNombre() + " " + ciudadano.getApellido());
        }
    }
}
