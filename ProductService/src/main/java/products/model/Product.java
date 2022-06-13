package products.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

@Document(collection = "products")
@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class Product {
    @Id
    private String id;
    private String shopId;
    private String name;
    private Category category;
    private List<Tag> tags;
    private List<String> images;
    private List<String> colors;
    private double price;
    private long timeView;
    private long timePurchased;
    private long total;
}
