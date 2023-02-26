package com.dnd8th.repository;

import com.dnd8th.entity.Keep;
import org.springframework.data.jpa.repository.JpaRepository;

public interface KeepRepository extends JpaRepository<Keep, Long> {
}
