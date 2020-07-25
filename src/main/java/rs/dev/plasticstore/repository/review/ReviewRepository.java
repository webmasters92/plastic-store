package rs.dev.plasticstore.repository.review;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import rs.dev.plasticstore.model.Review;

import java.util.List;
import java.util.Optional;

@Repository
public interface ReviewRepository extends JpaRepository<Review, Integer> {

    List<Review> findAllByProductId(int id);

    List<Review> findAllByUserId(int id);

    @Query(value = "SELECT avg(rating) FROM Review where productId=?1")
    Optional<Integer> findAverageRatingByProductId(int id);
}
