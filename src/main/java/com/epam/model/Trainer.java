package com.epam.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@Entity
@Table(name = "trainer")
public class Trainer {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne(cascade = CascadeType.PERSIST, fetch = FetchType.EAGER)
    @JoinColumn(name = "specialization")
    private TrainingType specialization;

    @OneToOne(cascade = CascadeType.PERSIST, fetch = FetchType.EAGER)
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name = "trainer_to_trainee",
            joinColumns = @JoinColumn(name = "trainer_id"),
            inverseJoinColumns = @JoinColumn(name = "trainee_id")
    )
    private List<Trainee> trainees;

    @OneToMany(mappedBy = "trainer", cascade = CascadeType.PERSIST, fetch = FetchType.EAGER)
    private List<Training> trainings;

}
