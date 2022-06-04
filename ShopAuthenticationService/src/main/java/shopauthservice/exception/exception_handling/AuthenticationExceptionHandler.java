package shopauthservice.exception.exception_handling;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import shopauthservice.exception.AccountWithEmailAlreadyExist;
import shopauthservice.exception.exception_handling.error.ErrorMessage;

import java.sql.Timestamp;
import java.time.Instant;

@RestControllerAdvice
public class AuthenticationExceptionHandler {
    @ExceptionHandler(AccountWithEmailAlreadyExist.class)
    public ResponseEntity<ErrorMessage> handleDuplicateEmail(AccountWithEmailAlreadyExist emailAlreadyExist, WebRequest request) {
        ErrorMessage errorMessage = ErrorMessage
                .builder()
                .message(emailAlreadyExist.getMessage())
                .status(HttpStatus.UNAUTHORIZED)
                .timestamp(Timestamp.from(Instant.now()))
                .statusCode(HttpStatus.UNAUTHORIZED.value())
                .build();

        return new ResponseEntity<>(errorMessage, errorMessage.getStatus());
    }
}
