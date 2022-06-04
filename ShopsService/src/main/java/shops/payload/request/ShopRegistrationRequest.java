package shops.payload.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import shops.model.Address;

import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class ShopRegistrationRequest {
    private String email;
    private String name;
    private String phoneNumber;
    private String shopBanner;
    private String shopId;
    private LocalDateTime openingTime;
    private LocalDateTime closingTime;
    private String registrationIp;
    private LocalDateTime creationDate;
    private Boolean phoneNumberVerified;
    private Address address;
}
