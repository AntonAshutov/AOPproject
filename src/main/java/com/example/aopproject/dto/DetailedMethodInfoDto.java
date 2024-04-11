package com.example.aopproject.dto;

import lombok.Data;

@Data
public class DetailedMethodInfoDto {
    private String methodName;
    private Integer callQuantity;
    private Double minRunningTime;
    private Double maxRunningTime;
    private Double avgRunningTime;
    private Double medianRunningTime;
}
