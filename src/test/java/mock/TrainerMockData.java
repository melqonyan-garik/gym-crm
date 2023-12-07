package mock;

import com.epam.dto.TrainerJsonDto;
import com.epam.model.Trainer;
import com.epam.model.TrainingType;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

import java.util.ArrayList;
import java.util.List;

public class TrainerMockData {
    private static final String TRAINER_RESOURCE_NAME = "prepared-data-file-for-trainers.json";
    private static final ObjectMapper mapper = new ObjectMapper();

    static {
        mapper.registerModule(new JavaTimeModule());
    }

    public static Trainer getMockedTrainer_1() {

        Trainer trainer = new Trainer();
        trainer.setId(1);
        TrainingType trainingType = new TrainingType(1, "Java Core", List.of(), List.of());
        trainer.setSpecialization(trainingType);
        trainer.setTrainings(List.of());
        trainer.setUser(UserMockData.getMockedUser());
        trainer.setTrainees(new ArrayList<>());
        trainer.setTrainings(new ArrayList<>());
        return trainer;
    }


    public static List<TrainerJsonDto> getMockedTrainer_2() {
        String stringTrainerData = MockFromFile.getMockData(TRAINER_RESOURCE_NAME);
        try {
            return List.of(mapper.readValue(stringTrainerData, TrainerJsonDto[].class));
        } catch (JsonProcessingException e) {
            throw new RuntimeException("Cannot readValue for Trainer", e);
        }
    }

}
