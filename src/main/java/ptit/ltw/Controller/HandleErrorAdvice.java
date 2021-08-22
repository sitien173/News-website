package ptit.ltw.Controller;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.multipart.MaxUploadSizeExceededException;

@ControllerAdvice
public class HandleErrorAdvice {

    @ExceptionHandler(MaxUploadSizeExceededException.class)
    public ResponseEntity<String> handleMaxSizeException(MaxUploadSizeExceededException exc) {
        return ResponseEntity.internalServerError().body(buildPage(exc.getMessage()));
    }

    @ExceptionHandler(UsernameNotFoundException.class)
    public ResponseEntity<?> handler(UsernameNotFoundException exc){
        return ResponseEntity.internalServerError().body(buildPage(exc.getMessage()));
    }

    @ExceptionHandler(IllegalStateException.class)
    public ResponseEntity<String> handlerIllegalStateException(IllegalStateException exc) {
        return ResponseEntity.internalServerError().body(buildPage(exc.getMessage()));
    }

    private String buildPage(String messenger){
        return "<!DOCTYPE html>\n" +
                "<html lang=\"en\" xmlns:th=\"http://www.thymeleaf.org\">\n" +
                "<head>\n" +
                "    <meta charset=\"UTF-8\">\n" +
                "    <title>Chờ xác nhận</title>\n" +
                "    <link rel=\"stylesheet\" href=\"https://unpkg.com/spectre.css/dist/spectre.min.css\">\n" +
                "    <link rel=\"stylesheet\" href=\"https://unpkg.com/spectre.css/dist/spectre-exp.min.css\">\n" +
                "    <link rel=\"stylesheet\" href=\"https://unpkg.com/spectre.css/dist/spectre-icons.min.css\">\n" +
                "    <style>\n" +
                "        .box-shadow {\n" +
                "            border: 0 none lightgray;\n" +
                "            box-shadow: rgba(0, 0, 0, 0.45) 0px 25px 20px -20px;\n" +
                "        }\n" +
                "        .text-break{\n" +
                "            width: 500px;\n" +
                "            word-wrap: break-word;\n" +
                "        }\n" +
                "    </style>\n" +
                "</head>\n" +
                "<body>\n" +
                "<div class=\"container d-flex flex-centered\" style=\"margin-top: 50px\">\n" +
                "    <div class=\"card d-flex flex-centered box-shadow\">\n" +
                "        <div class=\"card-body\">\n" +
                "            <h5  class=\"text-bold text-break\">"+messenger+"</h5>\n" +
                "            <div class=\"text-center\"><a href=\"javascript:window.history.back()\">Back</a></div>\n" +
                "        </div>\n" +
                "    </div>\n" +
                "</div>\n" +
                "</body>\n" +
                "</html>";
    }
}
