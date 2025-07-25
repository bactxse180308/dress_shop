package hsf302.assignment.controller.admin;

import hsf302.assignment.pojo.Fabric;
import hsf302.assignment.service.FabricService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Controller
@RequestMapping("/admin/fabrics")
public class FabricController {

    @Autowired
    private FabricService fabricService;

    // Hiển thị danh sách tất cả các loại vải
    @GetMapping
    public String listFabrics(Model model) {
        List<Fabric> fabrics = fabricService.findAll();
        model.addAttribute("fabrics", fabrics);
        // Để làm nổi bật mục "Fabrics" trong sidebar
        model.addAttribute("currentUri", "/admin/fabrics");
        return "admin/fabrics/list";
    }

    // Hiển thị form để thêm mới
    @GetMapping("/add")
    public String showAddForm(Model model) {
        model.addAttribute("fabric", new Fabric());
        model.addAttribute("pageTitle", "Add New Fabric");
        model.addAttribute("currentUri", "/admin/fabrics");
        return "admin/fabrics/form";
    }

    // Xử lý việc lưu (thêm mới hoặc cập nhật)
    @PostMapping("/save")
    public String saveFabric(@ModelAttribute("fabric") Fabric fabric, RedirectAttributes redirectAttributes) {
        if (fabric.getId() == null) {
            fabricService.save(fabric);
            redirectAttributes.addFlashAttribute("message", "The new fabric has been saved successfully.");
        } else {
            fabricService.update(fabric.getId(), fabric);
            redirectAttributes.addFlashAttribute("message", "The fabric has been updated successfully.");
        }
        return "redirect:/admin/fabrics";
    }

    // Hiển thị form để chỉnh sửa
    @GetMapping("/edit/{id}")
    public String showEditForm(@PathVariable("id") Integer id, Model model, RedirectAttributes redirectAttributes) {
        Fabric fabric = fabricService.findById(id);
        if (fabric == null) {
            redirectAttributes.addFlashAttribute("error", "Fabric not found with ID: " + id);
            return "redirect:/admin/fabrics";
        }
        model.addAttribute("fabric", fabric);
        model.addAttribute("pageTitle", "Edit Fabric (ID: " + id + ")");
        model.addAttribute("currentUri", "/admin/fabrics");
        return "admin/fabrics/form";
    }

    // Xử lý việc xóa
    @GetMapping("/delete/{id}")
    public String deleteFabric(@PathVariable("id") Integer id, RedirectAttributes redirectAttributes) {
        try {
            fabricService.deleteById(id);
            redirectAttributes.addFlashAttribute("message", "The fabric with ID " + id + " has been deleted.");
        } catch (Exception e) {
            // Bắt lỗi nếu fabric đang được sử dụng bởi một sản phẩm (lỗi khóa ngoại)
            redirectAttributes.addFlashAttribute("error", "Could not delete fabric ID " + id + ". It may be in use by a product.");
        }
        return "redirect:/admin/fabrics";
    }
}