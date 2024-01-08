package mock;

import com.epam.dto.json.TrainingJsonDto;
import com.epam.model.Trainee;
import com.epam.model.Trainer;
import com.epam.model.Training;
import com.epam.model.TrainingType;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

import java.time.LocalDate;
import java.util.List;

public class TrainingMockData {
    private static final ObjectMapper mapper = new ObjectMapper();

    static {
        mapper.registerModule(new JavaTimeModule());
    }
    public static Training getMockedTraining_1() {
        TrainingType trainingType = createMockTrainingType();
        Trainer trainer = TrainerMockData.getMockedTrainer_1();
        Trainee trainee = TraineeMockData.getMockedTrainee_1();

        Training training = new Training();
        training.setId(1);
        training.setTrainingName("Java Programming");
        training.setTrainingType(trainingType);
        training.setTrainee(trainee);
        training.setTrainer(trainer);
        training.setTrainingDate(LocalDate.of(2023, 12, 1));
        training.setTrainingDuration(3.5);

        return training;
    }

    private static TrainingType createMockTrainingType() {
        Trainer mockedTrainer1 = TrainerMockData.getMockedTrainer_1();
        return new TrainingType(1,"Technical Training", List.of(mockedTrainer1),List.of());
    }



    public static TrainingJsonDto getMockedTraining_2(String resourceName) {
        String stringTrainingData = MockFromFile.getMockData(resourceName);
        try {
            return mapper.readValue(stringTrainingData, TrainingJsonDto.class);
        } catch (JsonProcessingException e) {
            throw new RuntimeException("Cannot readValue for Training", e);
        }
    }

}
