package org.secureaccess.app.secureaccessfrontend.viewModels;

import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class DelincuenteAdminView {

    private final StringProperty nombre;
    private final StringProperty apellidos;
    private final StringProperty delito;
    private final DoubleProperty recompensa;

    private final StringProperty fechaRegistro;
    private final StringProperty usuarioRegistro;

    public DelincuenteAdminView(String nombre, String apellidos, String delito, double recompensa,
                                String fechaRegistro, String usuarioRegistro) {
        this.nombre = new SimpleStringProperty(nombre);
        this.apellidos = new SimpleStringProperty(apellidos);
        this.delito = new SimpleStringProperty(delito);
        this.recompensa = new SimpleDoubleProperty(recompensa);

        this.fechaRegistro = new SimpleStringProperty(fechaRegistro);
        this.usuarioRegistro = new SimpleStringProperty(usuarioRegistro);
    }

    public StringProperty nombreProperty() {
        return nombre;
    }

    public StringProperty apellidosProperty() {
        return apellidos;
    }

    public StringProperty delitoProperty() {
        return delito;
    }

    public DoubleProperty recompensaProperty() {
        return recompensa;
    }

    public StringProperty fechaRegistroProperty() {
        return fechaRegistro;
    }

    public StringProperty usuarioRegistroProperty() {
        return usuarioRegistro;
    }
}
