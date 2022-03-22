package com.kmmoon.assignment.repository;

import com.kmmoon.assignment.entity.Post;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PostRepository extends JpaRepository<Post, Long> {

	// 커서기반 페이징을 위한 쿼리
	@EntityGraph(attributePaths = {"user"})
	List<Post> findAllByActiveOrderByIdDesc(boolean active, Pageable pageable);

	@EntityGraph(attributePaths = {"user"})
	List<Post> findByIdLessThanAndActiveOrderByIdDesc(Long cursorId, boolean active, Pageable pageable);

	Boolean existsByIdLessThanAndActive(Long cursorId, boolean active);

}
