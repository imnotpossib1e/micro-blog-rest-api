package com.asdf.minilog.service;

import com.asdf.minilog.dto.FollowResponseDto;
import com.asdf.minilog.entity.Follow;
import com.asdf.minilog.entity.User;
import com.asdf.minilog.exception.UserNotFoundException;
import com.asdf.minilog.repository.FollowRepository;
import com.asdf.minilog.repository.UserRepository;
import com.asdf.minilog.util.EntityDtoMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.swing.text.html.Option;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class FollowService {

    // 의존성 주입
    private final FollowRepository followRepository;
    private final UserRepository userRepository;

    // 생성자 주입
    @Autowired
    public FollowService(FollowRepository followRepository, UserRepository userRepository){
        this.followRepository = followRepository;
        this.userRepository = userRepository;
    }

    // 팔로우
    // follower가 followee를 팔로우하는 로직
    public FollowResponseDto follow(Long followerId, Long followeeId){
        // 본인 팔로우 방지
        if(followerId.equals(followeeId)){
            throw new IllegalArgumentException("자신을 팔로우할 수 없습니다.");
        }

        // 팔로워 조회 (팔로우하는 사람 찾기)
        User follower =
                userRepository
                        .findById(followerId)
                        .orElseThrow(
                                () -> new UserNotFoundException(
                                        String.format("팔로워 아이디(%d)를 가진 사용자를 찾을 수 없습니다.", followerId)));

        // 팔로잉 대상 조회
        User followee =
                userRepository
                        .findById(followeeId)
                        .orElseThrow(
                                () ->
                                        new UserNotFoundException(
                                                String.format("팔로잉 아이디(%d)를 가진 사용자를 찾을 수 없습니다.", followeeId)));

        // Follow 엔티티 생성 후 저장
        Follow follow =
                followRepository.save(EntityDtoMapper.toEntity(follower.getId(), followee.getId()));

        // DTO 변환 후 반환
        return EntityDtoMapper.toDto(follow);
    }

    // 언팔로우
    public void unfollow (Long followerId, Long followeeId){
        // follow 관계 찾기
        Follow follow =
                        followRepository
                                .findByFollowerIdAndFolloweeId(followerId, followeeId)
                                .orElseThrow(
                                        () -> new UserNotFoundException(
                                                String.format(
                                                        "팔로워(%d)와 팔로잉(%d)를 연결하는 Follow를 찾을 수 없습니다.", followerId, followeeId)));

        // 삭제
        followRepository.delete(follow);
    }

    // 특정 사용자가 팔로우하고 있는 목록(팔로잉 리스트) 조회
    @Transactional(readOnly = true)
    public List<FollowResponseDto> getFollowList(Long userId){
        // 사용자 존재 확인
        if(userRepository.findById(userId).isEmpty()){
            throw new UserNotFoundException(String.format("해당 아이디(%d)를 가진 사용자를 찾을 수 없습니다.", userId));
        }

        // 팔로우 목록 조회(find) 후 DTO 변환(strema, map, toList)
        return followRepository.findByFollowerId(userId).stream().map(EntityDtoMapper::toDto).toList();
    }
}
