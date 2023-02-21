package com.dnd8th.api;

import com.dnd8th.dto.review.ReviewCreateRequest;
import com.dnd8th.service.ReviewService;
import javax.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("/api/review")
@RequiredArgsConstructor
public class ReviewApi {

    private final ReviewService reviewService;

    @PostMapping("")
    public ResponseEntity<String> createReview(
            @RequestBody @Valid ReviewCreateRequest reviewCreateRequest,
            @AuthenticationPrincipal
            UserDetails userDetails) {
        String userEmail = userDetails.getUsername();
        reviewService.createReview(reviewCreateRequest, userEmail);

        return ResponseEntity.status(HttpStatus.CREATED).body("");
    }
}
