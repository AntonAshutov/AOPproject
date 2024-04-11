package com.example.aopproject.repository;

import com.example.aopproject.entity.MethodExecutionTime;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MethodExecutionTimeRepository extends JpaRepository<MethodExecutionTime, Long> {

    @Query("SELECT methodName, COUNT(*), AVG(executionTime) FROM MethodExecutionTime GROUP BY methodName")
    List<Object[]> findGeneralMethodStatistics();

    @Query("SELECT DISTINCT methodName FROM MethodExecutionTime")
    List<String> findDistinctMethodNames();

    @Query("SELECT methodName, COUNT(*), MIN(executionTime), MAX(executionTime), AVG(executionTime)" +
            " FROM MethodExecutionTime GROUP BY methodName")
    List<Object[]> findAllDetailedMethodStatistics();

    @Query("SELECT executionTime FROM MethodExecutionTime WHERE methodName = :methodName")
    List<Object> findExecutionTimesByMethodName(@Param("methodName") String methodName);

    @Query("SELECT methodName, COUNT(*), MIN(executionTime), MAX(executionTime), AVG(executionTime)" +
            " FROM MethodExecutionTime WHERE methodName = :methodName GROUP BY methodName")
    Object[] findDetailedMethodStatistic(@Param("methodName") String methodName);
}
