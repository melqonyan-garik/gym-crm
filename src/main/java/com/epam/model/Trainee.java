package com.epam.model;


import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDate;
import java.util.List;


@Getter
@Setter
@Entity
@Table(name = "trainee")
@ToString
public class Trainee {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "date_of_birth")
    private LocalDate dateOfBirth;

    @Column(name = "address")
    private String address;

    @OneToOne(cascade = CascadeType.PERSIST, fetch = FetchType.EAGER)
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToMany(mappedBy = "trainees", fetch = FetchType.EAGER)
    private List<Trainer> trainers;

    @OneToMany(mappedBy = "trainee", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private List<Training> trainings;

}
