package pl.ks.empiktask.service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import pl.ks.empiktask.dto.ComplaintRequest;
import pl.ks.empiktask.mapper.ComplaintFactory;
import pl.ks.empiktask.model.Complaint;
import pl.ks.empiktask.repo.ComplaintRepository;

import java.util.function.Function;
import java.util.function.Supplier;

@Service
@RequiredArgsConstructor
public class ComplaintPersistenceService {
    private final ComplaintRepository repository;
    private final ComplaintFactory complaintFactory;

    @Transactional
    public Complaint saveOrUpdateComplaintTransactional(ComplaintRequest request, String country) {
        return repository.findByProductIdAndReporter(request.getProductId(), request.getReporter())
                .map(getComplaintComplaintFunction())
                .orElseGet(getComplaintAlreadyExists(request, country));
    }

    private Function<Complaint, Complaint> getComplaintComplaintFunction() {
        return complaint -> {
            complaint.incrementCounter();
            return repository.save(complaint);
        };
    }

    private Supplier<Complaint> getComplaintAlreadyExists(ComplaintRequest request, String country) {
        return () -> {
            try {
                Complaint newComplaint = complaintFactory.fromRequest(request, country);
                return repository.save(newComplaint);
            } catch (DataIntegrityViolationException e) {
                throw new ResponseStatusException(HttpStatus.CONFLICT, "Complaint already exists");
            }
        };
    }

}
