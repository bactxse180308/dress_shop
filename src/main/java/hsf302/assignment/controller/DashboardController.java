package hsf302.assignment.controller;

import hsf302.assignment.pojo.User;
import hsf302.assignment.service.UserService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/admin")
public class DashboardController {

    @Autowired
    private UserService userService;

    @GetMapping("/dashboard")
    public String showDashboard(HttpSession session, Model model) {
        Integer userId = (Integer) session.getAttribute("userId");
        String userRole = (String) session.getAttribute("userRole");

        if (userId == null || userRole == null) {
            return "redirect:/auth/login";
        }

        User user = userService.getUserById(userId);
        model.addAttribute("user", user);

        if ("ADMIN".equals(userRole)) {
            // Truyền danh sách người dùng vào model
            List<User> users = userService.getAllUsers();
            model.addAttribute("users", users);
            model.addAttribute("isAdmin", true);
        } else {
            model.addAttribute("isAdmin", false);
        }

        return "admin/dashboard";  // Đảm bảo trả về đúng template
    }
}
