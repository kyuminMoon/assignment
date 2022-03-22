package com.kmmoon.assignment.repository;

import com.kmmoon.assignment.entity.User;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {

	// Security Authorization 체크용
	Optional<User> findByIdAndAccountTypeAndQuit(long id, User.AccountType accountType, boolean quit);

	// 커서기반 페이징을 위한 쿼리
	List<User> findAllByOrderByIdDesc(Pageable pageable);

	List<User> findByIdLessThanOrderByIdDesc(Long cursorId, Pageable pageable);

	Boolean existsByIdLessThan(Long cursorId);

}
