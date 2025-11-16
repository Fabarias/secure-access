package org.secureaccess.app.secureaccessbackend.BD;

import org.secureaccess.app.secureaccessbackend.modelo.Credencial;
import org.secureaccess.app.secureaccessbackend.modelo.Usuario;


import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class CRUD {
    Credencial credencial;
    Usuario usuario;
    ArrayList<Usuario> user = new ArrayList<>();
    Conexion con = new Conexion();
    Usuario usuario1;

    public String solicitarContraseña(String nombreDeUsuario) {
        String contra = "no encontrada";
        String query = "Select clave from usuarios_registrados where nombreDeUsuario = '" + nombreDeUsuario + "'";
        try {
            PreparedStatement preparedStatement = con.Connection().prepareStatement(query);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                contra = resultSet.getString("clave");
                System.out.println("La contraseña es " + contra);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return contra;
    }

    public List<Usuario> datosUsuario(String nombreDeUsuario) {
        user.clear(); // Limpia la lista para no acumular resultados antiguos
        String query = "CALL DatosUsuario('" + nombreDeUsuario + "')";

        try (CallableStatement statement = con.Connection().prepareCall(query)) {
            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()) {
                int usuarioID = resultSet.getInt("usuario_id");
                String nombre = resultSet.getString("nombre");
                String apellido = resultSet.getString("apellido");
                String nombreUsuario = resultSet.getString("nombre_usuario");
                String usuarioClave = resultSet.getString("usuario_clave");
                String rolUsuario = resultSet.getString("rol_nombre");
                int estado = resultSet.getInt("estado");
                Usuario usuario1 = new Usuario(estado, nombre, apellido);
                Credencial credencial1 = new Credencial(nombreUsuario, usuarioClave, rolUsuario);
                usuario1.setCredencial(credencial1);
                user.add(usuario1);
            }

        } catch (SQLException e) {
            System.out.println("❌ Error al obtener datos del usuario.");
            e.printStackTrace();
        }

        return user;
    }

    public void registrandoUsuario(String nombre, String apellido, String nombreDeUsuario, String clave, int rolID) {
        String query = "Insert into usuarios (nombre, apellido, nombre_usuario, usuario_clave, rol_id ) VALUES (?, ?, ? , ?, ?)";
        try {
            PreparedStatement preparedStatement = con.Connection().prepareStatement(query);
            //  preparedStatement.se
            preparedStatement.setString(1, nombre);
            preparedStatement.setString(2, apellido);
            preparedStatement.setString(3, nombreDeUsuario);
            preparedStatement.setString(4, clave);
            preparedStatement.setInt(5, rolID);
            preparedStatement.executeUpdate();
            System.out.println("Ingreso de datos completado con exito!");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void Top3DelicuentesBuscados(String departamento) {
        String query = "select * from personasbuscadas where departamento = '" + departamento + "' limit 3";
        try {
            PreparedStatement preparedStatement = con.Connection().prepareStatement(query);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                String nombre = resultSet.getString("Nombre");
                System.out.println("Nombre " + nombre);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void deshabilitarEstado(int idUsuario) {
        String query = "update usuarios set estado = 0 where usuario_id = ?";
        try {
            PreparedStatement preparedStatement = con.Connection().prepareStatement(query);
            preparedStatement.setInt(1,idUsuario);
            preparedStatement.executeUpdate();
            System.out.println("Habilitación Exitosa!");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void HabilitarEstado(int idUsuario) {
        String query = "update usuarios set estado = 1 where usuario_id = ?";
        try {
            PreparedStatement preparedStatement = con.Connection().prepareStatement(query);
            preparedStatement.setInt(1,idUsuario);
            preparedStatement.executeUpdate();
            System.out.println("Habilitación Exitosa!");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void mostrarInformacionUsuarios() {
        String query = "SELECT * FROM USUARIOS;";
        try {
            PreparedStatement preparedStatement = con.Connection().prepareStatement(query);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                String usuarioId;
                String nombre;
                String apellido;
                String nombreUsuario;
                String usuario_clave;
                String rolId;
                int estado;
                usuarioId = resultSet.getString("usuario_id");
                nombre = resultSet.getString("nombre");
                apellido = resultSet.getString("apellido");
                nombreUsuario = resultSet.getString("nombre_usuario");
                usuario_clave = resultSet.getString("usuario_clave");
                estado = resultSet.getInt("estado");
                System.out.println("ID de usuario " + usuarioId);
                System.out.println("Nombre de usuario " + nombre);
                System.out.println("Apellido de usuario " + apellido);
                System.out.println("User name " + nombreUsuario);
                System.out.println("Clave cifrada " + usuario_clave);
                System.out.println("Rol de usuario " + estado);
                System.out.println(" ");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


        public void mostrarInformacionDelincuentes() {
            String query = "SELECT * FROM DELINCUENTES;";
            try {
                PreparedStatement preparedStatement = con.Connection().prepareStatement(query);
                ResultSet resultSet = preparedStatement.executeQuery();
                System.out.println("Datos delincuente");
                while (resultSet.next()) {
                    int Delincuente_id;
                    String delincuente_primer_nombre;
                    String delincuente_primer_apellido;
                    String delincuente_segundo_apellido;
                    int lugar_de_requisitoria_id;
                    int usuario_registro_id;
                    String fecha_registro;
                    Delincuente_id = resultSet.getInt("Delincuente_id");
                    delincuente_primer_nombre = resultSet.getString("delincuente_primer_nombre");
                    delincuente_primer_apellido = resultSet.getString("delincuente_primer_apellido");
                    delincuente_segundo_apellido = resultSet.getString("delincuente_segundo_apellido");
                    lugar_de_requisitoria_id = resultSet.getInt("lugar_de_requisitoria_id");
                    usuario_registro_id = resultSet.getInt("usuario_registro_id");
                    fecha_registro = resultSet.getString("fecha_registro");
                    System.out.println("ID de usuario  - Nombre de usuario - Apellido de usuario - Segundo apellido  -  Lugar de requisitoria ID - ID de usuario - Fecha registro ");
                    System.out.println(+ Delincuente_id + "  "+delincuente_primer_nombre+"  "+delincuente_primer_apellido+"  "+delincuente_segundo_apellido+"  "+lugar_de_requisitoria_id+" "+usuario_registro_id+" "+fecha_registro+"\n" );
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
         }
    }
