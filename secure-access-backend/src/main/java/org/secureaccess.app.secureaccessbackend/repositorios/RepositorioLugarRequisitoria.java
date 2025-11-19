package org.secureaccess.app.secureaccessbackend.repositorios;

import org.secureaccess.app.secureaccessbackend.config.DataBaseControl;
import org.secureaccess.app.secureaccessbackend.modelos.LugarRequisitoria;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class RepositorioLugarRequisitoria {

    public List<LugarRequisitoria> listarTodos() {
        List<LugarRequisitoria> lista = new ArrayList<>();
        String sql = "SELECT lugar_de_requisitoria_id, lugar_de_requisitoria_nombre " +
                "FROM lugares_de_requisitoria " +
                "WHERE activo = 1";

        try (Connection connection = DataBaseControl.getConnection();
             Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(sql)) {

            while (resultSet.next()) {
                lista.add(mapearLugar(resultSet));
            }
        } catch (SQLException e) {
            System.err.println("Error al listar lugares de requisitoria: " + e.getMessage());
        }
        return lista;
    }

    public Optional<String> buscarNombrePorId(int id) {
        String sql = "SELECT lugar_de_requisitoria_nombre " +
                "FROM lugares_de_requisitoria " +
                "WHERE lugar_de_requisitoria_id = ?";

        try (Connection conn = DataBaseControl.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, id);

            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return Optional.of(rs.getString("lugar_de_requisitoria_nombre"));
                }
            }
        } catch (SQLException e) {
            System.err.println("Error al buscar nombre de lugar por ID: " + e.getMessage());
        }
        return Optional.empty();
    }

    private LugarRequisitoria mapearLugar(ResultSet rs) throws SQLException {
        LugarRequisitoria lugar = new LugarRequisitoria();
        lugar.setLugarDeRequisitoriaId(rs.getInt("lugar_de_requisitoria_id"));
        lugar.setLugarDeRequisitoriaNombre(rs.getString("lugar_de_requisitoria_nombre"));
        return lugar;
    }

}
