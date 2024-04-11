package com.example.aopproject.api;

import com.example.aopproject.dto.DetailedInfoForEveryMethodDto;
import com.example.aopproject.dto.DetailedMethodInfoDto;
import com.example.aopproject.dto.GeneralInfoForEveryMethodDto;
import com.example.aopproject.dto.MethodNames;
import com.example.aopproject.service.MethodExecutionTimeService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/v1/stats/")
@RequiredArgsConstructor
public class StatsController {

    private final MethodExecutionTimeService methodExecutionTimeService;

    @GetMapping()
    public ResponseEntity<GeneralInfoForEveryMethodDto> get() {
        return ResponseEntity.ok(methodExecutionTimeService.getGeneralInfo());
    }

    @GetMapping("/methodNames")
    public ResponseEntity<MethodNames> getMethodNames() {
        return ResponseEntity.ok(methodExecutionTimeService.getMethodNames());
    }

    @GetMapping("/detailed")
    public ResponseEntity<DetailedInfoForEveryMethodDto> getDetailedStats() {
        return ResponseEntity.ok(methodExecutionTimeService.getDetailedStats());
    }

    @GetMapping("/detailed/{methodName}")
    public ResponseEntity getMethodNames(@PathVariable String methodName) {
        DetailedMethodInfoDto response = methodExecutionTimeService.getDetailedMethodStats(methodName);
        if (response != null) {
            return ResponseEntity.ok(response);
        }
        return ResponseEntity.badRequest().body("method stats not found");
    }
}
