package com.kmmoon.assignment.entity;

import com.kmmoon.assignment.dto.PostDTO;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.Formula;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.time.LocalDateTime;

@Getter
@Builder
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Post {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(targetEntity = User.class, fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", updatable = false, nullable = false)
    private User user;

    @Column(nullable = false, length = 200)
    private String title;

    @Column(nullable = false, length = 20000)
    private String content;

    @Formula("(select count(*) from post_like pl where pl.post_id = id)")
    private int likeCount;

    @Column(nullable = false, name = "is_active")
    private boolean active = true;

    @CreationTimestamp
    private LocalDateTime createdAt;

    @UpdateTimestamp
    private LocalDateTime lastModifiedAt;

    private LocalDateTime deletedAt;

    public Post modify(PostDTO postDTO){
        this.title = postDTO.getTitle();
        this.content = postDTO.getContent();
        return this;
    }

    public Post delete(){
        this.active = false;
        this.deletedAt = LocalDateTime.now();
        return this;
    }

}

