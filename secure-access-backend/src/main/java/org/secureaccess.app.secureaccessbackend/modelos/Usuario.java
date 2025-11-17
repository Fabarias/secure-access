package org.secureaccess.app.secureaccessbackend.modelos;

public class Usuario {

    private int usuarioId;
    private String nombre;
    private String apellido;
    private String nombreUsuario;
    private String usuarioClave;
    private Integer rolId;
    private int estado;

    public Usuario() {
    }

    public Usuario(String nombre, String apellido, String nombreUsuario,
                   String usuarioClave, int rolId, int estado) {
        this.nombre = nombre;
        this.apellido = apellido;
        this.nombreUsuario = nombreUsuario;
        this.usuarioClave = usuarioClave;
        this.rolId = rolId;
        this.estado = estado;
    }

    public int getUsuarioId() {
        return usuarioId;
    }

    public void setUsuarioId(int usuarioId) {
        this.usuarioId = usuarioId;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getApellido() {
        return apellido;
    }

    public void setApellido(String apellido) {
        this.apellido = apellido;
    }

    public String getNombreUsuario() {
        return nombreUsuario;
    }

    public void setNombreUsuario(String nombreUsuario) {
        this.nombreUsuario = nombreUsuario;
    }

    public String getUsuarioClave() {
        return usuarioClave;
    }

    public void setUsuarioClave(String usuarioClave) {
        this.usuarioClave = usuarioClave;
    }

    public int getRolId() {
        return rolId;
    }

    public void setRolId(int rolId) {
        this.rolId = rolId;
    }

    public int getEstado() {
        return estado;
    }

    public void setEstado(int estado) {
        this.estado = estado;
    }

    public String getNombreCompleto() {
        return nombre + " " + apellido;
    }


    public boolean estaActivo() {
        return estado == 1;
    }

    public String getNombreRol() {
        return rolId != null ? getRolNombre() : null;
    }

    public String getRolNombre() {
        switch (this.rolId) {
            case 1:
                return "ADMINISTRADOR";
            case 2:
                return "POLICIA";
            case 3:
                return "CIUDADANO";

            default:
                return null;
        }
    }
}


