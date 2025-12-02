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
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.stage.Stage;
import org.secureaccess.app.secureaccessbackend.modelos.CategoriaDelito;
import org.secureaccess.app.secureaccessbackend.modelos.Delito;
import org.secureaccess.app.secureaccessbackend.modelos.Reporte;
import org.secureaccess.app.secureaccessbackend.modelos.Usuario;
import org.secureaccess.app.secureaccessbackend.repositorios.RepositorioDelito;
import org.secureaccess.app.secureaccessbackend.repositorios.RepositorioReporte;
import org.secureaccess.app.secureaccessbackend.repositorios.RepositorioUsuario;
import org.secureaccess.app.secureaccessbackend.servicios.ReporteServicio;
import org.secureaccess.app.secureaccessfrontend.controllers.dashboard.MenuPoliciaController;
import org.secureaccess.app.secureaccessfrontend.util.Alerta;
import org.secureaccess.app.secureaccessfrontend.viewModels.ReportePoliciaView;

import java.io.IOException;
import java.net.URL;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.stream.Collectors;

public class RevisionReportesController implements Initializable {

    @FXML private TableView<ReportePoliciaView> tablaReportes;
    @FXML private TableColumn<ReportePoliciaView, String> columnaEnviadoPor;
    @FXML private TableColumn<ReportePoliciaView, String> columnaSupuestoDelito;
    @FXML private TableColumn<ReportePoliciaView, String> columnaFecha;
    @FXML private TableColumn<ReportePoliciaView, String> columnaUrgencia;

    private final ReporteServicio reporteServicio = new ReporteServicio();
    private final RepositorioUsuario repoUsuario = new RepositorioUsuario();
    private final RepositorioDelito repoDelito = new RepositorioDelito();
    private final RepositorioReporte repoReporte = new RepositorioReporte();

    private Usuario policiaActual;
    private final DateTimeFormatter formatter = DateTimeFormatter
            .ofPattern("yyyy-MM-dd HH:mm");

    public void setPoliciaActual(Usuario policia) {
        this.policiaActual = policia;
        cargarDatos();
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        configurarColumnas();
        cargarDatos();
    }

    public void cargarDatos() {
        if (policiaActual == null) return;

        List<Reporte> reportes = reporteServicio.obtenerReportesPendientes(policiaActual);

        List<CategoriaDelito> categorias = repoReporte.listarCategorias();
        Map<Integer, String> mapaCategorias = categorias.stream()
                .collect(Collectors.toMap(CategoriaDelito::getCategoriaId,
                        CategoriaDelito::getTipoDeUrgencia));

        List<ReportePoliciaView> datosVista = reportes.stream().map(r -> {
            String nombreCiudadano = repoUsuario.buscarPorId(r.getCiudadanoId())
                    .map(Usuario::getNombreCompleto)
                    .orElse("Desconocido (ID " + r.getCiudadanoId() + ")");

            String nombreDelito = repoDelito.buscarPorId(r.getDelitoId())
                    .map(Delito::getDelitoNombre)
                    .orElse("No identificado");

            String urgencia = mapaCategorias.getOrDefault(r.getCategoriaDelitoId(), "Desconocida");

            return new ReportePoliciaView(
                    r.getReporteId(),
                    nombreCiudadano,
                    nombreDelito,
                    r.getFechaDelito().format(formatter),
                    urgencia
            );
        }).collect(Collectors.toList());

        ObservableList<ReportePoliciaView> listaObservable = FXCollections.observableArrayList(datosVista);
        tablaReportes.setItems(listaObservable);
    }

    @FXML
    private void atenderReporte(ActionEvent event) {
        gestionarReporteSeleccionado("Atendido", true);
    }

    @FXML
    private void rechazarReporte(ActionEvent event) {
        gestionarReporteSeleccionado("Denegado", false);
    }

    private void gestionarReporteSeleccionado(String nuevoEstado, boolean elegirEstado) {

        ReportePoliciaView seleccionado = tablaReportes.getSelectionModel().getSelectedItem();

        if (seleccionado == null) {
            Alerta.mostrar("Selección Requerida", "Por favor, seleccione un reporte de la tabla.");
            return;
        }

        if (reporteServicio.gestionarReporte(this.policiaActual, seleccionado.idReporte().intValue(), elegirEstado)) {
            Alerta.mostrar("Exito", "El reporte ha sido marcado como " + nuevoEstado);
            cargarDatos();
        } else Alerta.mostrar("Error", "No se pudo actualizar el estado del reporte.");

    }

    private void configurarColumnas() {
        columnaEnviadoPor.setCellValueFactory(cell -> cell
                .getValue()
                .enviadoPorProperty());
        columnaSupuestoDelito.setCellValueFactory(cell -> cell
                .getValue()
                .supuestoDelitoProperty());
        columnaFecha.setCellValueFactory(cell -> cell
                .getValue()
                .fechaReporteProperty());
        columnaUrgencia.setCellValueFactory(cell -> cell
                .getValue()
                .nivelUrgenciaProperty());

        tablaReportes.getColumns().forEach(t -> t.setReorderable(false));
    }

    @FXML
    private void regresarAlMenu(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/ui/dashboard/menu-policia.fxml"));
        Parent root = loader.load();

        MenuPoliciaController controller = loader.getController();
        controller.iniciarDatos(this.policiaActual);

        Scene escena = new Scene(root);
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setTitle("Menú Policía");
        stage.setScene(escena);
        stage.show();
    }



}
