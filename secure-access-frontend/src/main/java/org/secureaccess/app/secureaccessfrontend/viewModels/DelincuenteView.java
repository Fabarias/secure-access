package org.secureaccess.app.secureaccessfrontend.viewModels;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.StringProperty;
import javafx.beans.property.DoubleProperty;

public class DelincuenteView {

    private final StringProperty nombre;
    private final StringProperty apellidos;
    private final StringProperty delito;
    private final DoubleProperty recompensa;

    public DelincuenteView(String nombre, String apellidos, String delito, double recompensa) {
        this.nombre = new SimpleStringProperty(nombre);
        this.apellidos = new SimpleStringProperty(apellidos);
        this.delito = new SimpleStringProperty(delito);
        this.recompensa = new SimpleDoubleProperty(recompensa);
    }

    public StringProperty nombreProperty() { return nombre; }
    public StringProperty apellidosProperty() { return apellidos; }
    public StringProperty delitoProperty() { return delito; }
    public DoubleProperty recompensaProperty() { return recompensa; }
}
