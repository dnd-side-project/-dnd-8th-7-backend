package com.dnd8th.repository;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import com.dnd8th.dto.review.ReviewCreateRequest;
import com.dnd8th.entity.Review;
import com.dnd8th.entity.User;
import com.dnd8th.error.exception.review.ReviewNotFoundException;
import com.dnd8th.error.exception.user.UserNotFoundException;
import java.util.Date;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class ReviewRepositoryTest extends RepositoryTest {

    @Test
    @DisplayName("정상적으로 리뷰를 생성 및 조회할 수 있다.")
    void createReview() {
        // given
        String userEmail = "test@gmail.com";
        ReviewCreateRequest reviewCreateRequest = ReviewCreateRequest.builder()
                .date("2023-03-01")
                .review("test")
                .emoji("😀")
                .isSecret(false)
                .build();
        // when
        User user = userRepository.findByEmail(userEmail).orElseThrow(UserNotFoundException::new);
        Date date = dateParser.parseDate(reviewCreateRequest.getDate());

        Review save = reviewRepository.save(reviewCreateRequest.toEntity(user, date));
        Long id = save.getId();

        entityManager.flush();
        entityManager.clear();
        // then
        Review review = reviewRepository.findById(id).orElseThrow(ReviewNotFoundException::new);
        assertThat(review.getRetrospection()).isEqualTo("test");
    }

    @Test
    @DisplayName("정상적으로 리뷰를 삭제할 수 있다.")
    void deleteReview() {
        // given
        String userEmail = "test@gmail.com";
        ReviewCreateRequest reviewCreateRequest = ReviewCreateRequest.builder()
                .date("2023-03-01")
                .review("test")
                .emoji("😀")
                .isSecret(false)
                .build();
        // when
        User user = userRepository.findByEmail(userEmail).orElseThrow(UserNotFoundException::new);
        Date date = dateParser.parseDate(reviewCreateRequest.getDate());

        Review save = reviewRepository.save(reviewCreateRequest.toEntity(user, date));
        Long id = save.getId();
        reviewRepository.delete(save);

        //then
        assertThatThrownBy(
                () -> reviewRepository.findById(id).orElseThrow(ReviewNotFoundException::new))
                .isInstanceOf(ReviewNotFoundException.class);
    }
}
