package org.secureaccess.app.secureaccessbackend.config;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class DataBaseControl {

    private static final Properties properties = new Properties();

    static {
        try (InputStream input = DataBaseControl.class.getClassLoader().getResourceAsStream("config.properties")) {

            if (input == null) {
                System.err.println("No se encontró el archivo config.properties en resources.");
                throw new RuntimeException("No se pudo encontrar config.properties");
            }
            properties.load(input);

        } catch (IOException e) {
            System.err.println("Error al leer el archivo de propiedades.");
            e.printStackTrace();
            throw new RuntimeException("Error al cargar config.properties", e);
        }
    }

    public static Connection getConnection() throws SQLException {
        try {
            return DriverManager.getConnection(
                    properties.getProperty("db.url"),
                    properties.getProperty("db.user"),
                    properties.getProperty("db.password")
            );
        } catch (SQLException e) {
            System.err.println("Error al establecer la conexión con la base de datos.");
            System.err.println("URL: " + properties.getProperty("db.url"));
            System.err.println("User: " + properties.getProperty("db.user"));
            throw e;
        }
    }
}

