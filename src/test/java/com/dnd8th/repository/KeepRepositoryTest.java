package com.dnd8th.repository;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import com.dnd8th.dto.block.BlockCreateRequest;
import com.dnd8th.entity.Block;
import com.dnd8th.entity.Keep;
import com.dnd8th.entity.User;
import com.dnd8th.error.exception.keep.KeepNotFoundException;
import com.dnd8th.error.exception.user.UserNotFoundException;
import java.util.Date;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class KeepRepositoryTest extends RepositoryTest {

    @Test
    @DisplayName("Keep을 정상적으로 생성할 수 있다.")
    void createKeep() {
        // given
        User user = userRepository.findByEmail("test@gmail.com")
                .orElseThrow(UserNotFoundException::new);

        BlockCreateRequest blockCreateRequest = BlockCreateRequest.builder()
                .date("2023-03-01")
                .title("test")
                .emoji("😀")
                .backgroundColor("#ffffff")
                .isSecret(false)
                .build();

        Date date = dateParser.parseDate(blockCreateRequest.getDate());

        Block block = blockCreateRequest.toEntity(user, date);
        blockRepository.save(block);
        // when

        Keep keep = Keep.builder()
                .block(block)
                .user(user)
                .blockColor(block.getBlockColor())
                .emotion(block.getEmotion())
                .title(block.getTitle())
                .sumOfTask(0).build();

        Keep save = keepRepository.save(keep);

        // then
        assertThat(save.getId()).isNotNull();
    }

    @Test
    @DisplayName("Keep을 정상적으로 조회할 수 있다.")
    void getKeep() {
        // given
        User user = userRepository.findByEmail("test@gmail.com")
                .orElseThrow(UserNotFoundException::new);

        BlockCreateRequest blockCreateRequest = BlockCreateRequest.builder()
                .date("2023-03-01")
                .title("test")
                .emoji("😀")
                .backgroundColor("#ffffff")
                .isSecret(false)
                .build();

        Date date = dateParser.parseDate(blockCreateRequest.getDate());

        Block block = blockCreateRequest.toEntity(user, date);
        blockRepository.save(block);

        Keep keep = Keep.builder()
                .block(block)
                .user(user)
                .blockColor(block.getBlockColor())
                .emotion(block.getEmotion())
                .title(block.getTitle())
                .sumOfTask(0).build();

        Keep save = keepRepository.save(keep);

        long id = save.getId();
        // when
        entityManager.flush();
        entityManager.clear();

        Keep findKeep = keepRepository.findById(id)
                .orElseThrow(KeepNotFoundException::new);
        // then
        assertThat(findKeep).isNotNull();
    }

    @Test
    @DisplayName("Keep을 정상적으로 삭제할 수 있다.")
    void deleteKeep() {
        // given
        User user = userRepository.findByEmail("test@gmail.com")
                .orElseThrow(UserNotFoundException::new);

        BlockCreateRequest blockCreateRequest = BlockCreateRequest.builder()
                .date("2023-03-01")
                .title("test")
                .emoji("😀")
                .backgroundColor("#ffffff")
                .isSecret(false)
                .build();

        Date date = dateParser.parseDate(blockCreateRequest.getDate());

        Block block = blockCreateRequest.toEntity(user, date);
        blockRepository.save(block);

        Keep keep = Keep.builder()
                .block(block)
                .user(user)
                .blockColor(block.getBlockColor())
                .emotion(block.getEmotion())
                .title(block.getTitle())
                .sumOfTask(0).build();

        Keep save = keepRepository.save(keep);

        long id = save.getId();
        // when
        keepRepository.deleteById(id);

        // then
        assertThatThrownBy(() -> keepRepository.findById(id)
                .orElseThrow(KeepNotFoundException::new))
                .isInstanceOf(KeepNotFoundException.class);
    }
}
