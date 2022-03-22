package com.kmmoon.assignment.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@IdClass(PostLikeId.class)
@Entity
public class PostLike {
    @Id
    @ManyToOne(targetEntity = Post.class)
    @JoinColumn(name = "post_id", updatable = false)
    private Post post;

    @Id
    @ManyToOne(targetEntity = User.class)
    @JoinColumn(name = "user_id", updatable = false)
    private User user;
}
