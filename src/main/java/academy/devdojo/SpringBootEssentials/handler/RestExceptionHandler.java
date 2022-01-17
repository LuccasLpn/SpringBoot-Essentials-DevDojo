package academy.devdojo.SpringBootEssentials.handler;

import academy.devdojo.SpringBootEssentials.exception.BadRequestException;
import academy.devdojo.SpringBootEssentials.exception.BadRequestExceptionDetails;
import academy.devdojo.SpringBootEssentials.exception.ValidationExceptionDetails;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@ControllerAdvice
public class RestExceptionHandler {

    @ExceptionHandler(BadRequestException.class)
    public ResponseEntity<BadRequestExceptionDetails> handlerBadRequestException(BadRequestException exception){
        return new ResponseEntity<>(
                BadRequestExceptionDetails.builder()
                        .timestamp(LocalDateTime.now())
                        .status(HttpStatus.BAD_REQUEST.value())
                        .title("Bad Request Exception, Check the Documentation")
                        .details(exception.getMessage())
                        .developerMessage(exception.getClass().getName())
                        .build(), HttpStatus.BAD_REQUEST);

    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ValidationExceptionDetails>
            handlerMethodArgumentNotValidException
            (MethodArgumentNotValidException exception){
                List<FieldError> fieldErrors = exception.getBindingResult().getFieldErrors();
                String fields = fieldErrors.stream().map(FieldError::getField)
                        .collect(Collectors.joining(", "));
        String fieldsMessage = fieldErrors.stream().map(FieldError::getDefaultMessage)
                .collect(Collectors.joining(", "));
        return new ResponseEntity<>(
                ValidationExceptionDetails.builder()
                        .timestamp(LocalDateTime.now())
                        .status(HttpStatus.BAD_REQUEST.value())
                        .title("Bad Request Exception, Invalid Fields")
                        .details("Check The Field(s) Error")
                        .developerMessage(exception.getClass().getName())
                        .filds(fields)
                        .fildMessage(fieldsMessage)
                        .build(), HttpStatus.BAD_REQUEST);

    }



}
