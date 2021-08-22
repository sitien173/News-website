package ptit.ltw.Controller;

import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.servlet.NoHandlerFoundException;

@ControllerAdvice
public class RuntimeExceptionHandler extends RuntimeException{
    @ExceptionHandler(NoHandlerFoundException.class)
    public String showView404(){
       return "/error/404";
    }
    @ExceptionHandler(HttpClientErrorException.Forbidden.class)
    public String showView403(){
        return "/error/403";
    }

}
