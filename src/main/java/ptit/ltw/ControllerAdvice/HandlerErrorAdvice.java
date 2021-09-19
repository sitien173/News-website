package ptit.ltw.ControllerAdvice;

import org.hibernate.Hibernate;
import org.hibernate.HibernateException;
import org.hibernate.exception.DataException;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.client.HttpServerErrorException;
import org.springframework.web.multipart.MaxUploadSizeExceededException;

import javax.persistence.EntityNotFoundException;
import java.sql.SQLIntegrityConstraintViolationException;

@ControllerAdvice
public class HandlerErrorAdvice {

    @ExceptionHandler(MaxUploadSizeExceededException.class)
    public ResponseEntity<String> handleMaxSizeException(MaxUploadSizeExceededException exc) {
        String message = "<div class='text-left'>" +
                "<h4><strong>Message: </strong><em>File Upload Quá Lớn</em></h4> " +
                "<h4 class='text-break'><strong>Error Messenger:</strong> <em>"+ exc.getMessage() +"</em> </h4>" +
                "</div>";
        return ResponseEntity.internalServerError().body(buildPageError(message));
    }

    @ExceptionHandler(UsernameNotFoundException.class)
    public ResponseEntity<?> handler(UsernameNotFoundException exc){
        String message = "<div class='text-left'>" +
                "<h4><strong>Message: </strong><em>Email Không Tồn Tại</em></h4> " +
                "<h4 class='text-break'><strong>Error Messenger:</strong> <em>"+ exc.getMessage() +"</em> </h4>" +
                "</div>";
        return ResponseEntity.internalServerError().body(buildPageError(message));
    }

    @ExceptionHandler(IllegalStateException.class)
    public ResponseEntity<String> handlerIllegalStateException(IllegalStateException exc) {
        String message = "<div class='text-left'>" +
                "<h4><strong>Message: </strong><em>IllegalStateException</em></h4> " +
                "<h4 class='text-break'><strong>Error Messenger:</strong> <em>"+ exc.getMessage() +"</em> </h4>" +
                "</div>";
        return ResponseEntity.internalServerError().body(buildPageError(message));
    }

    @ExceptionHandler(EntityNotFoundException.class)
    public ResponseEntity<String> handlerEntityNotFound(EntityNotFoundException exc) {
        String message = "<div class='text-left'>" +
                "<h4><strong>Message: </strong><em>Thực Thể Không Tồn Tại Trong CSDL</em></h4> " +
                "<h4 class='text-break'><strong>Error Messenger:</strong> <em>"+ exc.getMessage() +"</em> </h4>" +
                "</div>";

        return ResponseEntity.internalServerError().body(buildPageError(message));
    }

    @ExceptionHandler(HttpServerErrorException.InternalServerError.class)
    public ResponseEntity<String> showViewError(HttpServerErrorException.InternalServerError exc){
        String message = "<div class='text-left'>" +
                "<h4><strong>Error Code: </strong> "+exc.getStatusCode().toString()+" </h4>" +
                "<h4 class='text-break'><strong>Error Messenger:</strong> <em>"+ exc.getMessage() +"</em> </h4>" +
                "</div>";
        return ResponseEntity.internalServerError().body(buildPageError(message));
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

    @ExceptionHandler(DataException.class)
    public ResponseEntity<String> handlerDataException(DataException exc) {
        String message = "<div class='text-left'>" +
                "<h4><strong>Message: </strong><em>Thêm Dữ Liệu Vào CSDL Thất Bại. Dữ liệu quá dài cho cột </em></h4> " +
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
