package com.dnd8th.entity;

import javax.persistence.*;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;


@Entity
@Getter
@Table(name = "users")
@NoArgsConstructor
public class User extends BaseEntity{

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    @Column(name = "email", unique = true, nullable = false)
    private String email;

    @Column(name = "nickname", nullable = false)
    private String nickname;

    @Enumerated(EnumType.STRING)
    @Column(name = "roles", nullable = false)
    private Role role;

    @Column(name = "introduction")
    private String introduction;

    @Column(name = "user_lock")
    private String userLock;

    @Column(name = "img_path")
    private String imagePath;

    @OneToMany(mappedBy = "user")
    private List<Block> blocks = new ArrayList<>();
}
