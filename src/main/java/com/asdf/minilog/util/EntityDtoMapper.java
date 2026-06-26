package com.asdf.minilog.util;

import com.asdf.minilog.dto.ArticleResponseDto;
import com.asdf.minilog.dto.FollowResponseDto;
import com.asdf.minilog.dto.UserResponseDto;
import com.asdf.minilog.entity.Article;
import com.asdf.minilog.entity.Follow;
import com.asdf.minilog.entity.User;

public class EntityDtoMapper {
    // Article Entity -> ArticleResponseDto 매퍼
    public static ArticleResponseDto toDto(Article article) {
        return ArticleResponseDto.builder()
                .articleId(article.getId()) // 게시글 ID
                .content(article.getContent()) // 게시글 본문
                .authorId(article.getAuthor().getId()) // 작성자 ID
                .authorName(article.getAuthor().getUsername()) // 작성자 이름
                .createdAt(article.getCreatedAt()) // 게시글 생성 시
                .build();
    }

    // Follow Entity -> FollowResponseDto 매퍼
    public static FollowResponseDto toDto(Follow follow) {
        return FollowResponseDto.builder()
                .followerId(follow.getFollower().getId()) // 팔로워 ID
                .followeeId(follow.getFollowee().getId()) // 팔로이 ID
                .build();
    }

    // User Entity -> UserResponseDto 매퍼
    public static UserResponseDto toDto(User user) {
        return UserResponseDto.builder()
                .id(user.getId()) // 유저 ID
                .username(user.getUsername()) // 유저 이래
                .build();
    }

    // FollowRequestDto -> Follow Entity 매퍼
    public static Follow toEntity(Long followerId, Long followeeId) {
        return Follow.builder()
                .follower(User.builder().id(followerId).build())
                .followee(User.builder().id(followeeId).build())
                .build();
    }
}
