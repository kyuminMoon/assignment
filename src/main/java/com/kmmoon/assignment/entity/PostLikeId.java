package com.kmmoon.assignment.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PostLikeId implements Serializable {
    private Long post;
    private Long user;
}
