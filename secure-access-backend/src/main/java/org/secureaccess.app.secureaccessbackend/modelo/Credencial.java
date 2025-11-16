package org.secureaccess.app.secureaccessbackend.modelo;

public class Credencial {

    private String nombreUsuario;
    private String claveCifrada;
    private String rolUsuario;
    private int usuario_id;

    public String getRolUsuario() {
        return rolUsuario;
    }

    public void setRolUsuario(String rolUsuario) {
        this.rolUsuario = rolUsuario;
    }

    public Credencial(String nombreUsuario, String claveCifrada, int usuario_id) {
        this.nombreUsuario = nombreUsuario;
        this.claveCifrada = claveCifrada;
       this.usuario_id = usuario_id;
    }

    public Credencial(String nombreUsuario, String claveCifrada, String rolUsuario) {
        this.nombreUsuario = nombreUsuario;
        this.claveCifrada = claveCifrada;
        this.rolUsuario = rolUsuario;
    }

    public String getNombreUsuario() {
        return nombreUsuario;
    }

    public String getClaveCifrada() {
        return claveCifrada;
    }

    public void setNombreUsuario(String nombreUsuario) {
        this.nombreUsuario = nombreUsuario;
    }

    public void setClaveCifrada(String claveCifrada) {
        this.claveCifrada = claveCifrada;
    }
}
