package org.secureaccess.app.secureaccessbackend.repositorios;

import org.secureaccess.app.secureaccessbackend.config.DataBaseControl;
import org.secureaccess.app.secureaccessbackend.modelos.CategoriaDelito;
import org.secureaccess.app.secureaccessbackend.modelos.Reporte;

import java.sql.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class RepositorioReporte {


    public List<CategoriaDelito> listarCategorias() {

        List<CategoriaDelito> categorias = new ArrayList<>();
        String sql = "SELECT * FROM categoria_delito";

        try (Connection connection = DataBaseControl.getConnection();
             Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(sql)) {

            while (resultSet.next()) {
                categorias.add(new CategoriaDelito(
                        resultSet.getInt("categoria_id"),
                        resultSet.getString("nombre_delito"),
                        resultSet.getString("tipo_de_urgencia")
                ));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return categorias;
    }

    public boolean guardar(Reporte reporte) {
        String sql = "INSERT INTO tabla_reportes (categoriaDelitoID, departamento, ciudadanoId, fechaDelito, estadoReporte, descripción) " +
                "VALUES (?, ?, ?, ?, ?, ?)";

        try (Connection connection = DataBaseControl.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {

            preparedStatement.setInt(1, reporte.getCategoriaDelitoId());
            preparedStatement.setString(2, reporte.getDepartamento());
            preparedStatement.setInt(3, reporte.getCiudadanoId());

            LocalDateTime fecha = reporte.getFechaDelito() != null ? reporte.getFechaDelito() : LocalDateTime.now();
            preparedStatement.setTimestamp(4, Timestamp.valueOf(fecha));

            String estado = reporte.getEstadoReporte() != null ? reporte.getEstadoReporte() : "Espera";
            preparedStatement.setString(5, estado);

            preparedStatement.setString(6, reporte.getDescripcion());

            return preparedStatement.executeUpdate() > 0;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public List<Reporte> listarPorEstado(String estadoFiltro) {

        List<Reporte> lista = new ArrayList<>();
        String sql = (estadoFiltro == null)
                ? "SELECT * FROM reportes"
                : "SELECT * FROM reportes WHERE estado_reporte = ?";

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

    public List<Reporte> listarPorCiudadano(int ciudadanoId) {
        List<Reporte> lista = new ArrayList<>();
        String sql = "SELECT * FROM reportes WHERE ciudadano_id = ?";

        try (Connection connection = DataBaseControl.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {

            preparedStatement.setInt(1, ciudadanoId);

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

        String sql = "UPDATE reportes SET estado_reporte = ? WHERE reporte_id = ?";

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

    public int IndiceCategoriaDelito(String nombreDelito) throws SQLException {

        String sql = "SELECT categoria_id FROM categoria_delito WHERE nombre_delito = ?";
        int indice = 0;

        try (Connection connection = DataBaseControl.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {

            preparedStatement.setString(1, nombreDelito);

            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    indice = resultSet.getInt("categoria_id");
                }
            }
        }
        return indice;
    }
}
