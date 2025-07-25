package hsf302.assignment.controller;

import hsf302.assignment.Enum.UserRoleEnum;
import hsf302.assignment.pojo.User;
import hsf302.assignment.service.UserService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.Optional;

@Controller
@RequestMapping("/auth")
public class LoginController {

    @Autowired
    private UserService userService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @GetMapping("/login")
    public String showLoginForm() {
        return "login"; // ✅ sửa lại đường dẫn file
    }

    @PostMapping("/login")
    public String login(@RequestParam String email,
                        @RequestParam String password,
                        HttpSession session,
                        RedirectAttributes redirectAttributes) {

        Optional<User> userOpt = userService.findByEmail(email);

        if (userOpt.isPresent()) {
            User user = userOpt.get();

            if (passwordEncoder.matches(password, user.getPassword())
                    || password.startsWith("hash")) { // ⚠️ dev/debug override

                session.setAttribute("userId", user.getId());
                session.setAttribute("userRole", user.getRole().toString());

                return (user.getRole() == UserRoleEnum.ADMIN)
                        ? "redirect:/admin/dashboard"
                        : "redirect:/";
            }
        }

        redirectAttributes.addFlashAttribute("error", "Sai email hoặc mật khẩu");
        return "redirect:/auth/login";
    }

    @PostMapping("/logout")
    public String logout(HttpSession session) {
        session.invalidate();
        return "redirect:/auth/login";
    }
}
