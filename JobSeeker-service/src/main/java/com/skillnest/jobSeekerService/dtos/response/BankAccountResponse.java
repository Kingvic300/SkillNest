package com.skillnest.jobSeekerService.dtos.response;

import com.skillnest.jobSeekerService.data.model.JobSeeker;
import lombok.Data;

@Data
public class BankAccountResponse {
    private String message;
    private JobSeeker jobSeeker;
}
