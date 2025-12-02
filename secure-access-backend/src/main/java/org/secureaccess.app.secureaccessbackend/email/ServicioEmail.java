package org.secureaccess.app.secureaccessbackend.email;

import jakarta.mail.*;
import jakarta.mail.internet.InternetAddress;
import jakarta.mail.internet.MimeMessage;

import java.time.LocalDateTime;
import java.util.Properties;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;

public class ServicioEmail {

    private final Map<String, DatosCodigo> codigosPorEmail;
    private final GeneradorCodigos generador;
    private static final int MINUTOS_VALIDEZ = 10;
    private final ExecutorService executorService;


    public ServicioEmail() {
        this.codigosPorEmail = new HashMap<>();
        this.generador = new GeneradorCodigos();
        this.executorService = Executors.newFixedThreadPool(2, r -> {
            Thread t = new Thread(r);
            t.setDaemon(true);
            t.setName("EmailSender-Thread");
            return t;
        });
    }

    public boolean enviarCodigoVerificacion(String email, String nombreUsuario) {
        try {

            String codigo = generador.generar();

            DatosCodigo datos = new DatosCodigo(codigo, LocalDateTime.now());
            codigosPorEmail.put(email, datos);

            Future<Boolean> futuro = executorService.submit(() -> enviarEmail(email, nombreUsuario, codigo));

            try {
                boolean resultado = futuro.get(15, TimeUnit.SECONDS);

                if (!resultado) {
                    System.err.println("✗ No se pudo enviar, eliminando código");
                    codigosPorEmail.remove(email);
                }

                return resultado;

            } catch (java.util.concurrent.TimeoutException e) {
                futuro.cancel(true);
                codigosPorEmail.remove(email);
                return false;
            }

        } catch (Exception e) {
            e.printStackTrace();
            codigosPorEmail.remove(email);
            return false;
        }
    }

    public boolean verificarCodigo(String email, String codigoIngresado) {
        DatosCodigo datosGuardados = codigosPorEmail.get(email);

        if (datosGuardados == null) return false;

        if (datosGuardados.haExpirado()) {
            codigosPorEmail.remove(email);
            return false;
        }


        boolean esValido = datosGuardados.codigo.equals(codigoIngresado);

        if (esValido) {
            codigosPorEmail.remove(email);
        }
        return esValido;
    }

    public boolean tieneCodigoActivo(String email) {
        DatosCodigo datos = codigosPorEmail.get(email);
        return datos != null && !datos.haExpirado();
    }


    public void limpiarCodigosExpirados() {
        int cantidadAntes = codigosPorEmail.size();

        codigosPorEmail.entrySet().removeIf(entry ->
                entry.getValue().haExpirado()
        );

        int cantidadDespues = codigosPorEmail.size();
        int eliminados = cantidadAntes - cantidadDespues;
    }

    public int cantidadCodigosActivos() {
        return codigosPorEmail.size();
    }

    private boolean enviarEmail(String emailDestino, String nombreUsuario, String codigo) {

        ClassLoader originalClassLoader = Thread.currentThread().getContextClassLoader();

        try {
            Thread.currentThread().setContextClassLoader(getClass().getClassLoader());

            Properties props = getProperties();

            Session session = Session.getInstance(props, new Authenticator() {
                @Override
                protected PasswordAuthentication getPasswordAuthentication() {
                    return new PasswordAuthentication(
                            ConfiguracionEmail.EMAIL_REMITENTE,
                            ConfiguracionEmail.PASSWORD_EMAIL
                    );
                }
            });

            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress(
                    ConfiguracionEmail.EMAIL_REMITENTE,
                    ConfiguracionEmail.NOMBRE_REMITENTE
            ));
            message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(emailDestino));
            message.setSubject("SecureAccess - Código de Verificación");

            String mensajeHTML = construirHTMLCodigoVerificacion(nombreUsuario, codigo);
            message.setContent(mensajeHTML, "text/html; charset=utf-8");

            Transport.send(message);
            return true;

        } catch (MessagingException e) {
            e.printStackTrace();
            return false;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        } finally {
            Thread.currentThread().setContextClassLoader(originalClassLoader);
        }
    }

    private static Properties getProperties() {
        Properties props = new Properties();
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.host", ConfiguracionEmail.SMTP_HOST);
        props.put("mail.smtp.port", ConfiguracionEmail.SMTP_PORT);
        props.put("mail.smtp.ssl.trust", ConfiguracionEmail.SMTP_HOST);
        props.put("mail.smtp.ssl.protocols", ConfiguracionEmail.PROTOCOLO_SSL);

        props.put("mail.smtp.connectiontimeout", "10000");
        props.put("mail.smtp.timeout", "10000");
        props.put("mail.smtp.writetimeout", "10000");

        props.put("mail.debug", "true");
        return props;
    }

    private String construirHTMLCodigoVerificacion(String nombreUsuario, String codigo) {
        return String.format("""
            <!DOCTYPE html>
            <html>
            <body style="font-family: Arial; background-color: #f4f4f4; padding: 20px;">
                <div style="max-width: 600px; margin: 0 auto; background-color: white; padding: 40px; border-radius: 10px;">
                    <h1 style="color: #2563EB; text-align: center;">🔐 SecureAccess</h1>
                    <h2 style="color: #333; border-bottom: 2px solid #2563EB; padding-bottom: 10px;">Código de Verificación</h2>
                    <p style="color: #555; font-size: 16px;">Hola <strong>%s</strong>,</p>
                    <p style="color: #555;">Has solicitado un código de verificación. Utiliza el siguiente código:</p>
                    <div style="background-color: #EFF6FF; border: 2px dashed #2563EB; border-radius: 8px; padding: 30px; text-align: center; margin: 30px 0;">
                        <p style="color: #666; font-size: 14px; margin: 0;">Tu código es:</p>
                        <h1 style="color: #2563EB; font-size: 48px; letter-spacing: 10px; margin: 15px 0;">%s</h1>
                        <p style="color: #666; font-size: 12px; margin: 0;">Válido por 1 minuto</p>
                    </div>
                    <div style="background-color: #FEF3C7; border-left: 4px solid #F59E0B; padding: 15px; border-radius: 5px;">
                        <p style="color: #92400E; font-size: 14px; margin: 0;"><strong>⚠️ Importante:</strong> No compartas este código.</p>
                    </div>
                    <hr style="border: none; border-top: 1px solid #ddd; margin: 30px 0;">
                    <p style="color: #888; font-size: 12px; text-align: center;">© 2024 SecureAccess</p>
                </div>
            </body>
            </html>
            """, nombreUsuario, codigo);
    }

    // ========== CLASE INTERNA ==========

    private static class DatosCodigo {
        final String codigo;
        final LocalDateTime timestamp;

        DatosCodigo(String codigo, LocalDateTime timestamp) {
            this.codigo = codigo;
            this.timestamp = timestamp;
        }

        boolean haExpirado() {
            return LocalDateTime.now().isAfter(timestamp.plusMinutes(MINUTOS_VALIDEZ));
        }
    }
}

