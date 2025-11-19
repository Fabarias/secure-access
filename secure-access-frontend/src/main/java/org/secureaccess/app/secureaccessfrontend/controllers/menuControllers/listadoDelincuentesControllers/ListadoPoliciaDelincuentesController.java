package org.secureaccess.app.secureaccessfrontend.controllers.menuControllers.listadoDelincuentesControllers;


import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import org.secureaccess.app.secureaccessfrontend.viewModels.DelincuentePoliciaView;

public class ListadoPoliciaDelincuentesController {

    @FXML
    private TextField filtroNombre;

    @FXML
    private TextField filtroApellidos;

    @FXML
    private ComboBox<String> comboDepartamentos;

    @FXML
    private TableView<DelincuentePoliciaView> tablaDelincuentes;

    @FXML
    private TableColumn<DelincuentePoliciaView, String> columnaNombre;

    @FXML
    private TableColumn<DelincuentePoliciaView, String> columnaApellidos;

    @FXML
    private TableColumn<DelincuentePoliciaView, String> columnnaDelito;

    @FXML
    private TableColumn<DelincuentePoliciaView, Double> columnaRecompensa;

    @FXML
    private ObservableList<DelincuentePoliciaView> listaObservableDelincuente;



}
