package org.secureaccess.app.secureaccessfrontend.controllers.profilesControllers;

import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.stage.Stage;
import org.secureaccess.app.secureaccessbackend.modelos.Usuario;
import org.secureaccess.app.secureaccessbackend.repositorios.RepositorioUsuario;
import org.secureaccess.app.secureaccessbackend.servicios.AdministracionServicio;
import org.secureaccess.app.secureaccessfrontend.controllers.menuControllers.OpcionesDelMenuAdministradorController;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class GestionDeUsuariosController implements Initializable {

    @FXML private TableView<Usuario> tablaUsuarios;
    @FXML private TableColumn<Usuario, String> colNombre;
    @FXML private TableColumn<Usuario, String> colApellido;
    @FXML private TableColumn<Usuario, String> colUsuario;
    @FXML private TableColumn<Usuario, String> colRol;
    @FXML private TableColumn<Usuario, String> colEstado;

    private final RepositorioUsuario repoUsuario = new RepositorioUsuario();
    private final AdministracionServicio adminServicio = new AdministracionServicio();
    private Usuario administradorActual;

    public void setAdministradorActual(Usuario administrador) {
        this.administradorActual = administrador;
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        configurarColumnas();
        cargarDatos();
    }

    @FXML
    private void accionHabilitar() {
        Usuario seleccionado = tablaUsuarios.getSelectionModel().getSelectedItem();

        if (seleccionado == null) {
            mostrarAlerta(Alert.AlertType.ERROR, "Selección requerida",
                    "Por favor seleccione un usuario de la tabla");
            return;
        }

        if (seleccionado.getUsuarioId() == administradorActual.getUsuarioId()) {
            mostrarAlerta(Alert.AlertType.ERROR, "Acción Inválida",
                    "No puedes deshabilitar tu propia cuenta.");
        }

        adminServicio.habilitarUsuario(administradorActual, seleccionado.getUsuarioId());

        mostrarAlerta(Alert.AlertType.INFORMATION, "Éxito",
                "Usuario habilitado correctamente.");
        cargarDatos();
    }

    @FXML
    private void accionDeshabilitar() {
        Usuario seleccionado = tablaUsuarios.getSelectionModel().getSelectedItem();

        if (seleccionado == null) {
            mostrarAlerta(Alert.AlertType.WARNING, "Selección requerida", "Por favor seleccione un usuario de la tabla.");
            return;
        }

        if (seleccionado.getUsuarioId() == administradorActual.getUsuarioId()) {
            mostrarAlerta(Alert.AlertType.ERROR, "Acción Inválida", "No puedes deshabilitar tu propia cuenta.");
            return;
        }

        adminServicio.deshabilitarUsuario(administradorActual, seleccionado.getUsuarioId());

        mostrarAlerta(Alert.AlertType.INFORMATION, "Éxito", "Usuario deshabilitado correctamente.");
        cargarDatos();
    }




    private void configurarColumnas() {

        colNombre.setCellValueFactory(cell -> new SimpleStringProperty(cell
                .getValue()
                .getNombre()));

        colApellido.setCellValueFactory(cell -> new SimpleStringProperty(cell
                .getValue()
                .getApellido()));

        colUsuario.setCellValueFactory(cell -> new SimpleStringProperty(cell
                .getValue()
                .getNombreUsuario()));

        colRol.setCellValueFactory(cell -> new SimpleStringProperty(cell
                .getValue()
                .getNombreRol()));

        colEstado.setCellValueFactory(cell -> new SimpleStringProperty(cell
                .getValue()
                .getEstadoString()));
    }

    @FXML
    private void regresar(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/ui/menu/OpcionesDeMenuAdministradorView.fxml"));
        Parent root = loader.load();

        OpcionesDelMenuAdministradorController controller = loader.getController();
        controller.iniciarDatos(administradorActual);

        Scene scene = new Scene(root);
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setTitle("Menú Administrador");
        stage.setScene(scene);
        stage.show();
    }

    private void cargarDatos() {

        ObservableList<Usuario> listaUsuarios = FXCollections.observableArrayList(repoUsuario.listarTodos());
        tablaUsuarios.setItems(listaUsuarios);
    }

    private void mostrarAlerta(Alert.AlertType tipo, String titulo, String mensaje) {
        Alert alert = new Alert(tipo);
        alert.setHeaderText(titulo);
        alert.setContentText(mensaje);
        alert.showAndWait();
    }

}
