package hello.jejulu.web.controller.exception;

import hello.jejulu.web.exception.CustomException;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;

/**
 * 예외 처리 핸들러 ( 모든 예외 처리는 해당 컨트롤러 객체가 받음 )
 */
@ControllerAdvice
public class ExceptionHandler {
    /**
     * 예외 처리 핸들러 (CustomException 예외는 해당 핸들러에서 처리)
     * @param ex 예외 객체
     * @param model view로 넘겨줌
     */
    @org.springframework.web.bind.annotation.ExceptionHandler(CustomException.class)
    public String exceptionHandler(CustomException ex, Model model){
        String message = ex.getErrorCode().getDetail();
        model.addAttribute("message",message);
        return "error/error";
    }
}
