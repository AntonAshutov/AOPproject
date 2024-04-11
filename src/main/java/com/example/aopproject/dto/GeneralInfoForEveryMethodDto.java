package com.example.aopproject.dto;

import lombok.Data;

import java.util.List;

@Data
public class GeneralInfoForEveryMethodDto {
    private List<GeneralMethodInfoDto> info;
}
