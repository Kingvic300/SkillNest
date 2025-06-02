package com.skillnest.jobservice.mapper;


import com.skillnest.jobservice.data.model.Job;
import com.skillnest.jobservice.dtos.request.JobRequest;
import com.skillnest.jobservice.dtos.response.JobResponse;

import java.util.UUID;

public class JobMapper {
    public static Job mapToJob(JobRequest jobRequest){
        return Job.builder()
                .id(UUID.randomUUID().toString())
                .jobType(jobRequest.getJobType())
                .deadline(jobRequest.getDeadline())
                .title(jobRequest.getTitle())
                .employerId(jobRequest.getEmployerId())
                .description(jobRequest.getDescription())
                .contactInfo(jobRequest.getContactInfo())
                .postedDate(jobRequest.getPostedDate())
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
}
