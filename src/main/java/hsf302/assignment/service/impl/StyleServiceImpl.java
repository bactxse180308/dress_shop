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
}
