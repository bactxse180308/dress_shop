package hsf302.assignment.controller;

import hsf302.assignment.pojo.User;
import hsf302.assignment.service.UserService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

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

        return "admin/dashboard";
    }

    @GetMapping("/edit/{id}")
    public String editUser(@PathVariable("id") Integer id, Model model) {
        User user = userService.getUserById(id);
        model.addAttribute("user", user);
        return "admin/edit_user";  // Trang để sửa thông tin người dùng
    }

    @PostMapping("/update/{id}")
    public String updateUser(@PathVariable("id") Integer id, @ModelAttribute("user") User user) {
        userService.updateUser(id, user);
        return "redirect:/admin/dashboard";
    }

    @PostMapping("/delete/{id}")
    public String deleteUser(@PathVariable("id") Integer id) {
        userService.deleteUser(id);
        return "redirect:/admin/dashboard";
    }
    @PostMapping("/logout")
    public String logout(HttpSession session) {
        // Xóa session khi logout
        session.invalidate();
        return "redirect:/auth/login";  // Chuyển hướng về trang login
    }
}
