module org.secureaccess.app.secureaccessfrontend {

    requires javafx.controls;
    requires javafx.fxml;
    requires org.controlsfx.controls;
    requires com.dlsc.formsfx;
    requires org.secureaccess.app.secureaccessbackend;
    requires java.sql;
    requires jbcrypt;

    opens org.secureaccess.app.secureaccessfrontend to javafx.graphics, javafx.fxml;
    opens org.secureaccess.app.secureaccessfrontend.viewModels to javafx.graphics, javafx.fxml;
    opens org.secureaccess.app.secureaccessfrontend.controllers.components to javafx.fxml;
    opens org.secureaccess.app.secureaccessfrontend.controllers.auth to javafx.fxml;
    opens org.secureaccess.app.secureaccessfrontend.controllers.dashboard to javafx.fxml;
    opens org.secureaccess.app.secureaccessfrontend.controllers.users to javafx.fxml;
    opens org.secureaccess.app.secureaccessfrontend.controllers.crimes to javafx.fxml;
    opens org.secureaccess.app.secureaccessfrontend.controllers.reports to javafx.fxml;

}