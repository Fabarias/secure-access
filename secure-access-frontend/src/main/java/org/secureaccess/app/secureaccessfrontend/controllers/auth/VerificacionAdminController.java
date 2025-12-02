package org.secureaccess.app.secureaccessfrontend.controllers.auth;

import javafx.animation.KeyFrame;
import javafx.application.Platform;
import javafx.concurrent.Task;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.animation.Timeline;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import javafx.util.Duration;
import org.secureaccess.app.secureaccessbackend.modelos.Usuario;
import org.secureaccess.app.secureaccessfrontend.controllers.dashboard.MenuAdministradorController;
import org.secureaccess.app.secureaccessfrontend.util.Alerta;
import org.secureaccess.app.secureaccessfrontend.util.GestorServicios;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class VerificacionAdminController implements Initializable {

    @FXML private TextField txtCodigo;
    @FXML private Label lblReloj;
    @FXML private Button botonVerificar;
    @FXML private Button botonReenviar;

    private Usuario adminPendiente;
    private Timeline timeLine;
    private int tiempoRestante = 60;

    public void setAdminPendiente(Usuario administrador) {
        this.adminPendiente = administrador;
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        iniciarConteo();
    }

    private void iniciarConteo() {
        tiempoRestante = 60;
        botonVerificar.setDisable(false);
        botonReenviar.setVisible(false);
        botonReenviar.setDisable(true);

        if (timeLine != null) timeLine.stop();

        timeLine = new Timeline(new KeyFrame(Duration.seconds(1), e -> {
            tiempoRestante--;
            lblReloj.setText(String.format("00:%02d", tiempoRestante));

            if (tiempoRestante <= 0) {
                timeLine.stop();
                lblReloj.setText("EXPIRADO");
                botonVerificar.setDisable(true);
                botonReenviar.setVisible(true);
                botonReenviar.setDisable(false);
            }
        }));
        timeLine.setCycleCount(Timeline.INDEFINITE);
        timeLine.play();
    }

    @FXML
    private void verificarCodigo(ActionEvent event) throws IOException {

        String codigo = txtCodigo.getText().trim();

        if (codigo.isEmpty()) {
            Alerta.mostrar("Atención", "Ingrese el código de verificación");
            return;
        }
        boolean valido = GestorServicios.getServicioEmail()
                .verificarCodigo(adminPendiente.getCorreo(), codigo);

        if (valido) {
            if (timeLine != null) timeLine.stop();
            irAlMenu(event);
        } else Alerta.mostrar("Error", "Código incorrecto o expirado");
    }

    @FXML
    private void reenviarCodigo(ActionEvent event) {

        botonReenviar.setDisable(true);
        lblReloj.setText("Enviando...");

        Task<Boolean> tareaEnvio = new Task<>() {
            @Override
            protected Boolean call() {

                try {
                    return GestorServicios.getServicioEmail()
                            .enviarCodigoVerificacion(
                                    adminPendiente.getCorreo(),
                                    adminPendiente.getPrimerNombre()
                            );
                } catch (Exception e) {
                    e.printStackTrace();
                    return false;
                }
            }
        };

        tareaEnvio.setOnSucceeded(e -> {
            boolean enviado = tareaEnvio.getValue();

            if (enviado) {
                Platform.runLater(() -> {
                    Alerta.mostrar("Enviado", "Se ha enviado un nuevo código a tu correo.");
                    txtCodigo.clear();
                    iniciarConteo();
                });
            } else {
                Platform.runLater(() -> {
                    Alerta.mostrar("Error", "No se pudo reenviar el correo. Verifica tu conexión.");
                    lblReloj.setText("Error");
                    botonReenviar.setDisable(false);
                });
            }
        });

        tareaEnvio.setOnFailed(e -> {
            Throwable ex = tareaEnvio.getException();
            if (ex != null) {
                ex.printStackTrace();
            }

            Platform.runLater(() -> {
                Alerta.mostrar("Error Crítico", "Ocurrió un error al enviar el correo.");
                lblReloj.setText("Error");
                botonReenviar.setDisable(false);
            });
        });

        tareaEnvio.setOnCancelled(e -> {
            System.err.println(">>> [TASK] Tarea cancelada");
            Platform.runLater(() -> {
                lblReloj.setText("Cancelado");
                botonReenviar.setDisable(false);
            });
        });

        Thread hilo = new Thread(tareaEnvio);
        hilo.setDaemon(true);
        hilo.setName("JavaFX-EmailTask");
        hilo.start();
    }

    @FXML
    private void cancelar(ActionEvent event) throws IOException {
        if (timeLine != null) timeLine.stop();

        FXMLLoader loader = new FXMLLoader(getClass().getResource("/ui/auth/login-admin.fxml"));
        Parent root = loader.load();
        Scene scene = new Scene(root);
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setScene(scene);
        stage.show();
    }

    private void irAlMenu(ActionEvent event) throws IOException {

        FXMLLoader loader = new FXMLLoader(getClass().getResource("/ui/dashboard/menu-administrador.fxml"));
        Parent root = loader.load();

        MenuAdministradorController controller = loader.getController();
        controller.iniciarDatos(adminPendiente);

        Scene scene = new Scene(root);
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setTitle("Menú Administrador");
        stage.setScene(scene);
        stage.show();
    }
}
