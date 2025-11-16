package org.secureaccess.app.secureaccessfrontend.controllers.loginsControllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;

public class LoginPoliciaController {

    @FXML
    private TextField textoPlaca;
    @FXML
    private PasswordField clavePolicia;

    @FXML
    private void regresarDelLoginPolicia(ActionEvent event) throws IOException {

        FXMLLoader fxmlSeleccion = new FXMLLoader(getClass().getResource("/ui/selection/eleccionView.fxml"));

        Parent seleccion = fxmlSeleccion.load();

        Scene escenaSeleccion = new Scene(seleccion);
        Stage ventanaActual = (Stage) ((Node) event.getSource()).getScene().getWindow();

        ventanaActual.setTitle("SecureAccess - Panel");
        ventanaActual.setScene(escenaSeleccion);
        ventanaActual.show();
    }


    public ArrayList<String> verificarDatos(String placa, String clave) {

        return placa.equals("Faber") && clave.equals("Candy") ?
                new ArrayList<>(Arrays.asList(placa, clave)) : new ArrayList<>();
    }

    @FXML
    private void accesoLoginPolicia(ActionEvent event) throws IOException {

        String placaPolicia = textoPlaca.getText();
        String contraPolicia = clavePolicia.getText();

        ArrayList<String> contenidoCredenciales = verificarDatos(placaPolicia, contraPolicia);

        if (!contenidoCredenciales.isEmpty()) {

                FXMLLoader fxmlDashboard = new FXMLLoader(getClass().getResource("/ui/menu/listadoDelincuentes/listadoDelincuentesView.fxml"));
                Parent dashboard = fxmlDashboard.load();

                Scene escena = new Scene(dashboard);
                Stage ventanaActual = (Stage) ((Node) event.getSource()).getScene().getWindow();

                ventanaActual.setTitle("Secure Access - Dashboard");
                ventanaActual.setScene(escena);
                ventanaActual.show();
        }
    }
}
