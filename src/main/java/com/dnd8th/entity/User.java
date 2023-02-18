package com.dnd8th.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;


@Entity
@Getter
@Table(name = "member")
@NoArgsConstructor
public class User extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    @Column(name = "email", unique = true, nullable = false)
    private String email;

    @Column(name = "nickname", nullable = false)
    private String name;

    @Enumerated(EnumType.STRING)
    @Column(name = "role", nullable = false)
    private Role role;

    @Column(name = "introduction")
    private String introduction;

    @Column(name = "user_lock")
    private String userLock;

    @Column(name = "img_path")
    private String imagePath;

    @Builder
    public User(String email, String name, Role role, String imagePath) {
        this.email = email;
        this.name = name;
        this.role = role;
        this.imagePath = imagePath;
    }
}
