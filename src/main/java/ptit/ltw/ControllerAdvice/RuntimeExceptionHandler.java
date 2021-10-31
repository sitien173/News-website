package ptit.ltw.ControllerAdvice;

import org.springframework.security.access.AccessDeniedException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.NoHandlerFoundException;

@ControllerAdvice
public class RuntimeExceptionHandler extends RuntimeException{
    @ExceptionHandler(NoHandlerFoundException.class)
    public String showView404(){
        return "error/404";
    }

    @ExceptionHandler(AccessDeniedException.class)
    public String handler403(){
        return "error/40e";
    }
}
