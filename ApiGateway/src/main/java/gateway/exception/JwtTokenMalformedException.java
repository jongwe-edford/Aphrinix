package gateway.exception;

import javax.naming.AuthenticationException;
import java.io.Serial;

public class JwtTokenMalformedException extends AuthenticationException {

    @Serial
    private static final long serialVersionUID = 1L;

    public JwtTokenMalformedException(String msg) {
        super(msg);
    }

}