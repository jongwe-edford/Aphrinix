package shopauthservice.payload.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@AllArgsConstructor
@Data
@Builder
public class RegistrationRequest {
    private String email;
    private String password;
    private String lastname;
    private String firstname;

}
