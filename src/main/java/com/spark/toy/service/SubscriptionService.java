package com.spark.toy.service;

import com.spark.toy.domain.Customer;
import com.spark.toy.domain.Subscription;
import com.spark.toy.domain.SubscriptionRequest;
import com.spark.toy.domain.enums.SubscriptionCode;
import com.spark.toy.dto.SubscriptionDto;
import com.spark.toy.dto.SubscriptionRequestDto;
import com.spark.toy.repository.CustomerRepository;
import com.spark.toy.repository.SubscriptionRepository;
import com.spark.toy.repository.SubscriptionRequestRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class SubscriptionService {

    private final SubscriptionRepository subscriptionRepository;

    private final SubscriptionRequestRepository subscriptionRequestRepository;

    private final CustomerRepository customerRepository;


    public List<SubscriptionDto> getSubscriptions() {
        List<Subscription> subscriptions = subscriptionRepository.findAll();

        return entitySubscriptionToDtos(subscriptions);
    }

    public List<SubscriptionDto> getSubscriptionsWithIsProceeded(Boolean isProceeded) {
        if(isProceeded==null) {
            return getSubscriptions();
        }
        List<SubscriptionRequest> subscriptionRequests = subscriptionRequestRepository.findByIsProceeded(isProceeded);

        List<Subscription> subscriptions = subscriptionRequests.stream().map(dto -> dto.getSubscription()).collect(Collectors.toList());

        return entitySubscriptionToDtos(subscriptions);
    }

    public List<SubscriptionRequestDto> getSupscriptionRequests() {
        List<SubscriptionRequest> subscriptionRequests = subscriptionRequestRepository.findAll();

        return entitySubscriptionRequestsToDtos(subscriptionRequests);
    }

    public List<SubscriptionRequestDto> getSubscriptionRequestsWithPageNumber(int pageNo, int pageSize, Boolean isActivated) {
        Page<SubscriptionRequest> subscriptionRequests = subscriptionRequestRepository.findByIsProceededFalseOrIsProceeded(PageRequest.of(pageNo, pageSize), isActivated);

        return entitySubscriptionRequestsToDtos(subscriptionRequests.toList());
    }

    @Transactional
    public SubscriptionDto createSubscription(SubscriptionDto subscriptionDto) {
        Subscription subscription = SubscriptionDto.toEntity(subscriptionDto);

        Customer customer = customerRepository.findByAccount(subscriptionDto.getCustomerAccount()).orElseThrow(RuntimeException::new);

        subscription.setSubscriptionCode(SubscriptionCode.UNOPENED);
        subscription.setCustomer(customer);

        SubscriptionRequest subscriptionRequest = new SubscriptionRequest();
        subscriptionRequest.setIsProceeded(false);
        subscriptionRequest.setRequestedAt(LocalDateTime.now());
        subscriptionRequest.setRequestSubscriptionCode(SubscriptionCode.OPENED);

        subscription.setSubscriptionRequest(null);

        subscription = subscriptionRepository.save(subscription);

        subscriptionRequest.setSubscription(subscription);

        subscriptionRequest = subscriptionRequestRepository.save(subscriptionRequest);

        subscription.setSubscriptionRequest(subscriptionRequest);

        subscription = subscriptionRepository.save(subscription);

        customer.addCustomerSubsciptions(subscription);

        customerRepository.save(customer);
        subscription = subscriptionRepository.findById(subscription.getId()).orElseThrow(RuntimeException::new);

        return SubscriptionDto.toDto(subscription);
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
    private List<SubscriptionDto> entitySubscriptionToDtos(List<Subscription> subscriptions) {
        return subscriptions.stream().map(SubscriptionDto::toDto).collect(Collectors.toList());
    }
    private List<SubscriptionRequestDto> entitySubscriptionRequestsToDtos(List<SubscriptionRequest> subscriptionRequests) {
        return subscriptionRequests.stream().map(SubscriptionRequestDto::toDto).collect(Collectors.toList());
    }
}
