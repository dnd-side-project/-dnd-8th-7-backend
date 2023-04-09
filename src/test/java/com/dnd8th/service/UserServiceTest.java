package com.dnd8th.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import com.dnd8th.common.UserTest;
import com.dnd8th.dto.auth.UserLoginRequest;
import com.dnd8th.error.exception.auth.EmailDuplicateException;
import com.dnd8th.error.exception.user.UserNotFoundException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class UserServiceTest extends UserTest {

    @Test
    @DisplayName("이메일로 유저를 찾는다.")
    void checkUserByEmail() {
        // given
        String userEmail = "test@gmail.com";

        // when
        boolean existsByEmail = userService.existsByEmail(userEmail);

        // then
        assertThat(existsByEmail).isTrue();
    }

    @Test
    @DisplayName("최초 로그인시 회원가입을 하며 중복된 이메일이 있으면 예외를 던진다.")
    void signUp() {
        // given
        String userEmail = "test@gmail.com";

        //when
        UserLoginRequest userLoginRequest = UserLoginRequest.builder()
                .email(userEmail)
                .nickname("test")
                .imageUrl("")
                .build();
        //then
        assertThatThrownBy(() -> userService.signUp(userLoginRequest))
                .isInstanceOf(EmailDuplicateException.class);
    }

    @Test
    @DisplayName("유저를 삭제한다. 이 때 존재하지 않는 유저를 삭제하면 예외를 던진다.")
    void deleteUser() {
        // given
        String userEmail = "test4@gmail.com";

        //when & then
        assertThatThrownBy(() -> userService.deleteUser(userEmail))
                .isInstanceOf(UserNotFoundException.class);
    }

    @Test
    @DisplayName("유저를 삭제한다. 이 때 존재하는 유저를 삭제하면 삭제된다.")
    void deleteUser2() {
        // given
        String userEmail = "test9@gmail.com";

        //when
        userService.deleteUser(userEmail);
        entityManager.flush();
        entityManager.clear();

        //then
        assertThat(userService.existsByEmail(userEmail)).isFalse();
    }
}
