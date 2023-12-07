package com.epam.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Immutable;

import java.util.List;

@Getter
@Entity
@Table(name = "training_type")
@Immutable
@NoArgsConstructor
@AllArgsConstructor
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
