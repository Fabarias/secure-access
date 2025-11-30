package org.secureaccess.app.secureaccessbackend.email;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.time.LocalDateTime;
import java.util.Properties;
import java.util.HashMap;
import java.util.Map;

public class ServicioEmail {

    // HashMap para almacenar: email -> DatosCodigo (evita códigos repetidos)
    private final Map<String, DatosCodigo> codigosPorEmail;

    // Generador de códigos
    private final GeneradorCodigos generador;

    // Tiempo de validez en minutos
    private static final int MINUTOS_VALIDEZ = 10;

    /**
     * Constructor
     */
    public ServicioEmail() {
        this.codigosPorEmail = new HashMap<>();
        this.generador = new GeneradorCodigos();
    }

    /**
     * MÉTODO 1: Envía código de verificación al email (genera automáticamente).
     *
     * @param email Email del destinatario
     * @param nombreUsuario Nombre del usuario para personalizar el email
     * @return true si se envió correctamente
     */
    public boolean enviarCodigoVerificacion(String email, String nombreUsuario) {
        try {
            // 1. Generar código único
            String codigo = generador.generar();

            // 2. Verificar que no exista código repetido (aunque es muy improbable)
            while (codigosPorEmail.containsKey(email)) {
                codigo = generador.generar(); // Regenerar si existe
            }

            // 3. Guardar en HashMap con timestamp
            DatosCodigo datos = new DatosCodigo(codigo, LocalDateTime.now());
            codigosPorEmail.put(email, datos);

            System.out.println("✓ Código generado para " + email + ": " + codigo);

            // 4. Enviar por email
            boolean enviado = enviarEmail(email, nombreUsuario, codigo);

            if (!enviado) {
                // Si no se pudo enviar, eliminar del HashMap
                codigosPorEmail.remove(email);
                System.out.println("✗ No se pudo enviar el email, código eliminado");
                return false;
            }

            return true;

        } catch (Exception e) {
            System.err.println("✗ Error en enviarCodigoVerificacion: " + e.getMessage());
            e.printStackTrace();
            return false;
        }
    }

    /**
     * MÉTODO 2: Verifica si el código ingresado es correcto.
     *
     * @param email Email del usuario
     * @param codigoIngresado Código que el usuario escribió
     * @return true si el código es válido
     */
    public boolean verificarCodigo(String email, String codigoIngresado) {
        // Verificar si existe un código para este email
        DatosCodigo datosGuardados = codigosPorEmail.get(email);

        if (datosGuardados == null) {
            System.out.println("✗ No existe código para: " + email);
            return false;
        }

        // Verificar si el código ha expirado
        if (datosGuardados.haExpirado(MINUTOS_VALIDEZ)) {
            System.out.println("✗ Código expirado para: " + email);
            codigosPorEmail.remove(email); // Limpiar código expirado
            return false;
        }

        // Verificar si el código coincide
        boolean esValido = datosGuardados.codigo.equals(codigoIngresado);

        if (esValido) {
            System.out.println("✓ Código válido para: " + email);
            codigosPorEmail.remove(email); // Eliminar código usado
        } else {
            System.out.println("✗ Código incorrecto para: " + email);
        }

        return esValido;
    }

    /**
     * Verifica si existe un código activo para un email.
     *
     * @param email Email a verificar
     * @return true si hay un código activo y no ha expirado
     */
    public boolean tieneCodigoActivo(String email) {
        DatosCodigo datos = codigosPorEmail.get(email);
        return datos != null && !datos.haExpirado(MINUTOS_VALIDEZ);
    }

    /**
     * Limpia códigos expirados del sistema.
     */
    public void limpiarCodigosExpirados() {
        int cantidadAntes = codigosPorEmail.size();

        codigosPorEmail.entrySet().removeIf(entry ->
                entry.getValue().haExpirado(MINUTOS_VALIDEZ)
        );

        int cantidadDespues = codigosPorEmail.size();
        int eliminados = cantidadAntes - cantidadDespues;

        System.out.println("✓ Códigos expirados eliminados: " + eliminados);
        System.out.println("  Códigos activos restantes: " + cantidadDespues);
    }

    /**
     * Obtiene la cantidad de códigos activos en memoria.
     */
    public int cantidadCodigosActivos() {
        return codigosPorEmail.size();
    }

    // ========== MÉTODOS PRIVADOS INTERNOS ==========

    /**
     * Método privado que envía el email físicamente.
     */
    private boolean enviarEmail(String emailDestino, String nombreUsuario, String codigo) {
        String asunto = "SecureAccess - Código de Verificación";
        String mensajeHTML = construirHTMLCodigoVerificacion(nombreUsuario, codigo);

        try {
            Properties props = new Properties();
            props.put("mail.smtp.auth", "true");
            props.put("mail.smtp.starttls.enable", "true");
            props.put("mail.smtp.host", ConfiguracionEmail.SMTP_HOST);
            props.put("mail.smtp.port", ConfiguracionEmail.SMTP_PORT);
            props.put("mail.smtp.ssl.trust", ConfiguracionEmail.SMTP_HOST);
            props.put("mail.smtp.ssl.protocols", ConfiguracionEmail.PROTOCOLO_SSL);

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
            message.setSubject(asunto);
            message.setContent(mensajeHTML, "text/html; charset=utf-8");

            Transport.send(message);
            System.out.println("✓ Email enviado a: " + emailDestino);
            return true;

        } catch (Exception e) {
            System.err.println("✗ Error al enviar email: " + e.getMessage());
            return false;
        }
    }

    /**
     * Construye el HTML del email de verificación.
     */
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

    /**
     * Clase interna para almacenar código con timestamp.
     */
    private static class DatosCodigo {
        final String codigo;
        final LocalDateTime timestamp;

        DatosCodigo(String codigo, LocalDateTime timestamp) {
            this.codigo = codigo;
            this.timestamp = timestamp;
        }

        boolean haExpirado(int minutosValidez) {
            LocalDateTime ahora = LocalDateTime.now();
            LocalDateTime expiracion = timestamp.plusMinutes(minutosValidez);
            return ahora.isAfter(expiracion);
        }
    }
}

