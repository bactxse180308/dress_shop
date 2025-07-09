package hsf302.assignment.service.impl;
import hsf302.assignment.pojo.Fabric;
import hsf302.assignment.repository.FabricRepository;
import hsf302.assignment.service.FabricService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class FabricServiceImpl implements FabricService {
    @Autowired private FabricRepository fabricRepository;
    @Override public List<Fabric> findAll() { return fabricRepository.findAll(); }
}