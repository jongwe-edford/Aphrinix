package products.exception;

public class ReviewNotFoundException extends Exception {
    public ReviewNotFoundException(String no_review_found) {
        super(no_review_found);
    }
}
