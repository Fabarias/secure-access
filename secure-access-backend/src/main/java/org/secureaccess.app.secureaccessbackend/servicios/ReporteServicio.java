package org.secureaccess.app.secureaccessbackend.servicios;

import org.secureaccess.app.secureaccessbackend.modelos.Reporte;
import org.secureaccess.app.secureaccessbackend.modelos.Usuario;
import org.secureaccess.app.secureaccessbackend.repositorios.RepositorioReporte;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;

public class ReporteServicio {

    RepositorioReporte repositorioReporte = new RepositorioReporte();

    public boolean crearReporte(Usuario ciudadano,
                                int idCategoriaDelito,
                                String departamento,
                                String descripcion) {
        if (ciudadano.getRolId() != 3) {

            System.out.println("Los policias no crean reportes por esta vía");
            return false;
        }

        Reporte nuevoReporte = new Reporte(
                idCategoriaDelito,
                departamento,
                ciudadano.getUsuarioId(),
                LocalDateTime.now(),
                descripcion
        );

        boolean exito = repositorioReporte.guardar(nuevoReporte);
        if (exito) System.out.println("Reporte enviado correctamente!");
        return exito;
    }

    public List<Reporte> obtenerReportesPendientes(Usuario usuarioSolicitud) {
        if (usuarioSolicitud.getRolId() != 2) {
            System.out.println("Funciión autorizada solo para policias");
            return Collections.emptyList();
        }

        return repositorioReporte.listarPorEstado("Pendiente");
    }

    public void gestionarReporte(Usuario policia, int idReporte, boolean aceptar) {

        if (policia.getRolId() != 2) {
            System.out.println("Solamente usuarios con rol de POLICIA puede hacer esta operación");
            return;
        }

        String nuevoEstado = aceptar ? "Aceptado" : "Rechazado";
        boolean actualizado = repositorioReporte.actualizarEstado(idReporte, nuevoEstado);

        if (actualizado) {
            System.out.println("ReporteID " + idReporte + " marcado como " + nuevoEstado);
        } else {
            System.out.println("Error al actualizar el reporte");
        }
    }
}
