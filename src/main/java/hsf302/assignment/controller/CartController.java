package hsf302.assignment.controller;

import org.springframework.ui.Model;
import hsf302.assignment.pojo.Measurement;
import hsf302.assignment.pojo.Product;
import hsf302.assignment.repository.ProductRepository;
import hsf302.assignment.service.CartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/cart")
public class CartController {
    @Autowired
    private CartService cartService;

    @Autowired
    private ProductRepository productRepository;

    @GetMapping
    public String showCart(Model model) {
        model.addAttribute("cartItems", cartService.getCartItems());
        model.addAttribute("total", cartService.calculateTotal());
        return "cart";
    }

    @PostMapping("/add")
    public String addToCart(
            @RequestParam Integer productId,
            @RequestParam int quantity,
            @RequestParam(required = false) Long measurementId) {

        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new RuntimeException("Product not found"));

        Measurement measurement = null;
        if (measurementId != null) {
            measurement = new Measurement();
            measurement.setId(measurementId.intValue()); // Hoặc load Measurement từ DB nếu bạn lưu sẵn
        }

        cartService.addToCart(product, quantity, measurement);

        return "redirect:/cart";
    }

    @PostMapping("/clear")
    public String clearCart() {
        cartService.clearCart();
        return "redirect:/cart";
    }
}
