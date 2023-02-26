package com.dnd8th.api;

import com.dnd8th.dto.review.ReviewCreateRequest;
import com.dnd8th.dto.review.ReviewGetResponse;
import com.dnd8th.dto.review.ReviewUpdateRequest;
import com.dnd8th.service.ReviewService;
import javax.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

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
        //String userEmail = userDetails.getUsername();
        reviewService.createReview(reviewCreateRequest, "harin14@naver.com");

        return ResponseEntity.status(HttpStatus.CREATED).body("");
    }

    @DeleteMapping("/{reviewId}")
    public ResponseEntity<String> deleteReview(
            @PathVariable("reviewId") Long reviewId,
            @AuthenticationPrincipal UserDetails userDetails) {
        String userEmail = userDetails.getUsername();
        reviewService.deleteReview(userEmail, reviewId);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).body("");
    }

    @GetMapping ("/{reviewId}")
    public ResponseEntity<ReviewGetResponse> getReview(
            @PathVariable("reviewId") Long reviewId,
            @AuthenticationPrincipal UserDetails userDetails) {
        String userEmail = userDetails.getUsername();
        ReviewGetResponse reviewGetResponse = reviewService.getReview(userEmail, reviewId);
        return ResponseEntity.status(HttpStatus.OK).body(reviewGetResponse);
    }

    @PatchMapping ("/{reviewId}")
    public ResponseEntity<String> updateReview(
            @RequestBody @Valid ReviewUpdateRequest reviewUpdateRequest,
            @PathVariable("reviewId") Long reviewId,
            @AuthenticationPrincipal UserDetails userDetails) {
        String userEmail = userDetails.getUsername();
        reviewService.updateReview(userEmail, reviewId, reviewUpdateRequest);
        return ResponseEntity.status(HttpStatus.OK).body("");
    }
}
