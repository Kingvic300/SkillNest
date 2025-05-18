package com.skillnest.userservice.data.model;

import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

@EqualsAndHashCode(callSuper = true)
@Document
@Data
public class Employer extends User {

    private String companyName;
    private String companyDescription;
//    private List<String> postedJobIds;
    private String walletId;
}
