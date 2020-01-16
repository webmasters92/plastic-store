package rs.dev.plasticstore.services.review;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import rs.dev.plasticstore.model.Review;
import rs.dev.plasticstore.repository.review.ReviewRepository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;
import java.util.Optional;

@Service
public class ReviewServiceImpl implements ReviewService {

    @Autowired
    ReviewRepository reviewRepository;

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    @Transactional
    public void saveReview(Review review) {
        reviewRepository.save(review);
    }

    @Override
    @Transactional
    public List<Review> findReviewByUserId(int id) {
        return reviewRepository.findAllByUserId(id);
    }

    @Override
    @Transactional
    public List<Review> findReviewByProductId(int id) {
        return reviewRepository.findAllByProductId(id);
    }

    @Override
    @Transactional
    public List<Review> findAll() {
        return reviewRepository.findAll();
    }

    @Override
    @Transactional
    public Optional<Integer> findAverageRatingByProductId(int id) {
        return reviewRepository.findAverageRatingByProductId(id);
    }
}
