package hsf302.assignment.controller;

import hsf302.assignment.Enum.ReviewStatusEnum;
import hsf302.assignment.pojo.Review;
import hsf302.assignment.service.ReviewService;
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
    public String addReview() {
        // Chức năng thêm đánh giá sẽ được triển khai sau
        return "redirect:/products"; // Redirect về trang sản phẩm sau khi thêm đánh giá
    }

    @DeleteMapping("/admin/reviews/delete/{reviewId}")
    public ResponseEntity<?> deleteReview(@PathVariable Integer reviewId) {
        reviewService.deleteReview(reviewId);
        return ResponseEntity.ok().build(); // Redirect về trang sản phẩm sau khi xóa đánh giá
    }

    @PutMapping("/admin/reviews/update")
    public ResponseEntity<?> updateReview(@RequestParam Integer reviewId,
                               @RequestParam String status) {
        reviewService.updateReviewStatus(reviewId, status);
        return ResponseEntity.ok().build(); // Redirect về trang sản phẩm sau khi cập nhật đánh giá
    }

    @GetMapping("admin/reviews/list")
    public String listReviews(@RequestParam String status, @RequestParam Integer minRating, Model model) {
        List<Review> reviews = reviewService.getAllReviewsByStatusAndRating(status, minRating);
        model.addAttribute("reviews", reviews);
        return "admin/review-manage"; // Redirect về trang sản phẩm sau khi liệt kê đánh giá
    }

    @GetMapping("/admin/reviews")
    public String reviewDashboard(Model model) {
        List<Review> reviews = reviewService.getAllReviews();
        model.addAttribute("reviews", reviews);
        return "admin/review-manage";
    }

}
