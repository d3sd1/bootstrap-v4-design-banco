package utils;

import config.Configuration;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.apache.commons.mail.Email;
import org.apache.commons.mail.EmailException;
import org.apache.commons.mail.SimpleEmail;

public class SendMail
{

    public void sendPinCode(String email, int pin)
    {
        this.send(email, "Bienvenido a BBVA", "Tu PIN es: " + pin);
    }
    public void send(String to, String subject, String msg)
    {
        try
        {
            Email email = new SimpleEmail();

            email.setHostName(Configuration.getInstance().getSmtpServer());
            email.setSmtpPort(Configuration.getInstance().getSmtpPort());
            email.setAuthentication(Configuration.getInstance().getMailFrom(), Configuration.getInstance().getMailPass());
            email.setStartTLSEnabled(true);
            email.setFrom("Clase DAW<" + Configuration.getInstance().getMailFrom() + ">");
            email.setSubject(subject);
            email.setContent(msg, "text/html; charset=utf-8");
            email.addTo(to);

            email.send();
        }
        catch (EmailException ex)
        {
            Logger.getLogger(SendMail.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
