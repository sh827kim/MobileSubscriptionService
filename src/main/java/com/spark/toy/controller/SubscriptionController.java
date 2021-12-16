package com.spark.toy.controller;

import com.spark.toy.dto.SubscriptionDto;
import com.spark.toy.dto.SubscriptionRequestDto;
import com.spark.toy.service.SubscriptionService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/subscriptions")
public class SubscriptionController {

    private final SubscriptionService subscriptionService;

    private static final Integer DEFAULT_PAGE_SIZE = 10;

    private static final Boolean DEFAULT_IS_ACTIVATED = false;


    @GetMapping("/all")
    public ResponseEntity<List<SubscriptionRequestDto>> getSubscriptionRequests(@RequestParam int pageNo, @RequestParam(required = false) Integer pageSize, @RequestParam(required = false) Boolean isActivated) {
        pageSize = pageSize==null ? DEFAULT_PAGE_SIZE : pageSize;
        isActivated = isActivated==null ? DEFAULT_IS_ACTIVATED : isActivated;

        List<SubscriptionRequestDto> subscriptionRequestDtos = subscriptionService.getSubscriptionRequestsWithPageNumber(pageNo, pageSize, isActivated);

        return ResponseEntity.ok(subscriptionRequestDtos);
    }

    @PostMapping
    public ResponseEntity<SubscriptionDto> createSubscription(@RequestBody SubscriptionDto subscriptionDto) {
        SubscriptionDto subscriptionDtoResponse = subscriptionService.createSubscription(subscriptionDto);

        return ResponseEntity.ok(subscriptionDtoResponse);
    }

//    @PostMapping
//    public ResponseEntity<SubscriptionRequestDto> createSubscriptionRequest(@RequestBody SubscriptionRequestDto subscriptionRequestDto) {
//        SubscriptionRequestDto subscriptionRequestDtoResponse = subscriptionService.createSubscriptionRequest(subscriptionRequestDto);
//
//        return ResponseEntity.ok(subscriptionRequestDtoResponse);
//    }

    @PutMapping
    public ResponseEntity<SubscriptionRequestDto> updateSubscriptionRequest(@RequestBody SubscriptionRequestDto subscriptionRequestDto) {
        SubscriptionRequestDto subscriptionRequestDtoResponse = subscriptionService.updateSubscriptionRequest(subscriptionRequestDto);

        return ResponseEntity.ok(subscriptionRequestDtoResponse);
    }
}
