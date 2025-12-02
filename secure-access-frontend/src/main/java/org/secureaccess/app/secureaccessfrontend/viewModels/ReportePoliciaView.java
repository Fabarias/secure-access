package org.secureaccess.app.secureaccessfrontend.viewModels;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class ReportePoliciaView {

    private final IntegerProperty idReporte;
    private final StringProperty enviadoPor;
    private final StringProperty supuestoDelito;
    private final StringProperty fechaReporte;
    private final StringProperty nivelUrgencia;

    public ReportePoliciaView(int idReporte,
                              String enviadoPor,
                              String supuestoDelito,
                              String fechaReporte,
                              String nivelUrgencia) {
        this.idReporte = new SimpleIntegerProperty(idReporte);
        this.enviadoPor = new SimpleStringProperty(enviadoPor);
        this.supuestoDelito = new SimpleStringProperty(supuestoDelito);
        this.fechaReporte = new SimpleStringProperty(fechaReporte);
        this.nivelUrgencia = new SimpleStringProperty(nivelUrgencia);
    }

    public IntegerProperty idReporte() {
        return idReporte;
    }

    public StringProperty enviadoPorProperty() {
        return enviadoPor;
    }

    public StringProperty supuestoDelitoProperty() {
        return supuestoDelito;
    }

    public StringProperty fechaReporteProperty() {
        return fechaReporte;
    }

    public StringProperty nivelUrgenciaProperty() {
        return nivelUrgencia;
    }

}
