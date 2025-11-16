package org.secureaccess.app.secureaccessbackend;

import org.secureaccess.app.secureaccessbackend.modelo.UsuarioAdministrador;
import org.secureaccess.app.secureaccessbackend.nucleo.AutenticacionDeServicio;


import java.util.Optional;
import java.util.Scanner;
//12345
public class Main {
    public static void main(String[] args) {
        Scanner lec = new Scanner(System.in);
        AutenticacionDeServicio auth = new AutenticacionDeServicio();
        Optional <UsuarioAdministrador> admin = auth.iniciarSesion(UsuarioAdministrador.class,"Admin","Jonathantl");
        admin.ifPresent(UsuarioAdministrador::crearPolicia);
      }
    }

