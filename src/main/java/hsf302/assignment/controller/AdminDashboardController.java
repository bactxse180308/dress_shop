package hsf302.assignment.controller;

import hsf302.assignment.Enum.UserRoleEnum;
import hsf302.assignment.pojo.User;
import hsf302.assignment.service.UserService;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Controller
@RequestMapping("/admin")
@RequiredArgsConstructor
public class AdminDashboardController {

    private final UserService userService;

    @GetMapping("/dashboard")
    public String showDashboard(HttpSession session, Model model) {
        Integer userId = (Integer) session.getAttribute("userId");
        String userRole = (String) session.getAttribute("userRole");

        if (userId == null || userRole == null) {
            return "redirect:/login";
        }

        User user = userService.getUserById(userId);
        model.addAttribute("user", user);

        if ("ADMIN".equals(userRole)) {
            List<User> users = userService.getAllUsers();
            model.addAttribute("users", users);
            model.addAttribute("isAdmin", true);
        } else {
            model.addAttribute("isAdmin", false);
        }

        return "admin/dashboard";
    }

    @GetMapping("/user/edit/{id}")
    public String showEditForm(@PathVariable Integer id, Model model, RedirectAttributes redirectAttributes) {
        try {
            User user = userService.getUserById(id);
            model.addAttribute("user", user);
            model.addAttribute("roles", UserRoleEnum.values());
            return "admin/edit-user";
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("errorMessage", "Không tìm thấy người dùng.");
            return "redirect:/admin/dashboard";
        }
    }

    @PostMapping("/user/update/{id}")
    public String updateUser(@PathVariable Integer id,
                             @ModelAttribute User updatedUser,
                             @RequestParam(required = false) String password,
                             RedirectAttributes redirectAttributes) {
        try {
            if (password != null && !password.isBlank()) {
                updatedUser.setPassword(password); // Có mật khẩu mới thì set
            } else {
                updatedUser.setPassword(null); // Không có thì để null, service sẽ bỏ qua
            }

            userService.updateUser(id, updatedUser);
            redirectAttributes.addFlashAttribute("successMessage", "✅ Cập nhật người dùng thành công.");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("errorMessage", "❌ Cập nhật thất bại: " + e.getMessage());
        }
        return "redirect:/admin/dashboard";
    }

    @PostMapping("/user/delete/{id}")
    public String deleteUser(@PathVariable Integer id,
                             HttpSession session,
                             RedirectAttributes redirectAttributes) {
        Integer currentUserId = (Integer) session.getAttribute("userId");

        if (currentUserId != null && currentUserId.equals(id)) {
            redirectAttributes.addFlashAttribute("errorMessage", "⚠️ Bạn không thể tự xóa chính mình.");
            return "redirect:/admin/dashboard";
        }

        try {
            userService.deleteUser(id);
            redirectAttributes.addFlashAttribute("successMessage", "🗑️ Xóa người dùng thành công.");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("errorMessage", "❌ Xóa thất bại: " + e.getMessage());
        }

        return "redirect:/admin/dashboard";
    }
}
