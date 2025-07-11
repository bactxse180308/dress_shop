package hsf302.assignment.controller;

import hsf302.assignment.pojo.User;
import hsf302.assignment.service.UserService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/auth")
public class LoginController {

    @Autowired
    private UserService userService;

    @GetMapping("/login")
    public String showLoginForm() {
        return "login";
    }

    @PostMapping("/login")
    public String login(
            @RequestParam String email,
            @RequestParam String password,
            HttpSession session,
            Model model
    ) {
        User user = userService.findByEmailAndPassword(email, password);
        if (user == null) {
            model.addAttribute("message", "Sai email hoặc mật khẩu!");
            return "login";
        }

        session.setAttribute("userId", user.getId());
        session.setAttribute("userRole", user.getRole().name());

        // Chuyển hướng tùy theo role
        if (user.getRole().name().equals("ADMIN")) {
            return "redirect:/admin/dashboard";
        } else {
            return "redirect:/home";
        }
    }

    @PostMapping("/logout")
    public String logout(HttpSession session) {
        session.invalidate();
        return "redirect:/auth/login";
    }
}
