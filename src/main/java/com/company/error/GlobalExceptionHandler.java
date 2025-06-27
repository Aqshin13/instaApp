package com.company.error;

import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.NoHandlerFoundException;
import org.springframework.web.servlet.resource.NoResourceFoundException;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(NoHandlerFoundException.class)
    public String handleNotFound(Exception ex) {
        return "redirect:/home";
    }

    @ExceptionHandler(NoResourceFoundException.class)
    public String handleResourceNotFound(Exception ex) {
        return "redirect:/home";
    }

    @ExceptionHandler(Exception.class)
    public String handleGlobalException(Exception ex, Model model) {
        model.addAttribute("errorMessage", "OOPS! An error occurred: " + ex.getMessage());
        return "error";
    }
}
