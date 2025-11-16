package org.secureaccess.app.secureaccessbackend.modelo;
import org.secureaccess.app.secureaccessbackend.BD.CRUD;
import org.secureaccess.app.secureaccessbackend.nucleo.AutenticacionDeServicio;

import java.util.Scanner;

public class UsuarioAdministrador extends Usuario{
AutenticacionDeServicio auntenticacion = new AutenticacionDeServicio();
Scanner lec = new Scanner(System.in);

    private final CRUD crud = new CRUD();

    public UsuarioAdministrador(int activo, String nombre, String apellido) {
        super(activo, nombre, apellido);
    }

    public String getRol(){
      return super.getCredencial().getRolUsuario();
    }
   public void pedirDatos(){
       System.out.println("Registrando nuevo Usuario Policia");
       System.out.println("Ingrese su nombre");
       String nombre = lec.nextLine();
       System.out.println("Ingrese su apellido");
       String apellido = lec.nextLine();
       System.out.println("Ingrese su nombre de Usuario");
       String userName = lec.nextLine();
       System.out.println("Ingrese su clave");
       String clave = lec.nextLine();
       auntenticacion.registro(nombre,apellido,userName,clave,3);
   }
    public void crearPolicia(){ pedirDatos();}

    public void deshabilitarAcceso(int id){crud.deshabilitarEstado(id);}

    public void habilitarAcceso(int id){crud.HabilitarEstado(id);}

    public void MostrarInformacionDeUsuarios(){crud.mostrarInformacionUsuarios();}

    public void MostrarInformacionDelincuentes(){crud.mostrarInformacionDelincuentes();}
}
