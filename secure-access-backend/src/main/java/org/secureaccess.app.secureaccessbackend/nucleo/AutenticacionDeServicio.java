package org.secureaccess.app.secureaccessbackend.nucleo;

import org.secureaccess.app.secureaccessbackend.BD.CRUD;
import org.secureaccess.app.secureaccessbackend.modelo.Credencial;
import org.secureaccess.app.secureaccessbackend.modelo.Usuario;
import org.secureaccess.app.secureaccessbackend.procesarinformacion.RepositorioDeUsuario;
import org.mindrot.jbcrypt.BCrypt;


import java.util.Optional;

public class AutenticacionDeServicio {

    private final CRUD crud = new CRUD();
    private final RepositorioDeUsuario repositorioUsuario = new RepositorioDeUsuario();
     public Usuario usuario;

    public Usuario registro(String nombre, String apellido, String nombreDeUsuario, String clave) {

        // Verificamos si el nombre de usuario ya existe
        if (repositorioUsuario.encontrarPorUsuario(nombreDeUsuario).isPresent()) {
            System.out.println("⚠️ El nombre de usuario ya existe. Intente con otro.");
            return null;
        }

        // Encriptar la contraseña
        String claveCifrada = BCrypt.hashpw(clave, BCrypt.gensalt());

        // Crear los objetos de dominio
        Usuario nuevoUsuario = new Usuario(nombre, apellido);
        Credencial credencial = new Credencial(nombreDeUsuario, claveCifrada, 2);
        nuevoUsuario.setCredencial(credencial);

        // Guardar en memoria y base de datos
        boolean guardado = repositorioUsuario.save(nuevoUsuario);

        if (guardado) {
            crud.registrandoUsuario(nombre, apellido, nombreDeUsuario, claveCifrada, 2);
            System.out.println("✅ Registro exitoso.");
            return nuevoUsuario;
        } else {
            System.out.println("❌ Error: no se pudo registrar el usuario.");
            return null;
        }
    }
    public Usuario registro(String nombre, String apellido, String nombreDeUsuario, String clave, int id) {

        // Verificamos si el nombre de usuario ya existe
        if (repositorioUsuario.encontrarPorUsuario(nombreDeUsuario).isPresent()) {
            System.out.println("⚠️ El nombre de usuario ya existe. Intente con otro.");
            return null;
        }

        // Encriptar la contraseña
        String claveCifrada = BCrypt.hashpw(clave, BCrypt.gensalt());

        // Crear los objetos de dominio
        Usuario nuevoUsuario = new Usuario(nombre, apellido);
        Credencial credencial = new Credencial(nombreDeUsuario, claveCifrada, id);
        nuevoUsuario.setCredencial(credencial);

        // Guardar en memoria y base de datos
        boolean guardado = repositorioUsuario.save(nuevoUsuario);

        if (guardado) {
            crud.registrandoUsuario(nombre, apellido, nombreDeUsuario, claveCifrada, id);
            System.out.println("Registro exitoso.");
            return nuevoUsuario;
        } else {
            System.out.println("Error: no se pudo registrar el usuario.");
            return null;
        }
    }

    public <T extends Usuario> Optional<T> iniciarSesion(
            Class<T> tipoEsperado,
            String nombreUsuario,
            String claveIngresada) {

        Optional<? extends Usuario> usuarioOpt = repositorioUsuario.encontrarPorUsuario(nombreUsuario);

        if (usuarioOpt.isEmpty()) {
            System.out.println("Usuario no encontrado.");
            return Optional.empty();
        }

        Usuario usuario = usuarioOpt.get();

        // Verificar tipo real coincide con el solicitado
        if (!tipoEsperado.isInstance(usuario)) {
            System.out.println("Tipo de usuario incorrecto para este login.");
            return Optional.empty();
        }

        if (usuario.getActivo() == 0) {
            System.out.println("Su cuenta está deshabilitada.");
            return Optional.empty();
        }

        if (!BCrypt.checkpw(claveIngresada, usuario.getCredencial().getClaveCifrada())) {
            System.out.println("Contraseña incorrecta.");
            return Optional.empty();
        }

        System.out.println("Ingreso Exitoso!");

        // Convertimos de forma segura
        return Optional.of(tipoEsperado.cast(usuario));
    }
}