package hsf302.assignment.controller;

import hsf302.assignment.Enum.ReviewStatusEnum;
import hsf302.assignment.pojo.Review;
import hsf302.assignment.service.ReviewService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
public class ReviewController {

    @Autowired
    private ReviewService reviewService;

    @PostMapping("/reviews/add")
    public String addReview(@RequestParam Integer productId,
                            @RequestParam String content,
                            @RequestParam Integer rating,
                            @RequestParam Integer userId,
                            @RequestParam Integer orderId,
                            HttpSession session) {
        if (session.getAttribute("user") == null) {
            return "access-denied"; // Redirect to login page if session is not valid
        }
        System.out.println("Adding review: " + content + " for product ID: " + productId +
                " with rating: " + rating + " by user ID: " + userId + " for order ID: " + orderId);
        reviewService.addReviewWithOrder(productId, content, rating, userId, orderId);
        return "redirect:/order/detail/" + orderId;
    }

    @DeleteMapping("/admin/reviews/delete/{reviewId}")
    public ResponseEntity<?> deleteReview(@PathVariable Integer reviewId, HttpSession session) {
        if (session.getAttribute("user") == null || !session.getAttribute("userRole").equals("ADMIN")) {
            return ResponseEntity.status(401).build(); // Redirect to access denied page if session is not valid
        }
        reviewService.deleteReview(reviewId);
        return ResponseEntity.ok().build(); // Redirect về trang sản phẩm sau khi xóa đánh giá
    }

    @PutMapping("/admin/reviews/update")
    public ResponseEntity<?> updateReview(@RequestParam Integer reviewId,
                               @RequestParam String status,
                                          HttpSession session) {
        if (session.getAttribute("user") == null || !session.getAttribute("userRole").equals("ADMIN")) {
            return ResponseEntity.status(401).build(); // Redirect to access denied page if session is not valid
        }
        reviewService.updateReviewStatus(reviewId, status);
        return ResponseEntity.ok().build(); // Redirect về trang sản phẩm sau khi cập nhật đánh giá
    }

    @GetMapping("admin/reviews/list")
    public String listReviews(@RequestParam String status, @RequestParam Integer minRating, Model model, HttpSession session) {
        if (session.getAttribute("user") == null || !session.getAttribute("userRole").equals("ADMIN")) {
            return "access-denied"; // Redirect to access denied page if session is not valid
        }
        List<Review> reviews = reviewService.getAllReviewsByStatusAndRating(status, minRating);
        model.addAttribute("reviews", reviews);
        return "admin/review-manage"; // Redirect về trang sản phẩm sau khi liệt kê đánh giá
    }

    @GetMapping("/admin/reviews")
    public String reviewDashboard(Model model, HttpSession session) {
        if (session.getAttribute("user") == null || !session.getAttribute("userRole").equals("ADMIN")) {
            return "access-denied"; // Redirect to access denied page if session is not valid
        }
        List<Review> reviews = reviewService.getAllReviews();
        model.addAttribute("reviews", reviews);
        return "admin/review-manage";
    }

}
