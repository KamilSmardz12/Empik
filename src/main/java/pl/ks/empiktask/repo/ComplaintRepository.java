package pl.ks.empiktask.repo;

import jakarta.persistence.LockModeType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import pl.ks.empiktask.model.Complaint;

import java.util.Optional;

public interface ComplaintRepository extends JpaRepository<Complaint, Long> {

    @Lock(LockModeType.PESSIMISTIC_WRITE)
    Optional<Complaint> findByProductIdAndReporter(String productId, String reporter);

}
