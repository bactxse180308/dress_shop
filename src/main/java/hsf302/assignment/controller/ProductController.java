package hsf302.assignment.controller;

import hsf302.assignment.pojo.Product;
import hsf302.assignment.pojo.ReadyMadeDress;
import hsf302.assignment.service.FabricService;
import hsf302.assignment.service.ProductService;
import hsf302.assignment.service.StorageService;
import hsf302.assignment.service.StyleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.method.annotation.MvcUriComponentsBuilder;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.io.IOException;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

@Controller
public class ProductController {

    @Autowired private ProductService productService;
    @Autowired private FabricService fabricService;
    @Autowired private StyleService styleService;
    @Autowired private StorageService storageService;

    // ----- TRANG CHO NGƯỜI DÙNG -----

    @GetMapping("/products")
    public String showProductList(Model model) {
        model.addAttribute("products", productService.findAll());
        return "product-list";
    }

    @GetMapping("/products/{id}")
    public String showProductDetail(@PathVariable("id") Integer id, Model model) {
        Product product = productService.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Invalid product Id:" + id));

        Path folderPath = Paths.get("uploads", String.valueOf(id));
        List<String> imageFiles = new ArrayList<>();

        if (Files.exists(folderPath) && Files.isDirectory(folderPath)) {
            try (DirectoryStream<Path> stream = Files.newDirectoryStream(folderPath)) {
                for (Path entry : stream) {
                    if (Files.isRegularFile(entry)) {
                        imageFiles.add(entry.getFileName().toString());
                    }
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        model.addAttribute("imageFiles", imageFiles);
        model.addAttribute("product", product);
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
                              @RequestParam("imageFile") MultipartFile imageFile,
                              RedirectAttributes redirectAttributes) { // Thêm RedirectAttributes
        try {
            if (!imageFile.isEmpty()) {
                String fileName = storageService.store(imageFile);
                product.setImageUrl(fileName);
            }
            productService.save(product);
            redirectAttributes.addFlashAttribute("successMessage", "Lưu sản phẩm thành công!");
        } catch (RuntimeException e) {
            // Bắt lỗi từ StorageService và gửi thông báo lại cho form
            redirectAttributes.addFlashAttribute("errorMessage", e.getMessage());
            // Trả lại các giá trị người dùng đã nhập để họ không phải nhập lại
            redirectAttributes.addFlashAttribute("product", product);
            // Nếu là edit thì phải trả lại id
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