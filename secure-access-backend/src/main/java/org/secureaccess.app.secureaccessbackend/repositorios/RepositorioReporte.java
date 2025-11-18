package org.secureaccess.app.secureaccessbackend.repositorios;

import org.secureaccess.app.secureaccessbackend.config.DataBaseControl;
import org.secureaccess.app.secureaccessbackend.modelos.CategoriaDelito;
import org.secureaccess.app.secureaccessbackend.modelos.Reporte;

import java.nio.channels.ScatteringByteChannel;
import java.sql.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class RepositorioReporte {

    public boolean guardar(Reporte reporte) {
        String sql = "INSERT INTO tablareportes (categoriaDelitoID, departamento, ciudadanoId, fechaDelito, estadoReporte, descripción) " +
                "VALUES (?, ?, ?, ?, ?, ?)";

        System.out.println("CategoriaID: " + reporte.getCategoriaDelitoId());
        System.out.println("Departamento: " + reporte.getDepartamento());
        System.out.println("CiudadanoID: " + reporte.getCiudadanoId());
        System.out.println("Estado: " + reporte.getEstadoReporte());  // ← IMPORTANTE: debe ser "Espera"

        try (Connection connection = DataBaseControl.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {

            preparedStatement.setInt(1, reporte.getCategoriaDelitoId());
            preparedStatement.setString(2, reporte.getDepartamento());
            preparedStatement.setInt(3, reporte.getCiudadanoId());
            preparedStatement.setTimestamp(4, Timestamp.valueOf(reporte.getFechaDelito()));
            preparedStatement.setString(5, reporte.getEstadoReporte());
            preparedStatement.setString(6, reporte.getDescripcion());

            int filasAfectadas = preparedStatement.executeUpdate();

            return filasAfectadas > 0;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
    public List<Reporte> listarPorEstado(String estadoFiltro) {

        List<Reporte> lista = new ArrayList<>();
        String sql = (estadoFiltro == null) ? "SELECT * FROM tablareportes" : "SELECT * FROM tablareportes WHERE estadoReporte = ?";

        try (Connection connection = DataBaseControl.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {

            if (estadoFiltro != null) {
                preparedStatement.setString(1, estadoFiltro);
            }

            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                while (resultSet.next()) {
                    lista.add(mapearReporte(resultSet));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return lista;
    }

    public boolean actualizarEstado(int reporteId, String nuevoEstado) {

        String sql = "UPDATE tablareportes SET estadoReporte = ? WHERE reporteId = ?";

        try (Connection connection = DataBaseControl.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {

             preparedStatement.setString(1, nuevoEstado);
             preparedStatement.setInt(2, reporteId);

             return preparedStatement.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
    private Reporte mapearReporte(ResultSet resultSet) throws SQLException {
        Timestamp timestamp = resultSet.getTimestamp("fechaDelito");
        LocalDateTime fecha = (timestamp != null) ? timestamp.toLocalDateTime() : null;

        return new Reporte(
                resultSet.getInt("reporteID"),
                resultSet.getInt("categoriaDelitoID"),
                resultSet.getString("departamento"),
                resultSet.getInt("ciudadanoId"),
                fecha,
                resultSet.getString("estadoReporte"),
                resultSet.getString("descripcion")
        );
    }
   public int IndiceCategoriaDelito (String nombreDelito) throws SQLException {
     String query = "Select categoriaID from categoriadelito where nombreDelito = '"+nombreDelito+"'";
       int indice = 0;
      try(Connection connection = DataBaseControl.getConnection();
          PreparedStatement preparedStatement = connection.prepareStatement(query)){
          ResultSet resultSet = preparedStatement.executeQuery();
          while (resultSet.next()) {
               indice = resultSet.getInt("categoriaID");
          }
      }
      return indice;
   }
}
