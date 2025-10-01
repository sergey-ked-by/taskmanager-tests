package com.example.taskmanager.dto;

import com.example.taskmanager.model.TaskStatus;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class TaskDto {
    private Long id;
    private String title;
    private String description;
    private TaskStatus status;
    private Long authorId;
    private Long assigneeId;
}
