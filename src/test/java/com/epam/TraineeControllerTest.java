//package com.epam;
//
//import com.epam.controller.TraineeController;
//import com.epam.dto.trainee.TraineeProfileResponse;
//import com.epam.dto.trainee.TraineeRegistrationResponse;
//import com.epam.mappers.TraineeMapper;
//import com.epam.model.Trainee;
//import com.epam.model.User;
//import com.epam.service.TraineeService;
//import com.epam.service.TrainerService;
//import com.epam.utils.UserUtils;
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.Test;
//import org.mockito.InjectMocks;
//import org.mockito.Mock;
//import org.mockito.MockitoAnnotations;
//import org.springframework.http.MediaType;
//import org.springframework.test.web.servlet.MockMvc;
//import org.springframework.test.web.servlet.ResultActions;
//import org.springframework.test.web.servlet.setup.MockMvcBuilders;
//
//import java.util.Optional;
//
//import static org.mockito.Mockito.verify;
//import static org.mockito.Mockito.when;
//import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
//import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
//import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
//import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
//
//public class TraineeControllerTest {
//
//    private MockMvc mockMvc;
//
//    @InjectMocks
//    private TraineeController traineeController;
//
//    @Mock
//    private TraineeMapper traineeMapper;
//
//    @Mock
//    private TraineeService traineeService;
//
//    @Mock
//    private UserUtils userUtils;
//
//    @Mock
//    private TrainerService trainerService;
//
//    @BeforeEach
//    void setUp() {
//        MockitoAnnotations.openMocks(this);
//        mockMvc = MockMvcBuilders.standaloneSetup(traineeController).build();
//    }
//
//    @Test
//    void testRegisterTrainee() throws Exception {
//        TraineeRegistrationResponse response = new TraineeRegistrationResponse("firstname.lastname", "pass123");
//
//        Trainee createdTrainee = new Trainee();
//        User user = new User();
//        user.setUsername("firstname.lastname");
//        user.setPassword("pass123");
//        createdTrainee.setUser(user);
//        when(traineeService.createTrainee(null)).thenReturn(createdTrainee);
//        when(trainerService.getTrainerByUsername(user.getUsername())).thenReturn(Optional.empty());
//        ResultActions resultActions = mockMvc.perform(post("/trainee")
//                .contentType(MediaType.APPLICATION_JSON)
//                .content("{\n" +
//                         "    \"firstname\":\"firstname\",\n" +
//                         "    \"lastname\":\"lastname\"\n" +
//                         "}"));
//
//        resultActions.andExpect(status().isOk())
//                .andExpect(jsonPath("$.username").value(response.getUsername()))
//                .andExpect(jsonPath("$.password").value(response.getPassword()));
//
//    }
//
//    @Test
//    void testGetTraineeProfile() throws Exception {
//        String username = "testUsername";
//        Trainee trainee = new Trainee();
//        TraineeProfileResponse profile = new TraineeProfileResponse();
//        profile.setActive(true);
//        profile.setLastname("lastname");
//        profile.setFirstname("firstname");
//        when(traineeService.getTraineeByUsername(username)).thenReturn(Optional.of(trainee));
//        when(traineeMapper.traineeToTraineeProfileResponse(trainee)).thenReturn(profile);
//
//        ResultActions resultActions = mockMvc.perform(get("/trainee")
//                .param("username", username));
//
//        resultActions.andExpect(status().isOk())
//                .andExpect(jsonPath("$.firstname").value(profile.getFirstname()))
//                .andExpect(jsonPath("$.lastname").value(profile.getLastname()))
//                .andExpect(jsonPath("$.address").value(profile.getAddress()))
//                .andExpect(jsonPath("$.trainers").value(profile.getTrainers()))
//                .andExpect(jsonPath("$.active").value(profile.isActive()));
//
//        verify(traineeService).getTraineeByUsername(username);
//        verify(traineeMapper).traineeToTraineeProfileResponse(trainee);
//    }
//}
