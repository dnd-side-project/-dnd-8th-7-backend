package com.dnd8th.repository;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import com.dnd8th.dto.block.BlockCreateRequest;
import com.dnd8th.entity.Block;
import com.dnd8th.entity.User;
import com.dnd8th.error.exception.block.BlockNotFoundException;
import com.dnd8th.error.exception.user.UserNotFoundException;
import java.util.Date;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class BlockRepositoryTest extends RepositoryTest {

    @Test
    @DisplayName("정상적으로 블록을 생성할 수 있다.")
    void createBlock() {
        // given
        BlockCreateRequest blockCreateRequest = BlockCreateRequest.builder()
                .date("2023-03-01")
                .title("test")
                .emoji("😀")
                .backgroundColor("#ffffff")
                .isSecret(false)
                .build();

        User user = userRepository.findByEmail("test@gmail.com")
                .orElseThrow(UserNotFoundException::new);

        Date date = dateParser.parseDate(blockCreateRequest.getDate());

        Block block = blockCreateRequest.toEntity(user, date);

        // when
        Block save = blockRepository.save(block);

        // then
        assertThat(save).isEqualTo(block);
        assertThat(save.getId()).isNotNull();
    }

    @Test
    @DisplayName("정상적으로 블록을 조회할 수 있다.")
    void getBlock() {
        // given
        BlockCreateRequest blockCreateRequest = BlockCreateRequest.builder()
                .date("2023-03-01")
                .title("test")
                .emoji("😀")
                .backgroundColor("#ffffff")
                .isSecret(false)
                .build();

        User user = userRepository.findByEmail("test@gmail.com")
                .orElseThrow(UserNotFoundException::new);

        Date date = dateParser.parseDate(blockCreateRequest.getDate());

        Block block = blockCreateRequest.toEntity(user, date);

        Block save = blockRepository.save(block);
        long saveId = save.getId();

        //when
        entityManager.flush();
        entityManager.clear();

        //then
        Block block1 = blockRepository.findById(saveId).orElseThrow(BlockNotFoundException::new);
        assertThat(block1.getBlockColor()).isEqualTo("#ffffff");
        assertThat(block1.getTitle()).isEqualTo("test");
    }

    @Test
    @DisplayName("블록이 없는 경우 예외를 반환한다.")
    void getBlockException() {
        //given&when&then
        assertThatThrownBy(
                () -> blockRepository.findById(1L).orElseThrow(BlockNotFoundException::new))
                .isInstanceOf(BlockNotFoundException.class);
    }

    @Test
    @DisplayName("블록을 정상적으로 삭제할 수 있다.")
    void deleteBlock() {
        // given
        BlockCreateRequest blockCreateRequest = BlockCreateRequest.builder()
                .date("2023-03-01")
                .title("test")
                .emoji("😀")
                .backgroundColor("#ffffff")
                .isSecret(false)
                .build();

        User user = userRepository.findByEmail("test@gmail.com")
                .orElseThrow(UserNotFoundException::new);

        Date date = dateParser.parseDate(blockCreateRequest.getDate());

        Block block = blockCreateRequest.toEntity(user, date);

        Block save = blockRepository.save(block);
        long saveId = save.getId();

        //when
        blockRepository.delete(save);

        //then
        assertThatThrownBy(
                () -> blockRepository.findById(saveId).orElseThrow(BlockNotFoundException::new))
                .isInstanceOf(BlockNotFoundException.class);

    }
}
