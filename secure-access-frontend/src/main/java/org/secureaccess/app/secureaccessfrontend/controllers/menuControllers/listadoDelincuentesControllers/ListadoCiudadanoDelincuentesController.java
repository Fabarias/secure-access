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
import org.secureaccess.app.secureaccessbackend.modelos.Delincuente;
import org.secureaccess.app.secureaccessbackend.modelos.LugarRequisitoria;
import org.secureaccess.app.secureaccessbackend.modelos.Usuario;
import org.secureaccess.app.secureaccessbackend.repositorios.RepositorioDelincuente;
import org.secureaccess.app.secureaccessbackend.repositorios.RepositorioDelito;
import org.secureaccess.app.secureaccessbackend.repositorios.RepositorioLugarRequisitoria;
import org.secureaccess.app.secureaccessfrontend.controllers.menuControllers.OpcionesDelMenuCiudadanoController;
import org.secureaccess.app.secureaccessfrontend.viewModels.DelincuentePoliciaView;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.ResourceBundle;
import java.util.stream.Collectors;

public class ListadoCiudadanoDelincuentesController implements Initializable {

    @FXML private TextField filtroNombre;
    @FXML private TextField filtroApellidos;
    @FXML private ComboBox<String> comboDepartamentos;

    @FXML private TableView<DelincuentePoliciaView> tablaDelincuentes;
    @FXML private TableColumn<DelincuentePoliciaView, String> columnaNombre;
    @FXML private TableColumn<DelincuentePoliciaView, String> columnaApellidos;
    @FXML private TableColumn<DelincuentePoliciaView, String> columnaDelito;
    @FXML private TableColumn<DelincuentePoliciaView, Double> columnaRecompensa;

    @FXML private ObservableList<DelincuentePoliciaView> listaObservableDelincuentes;

    private final RepositorioDelincuente repoDelincuente = new RepositorioDelincuente();
    private final RepositorioLugarRequisitoria repoLugar = new RepositorioLugarRequisitoria();
    private final RepositorioDelito repoDelito = new RepositorioDelito();

    private List<LugarRequisitoria> cacheLugares;
    private Usuario ciudadanoActual;

    public void setCiudadanoActual(Usuario ciudadano) {
        this.ciudadanoActual = ciudadano;
    }

    @Override
    public void initialize(URL location, ResourceBundle resourceBundle) {
        configurarColumnas();
        cargarDepartamentos();
        buscarDelincuentes(null);
    }

    private void configurarColumnas() {
        columnaNombre.setCellValueFactory(cell -> cell
                .getValue()
                .nombreProperty());
        columnaApellidos.setCellValueFactory(cell -> cell
                .getValue()
                .apellidosProperty());
        columnaDelito.setCellValueFactory(cell -> cell
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
    private void buscarDelincuentes(ActionEvent event) {
        String nombreFiltro = filtroNombre.getText().toLowerCase().trim();
        String apellidosFiltro = filtroApellidos.getText().toLowerCase().trim();
        String departamentoFiltro = comboDepartamentos.getSelectionModel().getSelectedItem();

        List<Delincuente> resultadosEnBruto;

        boolean mostrarTodo = departamentoFiltro == null || "Todos los Departamentos".equals(departamentoFiltro);

        if (mostrarTodo) {
            resultadosEnBruto = repoDelincuente.buscarTodos();
        } else {
            int idLugar = cacheLugares.stream()
                    .filter(l -> l.getLugarDeRequisitoriaNombre().equals(departamentoFiltro))
                    .findFirst()
                    .map(LugarRequisitoria::getLugarDeRequisitoriaId)
                    .orElse(-1);

            resultadosEnBruto = (idLugar != -1)
                    ? repoDelincuente.buscarPorLugarDeRequisitoria(idLugar)
                    : new ArrayList<>();
        }

        List<DelincuentePoliciaView> listaFiltrada = resultadosEnBruto.stream()
                .filter(d -> nombreFiltro.isEmpty() || d.getDelincuentePrimerNombre()
                        .toLowerCase()
                        .contains(nombreFiltro))
                .filter(d -> apellidosFiltro.isEmpty() || d.getApellidosCompletos()
                        .toLowerCase()
                        .contains(apellidosFiltro))
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
                }).collect(Collectors.toList());

                listaObservableDelincuentes = FXCollections.observableArrayList(listaFiltrada);
                tablaDelincuentes.setItems(listaObservableDelincuentes);

    }

    @FXML
    private void regresar(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/ui/menu/OpcionesDeMenuCiudadanoView.fxml"));
        Parent root = loader.load();

        OpcionesDelMenuCiudadanoController controller = loader.getController();
        controller.iniciarDatos(this.ciudadanoActual);

        Scene escena = new Scene(root);
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setTitle("Menú Ciudadano");
        stage.setScene(escena);
        stage.show();
    }

}
