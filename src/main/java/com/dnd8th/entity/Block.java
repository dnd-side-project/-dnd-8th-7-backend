package com.dnd8th.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Date;

@Entity
@Getter
@Table(name = "block")
@NoArgsConstructor
public class Block {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    @Column(name = "user_id", nullable = false)
    private long userId;

    @Column(name = "block_lock")
    private Boolean blockLock;

    @Column(name = "save")
    private Boolean save;

    @Column(name = "title")
    private String title;

    @Column(name = "block_color")
    private String blockColor;

    @Column(name = "date")
    private Date date;

    @Column(name = "emotion")
    private String emotion;
}
