package org.secureaccess.app.secureaccessbackend.modelos;

public class Delito {

    private int delitoId;
    private String delitoNombre;
    private Integer estado;

    public Delito() {}

    public Delito(int delitoId, String delitoNombre) {
        this.delitoId = delitoId;
        this.delitoNombre = delitoNombre;
        this.estado = 1;
    }

    public Delito(int delitoId, String delitoNombre, Integer estado) {
        this.delitoId = delitoId;
        this.delitoNombre = delitoNombre;
        this.estado = estado;
    }

    public int getDelitoId() {
        return delitoId;
    }

    public void setDelitoId(int delitoId) {
        this.delitoId = delitoId;
    }

    public String getDelitoNombre() {
        return delitoNombre;
    }

    public void setDelitoNombre(String delitoNombre) {
        this.delitoNombre = delitoNombre;
    }

    public int getActivo() {
        return this.estado;
    }

    public void setActivo(int activo) {
        this.estado = activo;
    }

    public boolean estaActivo() {
        return estado == 1;
    }
}
