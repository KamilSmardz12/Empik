package pl.ks.empiktask.service.interfaces;

import pl.ks.empiktask.dto.ComplaintRequest;
import pl.ks.empiktask.dto.ComplaintResponse;

import java.util.List;

public interface ComplaintService {
    ComplaintResponse createComplaint(ComplaintRequest request, String ip);
    List<ComplaintResponse> getAllComplaints();
    ComplaintResponse updateComplaint(Long id, String content);
}
