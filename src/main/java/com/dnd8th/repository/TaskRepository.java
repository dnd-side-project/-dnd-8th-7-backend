package com.dnd8th.repository;

import com.dnd8th.entity.Task;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TaskRepository extends JpaRepository<Task, Long> {
    List<Task> findByBlock_Id(long id);
}
