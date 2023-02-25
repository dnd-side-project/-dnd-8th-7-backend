package com.dnd8th.api;

import com.dnd8th.dto.task.TaskCreateRequest;
import com.dnd8th.dto.task.TaskCreateResponse;
import com.dnd8th.dto.task.TaskUpdateRequest;
import com.dnd8th.entity.Task;
import javax.validation.Valid;

import com.dnd8th.service.TaskService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("/api/task")
@RequiredArgsConstructor
public class TaskApi {

    private final TaskService taskService;

    @PostMapping("/{blockId}")
    public ResponseEntity<TaskCreateResponse> createTask(
            @RequestBody @Valid TaskCreateRequest taskCreateRequest,
            @PathVariable("blockId") Long blockId) {
        Task newTask = taskService.createTask(taskCreateRequest, blockId);
        TaskCreateResponse taskCreateResponse = new TaskCreateResponse(newTask.getId(), newTask.getContents());
        return ResponseEntity.status(HttpStatus.CREATED).body(taskCreateResponse);
    }

    @DeleteMapping("/{taskId}")
    public ResponseEntity<String> deleteTask(
            @PathVariable("taskId") Long taskId
            ) {
        taskService.deleteTask(taskId);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).body("");
    }

    @PatchMapping("/{taskId}")
    public ResponseEntity<String> updateTask(
            @RequestBody @Valid TaskUpdateRequest taskUpdateRequest,
            @PathVariable("taskId") Long taskId
    ) {
        taskService.updateTask(taskId, taskUpdateRequest.getContent());
        return ResponseEntity.status(HttpStatus.OK).body("");
    }

    @PatchMapping("/status/{taskId}")
    public ResponseEntity<String> updateStatus(
            @PathVariable("taskId") Long taskId
    ) {
        taskService.toggleStatus(taskId);
        return ResponseEntity.status(HttpStatus.OK).body("");
    }
}
