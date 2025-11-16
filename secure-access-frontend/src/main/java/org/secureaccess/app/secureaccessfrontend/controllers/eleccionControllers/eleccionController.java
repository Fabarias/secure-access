package org.secureaccess.app.secureaccessfrontend.controllers.eleccionControllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;


public class eleccionController {

    @FXML
    private void accesoLoginCiudadano(ActionEvent event) throws IOException {

        FXMLLoader fxmlCiudadano = new FXMLLoader(getClass().getResource("/ui/logins/loginCiudadanoView.fxml"));

        Parent loginCiudadano = fxmlCiudadano.load();

        Scene escenarioLoginCiudadano = new Scene(loginCiudadano);
        Stage ventanaActual = (Stage) ((Node) event.getSource()).getScene().getWindow();

        ventanaActual.setScene(escenarioLoginCiudadano);
        ventanaActual.show();
    }

    @FXML
    private void accesoLoginPolicia(ActionEvent event) throws IOException {

        FXMLLoader fxmlPolicia = new FXMLLoader(getClass().getResource("/ui/logins/loginPoliciaView.fxml"));

        Parent loginPolicia = fxmlPolicia.load();

        Scene escenarioLoginCiudadano = new Scene(loginPolicia);
        Stage ventanaActual = (Stage) ((Node) event.getSource()).getScene().getWindow();

        ventanaActual.setScene(escenarioLoginCiudadano);
        ventanaActual.show();

    }

    @FXML
    private void verEleccionAdministrador(ActionEvent event) throws IOException {

        FXMLLoader fxmlTercera = new FXMLLoader(getClass().getResource("/ui/selection/terceraEleccionView.fxml"));

        Parent terceraEleccion = fxmlTercera.load();

        Scene escenarioLoginCiudadano = new Scene(terceraEleccion);
        Stage ventanaActual = (Stage) ((Node) event.getSource()).getScene().getWindow();

        ventanaActual.setScene(escenarioLoginCiudadano);
        ventanaActual.show();
    }


}
