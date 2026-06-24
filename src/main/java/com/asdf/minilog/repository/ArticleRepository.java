package com.asdf.minilog.repository;

import com.asdf.minilog.entity.Article;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ArticleRepository extends JpaRepository<Article, Long> {
    // 자동 쿼리 생성으로 특정 사용자가 작성한 모든 게시글 조회
    List<Article> findAllByAuthorId(Long authorId);

    // 커스텀 JPQL
    // 내가 팔로우한 사람들의 게시글을 최신순으로 조회
    @Query(
            "SELECT a FROM Article a JOIN a.author u JOIN Follow f"
            + " ON u.id = f.followee.id WHERE" // 그 사람들이 작성한 글 찾기
            + " f.follower.id = :authorId ORDER BY a.createdAt DESC") // follow 테이블에서 내가 팔로우한 사람 찾기, 최신순 정렬
    List<Article> findAllByFollowerId(@Param("authorId") Long authorId);
}
