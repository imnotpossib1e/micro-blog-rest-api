package com.asdf.minilog.controller;

import com.asdf.minilog.dto.ArticleResponseDto;
import com.asdf.minilog.service.ArticleService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/v1/feed")
public class FeedController {
    private final ArticleService articleService;

    @Autowired
    public FeedController(ArticleController articleController, ArticleService articleService){
        this.articleService = articleService;
    }

    // 피드 조회
    // 특정 사용자가 팔로우한 사람들의 게시글을 모아서 보여준다
    @GetMapping()
    @Operation(summary = "피드 조회")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "성공"),
            @ApiResponse(responseCode = "404", description = "사용자 없음")
    })
    public ResponseEntity<List<ArticleResponseDto>> getFeedList(@RequestParam Long followerId){ // 쿼리 파라미터 받기
        // 서비스 호출
        List<ArticleResponseDto> feedList = articleService.getFeedListByFollowerId(followerId);
        // 응답 반환
        return ResponseEntity.ok(feedList);
    }
}
