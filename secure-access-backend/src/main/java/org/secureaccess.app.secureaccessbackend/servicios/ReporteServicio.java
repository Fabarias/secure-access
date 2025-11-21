package org.secureaccess.app.secureaccessbackend.servicios;

import org.secureaccess.app.secureaccessbackend.modelos.CategoriaDelito;
import org.secureaccess.app.secureaccessbackend.modelos.Delito;
import org.secureaccess.app.secureaccessbackend.modelos.Reporte;
import org.secureaccess.app.secureaccessbackend.modelos.Usuario;
import org.secureaccess.app.secureaccessbackend.repositorios.RepositorioDelito;
import org.secureaccess.app.secureaccessbackend.repositorios.RepositorioReporte;

import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

public class ReporteServicio {

    private final RepositorioReporte repositorioReporte = new RepositorioReporte();
    private final RepositorioDelito repositorioDelito = new RepositorioDelito();

    public List<CategoriaDelito> obtenerCatalogoDeGravedad() {
        return repositorioReporte.listarCategorias();
    }

    public List<Delito> obtenerTodosLosDelitos() {
        return repositorioDelito.listarArchivos();
    }

    public Delito analizarDescripcion(String descripcion) {

        Optional<Integer> idDectetado = repositorioDelito.detectarDelitoPorPalabrasClave(descripcion);

        return idDectetado.flatMap(repositorioDelito::buscarPorId)
                .orElse(null);
    }

    public boolean crearReporte(Usuario ciudadano,
                                Delito delito,
                                CategoriaDelito gravedad,
                                String departamento,
                                String descripcion) throws SQLException {

        if (ciudadano.getRolId() != 3) return false;

        if (delito == null || gravedad == null) return false;

        Reporte nuevoReporte = new Reporte(
                gravedad.getCategoriaId(),
                delito.getDelitoId(),
                departamento,
                ciudadano.getUsuarioId(),
                LocalDateTime.now(),
                descripcion
        );

        return repositorioReporte.guardar(nuevoReporte);
    }

    public List<Reporte> obtenerReportesPendientes(Usuario usuarioSolicitud) {
        if (usuarioSolicitud.getRolId() != 2) return Collections.emptyList();

        return repositorioReporte.listarPorEstado("Espera");
    }

    public List<Reporte> obtenerHistorialPersonal(Usuario ciudadano) {

        if (ciudadano.getUsuarioId() != 3) return Collections.emptyList();

        return repositorioReporte.listarPorCiudadano(ciudadano.getUsuarioId());
    }

    public void gestionarReporte(Usuario policia, int idReporte, boolean aceptar) {

        if (policia.getRolId() != 2) return;

        String nuevoEstado = aceptar ? "Atendido" : "Denegado";

        repositorioReporte.actualizarEstado(idReporte, nuevoEstado);
    }
}
