package org.secureaccess.app.secureaccessbackend.email;
import java.util.Random;
public class GeneradorCodigos {
    public String generar() {
        int array[] = new int[6];

        for (int i = 0; i < 6; i++) {
            int numeroRandom = (int) (Math.random() * 10);
            array[i] = numeroRandom;
        }

        String codigo = "";
        for (int digito : array) {
            codigo += String.valueOf(digito);
        }

        return codigo;
    }
}
