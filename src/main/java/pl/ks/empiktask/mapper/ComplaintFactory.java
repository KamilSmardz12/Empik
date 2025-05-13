package pl.ks.empiktask.mapper;

import org.springframework.stereotype.Component;
import pl.ks.empiktask.dto.ComplaintRequest;
import pl.ks.empiktask.model.Complaint;
import pl.ks.empiktask.util.time.LocalDateTimeProvider;

@Component
public class ComplaintFactory {

    private final LocalDateTimeProvider dateTimeProvider;

    public ComplaintFactory(LocalDateTimeProvider dateTimeProvider) {
        this.dateTimeProvider = dateTimeProvider;
    }

    public Complaint fromRequest(ComplaintRequest request, String country) {
        return Complaint.builder()
                .productId(request.getProductId())
                .content(request.getContent())
                .reporter(request.getReporter())
                .createdAt(dateTimeProvider.now())
                .country(country)
                .counter(1)
                .build();
    }
}
