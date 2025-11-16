package org.secureaccess.app.secureaccessbackend.procesarinformacion;


import org.secureaccess.app.secureaccessbackend.BD.CRUD;
import org.secureaccess.app.secureaccessbackend.modelo.Usuario;
import org.secureaccess.app.secureaccessbackend.modelo.UsuarioAdministrador;
import org.secureaccess.app.secureaccessbackend.modelo.UsuarioCiudadano;
import org.secureaccess.app.secureaccessbackend.modelo.UsuarioPolicia;


import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class RepositorioDeUsuario {

    // Acceso a la base de datos
    private final CRUD crud = new CRUD();
    // Caché en memoria de usuarios obtenidos o creados durante la ejecución
    private final List<Usuario> usuarios;

    public RepositorioDeUsuario() {
        this.usuarios = new ArrayList<>();
    }

    /**
     * Busca un usuario por su nombre de usuario.
     * Primero revisa la lista en memoria, y si no lo encuentra, consulta la base de datos.
     */
        public Optional<Usuario> encontrarPorUsuario(String nombreUsuario) {

            // Buscar en memoria
            Optional<Usuario> usuarioEnMemoria = usuarios.stream()
                    .filter(u -> u.getCredencial() != null &&
                            u.getCredencial().getNombreUsuario().equals(nombreUsuario))
                    .findFirst();

            if (usuarioEnMemoria.isPresent()) {
                return usuarioEnMemoria;
            }

            // Buscar en base de datos
            List<Usuario> usuariosBD = crud.datosUsuario(nombreUsuario);

            if (usuariosBD.isEmpty()) {
                return Optional.empty();
            }

            Usuario base = usuariosBD.get(0);

            String rol = base.getCredencial().getRolUsuario();
            String nombre = base.getNombre();
            String apellido = base.getApellido();
            int estado = base.getActivo();
            Usuario usuarioFinal;

            switch (rol) {
                case "Administrador":
                    usuarioFinal = new UsuarioAdministrador(estado,nombre, apellido);
                    break;
                case "Policia":
                    usuarioFinal = new UsuarioPolicia(nombre, apellido);
                    break;
                case "Usuario":
                    usuarioFinal = new UsuarioCiudadano(nombre, apellido);
                    break;
                default:
                    usuarioFinal = base; // fallback
            }

            usuarioFinal.setCredencial(base.getCredencial());

            usuarios.add(usuarioFinal);
            return Optional.of(usuarioFinal);
        }


    /**
     * Guarda un nuevo usuario si no existe ya.
     */
    public boolean save(Usuario usuario) {
        String nombreUsuario = usuario.getCredencial().getNombreUsuario();

        // Comprobamos si ya existe
        if (encontrarPorUsuario(nombreUsuario).isEmpty()) {
            usuarios.add(usuario);
            System.out.println("Usuario registrado: " + nombreUsuario);
            return true;
        } else {
            System.out.println("El usuario '" + nombreUsuario + "' ya existe.");
            return false;
        }
    }
}

