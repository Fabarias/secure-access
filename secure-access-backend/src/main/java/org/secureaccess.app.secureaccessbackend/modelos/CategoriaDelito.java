package org.secureaccess.app.secureaccessbackend.modelos;

public class CategoriaDelito {

    private int categoriaId;
    private String tipoDeUrgencia;

    public CategoriaDelito() {}

    public CategoriaDelito(int categoriaId, String tipoDeUrgencia) {
        this.categoriaId = categoriaId;
        this.tipoDeUrgencia = tipoDeUrgencia;
    }

    public int getCategoriaId() {
        return categoriaId;
    }

    public void setCategoriaId(int categoriaId) {
        this.categoriaId = categoriaId;
    }

    public String getTipoDeUrgencia() {
        return tipoDeUrgencia;
    }

    public void setTipoDeUrgencia(String tipoDeUrgencia) {
        this.tipoDeUrgencia = tipoDeUrgencia;
    }

    public boolean esAltaUrgencia() {
        return tipoDeUrgencia != null &&
                (tipoDeUrgencia.equalsIgnoreCase("ALTA") ||
                        tipoDeUrgencia.equalsIgnoreCase("URGENTE"));
    }

    @Override
    public String toString() {
        return tipoDeUrgencia;
    }
}
