package co.uniquindio.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(ApiExceptions.EmailAlreadyExistsException.class)
    public ResponseEntity<?> handleEmailAlreadyExists(ApiExceptions.EmailAlreadyExistsException ex) {
        return buildResponse(ex.getMessage(), HttpStatus.CONFLICT);
    }

    @ExceptionHandler(ApiExceptions.InternalServerErrorException.class)
    public ResponseEntity<?> handleInternalError(ApiExceptions.InternalServerErrorException ex) {
        return buildResponse(ex.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(ApiExceptions.BadRequestException.class)
    public ResponseEntity<?> handleBadRequest(ApiExceptions.BadRequestException ex) {
        return buildResponse(ex.getMessage(), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(ApiExceptions.NotFoundException.class)
    public ResponseEntity<?> handleNotFound(ApiExceptions.NotFoundException ex) {
        return buildResponse(ex.getMessage(), HttpStatus.NOT_FOUND);
    }

    // Default para cualquier RuntimeException que no hayas capturado antes
    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<?> handleRuntimeException(RuntimeException ex) {
        return buildResponse("Error inesperado: " + ex.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
    }

    private ResponseEntity<?> buildResponse(String message, HttpStatus status) {
        Map<String, Object> errorBody = new HashMap<>();
        errorBody.put("timestamp", LocalDateTime.now());
        errorBody.put("status", status.value());
        errorBody.put("error", status.getReasonPhrase());
        errorBody.put("message", message);

        return new ResponseEntity<>(errorBody, status);
    }

    @ExceptionHandler(NotFoundException.class)
    public ResponseEntity<?> handleNotFound(NotFoundException ex) {
        return buildResponse(ex.getMessage(), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(InvalidValueException.class)
    public ResponseEntity<?> handleInvalidValue(InvalidValueException ex) {
        return buildResponse(ex.getMessage(), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(NotPermissionException.class)
    public ResponseEntity<?> handleNotPermission(NotPermissionException ex) {
        return buildResponse(ex.getMessage(), HttpStatus.FORBIDDEN);
    }

    @ExceptionHandler(AccessDeniedException.class)
    public ResponseEntity<?> handleAccessDenied(AccessDeniedException ex) {
        return buildResponse(ex.getMessage(), HttpStatus.FORBIDDEN);
    }

    @ExceptionHandler(FoundMatchException.class)
    public ResponseEntity<?> handleFoundMatch(FoundMatchException ex) {
        return buildResponse(ex.getMessage(), HttpStatus.NOT_ACCEPTABLE);
    }
}
