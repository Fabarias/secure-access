package org.secureaccess.app.secureaccessbackend.repositorios;


import org.secureaccess.app.secureaccessbackend.config.DataBaseControl;
import org.secureaccess.app.secureaccessbackend.modelos.Usuario;


import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class RepositorioUsuario {

    public Optional<Usuario> buscarPorNombreUsuario(String nombreUsuario) {

        String sql = "SELECT * FROM usuarios WHERE nombre_usuario = ?";

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
        String sql = "INSERT INTO usuarios (nombre, apellido, nombre_usuario, usuario_clave, rol_id, estado) VALUES (?, ?, ?, ?, ?, ?)";

        try (Connection conn = DataBaseControl.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, usuario.getNombre());
            stmt.setString(2, usuario.getApellido());
            stmt.setString(3, usuario.getNombreUsuario());
            stmt.setString(4, usuario.getUsuarioClave());
            stmt.setInt(5, usuario.getRolId());
            stmt.setInt(6, usuario.getEstado());

            int filasAfectadas = stmt.executeUpdate();
            return filasAfectadas > 0;

        } catch (SQLException e) {
            System.err.println("Error al guardar usuario: " + e.getMessage());
            e.printStackTrace();
            return false;
        }
    }

    public boolean actualizarEstado(int idUsuario, int nuevoEstado) {

        String sql = "UPDATE usuarios SET estado = ? WHERE usuario_id = ?";

        try (Connection connection = DataBaseControl.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {

             preparedStatement.setInt(1, nuevoEstado);
             preparedStatement.setInt(2, idUsuario);

             int filasAfectadas = preparedStatement.executeUpdate();

             if (filasAfectadas > 0) {
                 String accion = (nuevoEstado == 1) ? "Habilitado" : "Deshabilitado";
                 System.out.println("Usuario ID " + idUsuario + " ha sido " + accion);
                 return true;
             }
             return false;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    private Usuario mapearUsuario(ResultSet resultSet) throws SQLException {
        Usuario usuario = new Usuario();
        usuario.setUsuarioId(resultSet.getInt("usuario_id"));
        usuario.setNombre(resultSet.getString("nombre"));
        usuario.setApellido(resultSet.getString("apellido"));

        // Seteamos los campos que antes eran de Credencial
        usuario.setNombreUsuario(resultSet.getString("nombre_usuario"));
        usuario.setUsuarioClave(resultSet.getString("usuario_clave"));
        usuario.setRolId(resultSet.getInt("rol_id"));
        usuario.setEstado(resultSet.getInt("estado"));

        return usuario;
    }
}

