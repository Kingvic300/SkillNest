package com.skillnest.jobSeekerService.mapper;

import com.skillnest.jobSeekerService.data.model.AvailabilitySlot;
import com.skillnest.jobSeekerService.data.model.BankAccount;
import com.skillnest.jobSeekerService.data.model.JobSeeker;
import com.skillnest.jobSeekerService.data.model.VerificationDocument;
import com.skillnest.jobSeekerService.dtos.UserDto;
import com.skillnest.jobSeekerService.dtos.request.*;
import com.skillnest.jobSeekerService.dtos.response.*;

import java.util.List;
import java.util.UUID;

public class JobSeekerMapper {

    public static JobSeeker mapToRegisterJobSeeker(UserDto user, RegisterJobSeekerRequest registerJobSeekerRequest){
        JobSeeker jobSeeker = new JobSeeker();
        jobSeeker.setId(UUID.randomUUID().toString());
        jobSeeker.setUserId(user.getId());
        jobSeeker.setAvailabilitySlots(registerJobSeekerRequest.getAvailabilitySlots());
        jobSeeker.setBio(registerJobSeekerRequest.getBio());
        jobSeeker.setDocuments(registerJobSeekerRequest.getDocuments());
        jobSeeker.setFullName(registerJobSeekerRequest.getFullName());
        jobSeeker.setBankAccount(registerJobSeekerRequest.getBankAccount());
        jobSeeker.setSkillIds(registerJobSeekerRequest.getSkillIds());
        jobSeeker.setProfilePictureUrl(registerJobSeekerRequest.getProfilePictureUrl());
        jobSeeker.setWorkImages(registerJobSeekerRequest.getWorkImages());
        return jobSeeker;
    }
    public static RegisterJobSeekerResponse mapToRegisterJobSeekerResponse(String message, JobSeeker jobSeeker){
        RegisterJobSeekerResponse response = new RegisterJobSeekerResponse();
        response.setMessage(message);
        response.setJobSeeker(jobSeeker);
        return response;
    }

    public static void mapToUpdateJobSeekerProfile(JobSeeker jobSeeker, UpdateJobSeekerProfileRequest request) {
        jobSeeker.setFullName(request.getFullName());
        jobSeeker.setLocation(request.getLocation());
        jobSeeker.setPhoneNumber(request.getPhoneNumber());
        jobSeeker.setProfilePictureUrl(request.getProfilePictureUrl());
        jobSeeker.setResumeUrl(request.getResumeUrl());
    }
    public static UpdateJobSeekerProfileResponse mapToUpdateJobSeekerProfileResponse(String message, JobSeeker jobSeeker){
        UpdateJobSeekerProfileResponse response = new UpdateJobSeekerProfileResponse();
        response.setMessage(message);
        response.setJobSeeker(jobSeeker);
        return response;
    }
    public static List<AvailabilitySlot> mapToAvailabilitySlot(String jobSeekerId,List<AvailabilitySlotRequest> slots){
        AvailabilitySlot availabilitySlot = new AvailabilitySlot();
        availabilitySlot.setJobSeekerId(jobSeekerId);
        availabilitySlot.setEndTime(slots.getLast().getEndTime());
        availabilitySlot.setDayOfWeek(slots.getLast().getDayOfWeek());
        availabilitySlot.setStartTime(slots.getLast().getStartTime());
        availabilitySlot.setId(UUID.randomUUID().toString());
        return List.of(availabilitySlot);
    }
    public static AvailabilitySlotResponse mapToAvailabilitySlotResponse(String message, JobSeeker jobSeeker){
        AvailabilitySlotResponse response = new AvailabilitySlotResponse();
        response.setJobSeeker(jobSeeker);
        response.setMessage(message);
        return response;
    }
    public static BankAccount mapToBankAccount(String jobSeekerId, BankAccountRequest request){
        BankAccount account = new BankAccount();
        account.setJobSeekerId(jobSeekerId);
        account.setAccountName(request.getAccountName());
        account.setId(UUID.randomUUID().toString());
        account.setBankName(request.getBankName());
        account.setAccountNumber(request.getAccountNumber());
        return account;
    }
    public static BankAccountResponse mapToBankAccountResponse(String message, JobSeeker jobSeeker){
        BankAccountResponse response = new BankAccountResponse();
        response.setJobSeeker(jobSeeker);
        response.setMessage(message);
        return response;
    }
    public static VerificationDocument mapToVerificationDocument(String jobSeekerId, VerificationDocumentRequest request){
        VerificationDocument verificationDocument = new VerificationDocument();
        verificationDocument.setId(UUID.randomUUID().toString());
        verificationDocument.setDocumentUrl(request.getDocumentUrl());
        verificationDocument.setType(request.getType());
        verificationDocument.setStatus(request.getStatus());
        verificationDocument.setJobSeekerId(jobSeekerId);
        return verificationDocument;
    }
    public static VerificationDocumentResponse mapToVerificationDocumentResponse(String message, List<VerificationDocument> document){
        VerificationDocumentResponse response = new VerificationDocumentResponse();
        response.setDocument(document);
        response.setMessage(message);
        return response;
    }
    public static WorkImageResponse mapToWorkImageResponse
}
