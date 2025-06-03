package com.skillnest.jobservice.service;


import com.skillnest.jobservice.dtos.request.*;
import com.skillnest.jobservice.dtos.response.JobResponse;

import java.util.List;

public interface JobService {

    JobResponse postJobs(JobRequest jobRequest);

    List<JobResponse> getAllJobs();

    JobResponse getJobById(String id);

    JobResponse takeJob(TakeJobRequest jobRequest);

    JobResponse updateJob(UpdateJobRequest jobRequest);

    JobResponse deleteJob(String id);

    JobResponse changeJobStatus(ChangeJobStatusRequest jobRequest);

    JobResponse negotiatedPayment(NegotiatedPaymentRequest jobRequest);
}
