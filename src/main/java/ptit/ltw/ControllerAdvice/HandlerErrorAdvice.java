package ptit.ltw.ControllerAdvice;

import org.hibernate.HibernateException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.client.HttpServerErrorException;
import org.springframework.web.multipart.MaxUploadSizeExceededException;

import javax.servlet.http.HttpServletRequest;

@ControllerAdvice
public class HandlerErrorAdvice {
    @ExceptionHandler(MaxUploadSizeExceededException.class)
    public String handleMaxSizeException(MaxUploadSizeExceededException exc, HttpServletRequest request) {
        String message = "File upload quá lớn: Tối đa " + exc.getMaxUploadSize()/1024;
        request.setAttribute("message",message);
        return "error/500";
    }

    @ExceptionHandler(UsernameNotFoundException.class)
    public String handler(UsernameNotFoundException exc, HttpServletRequest request){
        request.setAttribute("message",exc.getMessage());
        return "error/500";
    }

    @ExceptionHandler(IllegalStateException.class)
    public String handlerIllegalStateException(IllegalStateException exc,HttpServletRequest request) {
        request.setAttribute("message",exc.getMessage());
        return "error/500";
    }

    @ExceptionHandler(HttpServerErrorException.InternalServerError.class)
    public String showViewError(HttpServerErrorException.InternalServerError exc, HttpServletRequest request){
        request.setAttribute("message",exc.getMessage());
        return "error/500";
    }

    @ExceptionHandler(HibernateException.class)
    public String handlerHibernateException(HibernateException exc, HttpServletRequest request) {
        request.setAttribute("message",exc.getMessage());
        return "error/500";
    }

    @ExceptionHandler(Exception.class)
    public String handlerException(Exception exc,HttpServletRequest request) {
        request.setAttribute("message",exc.getMessage());
        return "error/500";
    }



}