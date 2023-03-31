package br.dev.diego.springsecurity.controllers.exceptions;

import br.dev.diego.springsecurity.services.exceptions.TokenVerifyException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@RestControllerAdvice
public class ControllerExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(TokenVerifyException.class)
    public ResponseEntity<ProblemDetail> handleTokenVerifyException(TokenVerifyException ex) {
        HttpStatus status = HttpStatus.UNPROCESSABLE_ENTITY;
        ProblemDetail problemDetail = ProblemDetail.forStatusAndDetail(status, ex.getMessage());
        return ResponseEntity.status(status.value()).body(problemDetail);
    }

}
