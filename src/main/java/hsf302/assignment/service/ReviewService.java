package hsf302.assignment.service;

import hsf302.assignment.Enum.ReviewStatusEnum;
import hsf302.assignment.pojo.OrderItem;
import hsf302.assignment.pojo.Review;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public interface ReviewService {
    List<Review> getAllReviews();
    List<Review> getAllReviewsByProductId(Integer productId);

    void updateReviewStatus(Integer reviewId, String status);

    void deleteReview(Integer reviewId);
    List<Review> getAllReviewsByStatusAndRating(String status, Integer minRating);

    void addReview(Integer productId, String content, Integer rating, Integer userId);
    
    // Thêm đánh giá với orderId
    void addReviewWithOrder(Integer productId, String content, Integer rating, Integer userId, Integer orderId);
    
    public Map<Integer, Review> reviewedProducts(List<OrderItem> items, Integer userId);
    
    // Lấy tất cả đánh giá theo đơn hàng
    List<Review> getReviewsByOrderId(Integer orderId);
    
    // Lấy map đánh giá theo từng sản phẩm trong đơn hàng cụ thể
    Map<Integer, Review> getReviewsMapByOrder(Integer orderId);
}
