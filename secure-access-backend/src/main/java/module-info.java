module org.secureaccess.app.secureaccessbackend {

    requires java.sql;
    requires jbcrypt;
    requires java.mail;

    exports org.secureaccess.app.secureaccessbackend.modelos;
    exports org.secureaccess.app.secureaccessbackend.servicios;
    exports org.secureaccess.app.secureaccessbackend.repositorios;
    exports org.secureaccess.app.secureaccessbackend.email;
}