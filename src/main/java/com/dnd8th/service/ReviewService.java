package com.dnd8th.service;

import com.dnd8th.dto.review.ReviewCreateRequest;
import com.dnd8th.entity.User;
import com.dnd8th.error.exception.user.UserNotFoundException;
import com.dnd8th.repository.ReviewRepository;
import com.dnd8th.repository.UserRepository;
import com.dnd8th.util.DateParser;
import java.util.Date;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class ReviewService {

    private final ReviewRepository reviewRepository;
    private final UserRepository userRepository;
    private final DateParser dateParser;

    public void createReview(ReviewCreateRequest reviewCreateRequest, String userEmail) {
        User user = userRepository.findByEmail(userEmail).orElseThrow(UserNotFoundException::new);
        Date date = dateParser.parseDate(reviewCreateRequest.getDate());

        reviewRepository.save(reviewCreateRequest.toEntity(user, date));
    }
}
