package org.secureaccess.app.secureaccessbackend.repositorios;


import org.secureaccess.app.secureaccessbackend.config.DataBaseControl;
import org.secureaccess.app.secureaccessbackend.modelos.Usuario;


import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class RepositorioUsuario {

    public Optional<Usuario> buscarPorId(int id) {
        String sql = "SELECT * FROM usuarios WHERE usuario_id = ?";

        try (Connection connection = DataBaseControl.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {

            preparedStatement.setInt(1, id);

            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    return Optional.of(mapearUsuario(resultSet));
                }
            }
        } catch (SQLException e) {
            System.err.println("Error buscando usuario por ID: " + e.getMessage());
        }
        return Optional.empty();
    }

    public Optional<Usuario> buscarPorNombreUsuario(String nombreUsuario) {

        String sql = "SELECT * FROM usuarios WHERE usuario_username = ?";

        try (Connection connection = DataBaseControl.getConnection();
             PreparedStatement stmt = connection.prepareStatement(sql)) {

            stmt.setString(1, nombreUsuario);

            try (ResultSet resultSet = stmt.executeQuery()) {
                if (resultSet.next()) {
                    return Optional.of(mapearUsuario(resultSet));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return Optional.empty();
    }

    public List<Usuario> listarTodos() {
        List<Usuario> lista = new ArrayList<>();
        String sql = "SELECT * FROM usuarios";

        try (Connection connection = DataBaseControl.getConnection();
             Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(sql)) {

            while (resultSet.next()) {
                lista.add(mapearUsuario(resultSet));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return lista;
    }

    public boolean guardar(Usuario usuario) {
        String sql = "INSERT INTO usuarios (usuario_primer_nombre, " +
                "usuario_primer_apellido, " +
                "usuario_segundo_apellido, " +
                "usuario_correo, " +
                "usuario_username, " +
                "usuario_clave, " +
                "usuario_rol_id, " +
                "usuario_estado) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";

        try (Connection conn = DataBaseControl.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, usuario.getPrimerNombre());
            stmt.setString(2, usuario.getPrimerApellido());
            stmt.setString(3, usuario.getSegundoApellido());
            stmt.setString(4, usuario.getCorreo());
            stmt.setString(5, usuario.getUsername());
            stmt.setString(6, usuario.getClave());
            stmt.setInt(7, usuario.getRolId());
            stmt.setInt(8, usuario.getEstado());

            return stmt.executeUpdate() > 0;

        } catch (SQLException e) {
            System.err.println("Error al guardar usuario: " + e.getMessage());
            e.printStackTrace();
            return false;
        }
    }

    public boolean actualizarEstado(int idUsuario, int nuevoEstado) {

        String sql = "UPDATE usuarios SET usuario_estado = ? WHERE usuario_id = ?";

        try (Connection connection = DataBaseControl.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {

             preparedStatement.setInt(1, nuevoEstado);
             preparedStatement.setInt(2, idUsuario);

             return preparedStatement.executeUpdate() > 0;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }

    }

    private Usuario mapearUsuario(ResultSet resultSet) throws SQLException {

        return new Usuario(
                resultSet.getInt("usuario_id"),
                resultSet.getString("usuario_primer_nombre"),
                resultSet.getString("usuario_primer_apellido"),
                resultSet.getString("usuario_segundo_apellido"),
                resultSet.getString("usuario_correo"),
                resultSet.getString("usuario_username"),
                resultSet.getString("usuario_clave"),
                resultSet.getInt("usuario_rol_id"),
                resultSet.getInt("usuario_estado")
        );
    }
}

