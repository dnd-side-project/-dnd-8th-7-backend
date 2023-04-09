package com.dnd8th.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import com.dnd8th.common.BlockTest;
import com.dnd8th.dto.block.BlockCalendarResponse;
import com.dnd8th.dto.block.BlockCreateRequest;
import com.dnd8th.dto.block.BlockUpdateRequest;
import com.dnd8th.entity.Block;
import com.dnd8th.entity.User;
import com.dnd8th.error.exception.block.BlockAccessDeniedException;
import com.dnd8th.error.exception.block.BlockNotFoundException;
import com.dnd8th.error.exception.user.UserNotFoundException;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.transaction.annotation.Transactional;

@Transactional
public class BlockServiceTest extends BlockTest {

    @Test
    @DisplayName("블럭을 생성할 때 잘못된 이메일을 에러를 낸다.")
    void createBlockReturnErrorIfWrongEmailIsGiven() {
        //given
        String userEmail = "test3@gmail.com";

        //when
        BlockCreateRequest blockCreateRequest = BlockCreateRequest.builder()
                .date("2023-03-04")
                .backgroundColor("#111111")
                .isSecret(true)
                .emoji("😁")
                .title("content")
                .build();

        //then
        assertThatThrownBy(() -> blockService.createBlock(blockCreateRequest, userEmail))
                .isInstanceOf(UserNotFoundException.class);
    }

    @Test
    @DisplayName("블럭을 생성한다.")
    void createBlock() {
        //given
        String userEmail = "test2@gmail.com";

        //when
        BlockCreateRequest blockCreateRequest = BlockCreateRequest.builder()
                .date("2023-03-04")
                .backgroundColor("#111111")
                .isSecret(true)
                .emoji("😁")
                .title("content")
                .build();

        //then
        blockService.createBlock(blockCreateRequest, userEmail);
    }

    @Test
    @DisplayName("블러을 수정할 때, 잘못된 이메일을 에러를 낸다.")
    void updateBlockReturnErrorIfWrongEmailIsGiven() {
        //given
        String userEmail = "test3@gmail.com";
        Long blockId = 1L;

        //when
        BlockUpdateRequest updateRequest = BlockUpdateRequest.builder()
                .backgroundColor("#111111")
                .isSecret(true)
                .emoji("😁")
                .title("content")
                .build();

        //then
        assertThatThrownBy(() -> blockService.updateBlock(updateRequest, userEmail, blockId))
                .isInstanceOf(UserNotFoundException.class);
    }

    @Test
    @DisplayName("블럭을 수정할 때, 잘못된 블럭 아이디를 에러를 낸다.")
    void updateBlockReturnErrorIfWrongBlockIdIsGiven() {
        //given
        String userEmail = "test2@gmail.com";
        Long blockId = 666L;

        //when
        BlockUpdateRequest updateRequest = BlockUpdateRequest.builder()
                .backgroundColor("#111111")
                .isSecret(true)
                .emoji("😁")
                .title("content")
                .build();

        //then
        assertThatThrownBy(() -> blockService.updateBlock(updateRequest, userEmail, blockId))
                .isInstanceOf(BlockNotFoundException.class);

    }

    @Test
    @DisplayName("블럭을 수정할 때, 블럭의 주인이 아닌 경우 오류를 낸다.")
    void updateBlockReturnErrorIfNotOwner() {
        //given
        String ownerEmail = "test2@gmail.com";
        User owner = userRepository.findByEmail(ownerEmail).orElseThrow(UserNotFoundException::new);

        Block block = blockRepository.save(Block.builder()
                .user(owner)
                .title("content")
                .blockLock(true)
                .blockColor("#111111")
                .date(dateParser.parseDate("2023-03-01"))
                .emotion("😁")
                .build()
        );
        long id = block.getId();

        //when
        BlockUpdateRequest blockUpdateRequest = BlockUpdateRequest.builder()
                .backgroundColor("#222222")
                .isSecret(true)
                .emoji("😁")
                .title("content")
                .build();

        //then
        assertThatThrownBy(() -> blockService.updateBlock(blockUpdateRequest, "test@gmail.com", id))
                .isInstanceOf(BlockAccessDeniedException.class);
    }

    @Test
    @DisplayName("블럭을 수정한다.")
    void updateBlock() {
        //given
        String userEmail = "test2@gmail.com";
        User user = userRepository.findByEmail(userEmail).orElseThrow(UserNotFoundException::new);

        //when
        Block block = blockRepository.save(Block.builder()
                .user(user)
                .title("content")
                .blockLock(true)
                .blockColor("#111111")
                .date(dateParser.parseDate("2023-03-01"))
                .emotion("😁")
                .build()
        );
        long id = block.getId();

        BlockUpdateRequest blockUpdateRequest = BlockUpdateRequest.builder()
                .backgroundColor("#222222")
                .isSecret(true)
                .emoji("😁")
                .title("content")
                .build();
        blockService.updateBlock(blockUpdateRequest, userEmail, id);

        //then
        entityManager.flush();
        entityManager.clear();
        Optional<Block> byId = blockRepository.findById(id);
        assertThat(byId.get().getBlockColor()).isEqualTo("#222222");
    }

    @Test
    @DisplayName("블럭을 삭제할 때, 잘못된 이메일을 에러를 낸다.")
    void deleteBlockReturnErrorIfWrongEmailIsGiven() {
        //given
        String userEmail = "test3@gmail.com";
        Long blockId = 1L;

        //when & then
        assertThatThrownBy(() -> blockService.deleteBlock(blockId, userEmail))
                .isInstanceOf(UserNotFoundException.class);
    }

    @Test
    @DisplayName("블럭을 삭제할 때, 잘못된 블럭 아이디를 에러를 낸다.")
    void deleteBlockReturnErrorIfWrongBlockIdIsGiven() {
        //given
        String userEmail = "test2@gmail.com";
        Long blockId = 666L;

        //when & then
        assertThatThrownBy(() -> blockService.deleteBlock(blockId, userEmail))
                .isInstanceOf(BlockNotFoundException.class);
    }

    @Test
    @DisplayName("블럭을 삭제할 때, 블럭의 주인이 아닌 경우 오류를 낸다.")
    void deleteBlockReturnErrorIfNotOwner() {
        //given
        String ownerEmail = "test2@gmail.com";
        User owner = userRepository.findByEmail(ownerEmail).orElseThrow(UserNotFoundException::new);

        Block block = blockRepository.save(Block.builder()
                .user(owner)
                .title("content")
                .blockLock(true)
                .blockColor("#111111")
                .date(dateParser.parseDate("2023-03-01"))
                .emotion("😁")
                .build()
        );
        Long blockId = block.getId();

        //when & then
        assertThatThrownBy(() -> blockService.deleteBlock(blockId, "test@gmail.com"))
                .isInstanceOf(BlockAccessDeniedException.class);
    }

    @Test
    @DisplayName("블럭을 삭제한다.")
    void deleteBlock() {
        //given
        String userEmail = "test2@gmail.com";
        User user = userRepository.findByEmail(userEmail).orElseThrow(UserNotFoundException::new);

        Block block = blockRepository.save(Block.builder()
                .user(user)
                .title("content")
                .blockLock(true)
                .blockColor("#111111")
                .date(dateParser.parseDate("2023-03-01"))
                .emotion("😁")
                .build()
        );
        Long blockId = block.getId();

        //when
        blockService.deleteBlock(block.getId(), userEmail);

        //then
        entityManager.flush();
        entityManager.clear();
        assertThat(blockRepository.findById(blockId)).isEmpty();
    }

    @Test
    @DisplayName("13일 간의 블럭 결과를 가져올 때 잘못된 이메일을 에러를 낸다.")
    void getInA13DayBlocksReturnErrorIfWrongEmailIsGiven() {
        //given
        String userEmail = "wrongTest@gmail.com";
        String date = "2023-03-04";

        //when & then
        assertThatThrownBy(() -> blockService.getBlockWeek(userEmail, date))
                .isInstanceOf(UserNotFoundException.class);
    }

    @Test
    @DisplayName("13일 간의 블럭 결과를 가져온다.")
    void getInA13DayBlocks() {
        //given
        String userEmail = "test@gmail.com";
        String date = "2023-03-04";

        //when
        List<BlockCalendarResponse> blockCalendarResponse = blockService.getBlockWeek(
                userEmail, date
        );

        //then
        assertThat(blockCalendarResponse.size()).isEqualTo(13);
    }

    @Test
    @DisplayName("30일 간의 블럭 결과를 가져올 때 잘못된 이메일을 에러를 낸다.")
    void getInMonthBlocksReturnErrorIfWrongEmailIsGiven() {
        //given
        String userEmail = "wrongTest@gmail.com";
        String date = "2023-03-04";

        //when & then
        assertThatThrownBy(() -> blockService.getBlockMonth(userEmail, date))
                .isInstanceOf(UserNotFoundException.class);
    }

    @Test
    @DisplayName("30일 간의 블럭 결과를 가져온다.")
    void getInAMonthBlocks() {
        //given
        String userEmail = "test@gmail.com";
        String date = "2023-03-04";

        //when
        List<BlockCalendarResponse> blockCalendarResponse = blockService.getBlockMonth(
                userEmail, date
        );

        //then
        assertThat(blockCalendarResponse.size()).isEqualTo(31);
    }
}
