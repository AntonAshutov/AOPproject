package com.example.aopproject.dto;

import lombok.Data;

@Data
public class GeneralMethodInfoDto {
    private String methodName;
    private Integer callQuantity;
    private Double avgRunningTime;
}
