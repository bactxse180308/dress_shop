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
    public String showRegisterForm(Model model) {
        model.addAttribute("user", new User());
        return "register"; // return đúng tên file trong /templates
    }

    @PostMapping("/register")
    public String registerUser(
            @RequestParam String name,
            @RequestParam String email,
            @RequestParam String password,
            @RequestParam(required = false) String role,
            @RequestParam(required = false) String phone,
            @RequestParam(required = false) String address,
            Model model
    ) {
        if (userService.existsByEmail(email)) {
            model.addAttribute("message", "❌ Email đã được đăng ký!");
            model.addAttribute("name", name);
            model.addAttribute("email", email);
            return "register";
        }

        User newUser = new User();
        newUser.setName(name);
        newUser.setEmail(email);
        newUser.setPassword(password);
        newUser.setPhone(phone);
        newUser.setAddress(address);
        newUser.setRole((role != null && role.equalsIgnoreCase("ADMIN"))
                ? UserRoleEnum.ADMIN
                : UserRoleEnum.USER);

        try {
            userService.createUser(newUser);
            return "redirect:/auth/login";
        } catch (Exception e) {
            model.addAttribute("message", "⚠️ Lỗi khi đăng ký: " + e.getMessage());
            return "register";
        }
    }

}
