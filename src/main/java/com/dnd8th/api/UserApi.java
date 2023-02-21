package com.dnd8th.api;

import com.dnd8th.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("/api/user")
@RequiredArgsConstructor
public class UserApi {

    private final UserService userService;

    @GetMapping("/info")
    public ResponseEntity<String> getUserInfo(@AuthenticationPrincipal UserDetails userDetails) {
        String email = userDetails.getUsername();

        return ResponseEntity.ok(email);
    }
}
