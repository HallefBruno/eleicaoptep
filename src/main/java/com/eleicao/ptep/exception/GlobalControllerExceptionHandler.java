package com.eleicao.ptep.exception;

import javax.servlet.http.HttpServletRequest;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.servlet.ModelAndView;

/**
 *
 * @author hallef
 */
@ControllerAdvice
class GlobalDefaultExceptionHandler {

    public static final String DEFAULT_ERROR_VIEW = "error";

    @ExceptionHandler(value = Exception.class)
    public ModelAndView defaultErrorHandler(HttpServletRequest req, Exception ex) throws Exception {
        ModelAndView mav = new ModelAndView();
        if (AnnotationUtils.findAnnotation(ex.getClass(), ResponseStatus.class) != null) {
            throw ex;
        }
        if(ex instanceof DataIntegrityViolationException) {
            String msg = ((DataIntegrityViolationException) ex).getMostSpecificCause().getMessage();
            if(msg.contains("Detalhe")) {
                msg = msg.substring(msg.lastIndexOf("Detalhe"), msg.length());
                mav.addObject("exception", msg);
            }
        } else if (ex instanceof NullPointerException) {
            mav.addObject("exception", "Erro grave, favor entrar em contato com Admin do sistema!");
        }
        mav.addObject("url", req.getRequestURL());
        mav.setViewName(DEFAULT_ERROR_VIEW);
        return mav;
    }
}
