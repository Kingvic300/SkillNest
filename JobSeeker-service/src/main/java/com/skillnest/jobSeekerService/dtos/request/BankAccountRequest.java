package com.skillnest.jobSeekerService.dtos.request;

import lombok.Data;

@Data
public class BankAccountRequest {
    private String jobSeekerId;
    private String accountNumber;
    private String bankName;
    private String accountName;
}
