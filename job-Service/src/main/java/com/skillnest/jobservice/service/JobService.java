package com.skillnest.jobservice.service;


import com.skillnest.jobservice.dtos.request.JobRequest;
import com.skillnest.jobservice.dtos.response.JobResponse;

import java.util.List;

public interface JobService {

    JobResponse postJobs(JobRequest jobRequest);

    List<JobResponse> getAllJobs();

}
