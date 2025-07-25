package hsf302.assignment.service;

import hsf302.assignment.pojo.Decoration;

import java.util.List;

public interface DecorationService {
    List<Decoration> findAll();
    List<Decoration> findAllById(List<Integer> ids);
}
