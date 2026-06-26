package com.asdf.minilog.entity;

import jakarta.persistence.*;
import java.time.LocalDateTime;
import java.util.List;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

@Entity
@Table(name = "users")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EntityListeners(AuditingEntityListener.class)
public class User {
    // id
    @Id // 기본 키
    @GeneratedValue(strategy = GenerationType.IDENTITY) // id 값 자동 생성
    private Long id;

    // 사용자 이름
    @Column(nullable = false, unique = true) // DB 컬럼 설정
    private String username;

    // 비밀번호
    @Column(nullable = false)
    private String password;

    // 생성 날짜
    @CreatedDate
    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;

    // 수정 날짜
    @LastModifiedDate
    @Column(name = "updated_at", nullable = false)
    private LocalDateTime updatedAt;

    // User가 여러개의 Article을 가지는 관계 설정
    @OneToMany( // 1:N
            mappedBy = "author", // 관계의 주인은 Article의 author 필드
            cascade = CascadeType.ALL, // User에 대한 작업이 Article에도 전파 (User 삭제하면 Article도 삭제)
            orphanRemoval = true, // 리스트에서 빠진 Article은 DB에서도 삭제
            fetch = FetchType.LAZY) // articles는 필요할 때만 가져온다
    private List<Article> articles;
}
