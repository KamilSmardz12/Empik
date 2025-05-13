package pl.ks.empiktask.service;


import lombok.RequiredArgsConstructor;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import pl.ks.empiktask.controller.ComplaintController;
import pl.ks.empiktask.dto.ComplaintRequest;
import pl.ks.empiktask.dto.ComplaintResponse;
import pl.ks.empiktask.mapper.ComplaintMapper;
import pl.ks.empiktask.model.Complaint;
import pl.ks.empiktask.repo.ComplaintRepository;
import pl.ks.empiktask.service.interfaces.ComplaintService;

import java.util.List;
import java.util.stream.Collectors;

import static org.springframework.http.HttpStatus.NOT_FOUND;

@Service
@RequiredArgsConstructor
public class ComplaintServiceImpl implements ComplaintService {

    private final ComplaintRepository repository;
    private final ComplaintPersistenceService persistenceService;
    private final ComplaintMapper complaintMapper;
    private final IpService ipService;

    @Override
    public ComplaintResponse createComplaint(ComplaintRequest request, String ip) {
        String country = ipService.resolveCountryFromIp(ip).getCountry();
        Complaint complaint = persistenceService.saveOrUpdateComplaintTransactional(request, country);
        return enrichWithLinks(complaintMapper.toResponse(complaint));
    }

    @Override
    public List<ComplaintResponse> getAllComplaints() {
        return repository.findAll().stream()
                .map(complaintMapper::toResponse)
                .map(this::enrichWithLinks)
                .collect(Collectors.toList());
    }

    @Override
    public ComplaintResponse updateComplaint(Long id, String content) {
        Complaint complaint = repository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(NOT_FOUND, "Complaint not found"));
        complaint.setContent(content);
        return enrichWithLinks(complaintMapper.toResponse(repository.save(complaint)));
    }

    private ComplaintResponse enrichWithLinks(ComplaintResponse response) {
        response.add(WebMvcLinkBuilder
                .linkTo(WebMvcLinkBuilder
                        .methodOn(ComplaintController.class).getAll())
                .withRel("all")
        );
        response.add(WebMvcLinkBuilder
                .linkTo(WebMvcLinkBuilder.methodOn(ComplaintController.class)
                        .update(response.getId(), "..."))
                .withRel("update")
        );
        return response;
    }
}
