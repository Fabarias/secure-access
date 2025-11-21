package org.secureaccess.app.secureaccessbackend.modelos;

import java.time.LocalDateTime;

public class Delincuente {

    private int delincuenteId;
    private String delincuentePrimerNombre;
    private String delincuentePrimerApellido;
    private String delincuenteSegundoApellido;
    private Integer lugarDeRequisitoriaId;
    private int usuarioRegistroId;
    private Integer reporteOrigenId;
    private LocalDateTime fechaRegistro;
    private double recompensa;


    public Delincuente() {}

    public Delincuente(String primerNombre,
                       String primerApellido,
                       String segundoApellido,
                       int lugarRequisitoriaId,
                       int usuarioRegistroId,
                       Integer reporteOrigenId,
                       double recompensa) {

            this.delincuentePrimerNombre = primerNombre;
            this.delincuentePrimerApellido = primerApellido;
            this.delincuenteSegundoApellido = segundoApellido;
            this.lugarDeRequisitoriaId = lugarRequisitoriaId;
            this.usuarioRegistroId = usuarioRegistroId;
            this.reporteOrigenId = reporteOrigenId;
            this.recompensa = recompensa;
        }

    public Delincuente(int delincuenteId,
                       String primerNombre,
                       String primerApellido,
                       String segundoApellido,
                       int lugarRequisitoriaId,
                       int usuarioRegistroId,
                       Integer reporteOrigenId,
                       LocalDateTime fechaRegistro,
                       double recompensa) {
        this.delincuenteId = delincuenteId;
        this.delincuentePrimerNombre = primerNombre;
        this.delincuentePrimerApellido = primerApellido;
        this.delincuenteSegundoApellido = segundoApellido;
        this.lugarDeRequisitoriaId = lugarRequisitoriaId;
        this.usuarioRegistroId = usuarioRegistroId;
        this.reporteOrigenId = reporteOrigenId;
        this.fechaRegistro = fechaRegistro;
        this.recompensa = recompensa;
    }

    public int getDelincuenteId() {
        return delincuenteId;
    }

    public void setDelincuenteId(int delincuenteId) {
        this.delincuenteId = delincuenteId;
    }

    public String getDelincuentePrimerNombre() {
        return delincuentePrimerNombre;
    }

    public void setDelincuentePrimerNombre(String delincuentePrimerNombre) {
        this.delincuentePrimerNombre = delincuentePrimerNombre;
    }

    public String getDelincuentePrimerApellido() {
        return delincuentePrimerApellido;
    }

    public void setDelincuentePrimerApellido(String delincuentePrimerApellido) {
        this.delincuentePrimerApellido = delincuentePrimerApellido;
    }

    public String getDelincuenteSegundoApellido() {
        return delincuenteSegundoApellido;
    }

    public void setDelincuenteSegundoApellido(String delincuenteSegundoApellido) {
        this.delincuenteSegundoApellido = delincuenteSegundoApellido;
    }

    public String getApellidosCompletos() {
        String apellidos = delincuentePrimerApellido;
        if (delincuenteSegundoApellido != null && !delincuenteSegundoApellido.trim().isEmpty()) {
            apellidos += " " + delincuenteSegundoApellido;
        }
        return apellidos;
    }

    public int getLugarDeRequisitoriaId() {
        return lugarDeRequisitoriaId;
    }

    public void setLugarDeRequisitoriaId(int lugarDeRequisitoriaId) {
        this.lugarDeRequisitoriaId = lugarDeRequisitoriaId;
    }

    public int getUsuarioRegistroId() {
        return usuarioRegistroId;
    }

    public void setUsuarioRegistroId(int usuarioRegistroId) {
        this.usuarioRegistroId = usuarioRegistroId;
    }

    public Integer getReporteOrigenId() {
        return reporteOrigenId;
    }

    public void setReporteOrigenId(Integer reporteOrigenId) {
        this.reporteOrigenId = reporteOrigenId;
    }

    public LocalDateTime getFechaRegistro() {
        return fechaRegistro;
    }

    public void setFechaRegistro(LocalDateTime fechaRegistro) {
        this.fechaRegistro = fechaRegistro;
    }

    public double getRecompensa() {
        return recompensa;
    }

    public void setRecompensa(double recompensa) {
        this.recompensa = recompensa;
    }
}

