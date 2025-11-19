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
import org.secureaccess.app.secureaccessbackend.repositorios.RepositorioUsuario;
import org.secureaccess.app.secureaccessfrontend.viewModels.DelincuenteAdminView;


import java.io.IOException;
import java.net.URL;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import java.util.stream.Collectors;

public class ListadoAdminDelincuentesController implements Initializable{


    @FXML private TextField filtroNombre;
    @FXML private TextField filtroApellidos;
    @FXML private ComboBox<String> comboDepartamentos;

    @FXML private TableView<DelincuenteAdminView> tablaDelincuentes;
    @FXML private TableColumn<DelincuenteAdminView, String> colNombre;
    @FXML private TableColumn<DelincuenteAdminView, String> colApellidos;
    @FXML private TableColumn<DelincuenteAdminView, String> colDelito;
    @FXML private TableColumn<DelincuenteAdminView, Double> colRecompensa;
    @FXML private TableColumn<DelincuenteAdminView, String> colFechaRegistro;
    @FXML private TableColumn<DelincuenteAdminView, String> colUsuarioRegistro;

    private ObservableList<DelincuenteAdminView> listaObservable;

    private final RepositorioDelincuente repoDelincuente = new RepositorioDelincuente();
    private final RepositorioUsuario repoUsuario = new RepositorioUsuario();
    private final RepositorioDelito repoDelito = new RepositorioDelito();
    private final RepositorioLugarRequisitoria repoLugar = new RepositorioLugarRequisitoria();

    private final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
    private List<LugarRequisitoria> cacheLugares;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        this.cacheLugares = repoLugar.listarTodos();

        configurarComboBox();
        configurarColumnas();

        cargarDatos(repoDelincuente.buscarTodos());
    }

    private void configurarComboBox() {
        List<String> nombresLugares = this.cacheLugares.stream()
                .map(LugarRequisitoria::getLugarDeRequisitoriaNombre)
                .collect(Collectors.toList());

        this.comboDepartamentos.getItems()
                .addAll(nombresLugares);
        this.comboDepartamentos.getItems()
                .add(0, "Todos los Departamentos");
        this.comboDepartamentos.getSelectionModel().selectFirst();
    }

    private void configurarColumnas() {

        colNombre.setCellValueFactory(cell -> cell
                .getValue()
                .nombreProperty());
        colApellidos.setCellValueFactory(cell -> cell
                .getValue()
                .apellidosProperty());
        colDelito.setCellValueFactory(cell -> cell
                .getValue()
                .delitoProperty());
        colRecompensa.setCellValueFactory(cell -> cell
                .getValue()
                .recompensaProperty()
                .asObject());
        colFechaRegistro.setCellValueFactory(cell -> cell
                .getValue()
                .fechaRegistroProperty());
        colUsuarioRegistro.setCellValueFactory(cell -> cell
                .getValue()
                .usuarioRegistroProperty());

        tablaDelincuentes.getColumns().forEach(col -> col.setReorderable(false));
    }

    @FXML
    private void onClickBotonBuscar(ActionEvent event) {

        String nombre = filtroNombre.getText();
        String apellidos = filtroApellidos.getText();
        String departamento = comboDepartamentos
                .getSelectionModel()
                .getSelectedItem();

        List<Delincuente> delincuentesEnProceso;

        if ("Todos los Departamentos".equals(departamento)) {
            delincuentesEnProceso = repoDelincuente.buscarTodos();
        } else {
            int idLugar = this.cacheLugares.stream()
                    .filter(l -> l.getLugarDeRequisitoriaNombre().equals(departamento))
                    .findFirst()
                    .map(LugarRequisitoria::getLugarDeRequisitoriaId)
                    .orElse(-1);

            delincuentesEnProceso = (idLugar != -1) ? repoDelincuente.buscarPorLugarDeRequisitoria(idLugar) : new ArrayList<>();
        }

        List<Delincuente> delincuentesFiltrados = delincuentesEnProceso.stream()
                .filter(d -> nombre.isEmpty() || d.getDelincuentePrimerNombre()
                        .toLowerCase()
                        .contains(nombre.toLowerCase()))
                .filter(d -> apellidos.isEmpty() || d.getApellidosCompletos()
                        .toLowerCase()
                        .contains(apellidos.toLowerCase()))
                .collect(Collectors.toList());

        cargarDatos(delincuentesFiltrados);
    }

    private void cargarDatos(List<Delincuente> delincuentesCrudos) {

        List<DelincuenteAdminView> datosEnriquecidos = delincuentesCrudos.stream()
                .map(delincuente -> {

                    String nombreRegistrador = repoUsuario.buscarPorId(delincuente.getUsuarioRegistroId())
                            .map(Usuario::getNombreCompleto)
                            .orElse("ID Desconocido");

                    String nombreDelito = repoDelito.buscarNombrePorDelincuenteId(delincuente.getDelincuenteId())
                            .orElse("DELITO NO ASIGNADO");

                    String fechaFormateada = delincuente.getFechaRegistro() != null
                            ? delincuente.getFechaRegistro().format(formatter)
                            : "N/A";

                    return new DelincuenteAdminView(
                            delincuente.getDelincuentePrimerNombre(),
                            delincuente.getApellidosCompletos(),
                            nombreDelito,
                            delincuente.getRecompensa(),
                            fechaFormateada,
                            nombreRegistrador
                    );
                })
                .collect(Collectors.toList());

        ObservableList<DelincuenteAdminView> listaObservable = FXCollections
                .observableArrayList(datosEnriquecidos);

        tablaDelincuentes.setItems(listaObservable);
    }


    @FXML
    private void regresar(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/ui/menu/OpcionesDeMenuAdministradorView.fxml"));
        Parent root = loader.load();

        Scene scene = new Scene(root);
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setTitle("Menú Administrador");
        stage.setScene(scene);
        stage.show();
    }
}
