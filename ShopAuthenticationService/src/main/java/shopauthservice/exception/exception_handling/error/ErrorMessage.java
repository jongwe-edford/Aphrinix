package shopauthservice.exception.exception_handling.error;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;

import java.sql.Timestamp;

@AllArgsConstructor
@Data
@NoArgsConstructor
@Builder
public class ErrorMessage {
    private int statusCode;
    private String message;
    private HttpStatus status;
    private Timestamp timestamp;
}
