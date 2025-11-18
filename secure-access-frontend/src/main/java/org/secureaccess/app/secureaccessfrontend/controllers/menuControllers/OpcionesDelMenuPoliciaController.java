package org.secureaccess.app.secureaccessfrontend.controllers.menuControllers;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import org.secureaccess.app.secureaccessbackend.modelos.Usuario;

public class OpcionesDelMenuPoliciaController {

    @FXML
    private Label labelNombrePolicia;



    public void iniciarDatos(Usuario policia) {
        if (policia != null) {
            labelNombrePolicia.setText("Policia " + policia.getNombre() + " " + policia.getApellido());
        }
    }
}
