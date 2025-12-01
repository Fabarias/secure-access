package org.secureaccess.app.secureaccessfrontend.util;

import org.secureaccess.app.secureaccessbackend.email.ServicioEmail;

public class GestorServicios {

    private static final ServicioEmail servicioEmail = new ServicioEmail();

    public static ServicioEmail getServicioEmail() {
        return servicioEmail;
    }
}
