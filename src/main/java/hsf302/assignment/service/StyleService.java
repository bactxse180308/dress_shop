package hsf302.assignment.service;

import hsf302.assignment.pojo.Fabric;
import hsf302.assignment.pojo.Style;

import java.util.List;
import java.util.Optional;

public interface StyleService {
    List<Style> findAll();

    Style findById(Integer id);
    Style save(Style style);
    Style update(Integer id, Style styleDetails);
    void deleteById(Integer id);
}
