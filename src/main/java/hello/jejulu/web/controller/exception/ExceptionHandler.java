package hello.jejulu.web.controller.exception;

import hello.jejulu.web.exception.CustomException;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;


@ControllerAdvice
public class ExceptionHandler {

    @org.springframework.web.bind.annotation.ExceptionHandler(CustomException.class)
    public String exceptionHandler(CustomException ex, Model model){
        String message = ex.getErrorCode().getDetail();
        model.addAttribute("message",message);
        return "error/error";
    }
}
