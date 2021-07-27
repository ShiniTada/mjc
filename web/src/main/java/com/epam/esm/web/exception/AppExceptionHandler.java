package com.epam.esm.web.exception;

import com.epam.esm.service.exception.ResourceNotFoundException;
import com.epam.esm.service.exception.ServiceException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import java.util.Locale;
import java.util.Objects;

/**
 * Exceptions handler.
 */
@RestControllerAdvice
public class AppExceptionHandler {

    private final MessageSource messageSource;
    private static final int NOT_FOUND_CUSTOM_CODE = 40401;
    private static final int BAD_REQUEST_SERVICE_CUSTOM_CODE = 40001;
    private static final int BAD_REQUEST_VALIDATION_PARAM_CUSTOM_CODE = 40002;
    private static final int BAD_REQUEST_VALIDATION_PATH_VARIABLE_CUSTOM_CODE = 40003;
    private static final int BAD_REQUEST_VALIDATION_ID_CUSTOM_CODE = 40004;
    private static final String VALIDATION_REQUEST_PARAM = "validation.requestParam";
    private static final String VALIDATION_PATH_VARIABLE = "validation.pathVariable";
    private static final String ACCESS_DENIED = "access.denied";
    private static final String INCORRECT_VALUE = "incorrect.value";

    /**
     * Instantiates a new AppExceptionHandler
     *
     * @param messageSource the message source
     */
    @Autowired
    public AppExceptionHandler(MessageSource messageSource) {
        this.messageSource = messageSource;
    }

    @ExceptionHandler(ServiceException.class)
    public ResponseEntity<WebException> catchServiceException(ServiceException e, Locale locale) {
        String errorMessage = messageSource.getMessage((e.getMessage()), new Object[]{}, locale);
        return new ResponseEntity<>(new WebException(errorMessage, BAD_REQUEST_SERVICE_CUSTOM_CODE), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<WebException> resourceNotFound(ResourceNotFoundException e, Locale locale) {
        String errorMessage = messageSource.getMessage((e.getMessage()), new Object[]{}, locale);
        return new ResponseEntity<>(new WebException(errorMessage, NOT_FOUND_CUSTOM_CODE), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<WebException> validationException(MethodArgumentNotValidException e, Locale locale) {
        String errorMessage = messageSource.getMessage(Objects.requireNonNull(Objects.requireNonNull(e.getBindingResult().getFieldError()).getDefaultMessage()), new Object[]{}, locale);
        return new ResponseEntity<>(new WebException(errorMessage, BAD_REQUEST_VALIDATION_PARAM_CUSTOM_CODE), HttpStatus.BAD_REQUEST);
    }


    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    public ResponseEntity<WebException> validationException(MethodArgumentTypeMismatchException e, Locale locale) {
        String errorMessage = messageSource.getMessage((VALIDATION_REQUEST_PARAM), new Object[]{}, locale);
        return new ResponseEntity<>(new WebException(errorMessage, BAD_REQUEST_VALIDATION_PATH_VARIABLE_CUSTOM_CODE), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<WebException> validationException(ConstraintViolationException e, Locale locale) {
        String errorMessage = messageSource.getMessage((VALIDATION_PATH_VARIABLE), new Object[]{}, locale);
        StringBuilder message = new StringBuilder(messageSource.getMessage(INCORRECT_VALUE, new Object[]{}, locale));

        for (ConstraintViolation<?> violation : e.getConstraintViolations()) {
            message.append(violation.getInvalidValue().toString()).append(". ");
            message.append(errorMessage);
        }
        return new ResponseEntity<>(new WebException(message.toString(), BAD_REQUEST_VALIDATION_ID_CUSTOM_CODE), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(AccessDeniedException.class)
    public ResponseEntity<WebException> errorAccess(AccessDeniedException e, Locale locale) {
        String errorMessage = messageSource.getMessage(
                (ACCESS_DENIED), new Object[]{}, locale);
        return new ResponseEntity<>(new WebException(errorMessage, 40301), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(Throwable.class)
    public ResponseEntity<WebException> badRequest(Throwable e) {
        return new ResponseEntity<>(new WebException(e.getMessage(), 400), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<WebException> badRequest(RuntimeException e) {
        return new ResponseEntity<>(new WebException(e.getMessage(), 400), HttpStatus.BAD_REQUEST);
    }
}
