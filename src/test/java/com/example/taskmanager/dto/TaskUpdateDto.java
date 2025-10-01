package com.example.taskmanager.dto;

import com.example.taskmanager.model.TaskStatus;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class TaskUpdateDto {
    private String title;
    private String description;
    private TaskStatus status;
    private Long assigneeId;
}
