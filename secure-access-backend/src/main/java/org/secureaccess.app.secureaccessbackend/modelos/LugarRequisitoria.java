package org.secureaccess.app.secureaccessbackend.modelos;

public class LugarRequisitoria {

    private Integer lugarDeRequisitoriaId;
    private String lugarDeRequisitoriaNombre;
    private int activo;

    public LugarRequisitoria() {}

    public LugarRequisitoria(int lugarDeRequisitoriaId, String lugarDeRequisitoriaNombre) {
        this.lugarDeRequisitoriaId = lugarDeRequisitoriaId;
        this.lugarDeRequisitoriaNombre = lugarDeRequisitoriaNombre;
        this.activo = 1;
    }

    public LugarRequisitoria(int lugarDeRequisitoriaId, String lugarDeRequisitoriaNombre, int activo) {
        this.lugarDeRequisitoriaId = lugarDeRequisitoriaId;
        this.lugarDeRequisitoriaNombre = lugarDeRequisitoriaNombre;
        this.activo = activo;
    }

    public int getLugarDeRequisitoriaId() {
        return lugarDeRequisitoriaId;
    }

    public void setLugarDeRequisitoriaId(int lugarDeRequisitoriaId) {
        this.lugarDeRequisitoriaId = lugarDeRequisitoriaId;
    }

    public String getLugarDeRequisitoriaNombre() {
        return lugarDeRequisitoriaNombre;
    }

    public void setLugarDeRequisitoriaNombre(String lugarDeRequisitoriaNombre) {
        this.lugarDeRequisitoriaNombre = lugarDeRequisitoriaNombre;
    }

    public int getActivo() {
        return activo;
    }

    public void setActivo(int activo) {
        this.activo = activo;
    }

    public boolean estaActivo() {
        return activo == 1;
    }
}
