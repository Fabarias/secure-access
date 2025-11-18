package org.secureaccess.app.secureaccessbackend.servicios;

import org.secureaccess.app.secureaccessbackend.modelos.Reporte;
import org.secureaccess.app.secureaccessbackend.modelos.Usuario;
import org.secureaccess.app.secureaccessbackend.repositorios.RepositorioReporte;

import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;

public class ReporteServicio {

    RepositorioReporte repositorioReporte = new RepositorioReporte();

    public boolean crearReporte(Usuario ciudadano,
                                String delito,
                                String departamento,
                                String descripcion) throws SQLException {

        if (ciudadano.getRolId() != 3) {
            return false;
        }

        int categoriaDelitoID = repositorioReporte.IndiceCategoriaDelito(delito);

        if (categoriaDelitoID == 0) {
            return false;
        }

        Reporte nuevoReporte = new Reporte(
                categoriaDelitoID,
                departamento,
                ciudadano.getUsuarioId(),
                LocalDateTime.now(),
                descripcion
        );

        boolean exito = repositorioReporte.guardar(nuevoReporte);

        System.out.println("Reporte guardado con éxito: " + exito);
        return exito;
    }

    public List<Reporte> obtenerReportesPendientes(Usuario usuarioSolicitud) {
        if (usuarioSolicitud.getRolId() != 2) {
            return Collections.emptyList();
        }

        return repositorioReporte.listarPorEstado("Pendiente");
    }

    public void gestionarReporte(Usuario policia, int idReporte, boolean aceptar) {

        if (policia.getRolId() != 2) {
            return;
        }
        String nuevoEstado = aceptar ? "Aceptado" : "Rechazado";
        boolean actualizado = repositorioReporte.actualizarEstado(idReporte, nuevoEstado);
    }
}
