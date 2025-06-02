package com.skillnest.jobservice.service;

import com.skillnest.jobSeekerService.data.model.JobSeeker;
import com.skillnest.jobSeekerService.data.repository.JobSeekerRepository;
import com.skillnest.jobSeekerService.dtos.UserDto;
import com.skillnest.jobSeekerService.dtos.request.RegisterJobSeekerRequest;
import com.skillnest.jobSeekerService.dtos.response.RegisterJobSeekerResponse;
import com.skillnest.jobSeekerService.mapper.JobSeekerMapper;
import com.skillnest.jobSeekerService.service.JobSeekerServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.web.client.RestTemplate;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ActiveProfiles("test")
class JobSeekerServiceImplTest {

    @Mock
    private RestTemplate restTemplate;

    @Mock
    private JobSeekerRepository jobSeekerRepository;

    @InjectMocks
    private JobSeekerServiceImpl jobSeekerService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void register_ShouldSaveJobSeeker_WhenUserExistsAndIsJobSeeker() {
        String userId = "ddhd";
        RegisterJobSeekerRequest request = new RegisterJobSeekerRequest();
        request.setUserId(userId);

        UserDto userDto = new UserDto();
        userDto.setId(userId);
        userDto.setRole("JOB_SEEKER");

        JobSeeker jobSeeker = new JobSeeker();
        RegisterJobSeekerResponse expectedResponse = new RegisterJobSeekerResponse("Job seeker registered successfully", jobSeeker);

        String userServiceUrl = "http://localhost:8080/user" + userId;

        when(restTemplate.getForEntity(userServiceUrl, UserDto.class))
                .thenReturn(new ResponseEntity<>(userDto, HttpStatus.OK));

        try (MockedStatic<JobSeekerMapper> mockedMapper = mockStatic(JobSeekerMapper.class)) {
            mockedMapper.when(() -> JobSeekerMapper.mapToRegisterJobSeeker(userDto, request))
                    .thenReturn(jobSeeker);
            mockedMapper.when(() -> JobSeekerMapper.mapToRegisterJobSeekerResponse("Job seeker registered successfully", jobSeeker))
                    .thenReturn(expectedResponse);

            RegisterJobSeekerResponse actualResponse = jobSeekerService.completeProfile(request);

            assertNotNull(actualResponse);
            assertEquals(expectedResponse.getMessage(), actualResponse.getMessage());
            verify(jobSeekerRepository, times(1)).save(jobSeeker);
        }
    }
    @Test
    public void updateProfile_ShouldUpdateJobSeekerProfile_WhenUserExistsAndIsJobSeeker(){

    }
}
