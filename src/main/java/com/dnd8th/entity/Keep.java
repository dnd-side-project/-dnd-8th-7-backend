package com.dnd8th.entity;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Getter
@Table(name = "keep")
@NoArgsConstructor
public class Keep {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_email")
    private User user;

    @OneToOne
    @JoinColumn(name = "block_id")
    private Block block;

    @Column(name = "title")
    private String title;

    @Column(name = "block_color")
    private String blockColor;

    @Column(name = "emotion")
    private String emotion;

    @Column(name = "sum_of_task")
    private Integer sumOfTask;

    @Builder
    public Keep(User user, Block block, String title, String blockColor, String emotion, Integer sumOfTask) {
        this.user = user;
        this.block = block;
        this.title = title;
        this.blockColor = blockColor;
        this.emotion = emotion;
        this.sumOfTask = sumOfTask;
    }
}
