package shopauthservice.payload.response;

import lombok.AllArgsConstructor;
import lombok.Data;

@AllArgsConstructor
@Data
public class LoginResponse {
    private String accessToken;
    private String refreshToken;
}
