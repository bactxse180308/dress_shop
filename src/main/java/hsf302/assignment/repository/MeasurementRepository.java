package hsf302.assignment.repository;

import hsf302.assignment.pojo.Measurement;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MeasurementRepository extends JpaRepository<Measurement, Long> {
}
