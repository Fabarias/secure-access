package org.secureaccess.app.secureaccessbackend.modelos;

public class CategoriaDelito {

    private int categoriaId;
    private String nombreDelito;
    private String tipoDeUrgencia;

    public CategoriaDelito() {}

    public CategoriaDelito(int categoriaId, String nombreDelito, String tipoDeUrgencia) {
        this.categoriaId = categoriaId;
        this.nombreDelito = nombreDelito;
        this.tipoDeUrgencia = tipoDeUrgencia;
    }

    public int getCategoriaId() {
        return categoriaId;
    }

    public void setCategoriaId(int categoriaId) {
        this.categoriaId = categoriaId;
    }

    public String getNombreDelito() {
        return nombreDelito;
    }

    public void setNombreDelito(String nombreDelito) {
        this.nombreDelito = nombreDelito;
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
}
