package hsf302.assignment.service;

import hsf302.assignment.pojo.Fabric;

import java.util.List;

public interface FabricService {
    List<Fabric> findAll();
    Fabric findById(Integer id);
    Fabric save(Fabric fabric);
    Fabric update(Integer id, Fabric fabricDetails);
    void deleteById(Integer id);
}
