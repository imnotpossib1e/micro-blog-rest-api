package com.asdf.minilog.service;

import com.asdf.minilog.dto.ArticleResponseDto;
import com.asdf.minilog.entity.Article;
import com.asdf.minilog.entity.User;
import com.asdf.minilog.exception.ArticleNotFoundException;
import com.asdf.minilog.exception.UserNotFoundException;
import com.asdf.minilog.repository.ArticleRepository;
import com.asdf.minilog.repository.UserRepository;
import com.asdf.minilog.util.EntityDtoMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class ArticleService {
    // 의존성 주입
    private final ArticleRepository articleRepository; // 게시글 DB 처리
    private final UserRepository userRepository; // 작성자 조회

    // 생성자 주입
    @Autowired
    public ArticleService(ArticleRepository articleRepository, UserRepository userRepository){
        this.articleRepository = articleRepository;
        this.userRepository = userRepository;
    }

    // 게시글 생성
    // 사용자가 작성한 게시글 내용을 받아 저장하는 메서드
    public ArticleResponseDto createArticle(String content, Long userId){
        // 작성자 조회
        User user =
                userRepository
                        .findById(userId)
                        .orElseThrow( // 없으면 예외 처리(작성자 없으면 게시글 작성 불가)
                                () ->
                                        new UserNotFoundException(
                                                String.format("해당 아이디(%d)를 가진 사용자를 찾을 수 없습니다.", userId)));

        // Article 엔티티 생성
        // content 저장, author에 User 연결
        // 게시글 작성자 = userId 1번 사용자
        Article article = Article.builder().content(content).author(user).build();

        // DB 저장
        Article savedArticle = articleRepository.save(article);

        // DTO 변환
        return EntityDtoMapper.toDto(savedArticle);
    }

    // 게시글 삭제
    public void deleteArticle(Long articleId){
        Article article =
                articleRepository
                        .findById(articleId)
                        .orElseThrow(
                                () ->
                                        new ArticleNotFoundException(
                                                String.format("해당 아이디(%d)를 가진 게시글을 찾을 수 없습니다.", articleId)));

        // 삭제 처리
        articleRepository.deleteById(articleId);
    }

    // 게시글 수정
    // articleId: 수정할 게시글 ID, content: 새로 바꿀 게시글 내용
    public ArticleResponseDto updateArticle(Long articleId, String content){
        // 게시글 조회
        Article article =
                articleRepository
                        .findById(articleId)
                        .orElseThrow( // 없으면 예외 처리(게시글 없으면 수정 불가)
                                () -> new ArticleNotFoundException(
                                        String.format("해당 아이디(%d)를 가진 게시글을 찾을 수 없습니다.", articleId)));

        // 게시글 내용 수정
        article.setContent(content);

        // DB 저장
        Article updatedArticle = articleRepository.save(article);

        // DTO 반환
        return EntityDtoMapper.toDto(updatedArticle);
    }

    // 게시글 단건 조회
    @Transactional(readOnly = true)
    public ArticleResponseDto getArticleById(Long articleId){
        // 게시글 조회
        Article article =
                articleRepository
                        .findById(articleId)
                        .orElseThrow( // 예외 처리 (게시글 없음)
                                () -> new ArticleNotFoundException(
                                        String.format("해당 아이디(%d)를 가진 게시글을 찾을 수 없습니다.", articleId)));

        // DTO 반환
        return EntityDtoMapper.toDto(article);
    }

    // 특정 사용자 피드 목록 조회
    // 이 사용자가 팔로우하는 사람들의 게시글 목록 조회
    @Transactional(readOnly = true)
    public List<ArticleResponseDto> getFeedListByFollowerId(Long userId){
        // 사용자 존재 확인
        User user =
                userRepository
                        .findById(userId)
                        .orElseThrow( // 예외 처리 (존재하지 않는 사용자)
                                () -> new UserNotFoundException(
                                        String.format("해당 아이디(%d)를 가진 사용자를 찾을 수 없습니다.", userId)));

        // 피드 목록 조회 -> 내가 팔로우한 사람들의 게시글 조회
        var feedList = articleRepository.findAllByFollowerId(user.getId());

        // DTO 변환
        return feedList.stream().map(EntityDtoMapper::toDto).toList();
    }


    // 특정 사용자가 작성한 게시글 목록 조회
    // 내가 직접 작성한 글 조회
    @Transactional(readOnly = true)
    public List<ArticleResponseDto> getArticleListByUserId(Long userId){
        // 사용자 존재 확인
        User user =
                userRepository
                        .findById(userId)
                        .orElseThrow( // 예외처리 (존재하지 않는 사용자)
                                () -> new UserNotFoundException(
                                        String.format("해당 아이디(%d)를 가진 사용자를 찾을 수 없습니다.", userId)));

        // 작성 게시글 조회
        var articleList = articleRepository.findAllByAuthorId(user.getId());

        // DTO 반환
        return articleList.stream().map(EntityDtoMapper::toDto).toList();
    }
}
