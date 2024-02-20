package by.saveliykomlenok.boardgamesstore.util;

import org.springdoc.api.ErrorMessage;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.nio.file.AccessDeniedException;

@RestControllerAdvice
public class RestErrorHandler {
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorMessage> handleErrors(Exception exception){
        ErrorMessage errorMessage = new ErrorMessage(exception.getMessage());
        if(exception instanceof UsernameNotFoundException){
            errorMessage.setMessage("Username not found");
        }
        if(exception instanceof BadCredentialsException){
            errorMessage.setMessage("Bad credentials");
        }
        if(exception instanceof AccessDeniedException){
            errorMessage.setMessage("Access denied");
        }
        return ResponseEntity.status(HttpStatus.FORBIDDEN).body(errorMessage);
    }
}
