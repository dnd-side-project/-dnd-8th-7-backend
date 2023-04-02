package com.dnd8th.repository;

import static org.assertj.core.api.Assertions.assertThat;

import com.dnd8th.dto.block.BlockCreateRequest;
import com.dnd8th.dto.task.TaskCreateRequest;
import com.dnd8th.entity.Block;
import com.dnd8th.entity.Task;
import com.dnd8th.entity.User;
import com.dnd8th.error.exception.task.TaskNotFoundException;
import com.dnd8th.error.exception.user.UserNotFoundException;
import java.util.Date;
import java.util.List;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class TaskRepositoryTest extends RepositoryTest {

    @Test
    @DisplayName("블록 아이디로 연관된 태스크들을 조회한다.")
    void findByBlockId() {
        // given
        User user = userRepository.findByEmail("test@gmail.com")
                .orElseThrow(UserNotFoundException::new);

        BlockCreateRequest blockCreateRequest = BlockCreateRequest.builder()
                .date("2023-03-01")
                .title("test")
                .emoji("😀")
                .backgroundColor("#ffffff")
                .isSecret(false)
                .build();

        Date date = dateParser.parseDate(blockCreateRequest.getDate());

        Block block = blockCreateRequest.toEntity(user, date);
        Block save = blockRepository.save(block);

        long blockId = save.getId();

        // when
        TaskCreateRequest taskCreateRequest = TaskCreateRequest.builder()
                .content("test")
                .build();
        taskRepository.save(taskCreateRequest.toEntity(block));

        TaskCreateRequest taskCreateRequest2 = TaskCreateRequest.builder()
                .content("test2")
                .build();
        taskRepository.save(taskCreateRequest2.toEntity(block));

        // then
        List<Task> byBlockId = taskRepository.findByBlock_Id(blockId);
        assertThat(byBlockId.size()).isEqualTo(2);
    }

    @Test
    @DisplayName("태스크를 정상적으로 생성하고 조회한다.")
    void createTask() {
        // given
        User user = userRepository.findByEmail("test@gmail.com")
                .orElseThrow(UserNotFoundException::new);

        BlockCreateRequest blockCreateRequest = BlockCreateRequest.builder()
                .date("2023-03-01")
                .title("test")
                .emoji("😀")
                .backgroundColor("#ffffff")
                .isSecret(false)
                .build();

        Date date = dateParser.parseDate(blockCreateRequest.getDate());

        Block block = blockCreateRequest.toEntity(user, date);
        blockRepository.save(block);

        TaskCreateRequest taskCreateRequest = TaskCreateRequest.builder()
                .content("test")
                .build();
        // when
        Task save = taskRepository.save(taskCreateRequest.toEntity(block));
        long id = save.getId();

        entityManager.flush();
        entityManager.clear();

        // then
        Task task = taskRepository.findById(id).orElseThrow(TaskNotFoundException::new);
        assertThat(task.getContents()).isEqualTo(taskCreateRequest.getContent());
    }

    @Test
    @DisplayName("태스크를 정상적으로 삭제한다.")
    void deleteTask() {
        // given
        User user = userRepository.findByEmail("test@gmail.com")
                .orElseThrow(UserNotFoundException::new);

        BlockCreateRequest blockCreateRequest = BlockCreateRequest.builder()
                .date("2023-03-01")
                .title("test")
                .emoji("😀")
                .backgroundColor("#ffffff")
                .isSecret(false)
                .build();

        Date date = dateParser.parseDate(blockCreateRequest.getDate());

        Block block = blockCreateRequest.toEntity(user, date);
        blockRepository.save(block);

        TaskCreateRequest taskCreateRequest = TaskCreateRequest.builder()
                .content("test")
                .build();
        // when
        Task save = taskRepository.save(taskCreateRequest.toEntity(block));
        long id = save.getId();

        entityManager.flush();
        entityManager.clear();

        taskRepository.deleteById(id);

        // then
        Assertions.assertThatThrownBy(
                        () -> taskRepository.findById(id).orElseThrow(TaskNotFoundException::new))
                .isInstanceOf(TaskNotFoundException.class);
    }
}
