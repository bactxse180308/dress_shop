package hsf302.assignment.controller;

import hsf302.assignment.pojo.Product;
import hsf302.assignment.pojo.ProductImage;
import hsf302.assignment.pojo.ReadyMadeDress;
import hsf302.assignment.pojo.Review;
import hsf302.assignment.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.math.BigDecimal;
import java.util.List;

@Controller
public class ProductController {

    @Autowired
    private ProductService productService;
    @Autowired
    private FabricService fabricService;
    @Autowired
    private StyleService styleService;
    @Autowired
    private StorageService storageService;

    @Autowired
    private ReviewService reviewService;

    @Autowired
    private DecorationService decorationService;

    // ----- TRANG CHO NGƯỜI DÙNG -----

    @GetMapping("/products")
    public String showProductList(
            @RequestParam(required = false) String name,
            @RequestParam(required = false) Integer styleId,
            @RequestParam(required = false) Integer fabricId,
            @RequestParam(required = false) BigDecimal minPrice,
            @RequestParam(required = false) BigDecimal maxPrice,
            @RequestParam(required = false) String newest,
            Model model) {
        if (newest != null && newest.equals("true")) {
            model.addAttribute("products", productService.getNewestProducts());
            return "product-list";
        } else {
            model.addAttribute("products", productService.filterProducts(name, styleId, fabricId, minPrice, maxPrice));
        }
        model.addAttribute("styles", styleService.findAll());
        model.addAttribute("fabrics", fabricService.findAll());
        return "product-list";
    }

    @GetMapping("/products/{id}")
    public String showProductDetail(@PathVariable("id") Integer id, Model model) {
        Product product = productService.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Invalid product Id:" + id));
        List<Review> reviews = reviewService.getAllReviewsByProductId(id);
        model.addAttribute("reviews", reviews);
        model.addAttribute("product", product);
        model.addAttribute("decorations", decorationService.findAll());
        return "product-detail";
    }

    // ----- TRANG CHO ADMIN QUẢN LÝ -----

    @GetMapping("/admin/products")
    public String showAdminProductList(Model model) {
        model.addAttribute("products", productService.findAll());
        return "admin/product-manage";
    }

    @GetMapping({"/admin/products/add", "/admin/products/edit/{id}"})
    public String showProductForm(@PathVariable(name = "id", required = false) Integer id, Model model) {
        Product product;
        if (id != null) {
            product = productService.findById(id).orElse(new Product());
        } else {
            product = new Product();
            // Quan trọng: Khởi tạo ReadyMadeDress để form không bị lỗi Null
            product.setReadyMadeDress(new ReadyMadeDress());
        }
        model.addAttribute("product", product);
        model.addAttribute("fabrics", fabricService.findAll());
        model.addAttribute("styles", styleService.findAll());
        return "admin/product-form";
    }

    @PostMapping("/admin/products/save")
    public String saveProduct(@ModelAttribute("product") Product product,
                              @RequestParam("imageFiles") MultipartFile[] imageFiles, // Sửa tên và kiểu
                              RedirectAttributes redirectAttributes) {
        try {
            // Xử lý upload nhiều file
            for (MultipartFile file : imageFiles) {
                if (!file.isEmpty()) {
                    String fileName = storageService.store(file);
                    ProductImage productImage = new ProductImage();
                    productImage.setImageUrl(fileName);
                    productImage.setProduct(product); // Thiết lập quan hệ
                    product.getImages().add(productImage); // Thêm vào danh sách của product
                }
            }

            productService.save(product);
            redirectAttributes.addFlashAttribute("successMessage", "Lưu sản phẩm thành công!");
        } catch (RuntimeException e) {
            redirectAttributes.addFlashAttribute("errorMessage", e.getMessage());
            redirectAttributes.addFlashAttribute("product", product);
            if (product.getId() != null) {
                return "redirect:/admin/products/edit/" + product.getId();
            }
            return "redirect:/admin/products/add";
        }
        return "redirect:/admin/products";
    }


    @GetMapping("/admin/products/delete/{id}")
    public String deleteProduct(@PathVariable("id") Integer id) {
        productService.deleteById(id);
        return "redirect:/admin/products";
    }
}