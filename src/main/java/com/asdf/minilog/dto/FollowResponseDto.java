package com.asdf.minilog.dto;

import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;

@Getter
@Setter
public class FollowResponseDto {
    @NonNull private Long followerId;
    @NonNull private Long followeeId;
}
