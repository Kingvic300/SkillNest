package com.skillnest.jobservice.service;

import com.skillnest.jobservice.data.enums.JobStatus;
import com.skillnest.jobservice.data.model.Job;
import com.skillnest.jobservice.data.repository.JobRepository;
import com.skillnest.jobservice.dtos.request.*;
import com.skillnest.jobservice.dtos.response.JobResponse;
import com.skillnest.jobservice.exception.EmployerNotFoundException;
import com.skillnest.jobservice.exception.JobNotFoundException;
import com.skillnest.jobservice.exception.JobNotOpenException;
import com.skillnest.jobservice.exception.JobSeekerNotFoundException;
import com.skillnest.jobservice.mapper.JobMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class JobServiceImpl implements JobService {

    private final JobRepository jobRepository;

    @Override
    public JobResponse postJobs(JobRequest jobRequest){
        Job job = JobMapper.mapToJob(jobRequest);
        job.setStatus(JobStatus.OPEN);
        job.setPostedDate(LocalDateTime.now());
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
    @Override
    public JobResponse getJobById(String id){
        Optional<Job> job = jobRepository.findById(id);
        if(job.isPresent()){
            return JobMapper.mapToJobResponse("Job retrieved successfully",job.get());
        }
        throw new JobNotFoundException("Job not found");
    }
    @Override
    public JobResponse takeJob(TakeJobRequest jobRequest){
        Optional<Job> job = jobRepository.findById(jobRequest.getJobId());
        if(job.isEmpty()){
            throw new JobNotFoundException("Job not found");
        }
        if (job.get().getStatus() != JobStatus.OPEN){
            throw new JobNotOpenException("Job not open");
        }
        job.get().setJobSeekerId(jobRequest.getJobSeekerId());
        job.get().setStatus(JobStatus.TAKEN);
        job.get().setLastUpdatedDate(LocalDateTime.now());

        jobRepository.save(job.get());
        return new JobResponse("Job taken successfully",job.get());
    }
    @Override
    public JobResponse updateJob(UpdateJobRequest jobRequest){
        Optional<Job> job = jobRepository.findById(jobRequest.getJobId());
        if(job.isEmpty()){
            throw new JobNotFoundException("Job not found");
        }
        if (job.get().getStatus() != JobStatus.OPEN){
            throw new JobNotOpenException("Job not open");
        }
        JobMapper.mapToUpdateJobRequest(job.get(),jobRequest);
        jobRepository.save(job.get());
        return new JobResponse("Job updated successfully",job.get());
    }
    @Override
    public JobResponse deleteJob(String id){
        Optional<Job> job = jobRepository.findById(id);
        if(job.isEmpty()){
            throw new JobNotFoundException("Job not found");
        }
        jobRepository.delete(job.get());
        return new JobResponse("Job deleted successfully",null);
    }
    @Override
    public JobResponse changeJobStatus(ChangeJobStatusRequest jobRequest){
        Optional<Job> job = jobRepository.findById(jobRequest.getJobId());
        if(job.isEmpty()){
            throw new JobNotFoundException("Job not found");
        }
        job.get().setStatus(jobRequest.getStatus());
        job.get().setLastUpdatedDate(LocalDateTime.now());
        jobRepository.save(job.get());
        return new JobResponse("Job updated successfully",job.get());
    }
    @Override
    public JobResponse negotiatedPayment(NegotiatedPaymentRequest jobRequest){
        Optional<Job> job = jobRepository.findById(jobRequest.getJobId());
        if(job.isEmpty()){
            throw new JobNotFoundException("Job not found");
        }
        job.get().setNegotiatedPayment(jobRequest.getNegotiatedPaymentAmount());
        job.get().setLastUpdatedDate(LocalDateTime.now());
        jobRepository.save(job.get());
        return new JobResponse("Payment has been set",job.get());
    }
//    @Override
//    public JobResponse verifyCompletion(VerifyCompleteJobRequest jobRequest){
//        Optional<Job> job = jobRepository.findById(jobRequest.getJobId());
//        if(job.isEmpty()){
//            throw new JobNotFoundException("Job not found");
//        }
//        if(jobRequest.isJobSeeker()){
//            if(job.get().getJobSeekerId().isEmpty()){
//                throw new JobSeekerNotFoundException("Job seekerId is empty");
//            }
//            if (!job.get().getJobSeekerId().equals(jobRequest.getUserId())){
//                throw new JobSeekerNotFoundException("Job seekerId is empty");
//            }
//            job.get().setJobSeekerVerifiedCompletion(true);
//            if(job.get().getStatus() == JobStatus.IN_PROGRESS || job.get().getStatus() == JobStatus.TAKEN){
//                job.get().setStatus(JobStatus.COMPLETED_BY_JOB_SEEKER);
//            }
//        }else{
//            if(job.get().getEmployerId().isEmpty()){
//                throw new EmployerNotFoundException("employerId is empty");
//            }
//            if (!job.get().getEmployerId().equals(jobRequest.getUserId())){
//                throw new EmployerNotFoundException("employerId is empty");
//            }
//            job.get().setEmployerVerifiedCompletion(true);
//            if(job.get().getStatus() == JobStatus.IN_PROGRESS || job.get().getStatus() == JobStatus.TAKEN){
//                job.get().setStatus(JobStatus.COMPLETED_BY_EMPLOYER);
//            }
//        }
//        job.get().setLastUpdatedDate(LocalDateTime.now());
//        if (job.get().isJobSeekerVerifiedCompletion() && job.get().isEmployerVerifiedCompletion()) {
//            job.get().setStatus(JobStatus.VERIFIED_COMPLETION);
//        }
//
//
//    }
//    @Override
//    public JobResponse completeJob(CompleteJobRequest jobRequest){
//
//    }


}
