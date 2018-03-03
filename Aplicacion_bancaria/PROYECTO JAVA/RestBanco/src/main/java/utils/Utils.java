package utils;

import java.security.SecureRandom;
import java.util.Random;

public class Utils
{

    private final String ALPHA_NUMERIC_STRING = "ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";

    public String randomAlphaNumeric(int count)
    {
        StringBuilder builder = new StringBuilder();
        Random r = new SecureRandom();

        while (count-- != 0)
        {
            int character = (int) (r.nextInt(ALPHA_NUMERIC_STRING.length()));
            builder.append(ALPHA_NUMERIC_STRING.charAt(character));
        }
        return builder.toString();
    }

    public boolean comprobarFormatoCuenta(String numeroCuenta)
    {
        int suma = 0;
        for (int i = 0; i < numeroCuenta.length(); i++)
        {
            if (i < 9)
            {
                int numero = Integer.parseInt(numeroCuenta.substring(i, i+1));
                suma += numero;
            }
            else
            {
                break;
            }
        }
        int numeroFinalResultado = suma % 9;
        return numeroFinalResultado == Integer.parseInt(numeroCuenta.substring(9, 10));
    }
}