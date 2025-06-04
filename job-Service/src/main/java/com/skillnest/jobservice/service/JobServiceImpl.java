package com.skillnest.jobservice.service;


import com.skillnest.jobservice.data.model.Job;
import com.skillnest.jobservice.data.repository.JobRepository;
import com.skillnest.jobservice.dtos.request.JobRequest;
import com.skillnest.jobservice.dtos.response.JobResponse;
import com.skillnest.jobservice.mapper.JobMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;


@Service
@RequiredArgsConstructor
public class JobServiceImpl implements JobService {

    private final JobRepository jobRepository;

    @Override
    public JobResponse postJobs(JobRequest jobRequest){
        Job job = JobMapper.mapToJob(jobRequest);
        jobRepository.save(job);
        return new JobResponse("Job Posted successfully",job);
    }
    @Override
    public List<JobResponse> getAllJobs(){
        List<Job> jobs = jobRepository.findAll();
        return jobs.stream()
                .map(job -> JobMapper.mapToJobResponse("All jobs retrieved successfully",job))
                .collect(Collectors.toList());
    }

}
