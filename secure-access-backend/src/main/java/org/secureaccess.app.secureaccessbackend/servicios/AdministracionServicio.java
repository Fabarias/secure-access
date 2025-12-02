package org.secureaccess.app.secureaccessbackend.servicios;

import org.secureaccess.app.secureaccessbackend.modelos.Usuario;
import org.secureaccess.app.secureaccessbackend.repositorios.RepositorioUsuario;

public class AdministracionServicio {

    private final RepositorioUsuario repositorioUsuario = new RepositorioUsuario();

    public boolean deshabilitarUsuario(Usuario administrador, int usuarioId) {

        if (!esAdmin(administrador)) return false;
        return repositorioUsuario.actualizarEstado(usuarioId, 0);
    }

    public boolean habilitarUsuario(Usuario administrador, int usuarioId) {

        if (!esAdmin(administrador)) return false;
        return repositorioUsuario.actualizarEstado(usuarioId, 1);
    }

    private boolean esAdmin(Usuario u) {
        return u != null && (u.getRolId() == 1 || "ADMINISTRADOR".equalsIgnoreCase(u.getNombreRol()));
    }
}
