package se.kth.journalsystem.patient.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import java.time.LocalDate;
import java.util.List;

@Entity
@Table(name = "patients")
@Getter
@Setter
@NoArgsConstructor
public class Patient {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String firstName;

    @Column(nullable = false)
    private String lastName;

    @Column(unique = true, nullable = false)
    private String socialSecurityNumber;

    private LocalDate dateOfBirth;

    private String phoneNumber;

    private String address;

    @Column(name = "user_id", unique = true)
    private String userId;

    @OneToMany(mappedBy = "patient", cascade = CascadeType.ALL)
    private List<Observation> observations;

    @OneToMany(mappedBy = "patient", cascade = CascadeType.ALL)
    private List<Condition> conditions;

    @OneToMany(mappedBy = "patient", cascade = CascadeType.ALL)
    private List<Encounter> encounters;
}
