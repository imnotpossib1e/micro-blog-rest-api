package com.asdf.minilog.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;

@Getter
@Setter
@Builder
public class UserResponseDto {
    @NonNull private Long id; // 유저 ID
    @NonNull private String username; // 유저 이름
}
