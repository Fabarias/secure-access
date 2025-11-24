package org.secureaccess.app.secureaccessfrontend.viewModels;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class ReporteCiudadanoView {

    private final StringProperty fecha;
    private final StringProperty delito;
    private final StringProperty estado;

    public ReporteCiudadanoView(String fecha, String delito, String estado) {
        this.fecha = new SimpleStringProperty(fecha);
        this.delito = new SimpleStringProperty(delito);
        this.estado = new SimpleStringProperty(estado);
    }

    public StringProperty fechaProperty() {
        return fecha;
    }

    public StringProperty delitoProperty() {
        return delito;
    }

    public StringProperty estadoProperty() {
        return estado;
    }
}
