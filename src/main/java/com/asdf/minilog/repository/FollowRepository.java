package com.asdf.minilog.repository;

import com.asdf.minilog.entity.Follow;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

// 누가 누구를 팔로우하는지 조회/검사
@Repository
public interface FollowRepository extends JpaRepository<Follow, Long> {
    // 특정 사용자가 팔로우한 목록 조회
    List<Follow> findByFollowerId(Long followerId);

    // A가 B를 팔로우하고 있는지 확인
    Optional<Follow> findByFollowerIdAndFolloweeId(Long followerId, Long followeeId);
}
