package projet.dsi31.usersmicroservice.util;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.AllArgsConstructor;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

@AllArgsConstructor
@Service
public class EmailService implements EmailSender {

    private final JavaMailSender mailSender;

    @Override
    public void sendEmail(String to, String email) {
        try {
            MimeMessage mimeMessage = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, true, "utf-8");
            helper.setText(email, true);
            helper.setTo(to);
            helper.setSubject("Confirm your email");
            helper.setFrom("nissafhassad13@gmail.com");
            mailSender.send(mimeMessage);
            System.out.println(">>> [EMAIL SERVICE] Email successfully sent to " + to);
        } catch (Exception e) {
            System.err.println(">>> [EMAIL SERVICE ERROR] Email sending failed: " + e.getMessage());
            e.printStackTrace();
        }
    }
}
