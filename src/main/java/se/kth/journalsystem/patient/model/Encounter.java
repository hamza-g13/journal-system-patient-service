package se.kth.journalsystem.patient.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import se.kth.journalsystem.patient.model.enums.EncounterType;
import java.time.LocalDateTime;

@Entity
@Table(name = "encounters")
@Getter
@Setter
@NoArgsConstructor
public class Encounter {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "patient_id", nullable = false)
    private Patient patient;

    @ManyToOne
    @JoinColumn(name = "practitioner_id", nullable = false)
    private Practitioner practitioner;

    @CreationTimestamp
    @Column(nullable = false)
    private LocalDateTime encounterDate;

    @Enumerated(EnumType.STRING)
    private EncounterType type;

    @Column(columnDefinition = "TEXT")
    private String notes;

    @ManyToOne
    @JoinColumn(name = "location_id")
    private Location location;

    private String reasonForVisit;
}


