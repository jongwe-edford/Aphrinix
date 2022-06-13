package products.service.impl;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import products.exception.ReviewNotFoundException;
import products.model.Review;
import products.model.User;
import products.repository.ReviewRepository;
import products.service.ReviewService;

import java.util.ArrayList;
import java.util.List;

@Service
@AllArgsConstructor
public class ReviewServiceImpl implements ReviewService {
    private final ReviewRepository reviewRepository;
    private final RestTemplate restTemplate;

    @Override
    public Review save(String productId, String authorizationHeader, String message) {
        String token = authorizationHeader.substring(7);
        String url = "http://SHOP-AUTH-SERVICE/shop/auth/u?token={token}";
        User user = restTemplate.getForObject(url, User.class, token);
        Review review = Review
                .builder()
                .message(message)
                .user(user)
                .productId(productId)
                .build();
        return reviewRepository.save(review);
    }

    @Override
    public Review reply(String productId, String reviewId, String authorizationHeader, String message) throws ReviewNotFoundException {
        Review review = reviewRepository.findById(reviewId).orElseThrow(
                () -> new ReviewNotFoundException("No review found"));
        List<Review> reviews = review.getReplies();
        Review review1 = save(reviewId, authorizationHeader, message);
        if (reviews == null) {
            reviews = new ArrayList<>();
        }
        reviews.add(review1);
        review.setReplies(reviews);

        return reviewRepository.save(review);
    }

    @Override
    public void deleteReview(String id) {
        reviewRepository.deleteById(id);
    }

    @Override
    public Review findReviewById(String id) {
        return reviewRepository.findById(id).orElseThrow();
    }

    @Override
    public Review save(Review review) {
        return reviewRepository.save(review);
    }

    @Override
    public List<Review> reviews() {
        return null;
    }
}
