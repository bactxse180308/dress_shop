package hsf302.assignment.config;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ModelAttribute;

@ControllerAdvice
public class GlobalUriAdvice {
    @ModelAttribute
    public void addCurrentUri(Model model, HttpServletRequest request) {
        model.addAttribute("currentUri", request.getRequestURI());
    }
}
