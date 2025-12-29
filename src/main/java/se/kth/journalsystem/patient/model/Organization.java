package se.kth.journalsystem.patient.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import java.util.List;

@Entity
@Table(name = "organizations")
@Getter
@Setter
@NoArgsConstructor
public class Organization {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    private String type;

    private String address;

    private String phoneNumber;

    private String email;

    @OneToMany(mappedBy = "organization")
    private List<Practitioner> practitioners;

}


