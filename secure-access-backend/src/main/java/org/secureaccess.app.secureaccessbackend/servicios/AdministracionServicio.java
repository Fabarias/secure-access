package org.secureaccess.app.secureaccessbackend.servicios;

import org.secureaccess.app.secureaccessbackend.modelos.Usuario;
import org.secureaccess.app.secureaccessbackend.repositorios.RepositorioUsuario;

public class AdministracionServicio {

    private final RepositorioUsuario repositorioUsuario = new RepositorioUsuario();

    public void deshabilitarUsuario(Usuario administrador, int usuarioId) {

        if (!"ADMINISTRADOR".equalsIgnoreCase(administrador.getNombreRol())) {
            System.out.println("Operacion permitida solo por Administradores");
            return;
        }

        repositorioUsuario.actualizarEstado(usuarioId, 0);
    }

    public void habilitarUsuario(Usuario administrador, int usuarioId) {
        if (!"ADMINISTRADOR".equalsIgnoreCase(administrador.getNombreRol())) {
            System.out.println("Operación permitida solo por Administradores");
            return;
        }

        repositorioUsuario.actualizarEstado(usuarioId, 1);
    }

}
