package com.asdf.minilog.entity;

import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;

@Entity
@Table(
        name = "follows",
        indexes = {
                @Index(name = "idx_follower_id", columnList = "follower_id"),
                @Index(name = "idx_followee_id", columnList = "followee_id")
        },
        uniqueConstraints = {@UniqueConstraint(columnNames = {"follower_id", "followee_id"})})
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@EntityListeners(AuditingEntityListener.class)
public class Follow {
        // id
        @Id // 기본 키
        @GeneratedValue(strategy = GenerationType.IDENTITY) // id 값 자동 생성
        private Long id;

        // 누가 나를 팔로우했는지 (나의 팔로워)
        @ManyToOne(fetch = FetchType.LAZY) // 1:N, 여러개의 Follow 데이터가 하나의 User(팔로워) 참조
        @JoinColumn(name = "follower_id", nullable = false) // 외래키 컬럼 생성, follower_id -> user.id
        private User follower;

        // 내가 누구를 팔로우했는지 (나의 팔로잉)
        @ManyToOne(fetch = FetchType.LAZY) // 1:N, 여러개의 follow 데이터가 하나의 User(팔로우 대상) 참조
        @JoinColumn(name = "followee_id", nullable = false) // 외래키 컬럼 생성, followee_id -> user.id
        private User followee;

        // 생성 시간
        @CreatedDate
        @Column(name = "created_at", nullable = false, updatable = false)
        private LocalDateTime createdAt;

        // 수정 시간
        @LastModifiedDate // 수정할때마다 자동 기록
        @Column(name = "updated_at", nullable = false)
        private LocalDateTime updatedAt;
}
