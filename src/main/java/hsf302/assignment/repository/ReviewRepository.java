package hsf302.assignment.repository;
import hsf302.assignment.Enum.ReviewStatusEnum;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import hsf302.assignment.pojo.Review;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.lang.NonNullApi;

import java.util.List;

public interface ReviewRepository extends JpaRepository<Review, Integer> {
    List<Review> findReviewByProduct_Id(Integer productId);
    List<Review> findReviewByReviewStatusAndRatingGreaterThanEqual(ReviewStatusEnum status, Integer rating);
    List<Review> findReviewByRatingGreaterThanEqual(Integer rating);
}
