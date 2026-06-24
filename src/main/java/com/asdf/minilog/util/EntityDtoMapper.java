package com.asdf.minilog.util;

import com.asdf.minilog.dto.ArticleResponseDto;
import com.asdf.minilog.dto.FollowResponseDto;
import com.asdf.minilog.entity.Article;
import com.asdf.minilog.entity.Follow;

public class EntityDtoMapper {
    // Artcle Entity -> ArticleResponseDto 매퍼
    public static ArticleResponseDto toDto(Article article){
        return ArticleResponseDto.builder()
                .articleId(article.getId()) // 게시글 ID
                .content(article.getContent()) // 게시글 본문
                .authorId(article.getAuthor().getId()) // 작성자 ID
                .authorName(article.getAuthor().getUsername()) // 작성자 이름
                .createdAt(article.getCreatedAt()) // 게시글 생성 시
                .build();
    }

    // Follow Entity -> FollowResponseDTo 매퍼
    public static FollowResponseDto toDto(Follow follow) {
        return FollowResponseDto.builder()
                .followerId(follow.getFollower().getId()) // 팔로워 ID
                .followeeId(follow.getFollowee().getId()) // 팔로이 ID
                .build();
    }
}
