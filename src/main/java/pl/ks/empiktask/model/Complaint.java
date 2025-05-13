package pl.ks.empiktask.model;


import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Table(
        name = "complaints",
        uniqueConstraints = @UniqueConstraint(columnNames = {"product_id", "reporter"})
)
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Complaint {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "product_id", nullable = false)
    private String productId;

    @Column(length = 1000)
    private String content;

    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @Column(nullable = false)
    private String reporter;

    private String country;

    private int counter;

    public void incrementCounter() {
        this.counter++;
    }
}
