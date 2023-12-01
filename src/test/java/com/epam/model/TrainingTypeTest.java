package com.epam.model;
import org.hamcrest.CoreMatchers;
import org.junit.jupiter.api.Test;

import java.util.Collections;
import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;

public class TrainingTypeTest {

    @Test
    void testImmutableEntity() {
        Integer id = 1;
        String trainingTypeName = "Type A";
        List<Trainer> trainers = Collections.emptyList();
        List<Training> trainings = Collections.emptyList();
        TrainingType trainingType = new TrainingType(id, trainingTypeName, trainers, trainings);

        assertThat(trainingType, CoreMatchers.notNullValue());
        assertThat(trainingType.getId(), CoreMatchers.equalTo(id));
        assertThat(trainingType.getTrainingTypeName(), CoreMatchers.equalTo(trainingTypeName));
        assertThat(trainingType.getTrainers(), CoreMatchers.equalTo(trainers));
        assertThat(trainingType.getTrainings(), CoreMatchers.equalTo(trainings));
    }
}


