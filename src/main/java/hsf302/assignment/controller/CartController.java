package hsf302.assignment.controller;

import hsf302.assignment.Enum.Size;
import hsf302.assignment.pojo.Decoration;
import hsf302.assignment.pojo.Measurement;
import hsf302.assignment.pojo.Product;
import java.util.List;
import hsf302.assignment.service.CartService;
import hsf302.assignment.service.DecorationService;
import hsf302.assignment.service.ProductService;
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
    private DecorationService decorationService;

    @Autowired
    private ProductService productService;

    @GetMapping
    public String showCart(Model model) {
        model.addAttribute("cartItems", cartService.getCartItems());
        model.addAttribute("total", cartService.calculateTotal());
        model.addAttribute("availableDecorations", decorationService.findAll());
        return "cart";
    }


    @PostMapping("/add")
    public String addToCart(
            @RequestParam Integer productId,
            @RequestParam int quantity,
            @RequestParam(required = false) Size size,                             // enum Size
            @RequestParam(required = false, defaultValue = "false") boolean customSizing, // checkbox tùy chọn
            @RequestParam(required = false) Double bustSize,                       // các tham số measurement
            @RequestParam(required = false) Double waistSize,
            @RequestParam(required = false) Double hipsSize,
            @RequestParam(required = false) Double armLength,
            @RequestParam(required = false) Double buttSize,
            @RequestParam(required = false) Double hipDrop,
            @RequestParam(required = false) Double neckSize,
            @RequestParam(required = false) Double shoulderWidth,
            @RequestParam(required = false) Double skirtLength,
            @RequestParam(required = false) Double waistDrop,
            @RequestParam(required = false) List<Integer> decorationIds
    ) {
        // 1. Lấy product
        Product product = productService.findById(productId)
                .orElseThrow(() -> new RuntimeException("Product not found"));

        // 2. Chỉ tạo Measurement khi customSizing = true
        Measurement measurement = null;
        if (customSizing) {
            measurement = new Measurement();
            measurement.setBustSize(bustSize);
            measurement.setWaistSize(waistSize);
            measurement.setHipsSize(hipsSize);
            measurement.setArmLength(armLength);
            measurement.setButtSize(buttSize);
            measurement.setHipDrop(hipDrop);
            measurement.setNeckSize(neckSize);
            measurement.setShoulderWidth(shoulderWidth);
            measurement.setSkirtLength(skirtLength);
            measurement.setWaistDrop(waistDrop);
            measurement.setNote("Custom measurement for " + product.getName());
        }

        // 3. Lấy danh sách decorations
        List<Decoration> decorations = (decorationIds != null && !decorationIds.isEmpty())
                ? decorationService.findAllById(decorationIds)
                : List.of();

        // 4. Gọi service với size enum, measurement (có thể null), decorations
        cartService.addToCart(product, quantity, size, measurement, decorations);

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

    @PostMapping("/update-measurement")
    public String updateMeasurement(
            @RequestParam int itemIndex,
            @RequestParam(required = false) Double bustSize,
            @RequestParam(required = false) Double waistSize,
            @RequestParam(required = false) Double hipsSize,
            @RequestParam(required = false) Double armLength,
            @RequestParam(required = false) Double buttSize,
            @RequestParam(required = false) Double hipDrop,
            @RequestParam(required = false) Double neckSize,
            @RequestParam(required = false) Double shoulderWidth,
            @RequestParam(required = false) Double skirtLength,
            @RequestParam(required = false) Double waistDrop,
            @RequestParam(required = false) String note
    ) {
        try {
            // Tạo measurement mới
            Measurement measurement = new Measurement();
            measurement.setBustSize(bustSize);
            measurement.setWaistSize(waistSize);
            measurement.setHipsSize(hipsSize);
            measurement.setArmLength(armLength);
            measurement.setButtSize(buttSize);
            measurement.setHipDrop(hipDrop);
            measurement.setNeckSize(neckSize);
            measurement.setShoulderWidth(shoulderWidth);
            measurement.setSkirtLength(skirtLength);
            measurement.setWaistDrop(waistDrop);
            measurement.setNote(note != null && !note.trim().isEmpty() ? note : "Số đo tùy chỉnh");

            // Cập nhật measurement cho item trong cart
            cartService.updateItemMeasurement(itemIndex, measurement);
        } catch (Exception e) {
            // Log error if needed
            e.printStackTrace();
        }
        
        return "redirect:/cart";
    }
}
