package org.secureaccess.app.secureaccessfrontend.controllers.registersControllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextArea;
import javafx.stage.Stage;
import javafx.util.StringConverter;
import org.secureaccess.app.secureaccessbackend.modelos.CategoriaDelito;
import org.secureaccess.app.secureaccessbackend.modelos.Delito;
import org.secureaccess.app.secureaccessbackend.modelos.LugarRequisitoria;
import org.secureaccess.app.secureaccessbackend.modelos.Usuario;
import org.secureaccess.app.secureaccessbackend.repositorios.RepositorioLugarRequisitoria;
import org.secureaccess.app.secureaccessbackend.servicios.ReporteServicio;
import org.secureaccess.app.secureaccessfrontend.controllers.menuControllers.OpcionesDelMenuCiudadanoController;
import org.secureaccess.app.secureaccessfrontend.util.Alerta;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class RegistroReporteController implements Initializable {

    @FXML
    private ComboBox<LugarRequisitoria> comboDepartamento;
    @FXML
    private ComboBox<CategoriaDelito> comboGravedad;
    @FXML
    private TextArea txtDescripcion;

    private final ReporteServicio reporteServicio = new ReporteServicio();
    private final RepositorioLugarRequisitoria repoRequisitoria = new RepositorioLugarRequisitoria();
    private Usuario ciudadanoActual;

    public void setCiudadanoActual(Usuario ciudadano) {
        this.ciudadanoActual = ciudadano;
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        cargarDepartamentos();
        cargarCategoriasGravedad();
    }

    private void cargarDepartamentos() {

        comboDepartamento.getItems().addAll(repoRequisitoria.listarTodos());
        comboDepartamento.setConverter(new StringConverter<>() {
            @Override
            public String toString(LugarRequisitoria lugarRequisitoria) {
                return (lugarRequisitoria != null) ? lugarRequisitoria.getLugarDeRequisitoriaNombre() : "";
            }

            @Override
            public LugarRequisitoria fromString(String s) {
                return null;
            }
        });
    }

    private void cargarCategoriasGravedad() {

        comboGravedad.getItems().addAll(reporteServicio.obtenerCatalogoDeGravedad());

        comboGravedad.setConverter(new StringConverter<>() {
            @Override
            public String toString(CategoriaDelito categoriaDelito) {
                return categoriaDelito != null ? categoriaDelito.getTipoDeUrgencia() : "";
            }

            @Override
            public CategoriaDelito fromString(String s) {
                return null;
            }
        });
    }

    @FXML
    private void enviarReporte(ActionEvent event) {

        LugarRequisitoria lugarRequisitoria = comboDepartamento.getValue();
        CategoriaDelito gravedad = comboGravedad.getValue();
        String descripcion = txtDescripcion.getText();

        if (lugarRequisitoria == null || gravedad == null || descripcion.trim().isEmpty()) {
            Alerta.mostrar("Datos Incompletos",
                    "Por favor seleccione un departamento, una gravedad y describa el suceso");
            return;
        }

        if (ciudadanoActual == null) {
            Alerta.mostrar("Error de Sesión", "No se ha notificado al usuario ciudadano");
            return;
        }

        try {

            Delito delitoDetectado = reporteServicio.analizarDescripcion(descripcion);

            if (delitoDetectado == null) {
                Alerta.mostrar("Aviso" ,
                        "No se pudo identificar un delito específico en su descripción. Por favor sea más detallado");
                return;
            }

            boolean exito = reporteServicio.crearReporte(
                    ciudadanoActual,
                    delitoDetectado,
                    gravedad,
                    lugarRequisitoria.getLugarDeRequisitoriaNombre(),
                    descripcion
            );

            if (exito) {
                Alerta.mostrar("Reporte Enviado", "Su reporte ha sido registrado exitosamente.");
                regresar(event);
            } else Alerta.mostrar("Error",
                    "No se pudo guardar al reporte.");

        } catch (SQLException | IOException e) {
            e.printStackTrace();
            Alerta.mostrar("Error de Sistema",
                    "Hubo un problema de conexión con la base de datos");
        }
    }

    @FXML
    private void regresar(ActionEvent event) throws IOException {

        FXMLLoader loader = new FXMLLoader(getClass().getResource("/ui/menu/OpcionesDeMenuCiudadanoView.fxml"));
        Parent root = loader.load();

        if (loader.getController() instanceof OpcionesDelMenuCiudadanoController) {
            OpcionesDelMenuCiudadanoController controller = loader.getController();
            controller.iniciarDatos(ciudadanoActual);
        }

        Scene scene = new Scene(root);
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setTitle("Menú Ciudadano");
        stage.setScene(scene);
        stage.show();
    }
}