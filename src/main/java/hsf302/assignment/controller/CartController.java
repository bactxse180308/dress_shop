package hsf302.assignment.controller;

import hsf302.assignment.pojo.Measurement;
import hsf302.assignment.pojo.Product;
import hsf302.assignment.repository.ProductRepository;
import hsf302.assignment.service.CartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

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
            @RequestParam(required = false) String size
    ) {
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new RuntimeException("Product not found"));

        Measurement measurement = null;

        // Nếu có size, tạo Measurement và lưu size vào note
        if (size != null && !size.isEmpty()) {
            measurement = new Measurement();
            measurement.setNote(size);
        }

        cartService.addToCart(product, quantity, measurement);

        return "redirect:/cart";
    }

    @PostMapping("/remove")
    public String removeItem(@RequestParam int index) {
        cartService.getCartItems().remove(index);
        return "redirect:/cart";
    }

    @PostMapping("/clear")
    public String clearCart() {
        cartService.clearCart();
        return "redirect:/cart";
    }
}
