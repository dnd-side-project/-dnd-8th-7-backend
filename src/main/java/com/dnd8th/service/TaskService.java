package com.dnd8th.service;


import com.dnd8th.dao.TaskUpdateDao;
import com.dnd8th.dto.task.TaskCreateRequest;
import com.dnd8th.entity.Block;
import com.dnd8th.entity.Task;
import com.dnd8th.error.exception.block.BlockNotFoundException;
import com.dnd8th.error.exception.task.TaskNotFoundException;
import com.dnd8th.repository.BlockRepository;
import com.dnd8th.repository.TaskRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class TaskService {

    private final TaskRepository taskRepository;
    private final BlockRepository blockRepository;
    private final TaskUpdateDao taskUpdateDao;

    public Task createTask(TaskCreateRequest taskCreateRequest, Long blockId) {
        Block block = blockRepository.findById(blockId).orElseThrow(BlockNotFoundException::new);

        return taskRepository.save(taskCreateRequest.toEntity(block));
    }

    public void deleteTask(Long taskId) {
        Task task = taskRepository.findById(taskId).orElseThrow(TaskNotFoundException::new);
        taskRepository.delete(task);
    }

    public void updateTask(Long taskId, String content) {
        taskUpdateDao.updateContent(taskId, content);
    }

    public void toggleStatus(Long taskId) {
        taskUpdateDao.updateStatus(taskId);
    }
}
