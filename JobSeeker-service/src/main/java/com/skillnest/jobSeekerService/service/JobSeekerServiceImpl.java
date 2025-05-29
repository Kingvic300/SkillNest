package com.skillnest.jobSeekerService.service;

import com.skillnest.jobSeekerService.data.model.JobSeeker;
import com.skillnest.jobSeekerService.data.repository.JobSeekerRepository;
import com.skillnest.jobSeekerService.dtos.UserDto;
import com.skillnest.jobSeekerService.dtos.request.*;
import com.skillnest.jobSeekerService.dtos.response.*;
import com.skillnest.jobSeekerService.exception.JobSeekerNotFoundException;
import com.skillnest.jobSeekerService.mapper.JobSeekerMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class JobSeekerServiceImpl implements JobSeekerService{

    private final RestTemplate restTemplate;
    private final JobSeekerRepository jobSeekerRepository;

    private static final String USER_SERVICE_URL = "http://localhost:8080/user";

    @Override
    public RegisterJobSeekerResponse completeProfile(RegisterJobSeekerRequest registerJobSeekerRequest){
        String url = USER_SERVICE_URL + registerJobSeekerRequest.getUserId();

        ResponseEntity<UserDto> response = restTemplate.getForEntity(url, UserDto.class);
        UserDto user = response.getBody();
        if(user == null){
            throw new JobSeekerNotFoundException("Job seeker not found");
        }
        JobSeeker jobSeeker = JobSeekerMapper.mapToRegisterJobSeeker(user, registerJobSeekerRequest);
        jobSeekerRepository.save(jobSeeker);

        return JobSeekerMapper.mapToRegisterJobSeekerResponse("Job seeker registered successfully", jobSeeker);
    }

    @Override
    public UpdateJobSeekerProfileResponse updateProfile(String userid, UpdateJobSeekerProfileRequest request) {
        Optional<JobSeeker> existingJobSeeker = jobSeekerRepository.findByUserId(userid);
        if (existingJobSeeker.isEmpty()){
            throw new JobSeekerNotFoundException("Job seeker not found");
        }
        JobSeeker jobSeeker = existingJobSeeker.get();
        JobSeekerMapper.mapToUpdateJobSeekerProfile(jobSeeker, request);
        jobSeekerRepository.save(jobSeeker);
        return JobSeekerMapper.mapToUpdateJobSeekerProfileResponse("Job seeker updated Successfully", jobSeeker);
    }

    @Override
    public UpdateJobSeekerProfileResponse getProfile(String userid) {
        Optional<JobSeeker> existingJobSeeker = jobSeekerRepository.findByUserId(userid);
        if (existingJobSeeker.isEmpty()){
            throw new JobSeekerNotFoundException("Job seeker not found");
        }
        JobSeeker jobSeeker = existingJobSeeker.get();
        return JobSeekerMapper.mapToUpdateJobSeekerProfileResponse("Job seeker profile found", jobSeeker);
    }

    @Override
    public AvailabilitySlotResponse setAvailability(String jobSeekerId, List<AvailabilitySlotRequest> slots) {
        Optional<JobSeeker> existingJobSeeker = jobSeekerRepository.findById(jobSeekerId);
        if(existingJobSeeker.isEmpty()){
            throw new JobSeekerNotFoundException("Job seeker not found");
        }
        JobSeeker jobSeeker = existingJobSeeker.get();
        jobSeeker.setAvailabilitySlots(JobSeekerMapper.mapToAvailabilitySlot(jobSeeker.getId(),slots));
        jobSeekerRepository.save(jobSeeker);
        return JobSeekerMapper.mapToAvailabilitySlotResponse("Availability has been set successfully", jobSeeker);
    }

    @Override
    public BankAccountResponse setBankAccount(String jobSeekerId, BankAccountRequest request) {
        Optional<JobSeeker> existingJobSeeker = jobSeekerRepository.findById(jobSeekerId);
        if(existingJobSeeker.isEmpty()){
            throw new JobSeekerNotFoundException("Job seeker not found");
        }
        JobSeeker jobSeeker = existingJobSeeker.get();
        jobSeeker.setBankAccount(JobSeekerMapper.mapToBankAccount(jobSeeker.getId(), request));
        jobSeekerRepository.save(jobSeeker);
        return JobSeekerMapper.mapToBankAccountResponse("Account has been set Successfully", jobSeeker);
    }

    @Override
    public List<VerificationDocumentResponse> getDocuments(String jobSeekerId) {
        Optional<JobSeeker> existingJobSeeker = jobSeekerRepository.findById(jobSeekerId);
        if(existingJobSeeker.isEmpty()){
            throw new JobSeekerNotFoundException("Job seeker not found");
        }
        JobSeeker jobSeeker = existingJobSeeker.get();

        return List.of(JobSeekerMapper.mapToVerificationDocumentResponse("Document found", jobSeeker.getDocuments()));
    }

    @Override
    public List<WorkImageResponse> getWorkImages(String jobSeekerId) {
        Optional<JobSeeker> existingJobSeeker = jobSeekerRepository.findById(jobSeekerId);
        if(existingJobSeeker.isEmpty()){
            throw new JobSeekerNotFoundException("Job seeker not found");
        }
        JobSeeker jobSeeker = existingJobSeeker.get();
        return List.of(JobSeekerMapper.ma);
    }

    @Override
    public List<AvailabilitySlotResponse> getAvailability(String jobSeekerId) {
        Optional<JobSeeker> existingJobSeeker = jobSeekerRepository.findById(jobSeekerId);
        if(existingJobSeeker.isEmpty()){
            throw new JobSeekerNotFoundException("Job seeker not found");
        }
        JobSeeker jobSeeker = existingJobSeeker.get();
        return List.of();
    }

    @Override
    public BankAccountResponse getBankAccount(String jobSeekerId) {
        Optional<JobSeeker> existingJobSeeker = jobSeekerRepository.findById(jobSeekerId);
        if(existingJobSeeker.isEmpty()){
            throw new JobSeekerNotFoundException("Job seeker not found");
        }
        JobSeeker jobSeeker = existingJobSeeker.get();
        return null;
    }

    @Override
    public UploadDocumentsResponse uploadDocuments(String jobSeekerId, List<VerificationDocumentRequest> documents) {
        return null;
    }

    @Override
    public WorkImageResponse uploadWorkImages(String jobSeekerId, List<WorkImageRequest> images) {
        return null;
    }
}
