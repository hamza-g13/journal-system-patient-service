package se.kth.journalsystem.patient.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import java.time.LocalDateTime;

@Entity
@Table(name = "observations")
@Getter
@Setter
@NoArgsConstructor
public class Observation {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "patient_id", nullable = false)
    private Patient patient;

    @Column(nullable = false)
    private String type;

    private String value;

    private String unit;

    @Column(columnDefinition = "TEXT")
    private String notes;

    @CreationTimestamp
    @Column(nullable = false)
    private LocalDateTime observationDate;

    @ManyToOne
    @JoinColumn(name = "recorded_by")
    private Practitioner recordedBy;
}


