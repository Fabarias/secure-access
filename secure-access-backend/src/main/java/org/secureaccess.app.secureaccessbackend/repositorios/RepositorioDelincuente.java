package org.secureaccess.app.secureaccessbackend.repositorios;

import org.secureaccess.app.secureaccessbackend.config.DataBaseControl;
import org.secureaccess.app.secureaccessbackend.modelos.Delincuente;
import org.secureaccess.app.secureaccessbackend.modelos.Usuario;

import java.sql.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class RepositorioDelincuente {

    public List<Delincuente> buscarTodos() {

        List<Delincuente> delincuentes = new ArrayList<>();
        String sql = "SELECT * FROM delincuentes";

        try (Connection connection = DataBaseControl.getConnection();
             Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(sql)) {

            while (resultSet.next()) {
                delincuentes.add(mapearDelincuente(resultSet));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return delincuentes;
    }

    public List<Delincuente> buscarPorLugarDeRequisitoria(int idDepartamento) {

        List<Delincuente> resultados = new ArrayList<>();
        String sql = "SELECT * FROM delincuentes WHERE lugar_de_requisitoria_id = ?";

        try (Connection connection = DataBaseControl.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {

             preparedStatement.setInt(1, idDepartamento);

             try (ResultSet resultSet = preparedStatement.executeQuery()) {

                 while (resultSet.next()) {
                     resultados.add(mapearDelincuente(resultSet));
                 }
             }


        } catch (SQLException e) {
            e.printStackTrace();
        }
        return resultados;
    }

    private Delincuente mapearDelincuente(ResultSet resultSet) throws SQLException {

        int id = resultSet.getInt("delincuente_id");
        String nombre = resultSet.getString("delincuente_primer_nombre");
        String primerApellido = resultSet.getString("delincuente_primer_apellido");
        String segundoApellido = resultSet.getString("delincuente_segundo_apellido");
        int lugarId = resultSet.getInt("lugar_de_requisitoria_id");
        int usuarioRegistroId = resultSet.getInt("usuario_registro_id");
        double recompensa = resultSet.getDouble("recompensa");

        Timestamp timestamp = resultSet.getTimestamp("fecha_registro");
        LocalDateTime fechaRegistro = (timestamp != null) ? timestamp.toLocalDateTime() : null;

        int reporteIdTemp = resultSet.getInt("reporte_origen_id");
        Integer reporteOrigenId = resultSet.wasNull() ? null : reporteIdTemp;

        return new Delincuente(
                id,
                nombre,
                primerApellido,
                segundoApellido,
                lugarId,
                usuarioRegistroId,
                reporteOrigenId,
                fechaRegistro,
                recompensa
        );
    }

    public void Top3DelicuentesBuscados(String departamento) {
        String sql = "SELECT * FROM delincuentes WHERE departamento = ?" + " limit 3";
        try (Connection connection = DataBaseControl.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql);
             ResultSet resultSet = preparedStatement.executeQuery();) {

            while (resultSet.next()) {
                String nombre = resultSet.getString("Nombre");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }



    public boolean guardar(Delincuente delincuente, Usuario usuarioEncargado) {
        String sql = "INSERT INTO delincuentes (delincuente_primer_nombre, delincuente_primer_apellido, " +
                "delincuente_segundo_apellido, lugar_de_requisitoria_id, usuario_registro_id," +
                " recompensa, fecha_registro, reporte_origen_id) " +
                "VALUES (?, ?, ?, ?, ?, ?, ?, ?)";

        try (Connection connection = DataBaseControl.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {

             preparedStatement.setString(1, delincuente.getDelincuentePrimerNombre());
             preparedStatement.setString(2, delincuente.getDelincuentePrimerApellido());
             preparedStatement.setString(3, delincuente.getDelincuenteSegundoApellido());
             preparedStatement.setInt(4, delincuente.getLugarDeRequisitoriaId());
             preparedStatement.setInt(5, usuarioEncargado.getUsuarioId());
             preparedStatement.setDouble(6, delincuente.getRecompensa());

             LocalDateTime fecha = delincuente.getFechaRegistro() != null ? delincuente.getFechaRegistro() : LocalDateTime.now();
             preparedStatement.setTimestamp(7, Timestamp.valueOf(fecha));

             preparedStatement.setObject(8, delincuente.getReporteOrigenId(), Types.INTEGER);

             return preparedStatement.executeUpdate() > 0;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
}
