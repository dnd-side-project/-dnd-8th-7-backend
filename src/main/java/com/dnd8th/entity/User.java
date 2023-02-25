package com.dnd8th.entity;

import java.util.ArrayList;
import java.util.List;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;


@Entity
@Getter
@Table(name = "users")
@NoArgsConstructor
public class User{

    @Id
    @Column(name = "email", unique = true, nullable = false)
    private String email;

    @Column(name = "nickname", nullable = false)
    private String name;

    @Enumerated(EnumType.STRING)
    @Column(name = "roles", nullable = false)
    private Role role;

    @OneToMany(mappedBy = "user")
    private List<Block> blocks = new ArrayList<>();

    @OneToMany(mappedBy = "user")
    private List<Review> reviews = new ArrayList<>();

    @Column(name = "introduction")
    private String introduction;

    @Column(name = "user_lock")
    private Boolean userLock;

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
