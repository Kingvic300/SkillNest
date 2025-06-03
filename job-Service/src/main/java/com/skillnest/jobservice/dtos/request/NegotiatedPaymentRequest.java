package com.skillnest.jobservice.dtos.request;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class NegotiatedPaymentRequest {
    private String jobId;
    private BigDecimal negotiatedPaymentAmount;
}
