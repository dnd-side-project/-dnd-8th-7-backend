package com.dnd8th.entity;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.persistence.*;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

@Entity
@Getter
@Table(name = "block")
@NoArgsConstructor
public class Block {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    @Column(name = "block_lock")
    private Boolean blockLock;

    @Column(name = "save")
    private Boolean save;

    @Column(name = "title")
    private String title;

    @Column(name = "block_color")
    private String blockColor;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @Column(name = "datetime")
    private Date date;

    @Column(name = "emotion")
    private String emotion;

    @OneToMany(mappedBy = "block", cascade = CascadeType.REMOVE)
    private List<Task> tasks = new ArrayList<>();

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_email")
    private User user;

    @Builder
    public Block(Boolean blockLock, Boolean save, String title, String blockColor, Date date,
            String emotion, User user) {
        this.blockLock = blockLock;
        this.save = save;
        this.title = title;
        this.blockColor = blockColor;
        this.date = date;
        this.emotion = emotion;
        this.user = user;
    }
}
