package com.asdf.minilog.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@Builder
public class ArticleResponseDto {
    @NonNull private Long articleId; // 게시글 ID
    @NonNull private String content; // 게시글 본문
    @NonNull private Long authorId; // 작성자 ID
    @NonNull private String authorName; // 작성자 이름
    @NonNull private LocalDateTime createdAt; // 게시글 생성 시간
}
