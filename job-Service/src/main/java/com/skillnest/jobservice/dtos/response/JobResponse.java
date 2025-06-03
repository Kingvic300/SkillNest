package com.skillnest.jobservice.dtos.response;

import com.skillnest.jobservice.data.enums.JobStatus;
import com.skillnest.jobservice.data.model.Job;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.function.Function;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class JobResponse {
   private String message;
   private Job job;
}
