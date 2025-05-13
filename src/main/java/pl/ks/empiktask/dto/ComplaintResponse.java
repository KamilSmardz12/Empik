package pl.ks.empiktask.dto;


import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.hateoas.RepresentationModel;

import java.time.LocalDateTime;

@EqualsAndHashCode(callSuper = true)
@Data
@Builder
@Schema(description = "Reklamacja - dane wyjściowe")
public class ComplaintResponse extends RepresentationModel<ComplaintResponse> {
    private Long id;
    private String productId;
    private String content;
    private LocalDateTime createdAt;
    private String reporter;
    private String country;
    private int counter;
}
