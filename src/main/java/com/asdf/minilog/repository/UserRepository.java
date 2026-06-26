package com.asdf.minilog.repository;

import com.asdf.minilog.entity.User;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

// User 엔티티를 대상으로 DB 작업을 하는 Repository
@Repository
public interface UserRepository extends JpaRepository<User, Long> { // User: 엔티티 타입, Long: PK 타입(id)
    // 기본 생성 CRUD 이외의 username 기반 조회 메서드 선언
    Optional<User> findByUsername(String username);
}
