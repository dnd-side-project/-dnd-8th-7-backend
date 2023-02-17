package com.dnd8th.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Getter
@Table(name = "task")
@NoArgsConstructor
public class Task {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    @Column(name = "block_id", nullable = false)
    private long blockId;

    @Column(name = "task")
    private String task;

    @Column(name = "status")
    private Boolean status;
}
