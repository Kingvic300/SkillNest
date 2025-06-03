package com.skillnest.jobservice.mapper;


import com.skillnest.jobservice.data.model.Job;
import com.skillnest.jobservice.dtos.request.JobRequest;
import com.skillnest.jobservice.dtos.request.UpdateJobRequest;
import com.skillnest.jobservice.dtos.response.JobResponse;

import java.time.LocalDateTime;
import java.util.UUID;

public class JobMapper {
    public static Job mapToJob(JobRequest jobRequest){
        return Job.builder()
                .id(UUID.randomUUID().toString())
                .jobType(jobRequest.getJobType())
                .deadline(jobRequest.getDeadline())
                .title(jobRequest.getTitle())
                .workImage(jobRequest.getWorkImage())
                .employerId(jobRequest.getEmployerId())
                .description(jobRequest.getDescription())
                .contactInfo(jobRequest.getContactInfo())
                .requiredSkills(jobRequest.getRequiredSkillIds())
                .proposedPayment(jobRequest.getProposedPayment())
                .location(jobRequest.getLocation())
                .build();
    }
    public static JobResponse mapToJobResponse(String message, Job job){
        JobResponse jobResponse = new JobResponse();
        jobResponse.setJob(job);
        jobResponse.setMessage(message);
        return jobResponse;
    }
    public static void mapToUpdateJobRequest(Job job, UpdateJobRequest jobRequest){
        job.setJobType(jobRequest.getJobType());
        job.setTitle(jobRequest.getTitle());
        job.setDescription(jobRequest.getDescription());
        job.setLocation(jobRequest.getLocation());
        job.setRequiredSkills(jobRequest.getRequiredSkills());
        job.setProposedPayment(jobRequest.getProposedPayment());
        job.setStatus(jobRequest.getStatus());
        job.setWorkImage(jobRequest.getWorkImage());
        job.setDeadline(jobRequest.getDeadline());
        job.setLastUpdatedDate(LocalDateTime.now());
        job.setContactInfo(jobRequest.getContactInfo());
    }
}
