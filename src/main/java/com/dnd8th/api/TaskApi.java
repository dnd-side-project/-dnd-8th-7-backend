package com.dnd8th.api;

import com.dnd8th.dto.task.TaskCreateRequest;
import com.dnd8th.dto.task.TaskCreateResponse;
import com.dnd8th.dto.task.TaskUpdateRequest;
import com.dnd8th.entity.Task;
import com.dnd8th.service.TaskService;
import javax.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
        TaskCreateResponse taskCreateResponse = new TaskCreateResponse(newTask.getId(),
                newTask.getContents());
        return ResponseEntity.status(HttpStatus.CREATED).body(taskCreateResponse);
    }

    @DeleteMapping("/{taskId}")
    public ResponseEntity<String> deleteTask(
            @AuthenticationPrincipal UserDetails userDetails,
            @PathVariable("taskId") Long taskId
    ) {
        String email = userDetails.getUsername();
        taskService.deleteTask(email, taskId);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).body("");
    }

    @PatchMapping("/{taskId}")
    public ResponseEntity<String> updateTask(
            @AuthenticationPrincipal UserDetails userDetails,
            @RequestBody @Valid TaskUpdateRequest taskUpdateRequest,
            @PathVariable("taskId") Long taskId
    ) {
        String email = userDetails.getUsername();
        taskService.updateTask(email, taskId, taskUpdateRequest.getContent());
        return ResponseEntity.status(HttpStatus.OK).body("");
    }

    @PatchMapping("/status/{taskId}")
    public ResponseEntity<String> updateStatus(
            @AuthenticationPrincipal UserDetails userDetails,
            @PathVariable("taskId") Long taskId
    ) {
        String email = userDetails.getUsername();
        taskService.toggleStatus(email, taskId);
        return ResponseEntity.status(HttpStatus.OK).body("");
    }
}
