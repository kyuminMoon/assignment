package com.kmmoon.assignment.repository;

import com.kmmoon.assignment.entity.PostLike;
import com.kmmoon.assignment.entity.PostLikeId;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PostLikeRepository extends JpaRepository<PostLike, PostLikeId> {
    @EntityGraph(attributePaths = {"post"})
    List<PostLike> findByUserId(Long userId);
}
