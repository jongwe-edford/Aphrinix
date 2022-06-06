package shops.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
@Document(collection = "shops")
public class Shop {
    @Id
    private String id;
    private User shopAdmin;
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
    List<ShopManager> shopManagers;

}
