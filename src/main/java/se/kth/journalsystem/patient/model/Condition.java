package se.kth.journalsystem.patient.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import se.kth.journalsystem.patient.model.enums.ConditionStatus;
import java.time.LocalDateTime;

@Entity
@Table(name = "conditions")
@Getter
@Setter
@NoArgsConstructor
public class Condition {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "patient_id", nullable = false)
    private Patient patient;

    @Column(nullable = false)
    private String diagnosis;

    @Column(columnDefinition = "TEXT")
    private String description;

    @Enumerated(EnumType.STRING)
    private ConditionStatus status;

    @CreationTimestamp
    private LocalDateTime diagnosisDate;

    @ManyToOne
    @JoinColumn(name = "diagnosed_by")
    private Practitioner diagnosedBy;

    private String severity;
}


