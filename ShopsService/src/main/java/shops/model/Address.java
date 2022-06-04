package shops.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
@Document(collection = "addresses")
public class Address {
    @Id
    private String id;
    private String streetAddress;
    private String city;
    private String state;
    private String country;
    private String zipCode;
}
