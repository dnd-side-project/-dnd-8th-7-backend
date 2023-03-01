package com.dnd8th.api;


import com.dnd8th.dto.block.*;
import com.dnd8th.dto.keep.KeepBlockResponse;
import com.dnd8th.dto.keep.KeepCreateRequest;
import com.dnd8th.service.BlockService;
import com.dnd8th.service.KeepService;
import java.util.List;
import javax.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("/api/block")
@RequiredArgsConstructor
public class BlockApi {

    private final BlockService blockService;
    private final KeepService keepService;

    @PostMapping("")
    public ResponseEntity<String> createBlock(
            @RequestBody @Valid BlockCreateRequest blockCreateRequest,
            @AuthenticationPrincipal UserDetails userDetails) {
        blockService.createBlock(blockCreateRequest, userDetails.getUsername());
        return ResponseEntity.status(HttpStatus.CREATED).body("");
    }

    @PatchMapping("/{blockId}")
    public ResponseEntity<String> updateBlock(
            @PathVariable Long blockId,
            @RequestBody @Valid BlockUpdateRequest blockUpdateRequest,
            @AuthenticationPrincipal UserDetails userDetails
    ) {
        String email = userDetails.getUsername();
        blockService.updateBlock(blockUpdateRequest, email, blockId);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).body("");
    }

    @GetMapping("/{date}")
    public ResponseEntity
            <BlockMainWeekGetResponse> getMainWeek(@AuthenticationPrincipal UserDetails userDetails,
            @PathVariable("date") String date) {
        String email = userDetails.getUsername();
        BlockMainWeekGetResponse mainWeek = blockService.getBlockWeek(email, date);
        return ResponseEntity.status(HttpStatus.OK).body(mainWeek);
    }

    @GetMapping("/detail/{date}")
    public ResponseEntity
            <BlockMainGetResponse> getMainDetail(@AuthenticationPrincipal UserDetails userDetails,
            @PathVariable("date") String date) {
        String email = userDetails.getUsername();
        BlockMainGetResponse mainDto = blockService.getBlockDetail(email, date);
        return ResponseEntity.status(HttpStatus.OK).body(mainDto);
    }

    @GetMapping("/single/{blockId}")
    public ResponseEntity
            <BlockGetResponse> getBlock(@AuthenticationPrincipal UserDetails userDetails,
                                        @PathVariable("blockId") Long blockId) {
        String email = userDetails.getUsername();
        BlockGetResponse blockGetResponse = blockService.getBlock(email, blockId);
        return ResponseEntity.status(HttpStatus.OK).body(blockGetResponse);
    }

    @DeleteMapping("/{blockId}")
    public ResponseEntity<String> deleteBlock(@PathVariable Long blockId,
            @AuthenticationPrincipal UserDetails userDetails) {
        String email = userDetails.getUsername();
        blockService.deleteBlock(blockId, email);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).body("");
    }

    @PostMapping("/{blockId}/save")
    public ResponseEntity<String> createKeepBlock(
            @PathVariable("blockId") Long blockId,
            @AuthenticationPrincipal UserDetails userDetails) {
        String email = userDetails.getUsername();
        keepService.createKeepBlock(blockId, email);
        return ResponseEntity.status(HttpStatus.CREATED).body("");
    }

    @GetMapping("/save")
    public ResponseEntity<List<KeepBlockResponse>> getKeepBlock(
            @AuthenticationPrincipal UserDetails userDetails) {
        String email = userDetails.getUsername();
        List<KeepBlockResponse> keepBlocks = keepService.getKeepBlockList(email);
        return ResponseEntity.status(HttpStatus.CREATED).body(keepBlocks);
    }

    @DeleteMapping("/{blockId}/save")
    public ResponseEntity<String> deleteKeepBlock(
            @PathVariable("blockId") Long blockId,
            @AuthenticationPrincipal UserDetails userDetails) {
        String email = userDetails.getUsername();
        keepService.deleteKeepBlock(blockId, email);
        return ResponseEntity.status(HttpStatus.CREATED).body("");
    }

    @PostMapping("/load/{date}")
    public ResponseEntity<String> createKeepBlockOnADate(
            @RequestBody @Valid KeepCreateRequest keepCreateRequest,
            @PathVariable("date") String date,
            @AuthenticationPrincipal UserDetails userDetails) {
        String email = userDetails.getUsername();
        keepService.createKeepBlockOnADate(keepCreateRequest.getBlockIds(), email, date);
        return ResponseEntity.status(HttpStatus.CREATED).body("");
    }

}
