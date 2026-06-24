package com.asdf.minilog.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class ArticleRequestDto {
    @NotBlank private String content;
    @NotNull private Long authorId;
}
