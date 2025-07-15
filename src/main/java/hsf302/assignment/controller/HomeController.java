package hsf302.assignment.controller;


import hsf302.assignment.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
@RequiredArgsConstructor
public class HomeController {

    private final ProductService productService;

    @GetMapping("/")
    public String home(Model model) {
        // Lấy danh sách sản phẩm mới nhất
        model.addAttribute("newProducts", productService.getNewestProducts());
        return "home";
    }

}
