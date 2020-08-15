package rs.dev.plasticstore.services.review;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import rs.dev.plasticstore.model.Review;
import rs.dev.plasticstore.repository.review.ReviewRepository;

import java.util.List;
import java.util.Optional;

@Service
public class ReviewServiceImpl implements ReviewService {

    @Override
    @Transactional
    @Cacheable(value = "all_reviews")
    public List<Review> findAll() {
        return reviewRepository.findAll();
    }

    @Override
    @Transactional
    @Cacheable(value = "avg_rating", key = "#id")
    public Optional<Integer> findAverageRatingByProductId(int id) {
        return reviewRepository.findAverageRatingByProductId(id);
    }

    @Override
    @Transactional
    public List<Review> findReviewByProductId(int id) {
        return reviewRepository.findAllByProductId(id);
    }

    @Override
    @Transactional
    public List<Review> findReviewByUserId(int id) {
        return reviewRepository.findAllByUserId(id);
    }

    @Override
    @Transactional
    @CachePut(value = "all_reviews", key = "#id")
    public void saveReview(Review review) {
        reviewRepository.save(review);
    }

    @Autowired
    ReviewRepository reviewRepository;
}
