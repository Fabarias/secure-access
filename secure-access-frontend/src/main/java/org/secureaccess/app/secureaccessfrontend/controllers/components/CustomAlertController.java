package org.secureaccess.app.secureaccessfrontend.controllers.components;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.stage.Stage;


public class CustomAlertController {

        @FXML
        private Label lblTitulo;
        @FXML private Label lblMensaje;

        public void setContenido(String titulo, String mensaje) {
            lblTitulo.setText(titulo);
            lblMensaje.setText(mensaje);
        }

        @FXML
        private void cerrarAlerta(ActionEvent event) {
            Node source = (Node) event.getSource();
            Stage stage = (Stage) source.getScene().getWindow();
            stage.close();
        }

}

