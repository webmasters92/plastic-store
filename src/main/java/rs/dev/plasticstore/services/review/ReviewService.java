package rs.dev.plasticstore.services.review;

import rs.dev.plasticstore.model.Review;

import java.util.List;
import java.util.Optional;

public interface ReviewService {

    List<Review> findAll();

    Optional<Integer> findAverageRatingByProductId(int id);

    List<Review> findReviewByProductId(int id);

    List<Review> findReviewByUserId(int id);

    void saveReview(Review review);

}
