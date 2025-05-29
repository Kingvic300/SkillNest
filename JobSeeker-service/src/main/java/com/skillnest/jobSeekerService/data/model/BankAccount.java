package com.skillnest.jobSeekerService.data.model;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@Document("bank_account")
public class BankAccount {
    @Id
    private String id;
    private String jobSeekerId;
    private String accountNumber;
    private String bankName;
    private String accountName;
}
