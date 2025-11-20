package org.secureaccess.app.secureaccessfrontend.controllers.menuControllers.listadoDelincuentesControllers;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import org.secureaccess.app.secureaccessbackend.modelos.Usuario;
import org.secureaccess.app.secureaccessfrontend.controllers.menuControllers.OpcionesDelMenuPoliciaController;
import org.secureaccess.app.secureaccessfrontend.viewModels.DelincuentePoliciaView;

import java.io.IOException;

public class ListadoPoliciaDelincuentesController {

    @FXML private TextField filtroNombre;
    @FXML private TextField filtroApellidos;
    @FXML private ComboBox<String> comboDepartamentos;
    @FXML private TableView<DelincuentePoliciaView> tablaDelincuentes;
    @FXML private TableColumn<DelincuentePoliciaView, String> columnaNombre;
    @FXML private TableColumn<DelincuentePoliciaView, String> columnaApellidos;
    @FXML private TableColumn<DelincuentePoliciaView, String> columnnaDelito;
    @FXML private TableColumn<DelincuentePoliciaView, Double> columnaRecompensa;
    @FXML private ObservableList<DelincuentePoliciaView> listaObservableDelincuente;

    private Usuario usuarioActual;

    public void setUsuarioActual(Usuario usuario) {
        this.usuarioActual = usuario;
    }

    @FXML
    private void regresar(ActionEvent event) throws IOException {

        FXMLLoader loader = new FXMLLoader(getClass().getResource("/ui/menu/OpcionesDeMenuPoliciaView.fxml"));
        Parent root = loader.load();

        OpcionesDelMenuPoliciaController controller = loader.getController();
        controller.iniciarDatos(this.usuarioActual);

        Scene escena = new Scene(root);
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setTitle("Menú Policía");
        stage.setScene(escena);
        stage.show();
    }
}
