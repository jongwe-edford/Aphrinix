package products.service;

import products.exception.ReviewNotFoundException;
import products.model.Review;

import java.util.List;

public interface ReviewService {

    Review save(String productId, String token, String message);

    Review reply(String productId, String reviewId, String token, String message) throws ReviewNotFoundException;

    void deleteReview(String id);

    Review findReviewById(String id);
    Review save(Review review);

    List<Review> reviews();
}
