package com.dnd8th.repository;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import com.dnd8th.entity.Role;
import com.dnd8th.entity.User;
import com.dnd8th.error.exception.user.UserNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest
@Transactional
class UserRepositoryTest {

    @Autowired
    private UserRepository userRepository;

    @BeforeEach
    void userSetup() {
        userRepository.save(User.builder()
                .name("test")
                .email("test@gmail.com")
                .role(Role.USER)
                .imagePath("")
                .build());
    }

    @Test
    @DisplayName("email로 유저를 찾는다.")
    void findUserByEmail() {
        //given
        String email = "test@gmail.com";
        //when
        User user = userRepository.findByEmail(email)
                .orElseThrow(UserNotFoundException::new);
        //then
        assertThat(user.getEmail()).isEqualTo("test@gmail.com");
    }

    @Test
    @DisplayName("없는 email로 유저를 찾을 시 예외를 던진다.")
    void findUserByEmailException() {
        //given
        String email = "test1@gmail.com";

        //when && then
        assertThatThrownBy(() -> userRepository.findByEmail(email)
                .orElseThrow(UserNotFoundException::new))
                .isInstanceOf(UserNotFoundException.class);
    }

    @Test
    @DisplayName("email 중복 여부를 확인하기 위해 해당 email 존재 여부를 확인한다.")
    void existsByEmail() {
        //given
        String email = "test@gmail.com";

        //when
        boolean existsByEmail = userRepository.existsByEmail(email);

        //then
        assertThat(existsByEmail).isTrue();
    }
}
