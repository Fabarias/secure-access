package org.secureaccess.app.secureaccessbackend.modelos;

public class Usuario {

    private int usuarioId;
    private String primerNombre;
    private String primerApellido;
    private String segundoApellido;
    private String correo;
    private String username;
    private String clave;
    private Integer rolId;
    private Integer estado;

    public Usuario() {
    }

    public Usuario(int usuarioId, String primerNombre, String primerApellido, String segundoApellido,
                   String correo, String username, String clave, Integer rolId, Integer estado) {
        this.usuarioId = usuarioId;
        this.primerNombre = primerNombre;
        this.primerApellido = primerApellido;
        this.segundoApellido = segundoApellido;
        this.correo = correo;
        this.username = username;
        this.clave = clave;
        this.rolId = rolId;
        this.estado = estado;
    }

    public Usuario(String primerNombre, String primerApellido, String segundoApellido,
                   String correo, String username, String clave, Integer rolId) {
        this.primerNombre = primerNombre;
        this.primerApellido = primerApellido;
        this.segundoApellido = segundoApellido;
        this.correo = correo;
        this.username = username;
        this.clave = clave;
        this.rolId = rolId;
        this.estado = 1;
    }

    public int getUsuarioId() {
        return usuarioId;
    }

    public void setUsuarioId(int usuarioId) {
        this.usuarioId = usuarioId;
    }

    public String getPrimerNombre() {
        return primerNombre;
    }

    public void setPrimerNombre(String primerNombre) {
        this.primerNombre = primerNombre;
    }

    public String getPrimerApellido() {
        return primerApellido;
    }

    public void setPrimerApellido(String primerApellido) {
        this.primerApellido = primerApellido;
    }

    public String getSegundoApellido() {
        return segundoApellido;
    }

    public void setSegundoApellido(String segundoApellido) {
        this.segundoApellido = segundoApellido;
    }

    public String getCorreo() {
        return correo;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getClave() {
        return clave;
    }

    public void setClave(String clave) {
        this.clave = clave;
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

    public String getEstadoString() {
        return (getEstado() != 1) ? "INACTIVO" : "ACTIVO";
    }

    public String getNombreCompleto() {
        return getPrimerNombre() + " " + getPrimerApellido() + (getSegundoApellido() != null ? " " + getSegundoApellido(): "");
    }

    public String getApellidosCompleto() {
        return getPrimerApellido() + " " + getSegundoApellido();
    }

    public boolean estaActivo() {
        return estado == 1;
    }

    public String getNombreRol() {
        return rolId != null ? getRolNombre() : null;
    }

    public String getRolNombre() {
        return switch (this.rolId) {
            case 1 -> "ADMINISTRADOR";
            case 2 -> "POLICIA";
            case 3 -> "CIUDADANO";
            default -> null;
        };
    }
}


