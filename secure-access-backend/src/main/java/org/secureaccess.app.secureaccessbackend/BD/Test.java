package org.secureaccess.app.secureaccessbackend.BD;

import java.util.Scanner;

public class Test {
    public static void main(String[] args) {
        Scanner lec = new Scanner(System.in);
        CRUD datos = new CRUD();
        datos.mostrarInformacionUsuarios();
    }
}
