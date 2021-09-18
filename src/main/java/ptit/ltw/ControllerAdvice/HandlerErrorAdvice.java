package ptit.ltw.ControllerAdvice;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.client.HttpServerErrorException;
import org.springframework.web.multipart.MaxUploadSizeExceededException;
import org.springframework.web.servlet.ModelAndView;

import javax.persistence.EntityNotFoundException;
import javax.validation.ConstraintViolationException;
import java.sql.SQLIntegrityConstraintViolationException;

@ControllerAdvice
public class HandlerErrorAdvice {

    @ExceptionHandler(MaxUploadSizeExceededException.class)
    public ResponseEntity<String> handleMaxSizeException(MaxUploadSizeExceededException exc) {
        return ResponseEntity.internalServerError().body(buildPageError(exc.getMessage()));
    }

    @ExceptionHandler(UsernameNotFoundException.class)
    public ResponseEntity<?> handler(UsernameNotFoundException exc){
        return ResponseEntity.internalServerError().body(buildPageError(exc.getMessage()));
    }

    @ExceptionHandler(IllegalStateException.class)
    public ResponseEntity<String> handlerIllegalStateException(IllegalStateException exc) {
        return ResponseEntity.internalServerError().body(buildPageError(exc.getMessage()));
    }

    @ExceptionHandler(EntityNotFoundException.class)
    public ResponseEntity<String> handlerEntityNotFound(EntityNotFoundException exc) {
        return ResponseEntity.internalServerError().body(buildPageError(exc.getMessage()));
    }

    @ExceptionHandler(HttpServerErrorException.InternalServerError.class)
    public ModelAndView showViewError(HttpServerErrorException.InternalServerError exc){
        ModelAndView mv = new ModelAndView("error/500");
        mv.addObject("message",exc.getMessage());
        return mv;
    }

    // delete object failed
    @ExceptionHandler(SQLIntegrityConstraintViolationException.class)
    public ResponseEntity<String> handlerDeleteFailed(SQLIntegrityConstraintViolationException exc) {
        String message = "<div class='text-left'>" +
                "<h4><strong>Message: </strong><em>Không thể xoá đối tượng bởi vì đang có ràng buộc dữ liệu</em></h4> " +
                "<h4><strong>Error Code: </strong> "+exc.getErrorCode()+" </h4>" +
                "<h4 class='text-break'><strong>Error Messenger:</strong> <em>"+ exc.getMessage() +"</em> </h4>" +
                "</div>";

        return ResponseEntity.internalServerError().body(buildPageError(message));
    }

    private String buildPageError(String message){
        return "<!DOCTYPE html>\n" +
                "<html lang=\"en\" xmlns:th=\"http://www.thymeleaf.org\">\n" +
                "<head>\n" +
                "    <meta charset=\"UTF-8\">\n" +
                "    <title>Lỗi</title>\n" +
                "    <link rel=\"stylesheet\" href=\"https://unpkg.com/spectre.css/dist/spectre.min.css\">\n" +
                "    <link rel=\"stylesheet\" href=\"https://unpkg.com/spectre.css/dist/spectre-exp.min.css\">\n" +
                "    <link rel=\"stylesheet\" href=\"https://unpkg.com/spectre.css/dist/spectre-icons.min.css\">\n" +
                "    <style>\n" +
                "        .box-shadow {\n" +
                "            border: 0 none lightgray;\n" +
                "            box-shadow: rgba(0, 0, 0, 0.45) 0px 25px 20px -20px;\n" +
                "        }\n" +
                "\n" +
                "    </style>\n" +
                "</head>\n" +
                "<body>\n" +
                "<div class=\"container d-flex flex-centered\" style=\"margin-top: 50px\">\n" +
                "    <div class=\"card d-flex flex-centered box-shadow\">\n" +
                "        <div class=\"card-body\">\n" +
                "            <div class=\"text-center\">\n" +
                "                <div class='text-left'>"+message+"</div>\n" +
                "            </div>\n" +
                "            <div class=\"text-center\">\n" +
                "                <button  type=\"submit\" onclick=\"history.back()\" class=\"btn btn-primary input-group-btn\">Quay lại</button>\n" +
                "            </div>\n" +
                "        </div>\n" +
                "    </div>\n" +
                "</div>\n" +
                "</body>\n" +
                "</html>";
    }
}
