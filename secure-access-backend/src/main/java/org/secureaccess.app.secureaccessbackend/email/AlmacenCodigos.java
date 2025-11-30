package org.secureaccess.app.secureaccessbackend.email;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

public class AlmacenCodigos {
    private final Map<String, DatosCodigo> codigosActivos;
    private static final int MINUTOS_VALIDEZ = 1;

    public AlmacenCodigos() {
        this.codigosActivos = new HashMap<>();
    }

    public void guardar(String email, String codigo) {
        DatosCodigo datos = new DatosCodigo(codigo, LocalDateTime.now());
        codigosActivos.put(email, datos);
        System.out.println("✓ Código guardado para: " + email);
    }

    public boolean validar(String email, String codigoIngresado) {
        DatosCodigo datosGuardados = codigosActivos.get(email);

        if (datosGuardados == null) {
            System.out.println("✗ No existe código para: " + email);
            return false;
        }

        if (datosGuardados.haExpirado(MINUTOS_VALIDEZ)) {
            System.out.println("✗ Código expirado para: " + email);
            codigosActivos.remove(email);
            return false;
        }

        boolean esValido = datosGuardados.codigo.equals(codigoIngresado);

        if (esValido) {
            System.out.println("✓ Código válido para: " + email);
            codigosActivos.remove(email);
        } else {
            System.out.println("✗ Código incorrecto para: " + email);
        }

        return esValido;
    }

    public void eliminar(String email) {
        codigosActivos.remove(email);
    }

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
