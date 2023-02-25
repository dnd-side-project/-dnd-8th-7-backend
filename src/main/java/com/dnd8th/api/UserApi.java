package com.dnd8th.api;

import com.dnd8th.dto.user.UserGetDto;
import com.dnd8th.dto.user.UserGetResponse;
import com.dnd8th.entity.User;
import com.dnd8th.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@Slf4j
@RestController
@RequestMapping("/api/user")
@RequiredArgsConstructor
public class UserApi {

    private final UserService userService;

    @DeleteMapping("")
    public ResponseEntity<String> deleteUser(@AuthenticationPrincipal UserDetails userDetails) {
        String email = userDetails.getUsername();

        userService.deleteUser(email);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).body("");
    }

    @GetMapping("")
    public ResponseEntity<UserGetResponse> getUser(
            @AuthenticationPrincipal UserDetails userDetails) {
        String email = userDetails.getUsername();

        User user = userService.getUser(email);
        UserGetResponse userGetResponse =  UserGetResponse.builder()
                .imgPath(user.getImagePath())
                .user(user.getName())
                .introduction(user.getIntroduction())
                .lock(user.getUserLock()).build();
        return ResponseEntity.ok(userGetResponse);
    }

    @PatchMapping("")
    public ResponseEntity<String> patchUser(
            @AuthenticationPrincipal UserDetails userDetails,
            @RequestBody @Valid UserGetResponse userGetResponse) {
        String email = userDetails.getUsername();

        UserGetDto userGetDto = UserGetDto.builder()
                .imgPath(userGetResponse.getImgPath())
                .user(userGetResponse.getUser())
                .introduction(userGetResponse.getIntroduction())
                .lock(userGetResponse.getLock()).build();
        userService.updateUser(email, userGetDto);
        return ResponseEntity.ok("");
    }
}
