package com.xpotify.exception;

import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.NoHandlerFoundException;

@ControllerAdvice
@Log4j2
public class HandleException {

//    @ResponseStatus(HttpStatus.NOT_FOUND)
//    @ExceptionHandler(NoHandlerFoundException.class)
//    public String error404(Exception ex) {
//        return "404";
//    }
}
