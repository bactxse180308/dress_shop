package hsf302.assignment.service;

import hsf302.assignment.pojo.Decoration;

import java.util.List;

public interface DecorationService {
    List<Decoration> getAll();
    Decoration getById(Integer id);
    Decoration create(Decoration decoration);
    Decoration update(Integer id, Decoration decoration);
    void delete(Integer id);
}
