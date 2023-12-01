package mock;

import com.epam.dto.TraineeJsonDto;
import com.epam.model.Trainee;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import lombok.SneakyThrows;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class TraineeMockData {
    public static final String TRAINEE_RESOURCE_NAME = "prepared-data-file-for-trainee.json";
    private static final ObjectMapper mapper = new ObjectMapper();

    static {
        mapper.registerModule(new JavaTimeModule());
    }

    public static Trainee getMockedTrainee_1() {

        Trainee trainee = new Trainee();
        trainee.setId(1);
        trainee.setAddress("address");
        trainee.setDateOfBirth(LocalDate.of(2000, 12, 12));


        trainee.setUser(UserMockData.getMockedUser());
        trainee.setTrainers(new ArrayList<>());
        trainee.setTrainings(new ArrayList<>());
        return trainee;
    }



    @SneakyThrows
    public static List<TraineeJsonDto> getMockedTrainee_2() {
        String stringTraineeData = MockFromFile.getMockData(TRAINEE_RESOURCE_NAME);
        return List.of(mapper.readValue(stringTraineeData, TraineeJsonDto[].class));
    }
}
