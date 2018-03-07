package utils;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import java.security.SecureRandom;
import java.time.Instant;
import java.util.Calendar;
import java.util.Date;
import java.util.Random;
import model.User;

public class Utils
{

    private final String ALPHA_NUMERIC_STRING = "ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";

    public String generateUserToken(User user)
    {
        Date now = new Date();
        Date expiration = new Date(now.getTime() + Constantes.MINUTOS_EXPIRACION_LOGIN_TOKEN * 60 * 1000);
        String token;
        token = Jwts.builder()
                .setIssuer("BBVA")
                .setSubject("INTERNAL_LOGIN")
                .claim("name", user.getName() + " " + user.getSurnames())
                .claim("dni", user.getDni())
                .setIssuedAt(now)
                .setExpiration(expiration)
                .signWith(SignatureAlgorithm.HS256, Constantes.CLAVE_PRIVADA_TOKENS)
                .compact();
        return token;
    }

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
                int numero = Integer.parseInt(numeroCuenta.substring(i, i + 1));
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
