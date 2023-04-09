package com.dnd8th.dao.block;

import static org.assertj.core.api.Assertions.assertThat;

import com.dnd8th.common.BlockTest;
import com.dnd8th.dto.block.BlockUpdateRequest;
import com.dnd8th.entity.Block;
import com.dnd8th.entity.User;
import com.dnd8th.error.exception.block.BlockNotFoundException;
import com.dnd8th.error.exception.user.UserNotFoundException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class BlockUpdateDaoTest extends BlockTest {

    @Test
    @DisplayName("블록을 정상적으로 수정한다.")
    void updateBlock() {
        String userEmail = "test2@gmail.com";
        User user = userRepository.findByEmail(userEmail).orElseThrow(UserNotFoundException::new);
        Block block = blockRepository.save(Block.builder()
                .title("test")
                .emotion("😁")
                .blockColor("#111111")
                .blockLock(false)
                .user(user)
                .build()
        );
        long blockId = block.getId();

        blockUpdateDao.updateBlock(blockId, BlockUpdateRequest.builder()
                .title("test2")
                .emoji("😁")
                .backgroundColor("#222222")
                .isSecret(true)
                .build()
        );
        entityManager.flush();
        entityManager.clear();

        Block findBlock = blockRepository.findById(blockId).orElseThrow(
                BlockNotFoundException::new);

        assertThat(findBlock.getTitle()).isEqualTo("test2");
        assertThat(findBlock.getEmotion()).isEqualTo("😁");
        assertThat(findBlock.getBlockColor()).isEqualTo("#222222");
        assertThat(findBlock.getBlockLock()).isEqualTo(true);
    }
}
