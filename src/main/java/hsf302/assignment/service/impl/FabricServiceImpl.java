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

    @Override
    public Fabric findById(Integer id) {
        return fabricRepository.findById(id).orElse(null);
    }

    @Override
    public Fabric save(Fabric fabric) {
        return fabricRepository.save(fabric);
    }

    @Override
    public Fabric update(Integer id, Fabric fabricDetails) {
        Fabric existingFabric = findById(id);
        if (existingFabric != null) {
            // Cập nhật các trường
            existingFabric.setName(fabricDetails.getName());
            existingFabric.setDescription(fabricDetails.getDescription());
            // Lưu lại thay đổi
            return fabricRepository.save(existingFabric);
        }
        // Trả về null nếu không tìm thấy fabric để cập nhật
        return null;
    }

    @Override
    public void deleteById(Integer id) {
        fabricRepository.deleteById(id);
    }
}