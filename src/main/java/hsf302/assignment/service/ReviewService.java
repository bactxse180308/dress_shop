package hsf302.assignment.service;

import hsf302.assignment.Enum.ReviewStatusEnum;
import hsf302.assignment.pojo.Review;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface ReviewService {
    List<Review> getAllReviews();
    List<Review> getAllReviewsByProductId(Integer productId);

    void updateReviewStatus(Integer reviewId, String status);

    void deleteReview(Integer reviewId);
    List<Review> getAllReviewsByStatusAndRating(String status, Integer minRating);
}
