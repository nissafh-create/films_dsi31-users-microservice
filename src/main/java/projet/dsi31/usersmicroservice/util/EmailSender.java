package projet.dsi31.usersmicroservice.util;

public interface EmailSender {
    void sendEmail(String toEmail, String body);
}

