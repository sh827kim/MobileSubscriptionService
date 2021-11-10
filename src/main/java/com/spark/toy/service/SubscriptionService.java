package com.spark.toy.service;

import com.spark.toy.domain.Subscription;
import com.spark.toy.domain.SubscriptionRequest;
import com.spark.toy.dto.SubscriptionRequestDto;
import com.spark.toy.repository.SubscriptionRepository;
import com.spark.toy.repository.SubscriptionRequestRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class SubscriptionService {

    private final SubscriptionRepository subscriptionRepository;

    private final SubscriptionRequestRepository subscriptionRequestRepository;


    public List<SubscriptionRequestDto> getSubscriptionRequestsWithPageNumber(int pageNo, int pageSize, Boolean isActivated) {
        Page<SubscriptionRequest> subscriptionRequests = subscriptionRequestRepository.findByIsProceededFalseOrIsProceeded(PageRequest.of(pageNo, pageSize), isActivated);

        return entitySubscriptionRequestsToDtos(subscriptionRequests.toList());
    }

    public SubscriptionRequestDto createSubscriptionRequest(SubscriptionRequestDto subscriptionRequestDto) {
        SubscriptionRequest subscriptionRequest = SubscriptionRequestDto.toEntity(subscriptionRequestDto);
        Subscription subscription = subscriptionRequest.getSubscription();
        subscription = subscriptionRepository.save(subscription);

        subscriptionRequest.setSubscription(subscription);
        subscriptionRequest = subscriptionRequestRepository.save(subscriptionRequest);

        return SubscriptionRequestDto.toDto(subscriptionRequest);
    }

    public SubscriptionRequestDto updateSubscriptionRequest(SubscriptionRequestDto subscriptionRequestDto) {
        SubscriptionRequest subscriptionRequest = SubscriptionRequestDto.toEntity(subscriptionRequestDto);
        Subscription subscription = subscriptionRequest.getSubscription();
        Subscription oldSubscription = subscriptionRepository.findByPhoneNumber(subscription.getPhoneNumber()).orElseThrow(RuntimeException::new);

        Long subscriptionRequestId = subscriptionRequestRepository.findBySubscription(oldSubscription).orElseThrow(RuntimeException::new).getId();
        subscriptionRequest.setId(subscriptionRequestId);

        subscription.setId(oldSubscription.getId());
        subscriptionRequest.setSubscription(subscriptionRepository.save(subscription));

        return SubscriptionRequestDto.toDto(subscriptionRequestRepository.save(subscriptionRequest));
    }

    private List<SubscriptionRequestDto> entitySubscriptionRequestsToDtos(List<SubscriptionRequest> subscriptionRequests) {
        return subscriptionRequests.stream().map(SubscriptionRequestDto::toDto).collect(Collectors.toList());
    }
}
