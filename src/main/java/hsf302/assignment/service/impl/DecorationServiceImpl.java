package hsf302.assignment.service.impl;

import hsf302.assignment.pojo.Decoration;
import hsf302.assignment.repository.DecorationRepository;
import hsf302.assignment.service.DecorationService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class DecorationServiceImpl implements DecorationService {

    private final DecorationRepository decorationRepository;

    @Override
    public List<Decoration> getAll() {
        return decorationRepository.findAll();
    }

    @Override
    public Decoration getById(Integer id) {
        return decorationRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Decoration not found with id: " + id));
    }

    @Override
    public Decoration create(Decoration decoration) {
        return decorationRepository.save(decoration);
    }

    @Override
    public Decoration update(Integer id, Decoration decoration) {
        Decoration existing = getById(id);
        existing.setName(decoration.getName());
        existing.setDescription(decoration.getDescription());
        existing.setExtraPrice(decoration.getExtraPrice());
        return decorationRepository.save(existing);
    }

    @Override
    public void delete(Integer id) {
        decorationRepository.deleteById(id);
    }
}
