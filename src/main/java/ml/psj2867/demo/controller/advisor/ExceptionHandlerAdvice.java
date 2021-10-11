package ml.psj2867.demo.controller.advisor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.validation.BindException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@ControllerAdvice
public class ExceptionHandlerAdvice {

    @ExceptionHandler(Exception.class)
    public String exception(  HttpServletRequest request
                            , HttpServletResponse reponse
                            , Exception e){
        log.warn("{}", request.getRequestURL());
        log.warn("", e); 
        return "redirect:/error";
    }
    
    // @ExceptionHandler(BindException.class)
    // public String BindException(  HttpServletRequest request
    //                         , HttpServletResponse reponse
    //                         , Exception e){
    //     log.warn("{}", request.getRequestURL());
    //     log.warn("", e); 
    //     return "redirect:/error";
    // }

}