package com.asdf.minilog.controller;

import com.asdf.minilog.dto.FollowRequestDto;
import com.asdf.minilog.dto.FollowResponseDto;
import com.asdf.minilog.service.FollowService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/follow")
public class FollowController {
    private final FollowService followService;

    @Autowired
    public FollowController(FollowService followService) {
        this.followService = followService;
    }

    // 팔로우 생성
    @PostMapping // Post 요청
    @Operation(summary = "팔로우")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "성공"),
        @ApiResponse(responseCode = "404", description = "사용자 없음")
    })
    public ResponseEntity<FollowResponseDto> follow(
            @RequestBody FollowRequestDto request) { // 요청 데이터 받기
        // ID 추출
        Long followerId = request.getFollowerId();
        Long followeeId = request.getFolloweeId();

        // 서비스 호출
        FollowResponseDto follow = followService.follow(followerId, followeeId);
        // 응답 반환
        return ResponseEntity.ok(follow);
    }

    // 언팔로우
    @DeleteMapping("/{followerId}/{followeeId}")
    @Operation(summary = "언팔로우")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "성공"),
        @ApiResponse(responseCode = "200", description = "사용자 없음")
    })
    public ResponseEntity<Void> unfollow(
            @PathVariable Long followerId, @PathVariable Long followeeId) { // 팔로워, 팔로이 id 받기
        // 서비스 호출
        followService.unfollow(followerId, followeeId);
        // 응답 반환
        return ResponseEntity.ok().build();
    }

    // 특정 사용자의 팔로잉 목록 조회
    @GetMapping("/{followerId}")
    @Operation(summary = "팔로잉 목록 조회")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "성공"),
        @ApiResponse(responseCode = "404", description = "사용자 없음")
    })
    public ResponseEntity<List<FollowResponseDto>> getFollowList(
            @PathVariable Long followerId) { // URL에서 id 추출
        // 서비스 호출
        List<FollowResponseDto> follows = followService.getFollowList(followerId);
        return ResponseEntity.ok(follows);
    }
}
