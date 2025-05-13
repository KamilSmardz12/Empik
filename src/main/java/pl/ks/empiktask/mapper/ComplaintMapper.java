package pl.ks.empiktask.mapper;

import org.springframework.stereotype.Component;
import pl.ks.empiktask.dto.ComplaintResponse;
import pl.ks.empiktask.model.Complaint;

@Component
public class ComplaintMapper {

    public ComplaintResponse toResponse(Complaint c) {
        return ComplaintResponse.builder()
                .id(c.getId())
                .productId(c.getProductId())
                .content(c.getContent())
                .createdAt(c.getCreatedAt())
                .reporter(c.getReporter())
                .country(c.getCountry())
                .counter(c.getCounter())
                .build();
    }
}
