package org.secureaccess.app.secureaccessfrontend.controllers.crimes;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import javafx.util.StringConverter;
import org.secureaccess.app.secureaccessbackend.modelos.Delincuente;
import org.secureaccess.app.secureaccessbackend.modelos.Delito;
import org.secureaccess.app.secureaccessbackend.modelos.LugarRequisitoria;
import org.secureaccess.app.secureaccessbackend.modelos.Usuario;
import org.secureaccess.app.secureaccessbackend.repositorios.RepositorioDelincuente;
import org.secureaccess.app.secureaccessbackend.repositorios.RepositorioDelito;
import org.secureaccess.app.secureaccessbackend.repositorios.RepositorioLugarRequisitoria;
import org.secureaccess.app.secureaccessfrontend.controllers.dashboard.MenuPoliciaController;
import org.secureaccess.app.secureaccessfrontend.util.Alerta;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class RegistroDelincuenteController implements Initializable {

    @FXML private TextField txtNombres;
    @FXML private TextField txtPaterno;
    @FXML private TextField txtMaterno;
    @FXML private TextField txtRecompensa;
    @FXML private ComboBox<Delito> comboDelito;
    @FXML private ComboBox<LugarRequisitoria> comboLugar;

    private final RepositorioDelincuente repoDelincuente = new RepositorioDelincuente();
    private final RepositorioDelito repoDelito = new RepositorioDelito();
    private final RepositorioLugarRequisitoria repoLugar = new RepositorioLugarRequisitoria();

    private Usuario policiaActual;

    public void setPoliciaActual(Usuario policia) {
        this.policiaActual = policia;
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        cargarCombos();
    }

    private void cargarCombos() {
        comboLugar.getItems().addAll(repoLugar.listarTodos());
        comboLugar.setConverter(new StringConverter<>() {
            @Override
            public String toString(LugarRequisitoria l) { return l != null ? l.getLugarDeRequisitoriaNombre() : ""; }
            @Override
            public LugarRequisitoria fromString(String s) { return null; }
        });

        comboDelito.getItems().addAll(repoDelito.listarArchivos());
        comboDelito.setConverter(new StringConverter<>() {
            @Override
            public String toString(Delito d) { return d != null ? d.getDelitoNombre() : ""; }
            @Override
            public Delito fromString(String s) { return null; }
        });
    }

    @FXML
    private void guardarRegistro(ActionEvent event) {

        String nombres = txtNombres.getText();
        String paterno = txtPaterno.getText();
        String materno = txtMaterno.getText();
        String recompensaString = txtRecompensa.getText();
        Delito delito = comboDelito.getValue();
        LugarRequisitoria lugar = comboLugar.getValue();

        if (nombres.isEmpty() || paterno.isEmpty() || materno.isEmpty()
        || recompensaString.isEmpty() || delito == null || lugar == null) {
            Alerta.mostrar("Datos Incompletos", "Por favor llene todos los campos.");
            return;
        }

        try {
            double recompensa = Double.parseDouble(recompensaString);

            Delincuente nuevo = new Delincuente(
                    nombres,
                    paterno,
                    materno,
                    lugar.getLugarDeRequisitoriaId(),
                    policiaActual.getUsuarioId(),
                    recompensa
            );

            if (repoDelincuente.guardarConDelito(nuevo, delito.getDelitoId())) {

                Alerta.mostrar("Éxito", "Registro criminal creado correctamente.");
            } else Alerta.mostrar("Error", "No se pudo guardar el registro.");
        } catch (NumberFormatException e) {
            Alerta.mostrar("Error de Formato", "La recompensa debe ser un número válido.");
        }
        limpiarCampos();
    }

    @FXML
    private void regresarAlMenu(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/ui/dashboard/menu-policia.fxml"));
        Parent root = loader.load();

        MenuPoliciaController controller = loader.getController();
        controller.iniciarDatos(this.policiaActual);

        Scene scene = new Scene(root);
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setTitle("Menú Policía");
        stage.setScene(scene);
        stage.show();
    }

    private void limpiarCampos() {
        txtNombres.clear();
        txtPaterno.clear();
        txtMaterno.clear();
        txtRecompensa.clear();
        comboDelito.getSelectionModel().clearSelection();
        comboLugar.getSelectionModel().clearSelection();
    }


}
