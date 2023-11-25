package com.epam.model;

import lombok.Getter;
import lombok.Setter;

import jakarta.persistence.*;
import java.time.LocalDate;
import java.util.List;

@Getter
@Setter
@Entity
@Table(name = "trainee")
public class Trainee {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "date_of_birth")
    private LocalDate dateOfBirth;

    @Column
    private String address;

    @OneToOne
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToMany(mappedBy = "trainees")
    private List<Trainer> trainers;

    @OneToMany(mappedBy = "trainee")
    private List<Training> trainings;

}
