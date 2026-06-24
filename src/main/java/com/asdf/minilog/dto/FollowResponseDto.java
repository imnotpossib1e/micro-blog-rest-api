package com.asdf.minilog.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;

@Getter
@Setter
@Builder
public class FollowResponseDto {
    @NonNull private Long followerId; // 팔로워 ID
    @NonNull private Long followeeId; // 팔로이 ID
}
