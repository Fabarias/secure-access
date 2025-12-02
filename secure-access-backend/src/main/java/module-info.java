module org.secureaccess.app.secureaccessbackend {

    requires java.sql;
    requires jbcrypt;
    requires jakarta.mail;
    requires jakarta.activation;

    exports org.secureaccess.app.secureaccessbackend.modelos;
    exports org.secureaccess.app.secureaccessbackend.servicios;
    exports org.secureaccess.app.secureaccessbackend.repositorios;
    exports org.secureaccess.app.secureaccessbackend.email;
    exports org.secureaccess.app.secureaccessbackend.config;
}