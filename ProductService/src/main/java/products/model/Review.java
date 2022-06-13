package products.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;
import java.util.Objects;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
@Document(collation = "reviews")
public class Review {
    private String id;
    private User user;
    private String productId;
    private List<Review> replies;
    private int likes;
    private String message;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Review review = (Review) o;
        return likes == review.likes && id.equals(review.id) && user.equals(review.user) && productId.equals(review.productId) && replies.equals(review.replies) && message.equals(review.message);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, user, productId, replies, likes, message);
    }
}
