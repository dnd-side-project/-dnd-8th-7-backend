package com.dnd8th.entity;

import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

@Entity
@Getter
@Table(name = "review")
@NoArgsConstructor
public class Review extends BaseEntity {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_email")
    private User user;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @Column(name = "datetime")
    private Date date;

    @Column(name = "retrospection")
    private String retrospection;

    @Column(name = "retrospection_lock")
    private Boolean retrospectionLock;

    @Column(name = "emotion")
    private String emotion;

    @Builder
    public Review(User user, Date date, String retrospection, Boolean retrospectionLock,
            String emotion) {
        this.user = user;
        this.date = date;
        this.retrospection = retrospection;
        this.retrospectionLock = retrospectionLock;
        this.emotion = emotion;
    }
}
