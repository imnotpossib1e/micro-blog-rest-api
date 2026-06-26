package com.asdf.minilog.controller;

import com.asdf.minilog.dto.UserRequestDto;
import com.asdf.minilog.dto.UserResponseDto;
import com.asdf.minilog.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/api/v1/user")
public class UserController {
    // 의존성 주입
    private final UserService userService;

    // 생성자 주입 (UserController -> UserService 연결)
    @Autowired
    public UserController(UserService userService){
        this.userService = userService;
    }

    // 전체 사용자 조회
    @GetMapping // HTTP GET 요청 처리
    @Operation(summary = "전체 사용자 조회") // Swagger 문서화
    @ApiResponses({@ApiResponse(responseCode = "200", description = "성공")}) // 응답 코드 문서화
    public ResponseEntity<Iterable<UserResponseDto>> getUsers() {
        return ResponseEntity.ok(userService.getUsers());
    }

    // 사용자 단일 조회
    @GetMapping("/{userId}") // HTTP GET /user/1 요청 처리
    @Operation(summary = "사용자 조회") // Swagger 문서화
    @ApiResponses({ // 응답 코드 문서화
            @ApiResponse(responseCode = "200", description = "성공"),
            @ApiResponse(responseCode = "404", description = "사용자 없음")
    })
    public ResponseEntity<UserResponseDto> getUserById(@PathVariable Long userId){ // URL 값을 변수로 받음
        // 서비스에서 사용자 조회
        Optional<UserResponseDto> user = userService.getUserById(userId);

        // 사용자 있으면 200ok, 없으면 404
        // Optional 안에 값 있으면 꺼내서 ResponseEntity로 감싸라
        // user.map(userDto -> ResponseEntity.ok(userDto)) 의 축약형
        return user.map(ResponseEntity::ok).orElseGet(() ->
                ResponseEntity.notFound().build());
    }

    // 사용자 생성
    @PostMapping // HTTP POST 요청을 받음
    @Operation(summary = "사용자 생성") // Swagger 문서화
    @ApiResponses({@ApiResponse(responseCode = "200", description = "성공")}) // 응답 코드 생성
    public ResponseEntity<UserResponseDto> createUser(@RequestBody UserRequestDto user){ // 요청 데이터 받기
        UserResponseDto createUser = userService.createUser(user); // 서비스 호출
        return ResponseEntity.ok(createUser); // 응답 변환
    }

    // 사용자 정보 수정 API
    @PutMapping("/{userId}") // Put 요청 처리
    @Operation(summary = "사용자 수정")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "성공"),
            @ApiResponse(responseCode = "404", description = "사용자 없음")
    })
    public ResponseEntity<UserResponseDto> updateUser(
            @PathVariable Long userId, @RequestBody UserRequestDto updatedUser) { // URL 경로 값, RequestBody 받기
        // 서비스 호출
        UserResponseDto user = userService.updateUser(userId, updatedUser);
        return ResponseEntity.ok(user); // 응답 반환
    }

    // 사용자 삭제
    @DeleteMapping("/{userId}")
    @Operation(summary = "사용자 삭제")
    @ApiResponses({
            @ApiResponse(responseCode = "204", description = "성공"),
            @ApiResponse(responseCode = "404", description = "사용자 없음")
    })
    public ResponseEntity<Void> deleteUser(@PathVariable Long userId){ // URL 경로 값 가져오기
        userService.deleteUser(userId); // 서비스 호출
        return ResponseEntity.noContent().build(); // 응답 반환(body 없음)
    }
}
