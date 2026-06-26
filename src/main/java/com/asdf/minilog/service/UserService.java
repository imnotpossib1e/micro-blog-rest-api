package com.asdf.minilog.service;

import com.asdf.minilog.dto.UserRequestDto;
import com.asdf.minilog.dto.UserResponseDto;
import com.asdf.minilog.entity.User;
import com.asdf.minilog.exception.UserNotFoundException;
import com.asdf.minilog.repository.UserRepository;
import com.asdf.minilog.util.EntityDtoMapper;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class UserService {
    // 의존성 주입
    private final UserRepository userRepository;

    // 생성자 주입
    @Autowired
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    // 사용자 조회 메서드
    @Transactional(readOnly = true)
    public List<UserResponseDto> getUsers() {
        return userRepository.findAll().stream() // DB에서 User 전체 조회하여 Stream으로 하나씩 꺼냄
                .map(EntityDtoMapper::toDto) // Entity -> DTO 변환
                .collect(Collectors.toList()); // Stream 결과를 다시 List<UserResponseDto>로 변환
    }

    // id로 사용자 단일 조회
    @Transactional(readOnly = true)
    public Optional<UserResponseDto> getUserById(Long userId) {
        return userRepository
                .findById(userId)
                .map(EntityDtoMapper::toDto); // DB에서 ID로 User 조회해서 DTO로 변환
    }

    // 회원가입 요청
    // 회원가입 요청 DTO를 받아 DB에 유저를 저장하고, 저장 결과를 DTO를 반환하는 메서드
    public UserResponseDto createUser(UserRequestDto userRequestDto) {
        // username으로 중복 사용자 체크
        if (userRepository.findByUsername(userRequestDto.getUsername()).isPresent()) {
            throw new IllegalArgumentException("이미 존재하는 사용자 이름입니다.");
        }

        // 사용자 생성(Entity 생성 + 저장)
        User savedUser =
                userRepository.save( // save()로 DB insert
                        User.builder() // Entity 생성
                                .username(userRequestDto.getUsername()) // username 세팅
                                .password(userRequestDto.getPassword()) // password 세팅
                                .build());

        return EntityDtoMapper.toDto(savedUser); // DTO로 변환해서 반환, 저장된 결과를 savedUser에 저장
    }

    // 사용자 정보 수정
    // userId로 사용자를 찾은 뒤, 요청 DTO 값으로 수정하고 DB에 저장한 결과를 DTO로 반환
    public UserResponseDto updateUser(Long userId, UserRequestDto userRequestDto) {
        // 사용자 조회
        User user =
                userRepository
                        .findById(userId) // DB에서 PK(userId)로 사용자 조회
                        .orElseThrow( // 존재하지 않을 경우 처리
                                () ->
                                        new UserNotFoundException(
                                                String.format(
                                                        "해당 아이디(%d)를 가진 사용자를 찾을 수 없습니다.", userId)));
        // 값 수정 (Entity 변경)
        user.setUsername(userRequestDto.getUsername());
        user.setPassword(userRequestDto.getPassword());

        // 저장 (JPA에서는 이미 영속 상태라 save는 생략 가능할때도 많다)
        var updatedUser = userRepository.save(user);
        // DTO 변환
        return EntityDtoMapper.toDto(updatedUser);
    }

    // 사용자 삭제
    // userId로 사용자를 찾고, 존재하면 DB에서 삭제하는 메서드
    public void deleteUser(Long userId) {
        // 사용자 조회
        User user =
                userRepository
                        .findById(userId)
                        .orElseThrow( // 존재하지 않을 경우 처리
                                () ->
                                        new UserNotFoundException(
                                                String.format(
                                                        "해당 아이디(%d)를 가진 사용자를 찾을 수 없습니다.", userId)));
        // 삭제
        userRepository.deleteById(user.getId());
    }
}
