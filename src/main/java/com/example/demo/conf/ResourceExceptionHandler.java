package com.example.demo.conf;

import com.example.demo.exception.InternalPreconditionFailedException;
import com.example.demo.exception.InternalRollBackableException;
import com.example.demo.exception.MainBadRequestException;
import com.example.demo.exception.MainErrorMessage;
import com.example.demo.exception.MainNotFoundException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.time.LocalDateTime;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@ControllerAdvice
public class ResourceExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(MainNotFoundException.class)
    public ResponseEntity<MainErrorMessage> handleMainNotFoundException(MainNotFoundException ex, WebRequest request) {
        log.warn("Error, should be rollback: {}", ex.getMessage());
        log.debug("Error, should be rollback:", ex);
        final MainErrorMessage message = MainErrorMessage.builder()
                                                         .status(HttpStatus.NOT_FOUND.value())
                                                         .timestamp(LocalDateTime.now())
                                                         .error("Bad request")
                                                         .path(request.getDescription(false))
                                                         .message(ex.getMessage())
                                                         .description("Error main not found exception some description. ")
                                                         .build();

        return createResponseEntity(message, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(MainBadRequestException.class)
    public ResponseEntity<MainErrorMessage> handleMainBadRequestException(MainBadRequestException ex, WebRequest request) {
        log.warn("Error, should be rollback: {}", ex.getMessage());
        log.debug("Error, should be rollback:", ex);
        final MainErrorMessage message = MainErrorMessage.builder()
                                                         .status(HttpStatus.BAD_REQUEST.value())
                                                         .timestamp(LocalDateTime.now())
                                                         .error("Bad request")
                                                         .path(request.getDescription(false))
                                                         .message(ex.getMessage())
                                                         .description("Error main not found exception some description.")
                                                         .build();

        return createResponseEntity(message, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(InternalRollBackableException.class)
    public ResponseEntity<MainErrorMessage> handleInternalRollBackableException(InternalRollBackableException ex, WebRequest request) {
        log.warn("Error, should be rollback: {}", ex.getMessage());
        log.debug("Error, should be rollback:", ex);
        final MainErrorMessage message = MainErrorMessage.builder()
                                                         .status(HttpStatus.BAD_REQUEST.value())
                                                         .timestamp(LocalDateTime.now())
                                                         .error("Bad request")
                                                         .path(request.getDescription(false))
                                                         .message(ex.getMessage())
                                                         .description("Error internal rollback exception some description.")
                                                         .build();

        return createResponseEntity(message, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(InternalPreconditionFailedException.class)
    public ResponseEntity<MainErrorMessage> handleInternalRollBackableException(InternalPreconditionFailedException ex, WebRequest request) {
        log.warn("Error, should be rollback: {}", ex.getMessage());
        log.debug("Error, should be rollback:", ex);
        final MainErrorMessage message = MainErrorMessage.builder()
                                                         .status(HttpStatus.FORBIDDEN.value())
                                                         .timestamp(LocalDateTime.now())
                                                         .error("Bad request")
                                                         .path(request.getDescription(false))
                                                         .message(ex.getMessage())
                                                         .description("Error internal precondition exception some description.")
                                                         .build();

        return createResponseEntity(message, HttpStatus.FORBIDDEN);
    }

    @ExceptionHandler({ Exception.class })
    public ResponseEntity<MainErrorMessage> globalExceptionHandler(Exception ex, WebRequest request) {
        log.error("Technical error: ", ex);
        final MainErrorMessage message = MainErrorMessage.builder()
                                                         .status(HttpStatus.INTERNAL_SERVER_ERROR.value())
                                                         .timestamp(LocalDateTime.now())
                                                         .error("Technical error")
                                                         .path(request.getDescription(false))
                                                         .message(ex.getMessage())
                                                         .description("Error exception some description.")
                                                         .build();

        return createResponseEntity(message, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    private ResponseEntity<MainErrorMessage> createResponseEntity(final MainErrorMessage message, final HttpStatus status) {
        final HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setContentType(MediaType.APPLICATION_JSON);

        return new ResponseEntity<>(message, httpHeaders, status);
    }
}
