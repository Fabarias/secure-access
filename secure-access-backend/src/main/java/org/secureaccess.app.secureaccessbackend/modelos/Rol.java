package org.secureaccess.app.secureaccessbackend.modelos;

public class Rol {

    private int rolId;
    private String rolNombre;
    private int activo;

    public Rol() {}

    public Rol(int rolId, String rolNombre) {

        this.rolId = rolId;
        this.rolNombre = rolNombre;
        this.activo = 1;
    }

    public Rol(int rolId, String rolNombre, int activo) {
        this.rolId = rolId;
        this.rolNombre = rolNombre;
        this.activo = activo;
    }

    public int getRolId() {
        return rolId;
    }

    public void setRolId(int rolId) {
        this.rolId = rolId;
    }

    public String getRolNombre() {
        return rolNombre;
    }

    public void setRolNombre(String rolNombre) {
        this.rolNombre = rolNombre;
    }

    public int getActivo() {
        return activo;
    }

    public void setActivo(int activo) {
        this.activo = activo;
    }

    public boolean estaActivo() {
        return this.activo == 1;
    }
}
