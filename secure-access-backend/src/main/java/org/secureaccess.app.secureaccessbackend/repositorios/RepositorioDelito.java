package org.secureaccess.app.secureaccessbackend.repositorios;

import org.secureaccess.app.secureaccessbackend.config.DataBaseControl;
import org.secureaccess.app.secureaccessbackend.modelos.Delito;

import java.sql.*;
import java.util.*;

public class RepositorioDelito {

    public Optional<Integer> detectarDelitoPorPalabrasClave(String descripcion) {
        if (descripcion == null || descripcion.isEmpty()) {
            return Optional.empty();
        }

        String sql = "SELECT palabra, delito_id FROM palabras_clave_delito";
        String descripcionNormalizada = descripcion.toLowerCase();

        try (Connection connection = DataBaseControl.getConnection();
             Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(sql)) {

            while (resultSet.next()) {
                String palabraClave = resultSet.getString("palabra").toLowerCase();

                if (descripcionNormalizada.contains(palabraClave)) {
                    return Optional.of(resultSet.getInt("delito_id"));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return Optional.empty();
    }


    public Optional<Delito> buscarPorId(int delitoId) {
        String sql = "SELECT * FROM delitos WHERE delito_id = ?";

        try (Connection connection = DataBaseControl.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {

             preparedStatement.setInt(1, delitoId);

             try (ResultSet resultSet = preparedStatement.executeQuery()) {

                 if (resultSet.next()) {
                     return Optional.of(new Delito(
                             resultSet.getInt("delito_id"),
                             resultSet.getString("delito_nombre"),
                             resultSet.getInt("estado")
                     ));
                 }
             }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return Optional.empty();
    }

    public List<Delito> listarArchivos() {

        List<Delito> lista = new ArrayList<>();
        String sql = "SELECT * FROM delitos WHERE estado = 1";

        try (Connection connection = DataBaseControl.getConnection();
             Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(sql)) {

             while (resultSet.next()) {

                 Delito delito = new Delito(
                         resultSet.getInt("delito_id"),
                         resultSet.getString("delito_nombre"),
                         resultSet.getInt("estado")
                 );
                 lista.add(delito);
             }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return lista;
    }


    public Optional<String> buscarNombrePorDelincuenteId(int delincuenteId) {

        String sql = "SELECT d.delito_nombre FROM delincuentes_delitos dd " +
                "JOIN delitos d ON dd.delito_id = d.delito_id " +
                "WHERE dd.delincuente_id = ? " +
                "LIMIT 1";

        try (Connection conn = DataBaseControl.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, delincuenteId);

            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return Optional.of(rs.getString("delito_nombre"));
                }
            }
        } catch (SQLException e) {
            System.err.println("Error al buscar nombre del delito: " + e.getMessage());
        }
        return Optional.empty();
    }

}
