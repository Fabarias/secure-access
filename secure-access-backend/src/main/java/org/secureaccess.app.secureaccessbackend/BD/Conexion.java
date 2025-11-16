package org.secureaccess.app.secureaccessbackend.BD;


import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Conexion {
    private String URL = "jdbc:mysql://localhost:3306/SecurityAccesLite";
    private String USER = "root";
    private String PASSWORD = "root";

    private static final String PARAMS =
            "?useSSL=false" +
                    "&serverTimezone=UTC" +
                    "&allowPublicKeyRetrieval=true" +
                    "&useUnicode=true" +
                    "&characterEncoding=UTF-8";

    public Connection Connection() throws SQLException {

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");

            Connection connection = DriverManager.getConnection(URL + PARAMS,
                    USER,
                    PASSWORD);

            System.out.println("Conexión exitosa!");
            return connection;
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }
}
