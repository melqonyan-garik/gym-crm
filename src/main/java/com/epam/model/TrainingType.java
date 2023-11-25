package com.epam.model;

import lombok.Getter;
import lombok.Setter;

import jakarta.persistence.*;
import java.util.List;

@Getter
@Setter
@Entity
@Table(name = "training_type")
public class TrainingType {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "training_type_name")
    private String trainingTypeName;

    @OneToMany(mappedBy = "specialization")
    private List<Trainer> trainers;

    @OneToMany(mappedBy = "trainingType")
    private List<Training> trainings;
}
