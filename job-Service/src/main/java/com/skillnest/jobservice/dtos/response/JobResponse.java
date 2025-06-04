package com.skillnest.jobservice.dtos.response;

import com.skillnest.jobservice.data.model.Job;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class JobResponse {
   private String message;
   private Job job;

}
