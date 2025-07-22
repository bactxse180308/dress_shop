package hsf302.assignment.controller.admin;

import hsf302.assignment.pojo.Decoration;
import hsf302.assignment.repository.DecorationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@Controller
@RequestMapping("/admin/decorations")
public class DecorationController {

    @Autowired
    private DecorationRepository decorationRepository;

    @GetMapping
    public String listDecorations(Model model,
                                @RequestParam(defaultValue = "1") int page,
                                @RequestParam(defaultValue = "10") int size) {
        Page<Decoration> decorationPage = decorationRepository.findAll(PageRequest.of(page - 1, size));
        model.addAttribute("decorations", decorationPage.getContent());
        model.addAttribute("currentPage", page);
        model.addAttribute("totalPages", decorationPage.getTotalPages());
        return "admin/decoration-manage";
    }

    @GetMapping("/{id}")
    @ResponseBody
    public ResponseEntity<Decoration> getDecoration(@PathVariable Integer id) {
        Optional<Decoration> decoration = decorationRepository.findById(id);
        return decoration.map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    @ResponseBody
    public ResponseEntity<Decoration> createDecoration(@RequestBody Decoration decoration) {
        return ResponseEntity.ok(decorationRepository.save(decoration));
    }

    @PutMapping("/{id}")
    @ResponseBody
    public ResponseEntity<Decoration> updateDecoration(@PathVariable Integer id, @RequestBody Decoration decoration) {
        if (!decorationRepository.existsById(id)) {
            return ResponseEntity.notFound().build();
        }
        decoration.setId(id);
        return ResponseEntity.ok(decorationRepository.save(decoration));
    }

    @DeleteMapping("/{id}")
    @ResponseBody
    public ResponseEntity<Void> deleteDecoration(@PathVariable Integer id) {
        if (!decorationRepository.existsById(id)) {
            return ResponseEntity.notFound().build();
        }
        decorationRepository.deleteById(id);
        return ResponseEntity.ok().build();
    }
}
