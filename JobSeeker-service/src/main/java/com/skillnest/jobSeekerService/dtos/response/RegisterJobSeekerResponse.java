package com.skillnest.jobSeekerService.dtos.response;

import com.skillnest.jobSeekerService.data.model.JobSeeker;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RegisterJobSeekerResponse {
    private String message;
    private JobSeeker jobSeeker;
}
