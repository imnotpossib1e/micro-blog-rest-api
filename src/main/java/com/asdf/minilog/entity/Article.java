package com.asdf.minilog.entity;

import jakarta.persistence.*;
import java.time.LocalDateTime;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

@Entity
@Table(name = "articles")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EntityListeners(AuditingEntityListener.class)
public class Article {
    // Id
    @Id // 기본 키 지정
    @GeneratedValue(strategy = GenerationType.IDENTITY) // id 값 자동 생성
    private Long id;

    // 게시글
    @Column(columnDefinition = "TEXT") // 긴 문자열용 DB 컬럼 설정
    private String content;

    // 게시글 작성자 정보
    @ManyToOne(fetch = FetchType.LAZY) // N:1
    @JoinColumn(
            name = "author_id",
            nullable = false) // 외래키 컬럼 이름을 author_id로 만든다 (article.author_id -> user.id 연결)
    private User author;

    // 게시글 작성 시간
    @CreatedDate
    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;

    // 수정 시간
    @LastModifiedDate // 엔티티가 수정될 때 마다 자동으로 시간 기록
    @Column(name = "updated_at", nullable = false)
    private LocalDateTime updatedAt;
}
