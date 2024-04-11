package com.example.aopproject.dto;

import lombok.Data;

import java.util.List;

@Data
public class DetailedInfoForEveryMethodDto {
    private List<DetailedMethodInfoDto> info;
}
