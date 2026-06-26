package com.asdf.minilog.exception;

import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler {

    // 사용자 데이터 에러
    @ApiResponses( // Swagger 문서용
            value = {
                @io.swagger.v3.oas.annotations.responses.ApiResponse(
                        responseCode = "404", // 유저 없음
                        description = "User not Fount"),
                @io.swagger.v3.oas.annotations.responses.ApiResponse(
                        responseCode = "400", // 잘못된 요청
                        description = "Bad request"),
                @io.swagger.v3.oas.annotations.responses.ApiResponse(
                        responseCode = "500", // 서버 에러
                        description = "Internal server error")
            })
    @ExceptionHandler(UserNotFoundException.class) // UserNotFoundException이 발생하면 자동으로 여기로 와서 처리한다
    public ResponseEntity<String> handleUserNotFoundException(
            UserNotFoundException ex) { // HTTP 응답 커스터마이징
        return new ResponseEntity<>("해당 사용자를 찾을 수 없습니다.", HttpStatus.NOT_FOUND); // 실제 응답 부분
    }

    // 개사글 예외 처리
    @ApiResponses(
            value = {
                @io.swagger.v3.oas.annotations.responses.ApiResponse(
                        responseCode = "404",
                        description = "Article not Fount"),
                @io.swagger.v3.oas.annotations.responses.ApiResponse(
                        responseCode = "400",
                        description = "Bad request"),
                @io.swagger.v3.oas.annotations.responses.ApiResponse(
                        responseCode = "500",
                        description = "Internal server error")
            })
    @ExceptionHandler(ArticleNotFoundException.class)
    public ResponseEntity<String> handleArticleNotFoundException(ArticleNotFoundException ex) {
        return new ResponseEntity<>("해당 게시글을 찾을 수 없습니다.", HttpStatus.NOT_FOUND);
    }

    // 잘못된 값이 들어왔을 때 자바에서 기본으로 던지는 에러 처리
    @ApiResponses(
            value = {
                @io.swagger.v3.oas.annotations.responses.ApiResponse(
                        responseCode = "400",
                        description = "Bad request"),
                @io.swagger.v3.oas.annotations.responses.ApiResponse(
                        responseCode = "500",
                        description = "Internal server error")
            })
    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<String> handleIllegalArgumentException(IllegalArgumentException ex) {
        return new ResponseEntity<>("잘못된 요청 값입니다.", HttpStatus.BAD_REQUEST);
    }

    // 지금까지 따로 처리 안 된 모든 예외 처리
    @ApiResponses(
            value = {
                @io.swagger.v3.oas.annotations.responses.ApiResponse(
                        responseCode = "500",
                        description = "Internal server error")
            })
    @ExceptionHandler(Exception.class)
    public ResponseEntity<String> handleException(Exception ex) {
        //        return new ResponseEntity<>(ex.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        // 내부 에러 메시지를 그대로 노출하기보다는 따로 적어주는게 좋다
        return new ResponseEntity<>("서버 오류가 발생했습니다", HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
