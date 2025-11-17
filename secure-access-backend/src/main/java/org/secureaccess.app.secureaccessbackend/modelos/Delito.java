package org.secureaccess.app.secureaccessbackend.modelos;

public class Delito {

    private int delitoId;
    private String delitoNombre;
    private int activo;

    public Delito() {}

    public Delito(int delitoId, String delitoNombre) {
        this.delitoId = delitoId;
        this.delitoNombre = delitoNombre;
        this.activo = 1;
    }

    public Delito(int delitoId, String delitoNombre, int activo) {
        this.delitoId = delitoId;
        this.delitoNombre = delitoNombre;
        this.activo = activo;
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
        return activo;
    }

    public void setActivo(int activo) {
        this.activo = activo;
    }

    public boolean estaActivo() {
        return activo == 1;
    }
}
