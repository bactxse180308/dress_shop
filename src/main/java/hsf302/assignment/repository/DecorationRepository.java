package hsf302.assignment.repository;

import hsf302.assignment.pojo.Decoration;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DecorationRepository extends JpaRepository<Decoration, Integer> {
}
