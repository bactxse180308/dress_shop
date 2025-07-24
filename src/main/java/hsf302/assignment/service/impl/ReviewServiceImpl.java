package hsf302.assignment.service.impl;

import groovy.transform.AutoClone;
import hsf302.assignment.Enum.ReviewStatusEnum;
import hsf302.assignment.pojo.Review;
import hsf302.assignment.repository.ReviewRepository;
import hsf302.assignment.service.ReviewService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ReviewServiceImpl implements ReviewService {

    @Autowired
    private ReviewRepository reviewRepository;

    @Override
    public List<Review> getAllReviews() {
        return reviewRepository.findAll();
    }

    @Override
    public List<Review> getAllReviewsByProductId(Integer productId) {
        return reviewRepository.findReviewByProduct_Id(productId);
    }


    @Override
    public void updateReviewStatus(Integer reviewId, String status) {
        Review review = reviewRepository.findById(reviewId).orElse(null);
        if (review != null) {
            review.setReviewStatus(ReviewStatusEnum.valueOf(status));
            reviewRepository.save(review);
        }
    }

    @Override
    public void deleteReview(Integer reviewId) {
        Review review = reviewRepository.findById(reviewId).orElse(null);
        if (review != null) {
            reviewRepository.delete(review);
        }
    }

    @Override
    public List<Review> getAllReviewsByStatusAndRating(String status, Integer minRating) {
        if (status == null || status.isEmpty()) {
            return reviewRepository.findReviewByRatingGreaterThanEqual(minRating);
        } else {
            return reviewRepository.findReviewByReviewStatusAndRatingGreaterThanEqual(ReviewStatusEnum.valueOf(status), minRating);
        }

    }


}
