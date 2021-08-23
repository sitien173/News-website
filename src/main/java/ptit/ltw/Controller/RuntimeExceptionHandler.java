package ptit.ltw.Controller;

import org.springframework.security.access.AccessDeniedException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.NoHandlerFoundException;

@ControllerAdvice
public class RuntimeExceptionHandler extends RuntimeException{
    @ExceptionHandler(NoHandlerFoundException.class)
    public ModelAndView showView404(NoHandlerFoundException exc){
        ModelAndView mv = new ModelAndView("error/404");
        mv.addObject("message",exc.getMessage());
        return mv;
    }
    @ExceptionHandler(AccessDeniedException.class)
    public ModelAndView handler403(AccessDeniedException exc){
        ModelAndView mv = new ModelAndView("error/403");
        mv.addObject("message",exc.getMessage());
        return mv;
    }
    @ExceptionHandler(NullPointerException.class)
    public ModelAndView handlerNullPointerException(NullPointerException exc){
        ModelAndView mv = new ModelAndView("error/500");
        mv.addObject("message",exc.getMessage());
        return mv;
    }
}
