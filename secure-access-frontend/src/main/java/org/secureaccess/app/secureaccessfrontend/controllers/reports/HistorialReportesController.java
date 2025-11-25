package org.secureaccess.app.secureaccessfrontend.controllers.reports;

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
import javafx.stage.Stage;
import org.secureaccess.app.secureaccessbackend.modelos.Delito;
import org.secureaccess.app.secureaccessbackend.modelos.Reporte;
import org.secureaccess.app.secureaccessbackend.modelos.Usuario;
import org.secureaccess.app.secureaccessbackend.repositorios.RepositorioDelito;
import org.secureaccess.app.secureaccessbackend.servicios.ReporteServicio;
import org.secureaccess.app.secureaccessfrontend.controllers.dashboard.MenuCiudadanoController;
import org.secureaccess.app.secureaccessfrontend.viewModels.ReporteCiudadanoView;

import java.io.IOException;
import java.net.URL;
import java.time.format.DateTimeFormatter;
import java.util.Comparator;
import java.util.List;
import java.util.ResourceBundle;
import java.util.stream.Collectors;

public class HistorialReportesController implements Initializable {

    @FXML private ComboBox<String> comboEstado;
    @FXML private TableView<ReporteCiudadanoView> tablaReportes;
    @FXML private TableColumn<ReporteCiudadanoView, String> columnaFecha;
    @FXML private TableColumn<ReporteCiudadanoView, String> columnaDelito;
    @FXML private TableColumn<ReporteCiudadanoView, String> columnaEstado;

    private final ReporteServicio reporteServicio = new ReporteServicio();
    private final RepositorioDelito repoDelito = new RepositorioDelito();
    private Usuario ciudadanoActual;

    private final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");
    private List<Reporte> historialCompleto;

    public void setCiudadanoActual(Usuario ciudadano) {
        this.ciudadanoActual = ciudadano;

        cargarDatosIniciales();
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        configurarTabla();
        configurarFiltros();
    }

    private void configurarTabla() {
        columnaFecha.setCellValueFactory(cell -> cell
                .getValue().fechaProperty());
        columnaDelito.setCellValueFactory(cell -> cell
                .getValue().delitoProperty());
        columnaEstado.setCellValueFactory(cell -> cell
                .getValue().estadoProperty());

        tablaReportes.getColumns().forEach(c -> c.setReorderable(false));
    }

    private void configurarFiltros() {

        comboEstado.getItems().addAll("Todos", "Pendiente", "Atendido", "Denegado");
        comboEstado.getSelectionModel().select("Pendiente");
    }

    private void cargarDatosIniciales() {
        if (ciudadanoActual == null) return;

        this.historialCompleto = reporteServicio.obtenerHistorialPersonal(ciudadanoActual);
        filtrarReportes();
    }

    @FXML
    private void filtrarReportes() {
        if (historialCompleto == null) return;

        String estadoEstablecido = comboEstado.getValue();

        List<ReporteCiudadanoView> listaFiltrada = historialCompleto.stream()
                .filter(r -> "Todos".equals(estadoEstablecido) || r.getEstadoReporte() == null
                && r.getEstadoReporte().equalsIgnoreCase(estadoEstablecido))
                .sorted(Comparator.comparing(Reporte::getFechaDelito).reversed())
                .map(this::convertirAViewModel)
                .collect(Collectors.toList());

        ObservableList<ReporteCiudadanoView> items = FXCollections.observableArrayList();
        tablaReportes.setItems(items);
    }

    private ReporteCiudadanoView convertirAViewModel(Reporte reporte) {

        String nombreDelito = repoDelito.buscarPorId(reporte.getDelitoId())
                .map(Delito::getDelitoNombre)
                .orElse("Delito no especificado");

        String fechaStr = reporte.getFechaDelito() != null
                ? reporte.getFechaDelito().format(formatter) : "N/A";

        return new ReporteCiudadanoView(fechaStr, nombreDelito, reporte.getEstadoReporte());
    }

    @FXML
    private void regresar(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/ui/dashboard/menu-ciudadano.fxml"));
        Parent root = loader.load();

        MenuCiudadanoController controller = loader.getController();
        controller.iniciarDatos(ciudadanoActual);

        Scene escena = new Scene(root);
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setTitle("Menú Ciudadano");
        stage.setScene(escena);
        stage.show();
    }

}
