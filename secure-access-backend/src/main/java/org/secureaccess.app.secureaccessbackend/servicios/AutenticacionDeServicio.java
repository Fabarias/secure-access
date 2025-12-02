package org.secureaccess.app.secureaccessbackend.servicios;

import org.secureaccess.app.secureaccessbackend.modelos.Usuario;
import org.secureaccess.app.secureaccessbackend.repositorios.RepositorioUsuario;
import org.mindrot.jbcrypt.BCrypt;


import java.util.Optional;

public class AutenticacionDeServicio {

    private final RepositorioUsuario repositorioUsuario = new RepositorioUsuario();

    public Usuario registro(String primerNombre,
                            String primerApellido,
                            String segundoApellido,
                            String correo,
                            String nombreDeUsuario,
                            String clave,
                            int rolId) {

        if (repositorioUsuario.buscarPorNombreUsuario(nombreDeUsuario).isPresent()) {
            return null;
        }

        String claveCifrado = BCrypt.hashpw(clave, BCrypt.gensalt());

        Usuario nuevoUsuario = new Usuario(primerNombre,
                primerApellido,
                segundoApellido,
                correo,
                nombreDeUsuario,
                claveCifrado,
                rolId);

        boolean guardado = repositorioUsuario.guardar(nuevoUsuario);

        if (guardado) {
            return nuevoUsuario;
        } else {
            return null;
        }
    }

    public Optional<Usuario> iniciarSesion(String nombreUsuario, String claveIngresada) {
            Optional<Usuario> optionalUsuario = repositorioUsuario.buscarPorNombreUsuario(nombreUsuario);

            if (optionalUsuario.isEmpty()) {
                return Optional.empty();
            }

            Usuario usuario = optionalUsuario.get();

            if (!usuario.estaActivo()) {
                return Optional.empty();
            }

            if (BCrypt.checkpw(claveIngresada, usuario.getClave())) {
                usuario.setClave(null);
                return Optional.of(usuario);

            } else {
                return Optional.empty();
            }
    }

    public String generarNombreUsuarioUnico(String nombre, String apellido) {
        String limpiarNombre = nombre.trim().toLowerCase().split(" ")[0];
        String limpiarApellido = apellido.trim().toLowerCase();

        for (int i = 1; i <= limpiarNombre.length(); i++) {
            String prefijo = limpiarNombre.substring(0, i);
            String candidatoUsuario = prefijo + limpiarApellido;

            if (repositorioUsuario.buscarPorNombreUsuario(candidatoUsuario).isEmpty()) {
                return candidatoUsuario;
            }
        }
        return null;
    }
}