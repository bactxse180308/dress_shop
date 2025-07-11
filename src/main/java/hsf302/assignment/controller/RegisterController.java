package hsf302.assignment.controller;

import hsf302.assignment.Enum.UserRoleEnum;
import hsf302.assignment.pojo.User;
import hsf302.assignment.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/auth")
public class RegisterController {

    @Autowired
    private UserService userService;

    @GetMapping("/register")
    public String showRegisterForm() {
        return "register";
    }

    @PostMapping("/register")
    public String registerUser(
            @RequestParam String name,
            @RequestParam String email,
            @RequestParam String password,
            @RequestParam(required = false) String role,
            Model model
    ) {
        if (userService.existsByEmail(email)) {
            model.addAttribute("message", "Email đã được đăng ký!");
            return "register";
        }

        User user = new User();
        user.setName(name);
        user.setEmail(email);
        user.setPassword(password);
        user.setRole(
                (role != null && role.equalsIgnoreCase("ADMIN"))
                        ? UserRoleEnum.ADMIN
                        : UserRoleEnum.USER
        );

        userService.createUser(user);
        model.addAttribute("message", "Đăng ký thành công! Vui lòng đăng nhập.");
        return "redirect:/auth/login";
    }
}
