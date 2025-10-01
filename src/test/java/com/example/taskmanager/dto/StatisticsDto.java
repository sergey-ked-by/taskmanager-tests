package com.example.taskmanager.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class StatisticsDto {
    private long totalUsers;
    private long totalTasks;
}
