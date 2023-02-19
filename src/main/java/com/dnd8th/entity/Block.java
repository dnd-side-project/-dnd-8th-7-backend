package com.dnd8th.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Getter
@Table(name = "block")
@NoArgsConstructor
public class Block {

    @Id
    @JsonIgnore
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

    @OneToMany(mappedBy = "block")
    private List<Task> tasks = new ArrayList<>();

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_email")
    private User user;
}
