package org.secureaccess.app.secureaccessfrontend.controllers.menuControllers.listadoDelincuentesControllers;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import org.secureaccess.app.secureaccessfrontend.viewModels.DelincuenteView;

import java.net.URL;
import java.util.ResourceBundle;

public class listadoDelincuentesController implements Initializable {

    @FXML
    private TextField filtroNombre;

    @FXML
    private TextField filtroApellidos;

    @FXML
    private ComboBox<String> comboDepartamentos;

    @FXML
    private TableView<DelincuenteView> tablaDelincuentes;

    @FXML
    private TableColumn<DelincuenteView, String> columnaNombre;

    @FXML
    private TableColumn<DelincuenteView, String> columnaApellidos;

    @FXML
    private TableColumn<DelincuenteView, String> columnnaDelito;

    @FXML
    private TableColumn<DelincuenteView, Double> columnaRecompensa;

    @FXML
    private ObservableList<DelincuenteView> listaObservableDelincuente;


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        /* Cargamos los departamentos para el ComboBox sin necesidad de cargar los datos de una entidad
           de la Base de Datos.
         */

         this.comboDepartamentos.getItems().addAll(
                "Amazonas",
                "Ancash",
                "Apurimac",
                "Arequipa",
                "Arequipa",
                "Ayacucho",
                "Cajamarca",
                "Callao",
                "Cusco",
                "Huancavelica",
                "Huanuco",
                "Ica",
                "Junin",
                "La Libertad",
                "Lambayeque",
                "Lima",
                "Loreto",
                "Madre de Dios",
                "Moquegua",
                "Pasco",
                "Piura",
                "Puno",
                "San Martin",
                "Tacna",
                "Tumbes",
                "Ucayali"
        );

        // Bloqueo de Columnas (para que el usuario no pueda alterar su orden designado)
        columnaNombre.setReorderable(false);
        columnaApellidos.setReorderable(false);
        columnnaDelito.setReorderable(false);
        columnaRecompensa.setReorderable(false);


        columnaNombre.setCellValueFactory(cellData -> cellData.getValue().nombreProperty());
        columnaApellidos.setCellValueFactory(cellData -> cellData.getValue().apellidosProperty());
        columnnaDelito.setCellValueFactory(cellData -> cellData.getValue().delitoProperty());
        columnaRecompensa.setCellValueFactory(cellData -> cellData.getValue().recompensaProperty().asObject());

        this.listaObservableDelincuente = FXCollections.observableArrayList();

        tablaDelincuentes.setItems(this.listaObservableDelincuente);

    }

    @FXML
    private void onClickBoton(ActionEvent event) {

        String nombre = filtroNombre.getText();
        String apellidos = filtroApellidos.getText();
        String depto = comboDepartamentos.getValue();

        /*
        Features para terminar el metodo:
        1. Metodo del Back-end que realice una Query a la Base de Datos.
        2. Entregar los resultados a la TableView con su metodo "setItems()".
         */
    }


}
