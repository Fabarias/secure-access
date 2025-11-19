package org.secureaccess.app.secureaccessbackend.repositorios;

import org.secureaccess.app.secureaccessbackend.config.DataBaseControl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Optional;

public class RepositorioDelito {

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
