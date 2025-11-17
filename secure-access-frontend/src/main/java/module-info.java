module org.secureaccess.app.secureaccessfrontend {

    requires javafx.controls;
    requires javafx.fxml;
    requires org.controlsfx.controls;
    requires com.dlsc.formsfx;
    requires org.secureaccess.app.secureaccessbackend;

    opens org.secureaccess.app.secureaccessfrontend to javafx.graphics, javafx.fxml;
    opens org.secureaccess.app.secureaccessfrontend.controllers.menuControllers.listadoDelincuentesControllers to javafx.fxml;
    opens org.secureaccess.app.secureaccessfrontend.viewModels to javafx.graphics, javafx.fxml;
    opens org.secureaccess.app.secureaccessfrontend.controllers.eleccionControllers to javafx.fxml;
    opens org.secureaccess.app.secureaccessfrontend.controllers.loginsControllers to javafx.fxml;
    opens org.secureaccess.app.secureaccessfrontend.controllers.menuControllers.profilesControllers to javafx.fxml;
}