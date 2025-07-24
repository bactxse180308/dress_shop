package hsf302.assignment.service.impl;

import hsf302.assignment.pojo.Measurement;
import hsf302.assignment.repository.MeasurementRepository;
import hsf302.assignment.service.MeasurementService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class MeasurementServiceImpl implements MeasurementService {
    private MeasurementRepository measurementRepository;
    @Override
    public Measurement createMeasurement(Measurement measurement) {
        return measurementRepository.save(measurement);
    }
}
