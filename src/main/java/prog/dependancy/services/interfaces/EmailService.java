package prog.dependancy.services.interfaces;
public interface EmailService {
    void sendEmail(String to, String subject, String message, String htmlContent);
    void sendEmail(String to, String subject, String message);
    void sendEmail(String to, String message);
}


