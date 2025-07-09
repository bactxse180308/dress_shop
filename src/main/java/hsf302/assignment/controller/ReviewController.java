package hsf302.assignment.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/reviews")
public class ReviewController {
    // Chưa có chức năng review, chỉ tạo controller để tránh lỗi
    // Trong tương lai sẽ thêm các phương thức để quản lý đánh giá sản phẩm
    // Ví dụ: xem danh sách đánh giá, thêm đánh giá mới, xóa đánh giá, v.v.
    @PostMapping("/add")
    public String addReview() {
        // Chức năng thêm đánh giá sẽ được triển khai sau
        return "redirect:/products"; // Redirect về trang sản phẩm sau khi thêm đánh giá
    }

    @PostMapping("/delete")
    public String deleteReview() {
        // Chức năng xóa đánh giá sẽ được triển khai sau
        return "redirect:/products"; // Redirect về trang sản phẩm sau khi xóa đánh giá
    }

    @PostMapping("/update")
    public String updateReview() {
        // Chức năng cập nhật đánh giá sẽ được triển khai sau
        return "redirect:/products"; // Redirect về trang sản phẩm sau khi cập nhật đánh giá
    }

    @PostMapping("/list")
    public String listReviews() {
        // Chức năng liệt kê đánh giá sẽ được triển khai sau
        return "redirect:/products"; // Redirect về trang sản phẩm sau khi liệt kê đánh giá
    }
}
