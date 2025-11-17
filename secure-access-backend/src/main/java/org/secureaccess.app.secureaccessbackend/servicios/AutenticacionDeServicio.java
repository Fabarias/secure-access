package org.secureaccess.app.secureaccessbackend.servicios;

import org.secureaccess.app.secureaccessbackend.modelos.Usuario;
import org.secureaccess.app.secureaccessbackend.repositorios.RepositorioUsuario;
import org.mindrot.jbcrypt.BCrypt;


import java.util.Optional;

public class AutenticacionDeServicio {

    private final RepositorioUsuario repositorioUsuario = new RepositorioUsuario();

    public Usuario registro(String nombre,
                            String apellido,
                            String nombreDeUsuario,
                            String clave,
                            int rolId) {

        if (repositorioUsuario.buscarPorNombreUsuario(nombreDeUsuario).isPresent()) {
            System.out.println("No se encontro el usuario con nombre de " + nombreDeUsuario);
            return null;
        }

        String claveCifrado = BCrypt.hashpw(clave, BCrypt.gensalt());

        Usuario nuevoUsuario = new Usuario(nombre,
                apellido,
                nombreDeUsuario,
                claveCifrado,
                rolId,
                1);

        boolean guardado = repositorioUsuario.guardar(nuevoUsuario);

        if (guardado) {
            System.out.println("Registro exitoso para: " + nombreDeUsuario);
            return nuevoUsuario;
        } else {
            System.out.println("Error al guardar en la base de datos");
            return null;
        }
    }

    public Optional<Usuario> iniciarSesion(String nombreUsuario, String claveIngresada) {
            Optional<Usuario> optionalUsuario = repositorioUsuario.buscarPorNombreUsuario(nombreUsuario);

            if (optionalUsuario.isEmpty()) {
                System.out.println("Usuario no encontrado");
                return Optional.empty();
            }

            Usuario usuario = optionalUsuario.get();

            if (!usuario.estaActivo()) {
                return Optional.empty();
            }

            if (BCrypt.checkpw(claveIngresada, usuario.getUsuarioClave())) {
                System.out.println("Bienvenido");
                usuario.setUsuarioClave(null);

                return Optional.of(usuario);
            } else {
                return Optional.empty();
            }
    }


}