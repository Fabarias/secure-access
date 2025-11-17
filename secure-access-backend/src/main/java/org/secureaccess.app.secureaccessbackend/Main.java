package org.secureaccess.app.secureaccessbackend;

import org.secureaccess.app.secureaccessbackend.modelos.Usuario;
import org.secureaccess.app.secureaccessbackend.servicios.AutenticacionDeServicio;


import java.sql.SQLException;
import java.util.Optional;

public class Main {
    public static void main(String[] args) throws SQLException {
        AutenticacionDeServicio authService = new AutenticacionDeServicio();

        String usuarioAdmin = "Admin";
        String claveAdmin = "Jonathantl";

        System.out.println("Intentando iniciar sesión con: " + usuarioAdmin);

        Optional<Usuario> resultado = authService.iniciarSesion(usuarioAdmin, claveAdmin);

        // 4. Verificar el resultado
        if (resultado.isPresent()) {
            Usuario u = resultado.get();

            System.out.println("\n¡LOGIN EXITOSO!");
            System.out.println("----------------------------------");
            System.out.println("Nombre: " + u.getNombre() + " " + u.getApellido());
            System.out.println("Rol ID: " + u.getRolId());
        }
    }
}

