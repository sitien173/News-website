package ptit.ltw.Service;

public interface MailService {
    void sendMail(String to, String subject, String text);
}
