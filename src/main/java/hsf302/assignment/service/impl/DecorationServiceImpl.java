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
    public List<Decoration> findAll() {
        return decorationRepository.findAll();
    }

    @Override
    public List<Decoration> findAllById(List<Integer> ids) {
        return decorationRepository.findAllById(ids);
    }
}
