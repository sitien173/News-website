package ptit.ltw.ControllerAdvice;

import org.hibernate.HibernateException;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.client.HttpServerErrorException;
import org.springframework.web.multipart.MaxUploadSizeExceededException;
import org.springframework.web.servlet.ModelAndView;

import javax.persistence.EntityNotFoundException;

@ControllerAdvice
public class HandlerErrorAdvice {

    @ExceptionHandler(MaxUploadSizeExceededException.class)
    public ResponseEntity<String> handleMaxSizeException(MaxUploadSizeExceededException exc) {
        return ResponseEntity.internalServerError().body(exc.getMessage());
    }

    @ExceptionHandler(UsernameNotFoundException.class)
    public ResponseEntity<?> handler(UsernameNotFoundException exc){
        return ResponseEntity.internalServerError().body(exc.getMessage());
    }

    @ExceptionHandler(IllegalStateException.class)
    public ResponseEntity<String> handlerIllegalStateException(IllegalStateException exc) {
        return ResponseEntity.internalServerError().body(exc.getMessage());
    }

    @ExceptionHandler(HttpServerErrorException.InternalServerError.class)
    public ModelAndView showViewError(HttpServerErrorException.InternalServerError exc){
        ModelAndView mv = new ModelAndView("error/500");
        mv.addObject("message",exc.getMessage());
        return mv;
    }
    @ExceptionHandler(EntityNotFoundException.class)
    public ResponseEntity<String> handlerEntityNotFound(EntityNotFoundException exc) {
        return ResponseEntity.internalServerError().body(exc.getMessage());
    }

    @ExceptionHandler(HibernateException.class)
    public ResponseEntity<String> handlerHibernateException(HibernateException exc) {
        return ResponseEntity.internalServerError().body(exc.getMessage());
    }
}
