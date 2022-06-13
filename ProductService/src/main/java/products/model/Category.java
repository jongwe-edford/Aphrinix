package products.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collation = "products")
@AllArgsConstructor
@NoArgsConstructor
@Data
public class Category {
    private String id;
    private String name;
    private String imageUrl;
    private int numberVisited;

}
