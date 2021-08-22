package ptit.ltw.Exeption;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class IllegalStateExceptionAdvice extends ResponseEntityExceptionHandler {
    @ExceptionHandler(IllegalStateException.class)
    public ResponseEntity<String> handlerIllegalStateException(IllegalStateException exc) {
        return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED)
                .body(exc.getMessage());
    }
}
