package hsf302.assignment.service.impl;

import hsf302.assignment.pojo.Style;
import hsf302.assignment.repository.FabricRepository;
import hsf302.assignment.repository.StyleRepository;
import hsf302.assignment.service.StyleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class StyleServiceImpl implements StyleService {

    @Autowired private StyleRepository styleRepository;
    @Override
    public List<Style> findAll() {
        return styleRepository.findAll();
    }

    @Override
    public Style findById(Integer id) {
        return styleRepository.findById(id).orElse(null);
    }

    @Override
    public Style save(Style style) {
        return styleRepository.save(style);
    }

    @Override
    public Style update(Integer id, Style styleDetails) {
        Style existingStyle = findById(id);
        if (existingStyle != null) {
            existingStyle.setName(styleDetails.getName());
            existingStyle.setDescription(styleDetails.getDescription());
            return styleRepository.save(existingStyle);
        }
        return null; // Hoặc ném ra một exception
    }

    @Override
    public void deleteById(Integer id) {
        styleRepository.deleteById(id);

    }
}
