package rs.dev.plasticstore.services.review;

import rs.dev.plasticstore.model.Review;

import java.util.List;
import java.util.Optional;

public interface ReviewService {

    void saveReview(Review review);

    List<Review> findReviewByUserId(int id);

    List<Review> findReviewByProductId(int id);

    List<Review> findAll();

    Optional<Integer> findAverageRatingByProductId(int id);

}
