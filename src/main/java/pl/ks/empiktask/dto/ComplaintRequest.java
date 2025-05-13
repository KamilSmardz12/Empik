package pl.ks.empiktask.dto;


import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
@Schema(description = "Reklamacja - dane wejściowe")
public class ComplaintRequest {

    @NotBlank(message = "Product ID is required")
    @Schema(description = "Identyfikator produktu", example = "ABC123")
    private String productId;

    @NotBlank(message = "Content is required")
    @Schema(description = "Treść reklamacji", example = "Produkt jest uszkodzony")
    private String content;

    @Email(message = "Reporter must be a valid email")
    @NotBlank(message = "Reporter is required")
    @Schema(description = "Zgłaszający", example = "jan.kowalski@example.com")
    private String reporter;
}
