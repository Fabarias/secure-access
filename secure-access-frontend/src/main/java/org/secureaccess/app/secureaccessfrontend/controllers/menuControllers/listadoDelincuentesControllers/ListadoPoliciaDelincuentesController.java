package org.secureaccess.app.secureaccessfrontend.controllers.menuControllers.listadoDelincuentesControllers;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import org.secureaccess.app.secureaccessbackend.modelos.*;
import org.secureaccess.app.secureaccessbackend.repositorios.*;
import org.secureaccess.app.secureaccessfrontend.controllers.menuControllers.OpcionesDelMenuPoliciaController;
import org.secureaccess.app.secureaccessfrontend.viewModels.DelincuentePoliciaView;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.ResourceBundle;
import java.util.stream.Collectors;

public class ListadoPoliciaDelincuentesController implements Initializable {

    @FXML private TextField filtroNombre;
    @FXML private TextField filtroApellidos;
    @FXML private ComboBox<String> comboDepartamentos;

    @FXML private TableView<DelincuentePoliciaView> tablaDelincuentes;
    @FXML private TableColumn<DelincuentePoliciaView, String> columnaNombre;
    @FXML private TableColumn<DelincuentePoliciaView, String> columnaApellidos;
    @FXML private TableColumn<DelincuentePoliciaView, String> columnnaDelito;
    @FXML private TableColumn<DelincuentePoliciaView, Double> columnaRecompensa;

    @FXML private ObservableList<DelincuentePoliciaView> listaObservableDelincuente;

    private final RepositorioDelincuente repoDelincuente = new RepositorioDelincuente();
    private final RepositorioLugarRequisitoria repoLugar = new RepositorioLugarRequisitoria();
    private final RepositorioDelito repoDelito = new RepositorioDelito();

    private List<LugarRequisitoria> cacheLugares;
    private Usuario usuarioActual;

    @Override
    public void initialize(URL location, ResourceBundle resourceBundle) {
        configurarColumnas();
        cargarDepartamentos();

        buscarDelicuentes(null);
    }

    private void configurarColumnas() {

        columnaNombre.setCellValueFactory(cell -> cell
                .getValue()
                .nombreProperty());
        columnaApellidos.setCellValueFactory(cell -> cell
                .getValue()
                .apellidosProperty());
        columnnaDelito.setCellValueFactory(cell -> cell
                .getValue()
                .delitoProperty());
        columnaRecompensa.setCellValueFactory(cell -> cell
                .getValue()
                .recompensaProperty()
                .asObject());

        tablaDelincuentes.getColumns().forEach(col -> col.setReorderable(false));
    }

    private void cargarDepartamentos() {

        this.cacheLugares = repoLugar.listarTodos();

        comboDepartamentos.getItems().add("Todos los Departamentos");

        List<String> nombres = cacheLugares.stream()
                .map(LugarRequisitoria::getLugarDeRequisitoriaNombre)
                .collect(Collectors.toList());

        comboDepartamentos.getItems().addAll(nombres);
        comboDepartamentos.getSelectionModel().selectFirst();
    }

    @FXML
    private void buscarDelicuentes(ActionEvent event) {

        String nombreFiltro = filtroNombre.getText().toLowerCase().trim();
        String apellidoFiltro = filtroApellidos.getText().toLowerCase().trim();
        String departamentoSeleccionado = comboDepartamentos.getSelectionModel().getSelectedItem();

        List<Delincuente> resultadosBrutos;

        if (departamentoSeleccionado == null || "Todos los Departamentos".equals(departamentoSeleccionado)) {
            resultadosBrutos = repoDelincuente.buscarTodos();
        } else {

            int idLugar = cacheLugares.stream()
                    .filter(l -> l.getLugarDeRequisitoriaNombre().equals(departamentoSeleccionado))
                    .findFirst()
                    .map(LugarRequisitoria::getLugarDeRequisitoriaId)
                    .orElse(-1);

            resultadosBrutos = (idLugar != -1)
                    ? repoDelincuente.buscarPorLugarDeRequisitoria(idLugar)
                    : new ArrayList<>();
        }

        List<DelincuentePoliciaView> listaFiltrada = resultadosBrutos.stream()
                .filter(d -> nombreFiltro.isEmpty() ||
                        d.getDelincuentePrimerNombre().toLowerCase().contains(nombreFiltro))
                .filter(d -> apellidoFiltro.isEmpty() ||
                        d.getApellidosCompletos().toLowerCase().contains(apellidoFiltro))
                .sorted(Comparator.comparingDouble(Delincuente::getRecompensa).reversed())
                .map(d -> {
                    String nombreDelito = repoDelito.buscarNombrePorDelincuenteId(d.getDelincuenteId())
                            .orElse("Sin Delito Registrado");

                    return new DelincuentePoliciaView(
                            d.getDelincuentePrimerNombre(),
                            d.getApellidosCompletos(),
                            nombreDelito,
                            d.getRecompensa()
                    );
                })
                .collect(Collectors.toList());

        listaObservableDelincuente = FXCollections.observableArrayList(listaFiltrada);
        tablaDelincuentes.setItems(listaObservableDelincuente);

    }

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
