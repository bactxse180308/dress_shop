package hsf302.assignment.controller.admin;

import hsf302.assignment.pojo.Style;
import hsf302.assignment.service.StyleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Controller
@RequestMapping("/admin/styles")
public class StyleController {

    @Autowired
    private StyleService styleService;

    // Hiển thị danh sách tất cả style
    @GetMapping
    public String listStyles(Model model) {
        List<Style> styles = styleService.findAll();
        model.addAttribute("styles", styles);
        // Để làm nổi bật mục "Styles" trong sidebar
        model.addAttribute("currentUri", "/admin/styles");
        return "admin/styles/list";
    }

    // Hiển thị form để thêm style mới
    @GetMapping("/add")
    public String showAddForm(Model model) {
        model.addAttribute("style", new Style());
        model.addAttribute("pageTitle", "Add New Style");
        model.addAttribute("currentUri", "/admin/styles");
        return "admin/styles/form";
    }

    // Xử lý việc lưu style mới hoặc cập nhật style đã có
    @PostMapping("/save")
    public String saveStyle(@ModelAttribute("style") Style style, RedirectAttributes redirectAttributes) {
        if (style.getId() == null) {
            // Create new
            styleService.save(style);
            redirectAttributes.addFlashAttribute("message", "The style has been saved successfully.");
        } else {
            // Update existing
            styleService.update(style.getId(), style);
            redirectAttributes.addFlashAttribute("message", "The style has been updated successfully.");
        }
        return "redirect:/admin/styles";
    }

    // Hiển thị form để chỉnh sửa style
    @GetMapping("/edit/{id}")
    public String showEditForm(@PathVariable("id") Integer id, Model model, RedirectAttributes redirectAttributes) {
        try {
            Style style = styleService.findById(id);
            if (style == null) {
                redirectAttributes.addFlashAttribute("error", "Style not found with ID: " + id);
                return "redirect:/admin/styles";
            }
            model.addAttribute("style", style);
            model.addAttribute("pageTitle", "Edit Style (ID: " + id + ")");
            model.addAttribute("currentUri", "/admin/styles");
            return "admin/styles/form";
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", "An error occurred.");
            return "redirect:/admin/styles";
        }
    }

    // Xử lý việc xóa style
    @GetMapping("/delete/{id}")
    public String deleteStyle(@PathVariable("id") Integer id, RedirectAttributes redirectAttributes) {
        try {
            styleService.deleteById(id);
            redirectAttributes.addFlashAttribute("message", "The style with ID " + id + " has been deleted.");
        } catch (Exception e) {
            // Bắt lỗi nếu style đang được sử dụng bởi một product (khóa ngoại)
            redirectAttributes.addFlashAttribute("error", "Could not delete style with ID " + id + ". It may be in use by a product.");
        }
        return "redirect:/admin/styles";
    }
}