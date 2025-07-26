package hsf302.assignment.service.impl;

import groovy.transform.AutoClone;
import hsf302.assignment.Enum.ReviewStatusEnum;
import hsf302.assignment.pojo.*;
import hsf302.assignment.repository.*;
import hsf302.assignment.service.ReviewService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class ReviewServiceImpl implements ReviewService {

    @Autowired
    private ReviewRepository reviewRepository;

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private OrderItemRepository orderItemRepository;

    @Autowired
    private OrderRepository orderRepository;

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

    @Override
    public void addReview(Integer productId, String content, Integer rating, Integer userId) {
        Product product = productRepository.findById(productId).orElse(null);
        User user = userRepository.findById(userId).orElse(null);
        Review review = new Review(product, user, rating, content, ReviewStatusEnum.PENDING);
        reviewRepository.save(review);
    }

    @Override
    public void addReviewWithOrder(Integer productId, String content, Integer rating, Integer userId, Integer orderId) {
        Product product = productRepository.findById(productId).orElse(null);
        User user = userRepository.findById(userId).orElse(null);
        Order order = orderRepository.findById(orderId).orElse(null);
        
        Review review = new Review();
        review.setProduct(product);
        review.setUser(user);
        review.setOrder(order);
        review.setRating(rating);
        review.setComment(content);
        review.setReviewStatus(ReviewStatusEnum.PENDING);
        review.setCreatedAt(java.time.LocalDateTime.now());
        
        System.out.println("Saving review with order ID: " + orderId + " for product: " + productId);
        reviewRepository.save(review);
    }

    @Override
    public Map<Integer, Review> reviewedProducts(List<OrderItem> items, Integer userId) {
        System.out.println("=== DEBUG ReviewService.reviewedProducts ===");
        System.out.println("User ID: " + userId);
        System.out.println("Number of order items: " + items.size());
        
        Map<Integer, Review> map = new HashMap<>();
        for (OrderItem item : items) {
            Integer productId = item.getProduct().getId();
            System.out.println("Checking reviews for Product ID: " + productId + " by User ID: " + userId);
            
            // Lấy danh sách reviews thay vì single review để tránh NonUniqueResultException
            List<Review> reviews = reviewRepository.findReviewByUser_IdAndProduct_Id(userId, productId);
            System.out.println("Found " + reviews.size() + " reviews");
            
            if (!reviews.isEmpty()) {
                // Lấy review mới nhất (có thể sắp xếp theo createdAt)
                Review latestReview = reviews.stream()
                    .max((r1, r2) -> r1.getCreatedAt().compareTo(r2.getCreatedAt()))
                    .orElse(null);
                    
                if (latestReview != null) {
                    System.out.println("  - Latest Review ID: " + latestReview.getId());
                    System.out.println("  - Product ID: " + latestReview.getProduct().getId());
                    System.out.println("  - User ID: " + latestReview.getUser().getId());
                    System.out.println("  - Rating: " + latestReview.getRating());
                    System.out.println("  - Comment: " + latestReview.getComment());
                    
                    map.put(productId, latestReview);
                }
            }
        }
        
        System.out.println("Final reviewedProducts map size: " + map.size());
        return map;
    }

    @Override
    public List<Review> getReviewsByOrderId(Integer orderId) {
        System.out.println("=== getReviewsByOrderId ===");
        System.out.println("Getting reviews for order ID: " + orderId);
        
        List<Review> reviews = reviewRepository.findReviewByOrder_Id(orderId);
        System.out.println("Found " + reviews.size() + " reviews for order " + orderId);
        
        return reviews;
    }

    @Override
    public Map<Integer, Review> getReviewsMapByOrder(Integer orderId) {
        System.out.println("=== getReviewsMapByOrder ===");
        System.out.println("Getting reviews map for order ID: " + orderId);
        
        List<Review> reviews = reviewRepository.findReviewByOrder_Id(orderId);
        Map<Integer, Review> reviewsMap = new HashMap<>();
        
        for (Review review : reviews) {
            if (review.getProduct() != null) {
                reviewsMap.put(review.getProduct().getId(), review);
                System.out.println("Added review for product " + review.getProduct().getId() 
                    + " in order " + orderId);
            }
        }
        
        System.out.println("Final reviews map size for order " + orderId + ": " + reviewsMap.size());
        return reviewsMap;
    }

}
