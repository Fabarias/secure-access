package org.secureaccess.app.secureaccessbackend.modelos;

import java.time.LocalDateTime;

public class Reporte {

    private int reporteId;
    private int categoriaDelitoId;
    private int delitoId;
    private String departamento;
    private int ciudadanoId;
    private LocalDateTime fechaDelito;
    private String estadoReporte;
    private String descripcion;

    public Reporte() {}

    public Reporte(int categoriaDelitoId,
                   int delitoId,
                   String departamento,
                   int ciudadanoId,
                   LocalDateTime localDateTime,
                   String descripcion) {
        this.categoriaDelitoId = categoriaDelitoId;
        this.delitoId = delitoId;
        this.departamento = departamento;
        this.ciudadanoId = ciudadanoId;
        this.fechaDelito = localDateTime;
        this.descripcion = descripcion;
        this.estadoReporte = "Espera";
    }

    public Reporte(int reporteId,
                   int categoriaDelitoId,
                   int delitoId,
                   String departamento,
                   int ciudadanoId,
                   LocalDateTime fechaDelito,
                   String estadoReporte,
                   String descripcion) {
        this.reporteId = reporteId;
        this.categoriaDelitoId = categoriaDelitoId;
        this.delitoId = delitoId;
        this.departamento = departamento;
        this.ciudadanoId = ciudadanoId;
        this.fechaDelito = fechaDelito;
        this.estadoReporte = estadoReporte;
        this.descripcion = descripcion;
    }

    public int getReporteId() {
        return reporteId;
    }

    public void setReporteId(int reporteId) {
        this.reporteId = reporteId;
    }

    public int getCategoriaDelitoId() {
        return categoriaDelitoId;
    }

    public void setCategoriaDelitoId(int categoriaDelitoId) {
        this.categoriaDelitoId = categoriaDelitoId;
    }

    public int getDelitoId() {
        return delitoId;
    }

    public void setDelitoId(int delitoId) {
        this.delitoId = delitoId;
    }

    public String getDepartamento() {
        return departamento;
    }

    public void setDepartamento(String departamento) {
        this.departamento = departamento;
    }

    public int getCiudadanoId() {
        return ciudadanoId;
    }

    public void setCiudadanoId(int ciudadanoId) {
        this.ciudadanoId = ciudadanoId;
    }

    public LocalDateTime getFechaDelito() {
        return fechaDelito;
    }

    public void setFechaDelito(LocalDateTime fechaDelito) {
        this.fechaDelito = fechaDelito;
    }

    public String getEstadoReporte() {
        return estadoReporte;
    }

    public void setEstadoReporte(String estadoReporte) {
        this.estadoReporte = estadoReporte;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public boolean estaPendiente() {
        return "pendiente".equalsIgnoreCase(estadoReporte);
    }


    public boolean estaEnProceso() {
        return "en_proceso".equalsIgnoreCase(estadoReporte);
    }

    public boolean estaResuelto() {
        return "resuelto".equalsIgnoreCase(estadoReporte);
    }
}
