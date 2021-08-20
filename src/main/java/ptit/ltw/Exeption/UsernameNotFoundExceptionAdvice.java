package ptit.ltw.Exeption;

import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class UsernameNotFoundExceptionAdvice {
    @ExceptionHandler(UsernameNotFoundException.class)
    public String showViewError(UsernameNotFoundException exc){
        return buildPage(exc.getMessage());
    }

    private String buildPage(String messenger) {
        return "<!DOCTYPE html>\n" +
                "<html lang=\"en\" xmlns:th=\"http://www.thymeleaf.org\">\n" +
                "<head>\n" +
                "<meta charset=\"UTF-8\">\n" +
                "<title>Exception</title>\n" +
                "<link rel=\"stylesheet\" href=\"https://unpkg.com/spectre.css/dist/spectre.min.css\">\n" +
                "<link rel=\"stylesheet\" href=\"https://unpkg.com/spectre.css/dist/spectre-exp.min.css\">\n" +
                "<link rel=\"stylesheet\" href=\"https://unpkg.com/spectre.css/dist/spectre-icons.min.css\">\n" +
                "</head>\n" +
                "<body>\n" +
                "<div class=\"container d-flex flex-centered\" style=\"margin-top: 50px\">\n" +
                "<div class=\"col-6-auto\">\n" +
                "<h5 class=\"text-bold\">" + messenger + "</h5>\n" +
                "</div>\n" +
                "</div>\n" +
                "<button onclick='window.history.back()'>Back</button>" +
                "</body>\n" +
                "</html>";
    }

}
