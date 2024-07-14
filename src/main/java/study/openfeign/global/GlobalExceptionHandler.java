package study.openfeign.global;

import jakarta.persistence.EntityExistsException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(EntityExistsException.class)
    public String entityExistsException(EntityExistsException e) {
        return "user jwt";
    }
}
