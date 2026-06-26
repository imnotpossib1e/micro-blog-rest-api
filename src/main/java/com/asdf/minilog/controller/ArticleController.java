package com.asdf.minilog.controller;

import com.asdf.minilog.dto.ArticleRequestDto;
import com.asdf.minilog.dto.ArticleResponseDto;
import com.asdf.minilog.service.ArticleService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/article")
public class ArticleController {
    private final ArticleService articleService;

    @Autowired
    public ArticleController(ArticleService articleService) {
        this.articleService = articleService;
    }

    // 개사굴 생성
    @PostMapping // POST 요청
    @Operation(summary = "포스트 생성")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "성공"),
        @ApiResponse(responseCode = "404", description = "사용자 없음")
    })
    public ResponseEntity<ArticleResponseDto> createArticle(
            @RequestBody ArticleRequestDto article) { // 요청 데이터 받기
        // 작성자 ID 추출
        Long userId = article.getAuthorId();
        // 서비스 호출
        ArticleResponseDto createdArticle =
                articleService.createArticle(article.getContent(), userId);
        // 응답 반환
        return ResponseEntity.ok(createdArticle);
    }

    // 특정 게시글 조회
    @GetMapping("/{articleId}") // GET 요청
    @Operation(summary = "포스트 조회")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "성공"),
        @ApiResponse(responseCode = "404", description = "포스트 없음")
    })
    public ResponseEntity<ArticleResponseDto> getArticle(
            @PathVariable Long articleId) { // URL 경로 받기
        // 서비스 호출
        var article = articleService.getArticleById(articleId);
        // 응답 반환
        return ResponseEntity.ok(article);
    }

    // 게시글 수정
    @PutMapping("/{articleId}") // PUT 요청
    @Operation(summary = "포스트 수정")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "성공"),
        @ApiResponse(responseCode = "404", description = "포스트 없음")
    })
    public ResponseEntity<ArticleResponseDto> updateArticle(
            @PathVariable Long articleId,
            @RequestBody ArticleRequestDto article) { // URL 경로, 요청 Body 받기
        // 서비스 계층 호출
        var updatedArticle = articleService.updateArticle(articleId, article.getContent());
        // 응답 반환
        return ResponseEntity.ok(updatedArticle);
    }

    // 게시글 삭제
    @DeleteMapping("/{articleId}") // DELETE 요청
    @Operation(summary = "포스트 삭제")
    @ApiResponses({
        @ApiResponse(responseCode = "204", description = "삭제됨"),
        @ApiResponse(responseCode = "404", description = "포스트 없음")
    })
    public ResponseEntity<Void> deleteArticle(@PathVariable Long articleId) { // URL에서 게시글 id 추출
        // 서비스 계층 호출
        articleService.deleteArticle(articleId);
        // 응답 반환
        return ResponseEntity.noContent().build();
    }

    // 특정 사용자가 작성한 게시글 목록 조회
    @GetMapping // Get 요청 처리
    @Operation(summary = "유저의 게시글 조회")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "성공"),
        @ApiResponse(responseCode = "404", description = "게시글 없음")
    })
    public ResponseEntity<List<ArticleResponseDto>> getArticleByUserId(
            @RequestParam Long authorId) { // URL에서 쿼리 파라미터 받기
        // 서비스 호출
        var articleList = articleService.getArticleListByUserId(authorId);
        // 응답 반환
        return ResponseEntity.ok(articleList);
    }
}
