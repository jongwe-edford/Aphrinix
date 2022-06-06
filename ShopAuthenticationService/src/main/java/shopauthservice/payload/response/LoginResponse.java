package shopauthservice.payload.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.util.List;

@AllArgsConstructor
@Data
@Builder
public class LoginResponse {
    private String accessToken;
    private String refreshToken;
    private List<String> roles;
}
