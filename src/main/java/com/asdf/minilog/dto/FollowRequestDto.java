package com.asdf.minilog.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class FollowRequestDto {
    @NotNull private Long followerId;
    @NotNull private Long followeeId;
}
