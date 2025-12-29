package se.kth.journalsystem.patient.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import se.kth.journalsystem.patient.model.enums.PractitionerType;

import java.util.List;

@Entity
@Table(name = "practitioners")
@Getter
@Setter
@NoArgsConstructor
public class Practitioner {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String firstName;

    @Column(nullable = false)
    private String lastName;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private PractitionerType type;

    private String licenseNumber;

    @Column(name = "user_id", unique = true)
    private String userId;

    @ManyToOne
    @JoinColumn(name = "organization_id")
    private Organization organization;

    @OneToMany(mappedBy = "practitioner", cascade = CascadeType.ALL)
    private List<Encounter> encounters;
}
